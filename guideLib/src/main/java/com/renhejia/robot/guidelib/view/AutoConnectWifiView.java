package com.renhejia.robot.guidelib.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.renhejia.robot.commandlib.log.LogUtils;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.renhejia.robot.guidelib.R;
import com.renhejia.robot.guidelib.ble.callback.BleConnectStatusCallback;
import com.renhejia.robot.guidelib.manager.LTPGuideConfigManager;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.utils.ViewUtils;
import com.renhejia.robot.guidelib.wifi.WIFIAutoConnectionService;
import com.renhejia.robot.guidelib.wifi.WIFIStateReceiver;
import com.renhejia.robot.guidelib.wifi.callback.GuideWifiConnectCallback;

import java.lang.ref.WeakReference;

/**
 * 自动联网页面
 *
 * @author liujunbin
 */
public class AutoConnectWifiView extends RelativeLayout {
    private Context mContext;
    private AnimationDrawable animationDrawable;
    private RelativeLayout root;
    private ImageView ivWifi;
    private TextView tvWifiName;
    private TextView tvWifiStatus;
    private String wifiName = "";
    private String wifiPassword = "";
    private WIFIStateReceiver mWIFIStateReceiver;
    private boolean isConnected = false;
    private LottieAnimationView mLottieAnimationView;
    private static final String CONNECTING = "connectwifi/wifi_connecting.json";
    private static final String CONNECT_SUCCESS = "connectwifi/connect_wifi_success.json";
    private static final String CONNECT_FAILED = "connectwifi/sonconnect_wifi_failed.json";
    private static final int CHANGE_LOTTIE = 1;
    private String currentLottie;
    private Handler mHandler;

    public AutoConnectWifiView(Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        unregisterWIFIStateReceiver();
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(mContext, R.layout.robot_guide_connect_wifi_view, this);
        initView();
        resizeView();
        showWifiConnectAnimation();
        addListeners();
        registerCallback();
        registerBleUpdateCallback();
//        registerWIFIStateReceiver();
    }

    private void registerBleUpdateCallback() {
        BleConnectStatusCallback.getInstance().registerBleConnectStatusListener(new BleConnectStatusCallback.BleConnectStatusChangedListener() {
            @Override
            public void onBleConnectStatusChanged(int connectStatus) {
                if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED) {
                    updateAnimation(CONNECT_FAILED, false, BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED);

                } else if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTING_NET) {
                    updateAnimation(CONNECTING, true, BleConnectStatusCallback.BLE_STATUS_CONNECTING_NET);

                } else if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT) {
                    updateAnimation(CONNECTING, true, BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT);
                }

            }
        });
    }

//    public void registerWIFIStateReceiver() {
//        if (mWIFIStateReceiver == null) {
//            mWIFIStateReceiver = new WIFIStateReceiver(mContext);
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//            filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
//            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//            mContext.registerReceiver(mWIFIStateReceiver, filter);
//        }
//    }
//
//    public void unregisterWIFIStateReceiver() {
//        if (mWIFIStateReceiver != null) {
//            mContext.unregisterReceiver(mWIFIStateReceiver);
//        }
//
//    }

    private void resizeView() {
        int width = ViewUtils.getViewWidthSize(mContext, ((Activity) mContext).getWindow());
        int iconWidth = (150 * width) / 480;
        ViewUtils.resizeImageViewSize(ivWifi, iconWidth, iconWidth);
    }

    //注册网络回调信息
    private void registerCallback() {
        GuideWifiConnectCallback.getInstance().setChangingStatusUpdateListener(new GuideWifiConnectCallback.NetworkChangingUpdateListener() {
            @Override
            public void onNetworkChargingUpdateReceived(int networkType, boolean networkStatus) {
                LogUtils.logi("auto_connect", "networkType: " + networkStatus);
                LogUtils.logi("auto_connect", "networkStatus: " + networkStatus);
                if ((networkType == GuideWifiConnectCallback.NETWORK_TYPE_WIFI) && (networkStatus == true)) {

                    tvWifiName.setText(wifiName);
                    tvWifiStatus.setText(R.string.connect_success);

                    updateAnimation(CONNECT_SUCCESS, false, BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS);
                    isConnected = true;

                }
            }

            @Override
            public void onWifiInfoChanged(String ssid, String password) {
                wifiName = ssid;
            }
        });
    }

    private void playLottieAnimation(String animation, boolean loop) {
        mLottieAnimationView.setAnimation(animation);
        mLottieAnimationView.loop(loop);
        currentLottie = animation;
        mLottieAnimationView.playAnimation();

    }

    private void addListeners() {
        root.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLottieAnimationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        mLottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (currentLottie.equals(CONNECT_SUCCESS)) {
                    //关闭Activity
                    BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_ANIMATION_PLAY_END);
//                    BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS);

                } else if (currentLottie.equals(CONNECT_FAILED)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT);
                        }
                    }, 1000);

                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void startConnectWifi() {
        tvWifiName.setText(wifiName);
        tvWifiStatus.setText(R.string.connecting_wifi);
        LogUtils.logi("auto_connect", "onClick_1");
        WIFIAutoConnectionService.start(mContext, wifiName, wifiPassword);
    }

    private void initView() {
        mHandler = new AnimationHandler(mContext);
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.wifi_animlist);
        root = findViewById(R.id.guide_root);
        ivWifi = findViewById(R.id.iv_wifi);
        tvWifiName = findViewById(R.id.tv_wifi_name);
        tvWifiStatus = findViewById(R.id.tv_wifi_status);

        mLottieAnimationView = findViewById(R.id.lav_view);
//        playLottieAnimation(CONNECTING,true);
        updateAnimation(CONNECTING, true, BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT);
        //showWifiConnectAnimation();
    }

    public AutoConnectWifiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoConnectWifiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AutoConnectWifiView(Context context, AttributeSet attrs, int defStyleAttr,
                               int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void showWifiConnectAnimation() {

        if (animationDrawable != null) {
            animationDrawable.setOneShot(false);
            ivWifi.setBackground(animationDrawable);
            animationDrawable.start();
        }

    }

    private void updateAnimation(String animation, boolean loop, int status) {

        mHandler.removeMessages(CHANGE_LOTTIE); //TODO 111
        Message message = new Message();
        message.what = CHANGE_LOTTIE;
        message.obj = animation;
        if (loop) {
            message.arg1 = 2;
        } else {
            message.arg1 = 0;
        }
        message.arg2 = status;
        mHandler.sendMessage(message);
    }

    private class AnimationHandler extends android.os.Handler {
        private final WeakReference<Context> context;

        public AnimationHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == CHANGE_LOTTIE) {
                if (context != null && context.get() != null) {
                    if (msg.arg1 == 2) {
                        playLottieAnimation((String) msg.obj, true);
                    } else {
                        playLottieAnimation((String) msg.obj, false);
                    }
                    if (msg.arg2 == BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT) {
                        tvWifiStatus.setText(mContext.getText(R.string.wait_connect_wifi) + "\n" + mContext.getText(R.string.robot_info) + SystemUtil.getLtpLastSn());

                    } else if (msg.arg2 == BleConnectStatusCallback.BLE_STATUS_CONNECTING_NET) {
                        // tvWifiStatus.setText(mContext.getText(R.string.connecting_wifi) + "\n" + wifiName);
                        tvWifiStatus.setText("\n" + wifiName);

                    } else if (msg.arg2 == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED) {
                        //为了统一海外国内的文案显示，所以去掉原来的中文，海外国内改成一致的。
                        // tvWifiStatus.setText(R.string.connect_failed);
                        tvWifiStatus.setText("");

                    } else if (msg.arg2 == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS) {
                        tvWifiStatus.setText(R.string.connect_success);
                    }

                }
            }
        }
    }


}
