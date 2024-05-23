package com.renhejia.robot.launcherbaselib.info;

import android.content.Context;

import com.renhejia.robot.launcherbaselib.broadcast.LauncherBroadcastReceiverManager;

/**
 * 手表状态持有
 */
public class LauncherInfoManager {
    private static LauncherInfoManager instance;
    private Context mContext;

    private boolean mIsCharging;
    private int mBatteryLevel;
    private boolean wifiStatus;

    private LauncherInfoManager(Context context){
        this.mContext = context;
        init(context);

    }

    public static LauncherInfoManager getInstance(Context context){
        synchronized (LauncherBroadcastReceiverManager.class){
            if (instance == null){
                instance = new LauncherInfoManager(context.getApplicationContext());
            }
            return instance;
        }

    };

    private void init(Context context) {

    }

    /**
     * 获取充电状态
     *
     * @return
     */
    public boolean isChargingMode() {
        return mIsCharging;
    }

    /**
     * 设置充电状态
     *
     * @param isCharging
     */
    public void setChargingMode(boolean isCharging) {
        this.mIsCharging = isCharging;
    }

    /**
     * 获取电池电量
     *
     * @return
     */
    public int getBatteryLevel() {
        return mBatteryLevel;
    }

    /**
     * 设置电量等级
     *
     * @param mBatteryLevel
     */
    public void setBatteryLevel(int mBatteryLevel) {
        this.mBatteryLevel = mBatteryLevel;
    }

    public void setWifiStates(boolean wifiStatus) {
        this.wifiStatus = wifiStatus;
    }

    public boolean getWifiStates() {
        return wifiStatus;
    }



}
