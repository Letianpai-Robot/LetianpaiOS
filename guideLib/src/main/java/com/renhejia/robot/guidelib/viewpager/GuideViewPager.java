package com.renhejia.robot.guidelib.viewpager;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.renhejia.robot.commandlib.log.LogUtils;

import android.view.View;

import com.renhejia.robot.guidelib.R;
import com.renhejia.robot.guidelib.ble.callback.BleConnectStatusCallback;
import com.renhejia.robot.guidelib.ble.callback.GuideFunctionCallback;
import com.renhejia.robot.guidelib.qrcode.QRCodeView;
import com.renhejia.robot.guidelib.view.AutoConnectWifiView;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页ViewPager
 */
public class GuideViewPager extends ViewPager {
    public static final int CLOSE_DEVICE = 0x201;
    public static final int CLOSE_TIME = 5 * 60 * 1000;
    private GuideViewpagerAdapter adapter;
    private Context mContext;
    private List<View> guideViews = new ArrayList<>();
    private BleConnectStatusCallback.BleConnectStatusChangedListener bleConnectStatusChangedListener;
    private String TAG = "ble_viewpager";
    private ChargeBroadCast chargeBroadCast = new ChargeBroadCast();
    private boolean isCharging;
    private Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.logd("GuideViewPager", "handleMessage: 关机");
            if (msg.what == CLOSE_DEVICE && isLauncherOnTheTop()) {
                LogUtils.logd("GuideViewPager", "handleMessage: 关机1");
                GuideFunctionCallback.getInstance().onShutDown();
            }
        }
    };

    public boolean isLauncherOnTheTop() {
        String activityName = getTopActivityName();
        if (activityName != null && activityName.contains(getContext().getPackageName())) {
            return true;
        } else {
            return false;
        }

    }

    public String getTopActivityName() {
        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks != null && runningTasks.size() > 0) {
            ActivityManager.RunningTaskInfo taskInfo = runningTasks.get(0);
            ComponentName componentName = taskInfo.topActivity;
            if (componentName != null && componentName.getClassName() != null) {
                return componentName.getClassName();
            }
        }
        return null;
    }

    public GuideViewPager(@NonNull Context context) {
        super(context);
        init(context);
    }

    public GuideViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        LogUtils.logd("GuideViewPager", "init: ");
        initView();
        initBleConnectStatusListener();
        registerBleConnectStatusListener();
        addListeners();
        initCloseHandler();
//        LogUtils.logi("auto_connect"," SystemUtils.getWlanMacAddress(): "+ SystemUtils.getWlanMacAddress());
    }

    private void initCloseHandler() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = mContext.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
        chargeStatusChange();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        mContext.registerReceiver(chargeBroadCast, intentFilter);
    }

    private void chargeStatusChange() {
        if (!isCharging && GuideViewPager.this.getCurrentItem() == 0) {
            uiHandler.sendEmptyMessageDelayed(CLOSE_DEVICE, CLOSE_TIME);
        } else {
            uiHandler.removeMessages(CLOSE_DEVICE);
        }
    }


    private void initBleConnectStatusListener() {
        bleConnectStatusChangedListener = new BleConnectStatusCallback.BleConnectStatusChangedListener() {
            @Override
            public void onBleConnectStatusChanged(int connectStatus) {
                if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT) {
                    LogUtils.logi(TAG, "1__BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT" + Thread.currentThread());
                    uiHandler.removeMessages(CLOSE_DEVICE);
                    uiHandler.post(() -> GuideViewPager.this.setCurrentItem(1));
                } else if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS) {
                    LogUtils.logi(TAG, "2__BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS");
                } else if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED) {
                    LogUtils.logi(TAG, "3__BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED");
                } else if (connectStatus == BleConnectStatusCallback.BLE_STATUS_DISCONNECT_FROM_CLIENT) {
                    LogUtils.logi(TAG, "4__BleConnectStatusCallback.BLE_STATUS_DISCONNECT_FROM_CLIENT");
                }
            }
        };
    }

    private void addListeners() {
        BleConnectStatusCallback.getInstance().registerBleConnectStatusListener(new BleConnectStatusCallback.BleConnectStatusChangedListener() {
            @Override
            public void onBleConnectStatusChanged(int connectStatus) {
                if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS) {
                    //配网成功 TODO
                    // uiHandler.post(() -> GuideViewPager.this.setCurrentItem(2));
                    uiHandler.post(() -> GuideFunctionCallback.getInstance().closeGuide());
                }
            }
        });
    }

    private void registerBleConnectStatusListener() {
        BleConnectStatusCallback.getInstance().registerBleConnectStatusListener(bleConnectStatusChangedListener);
    }

    private void unregisterBleConnectStatusListener() {
        if (bleConnectStatusChangedListener != null) {
            BleConnectStatusCallback.getInstance().unregisterBleConnectStatusListener(bleConnectStatusChangedListener);
        }
    }

    private void initView() {
        QRCodeView finishView = new QRCodeView(mContext);
        finishView.setQrcodeVisible(View.GONE);
        finishView.setTipsDescVisible(View.GONE);
        finishView.setAddDeviceTvVisible(View.GONE);
//        finishView.setTvScan("已绑定成功\n请用微信扫码学习教程");
        finishView.setTvScan(getContext().getString(R.string.guide_tips));
        guideViews.add(new QRCodeView(mContext));
        guideViews.add(new AutoConnectWifiView(mContext));
        guideViews.add(finishView);
        adapter = new GuideViewpagerAdapter(mContext, guideViews);
        setAdapter(adapter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContext.unregisterReceiver(chargeBroadCast);
        unregisterBleConnectStatusListener();
    }

    class ChargeBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_POWER_DISCONNECTED: {
                    isCharging = false;
                    chargeStatusChange();
                    break;
                }
                case Intent.ACTION_POWER_CONNECTED: {
                    isCharging = true;
                    chargeStatusChange();
                    break;
                }
            }
        }
    }
}
