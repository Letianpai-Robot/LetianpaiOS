package com.renhejia.robot.display.callback;


/**
 * 桌面信息更新回调
 * Created by liujunbin on 28/11/2017.
 */

public class RobotPlatformInfoUpdateCallback {

    private SpinePlatformListener mSpinePlatformListener;

    private static class SpinePlatformInfoUpdateCallbackHolder {
        private static RobotPlatformInfoUpdateCallback instance = new RobotPlatformInfoUpdateCallback();
    }

    private RobotPlatformInfoUpdateCallback() {

    }

    public static RobotPlatformInfoUpdateCallback getInstance() {
        return SpinePlatformInfoUpdateCallbackHolder.instance;
    }

    public interface SpinePlatformListener {
        void updateBluetoothEnabled(boolean isBluetoothEnabled);
        void updateBatteryLevel(int batteryLevel);
        void updateBatteryCharging(boolean isBatteryCharging);
        void updateWifiEnabled(boolean isBatteryCharging);
        void updateMediaVolume(int batteryLevel);
        void updateStepNumber(int batteryLevel);
        void updateWeather(int weatherState, int currentTemp, int airQuality);
    }

    public void setSpinePlatformListener(SpinePlatformListener listener) {
        this.mSpinePlatformListener = listener;
    }

    /**
     * 更新蓝牙状态
     * @param isBluetoothEnabled
     */
    public void updateBluetoothEnabled(boolean isBluetoothEnabled) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener.updateBluetoothEnabled(isBluetoothEnabled);
        }
    }

    /**
     * 更新电池等级
     * @param batteryLevel
     */
    public void updateBatteryLevel(int batteryLevel) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener.updateBatteryLevel(batteryLevel);
        }
    }

    /**
     * 更新电池充电状态
     * @param isBatteryCharging
     */
    public void updateBatteryCharging(boolean isBatteryCharging) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener.updateBatteryCharging(isBatteryCharging);
        }
    }

    /**
     * 更新Wifi 状态
     * @param wifiEnabled
     */
    public void updateWifiEnabled(boolean wifiEnabled) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener.updateWifiEnabled(wifiEnabled);
        }
    }

    /**
     * 更新媒体音量
     * @param batteryLevel
     */
    public void updateMediaVolume(int batteryLevel) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener.updateMediaVolume(batteryLevel);
        }
    }

    /**
     * 更新步数信息
     * @param stepNumber
     */
    public void updateStepNumber(int stepNumber) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener.updateStepNumber(stepNumber);
        }
    }

    /**
     * 更新天气信息
     * @param weatherState  天气状态码
     * @param currentTemp   当前天气
     * @param airQuality    当前天气质量
     */
    public void updateWeather(int weatherState, int currentTemp, int airQuality) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener.updateWeather(weatherState,  currentTemp, airQuality);
        }
    }





}
