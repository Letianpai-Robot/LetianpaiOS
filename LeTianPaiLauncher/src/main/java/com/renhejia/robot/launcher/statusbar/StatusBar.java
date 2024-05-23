package com.renhejia.robot.launcher.statusbar;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.dispatch.statusbar.StatusBarUpdateCallback;
import com.renhejia.robot.launcherbaselib.battery.ChargingUpdateCallback;
import com.renhejia.robot.launcherbaselib.callback.NetworkChangingUpdateCallback;
import com.renhejia.robot.launcherbaselib.info.LauncherInfoManager;
import com.renhejia.robot.launcherbaselib.timer.TimerKeeperCallback;

import java.lang.ref.WeakReference;

/**
 * 未曾使用，yujianbin24年2月18日记录
 */
@Deprecated
public class StatusBar extends LinearLayout {
    private Context mContext;
    private ImageView topImage;
    private TextView bottomText;
    private BatteryCharging batteryCharging;
    private LinearLayout rootStatus;
    private LinearLayout rlTitlePart;
    private View emptyView;
    private static final int LOW_BATTERY_STANDARD = 20;
    private UpdateViewHandler mHandler;
    private static final int SHOW_TIME = 110;
    private static final int UPDATE_TIME = 111;

    public StatusBar(Context context) {
        super(context);
        init(context);
    }


    public StatusBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mHandler = new UpdateViewHandler(context);
        inflate(mContext, R.layout.robot_status_bar, this);
        initView();
        showWifiStatus();
//        setDisplayTime();
//        setBatteryLow();
//        setBottomText("请说 '嗨,小乐' 唤醒我");
        addUpdateTextListeners();
        addNetworkChangeListeners();
        addTimerUpdateCallback();
    }

    private void showWifiStatus() {
        boolean status = LauncherInfoManager.getInstance(mContext).getWifiStates();
        if (status){
            hideNoNetworkStatus();
        }else{
            setNoNetworkStatus();
        }
    }

    private void addUpdateTextListeners() {
        StatusBarUpdateCallback.getInstance().setStatusBarTextChangeListener(new StatusBarUpdateCallback.StatusBarChangeListener() {
            @Override
            public void onStatusBarTextChanged(String content) {
                showText(content);
            }
        });
    }

    private void addTimerUpdateCallback() {
        TimerKeeperCallback.getInstance().registerTimerKeeperUpdateListener(new TimerKeeperCallback.TimerKeeperUpdateListener() {
            @Override
            public void onTimerKeeperUpdateReceived(int hour, int minute) {
                updateTime();
            }
        });
    }


    private void addNetworkChangeListeners() {
//        NetworkChangingUpdateCallback.getInstance().setChargingStatusUpdateListener(new NetworkChangingUpdateCallback.NetworkChangingUpdateListener() {
//            @Override
//            public void onNetworkChargingUpdateReceived(int networkType, int networkStatus) {
//                if (networkType == NetworkChangingUpdateCallback.NETWORK_TYPE_DISABLED) {
//                    setNoNetworkStatus();
//                }else{
//                    hideNoNetworkStatus();
//                }
//
//            }
//        });
        NetworkChangingUpdateCallback.getInstance().registerChargingStatusUpdateListener(new NetworkChangingUpdateCallback.NetworkChangingUpdateListener() {
            @Override
            public void onNetworkChargingUpdateReceived(int networkType, int networkStatus) {
                Log.e("letianpai_net","networkType: "+ networkType);
                if (networkType == NetworkChangingUpdateCallback.NETWORK_TYPE_DISABLED) {
                    setNoNetworkStatus();
                }else{
                    hideNoNetworkStatus();
                }
            }
        });

        ChargingUpdateCallback.getInstance().registerChargingStatusUpdateListener(new ChargingUpdateCallback.ChargingUpdateListener() {
            @Override
            public void onChargingUpdateReceived(boolean changingStatus, int percent) {
                Log.e("letianpai_1213","changingStatus: "+ changingStatus);
                Log.e("letianpai_1213","percent: "+ percent);
                if (percent > LOW_BATTERY_STANDARD){
                    bottomText.setText("");
                }
                if (changingStatus) {
                    setCharging(percent);
                } else if (percent < LOW_BATTERY_STANDARD) {
                    setBatteryLow(percent);
                } else {
                    batteryCharging.setVisibility(View.GONE);
                }

            }

            @Override
            public void onChargingUpdateReceived(boolean changingStatus, int percent, int chargePlug) {

            }
        });
    }

    private void initView() {
        rootStatus = findViewById(R.id.root_status);
//        rootStatus.getBackground().setAlpha(256);
        topImage = findViewById(R.id.title_part);
        bottomText = findViewById(R.id.bottom_part);
        batteryCharging = findViewById(R.id.bcCharging);
        emptyView = findViewById(R.id.empty_view);
        rlTitlePart = findViewById(R.id.rl_title_part);

    }

    public void setNoNetworkStatus() {
        topImage.setVisibility(View.VISIBLE);
        topImage.setImageResource(R.drawable.no_network);
    }

    private void hideNoNetworkStatus() {
        topImage.setVisibility(View.GONE);
//        bottomText.setVisibility(View.GONE);
    }


    public void setBatteryLow(int percent) {
//        topImage.setVisibility(View.GONE);
        batteryCharging.setVisibility(View.VISIBLE);
        batteryCharging.setBatteryLow(percent);
        bottomText.setVisibility(View.VISIBLE);
        bottomText.setText(R.string.battery_low);
    }

    public void setCharging(int percent) {
        batteryCharging.setVisibility(View.VISIBLE);
        batteryCharging.setBatteryLevel(percent);
    }

    public void setDisplayTime() {
        bottomText.setVisibility(View.VISIBLE);
        setBottomText(TimeUtil.getCorrectTime());
    }



    /**
     * 设置底部文案
     *
     * @param content
     */
    public void setBottomText(String content) {
//        bottomText.setVisibility(View.VISIBLE);
//        bottomText.setText(content);
    }

    private class UpdateViewHandler extends android.os.Handler {
        private final WeakReference<Context> context;

        public UpdateViewHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_TIME:
                    if (msg.obj != null){
                        setBottomText((String) (msg.obj));
                    }
                    break;

                case UPDATE_TIME:
//                    setBottomText(TimeUtil.getCorrectTime());
                    break;

            }
        }
    }

    public void showText(String text) {
        Message message = new Message();
        message.what = SHOW_TIME;
        message.obj = text;
        mHandler.sendMessage(message);
    }

    public void updateTime() {
        Message message = new Message();
        message.what = UPDATE_TIME;
        mHandler.sendMessage(message);
    }


}
