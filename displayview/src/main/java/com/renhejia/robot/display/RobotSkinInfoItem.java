package com.renhejia.robot.display;

/**
 * 机器人 信息结构体
 * @author liujunbin
 */
public class RobotSkinInfoItem {

    private int batteryLevel;               //电池电量
    private int volume;                     //音量
    private int stepCount;                  //记步
    private boolean wifiStates;             //wifi状态
    private boolean chargingStates;         //充电状态
    private boolean bluetoothStatus;        //蓝牙状态
//    private WeatherInfoItem weatherInfoItem;    //天气信息

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

//    public WeatherInfoItem getWeatherInfoItem() {
//        return weatherInfoItem;
//    }
//
//    public void setWeatherInfoItem(WeatherInfoItem weatherInfoItem) {
//        this.weatherInfoItem = weatherInfoItem;
//    }

    public boolean getWifiStatus(){
        return wifiStates;
    }

    public void setWifiStates(boolean wifiStates) {
        this.wifiStates = wifiStates;
    }

    public void setBluetoothStatus(boolean bluetoothStatus) {
        this.bluetoothStatus = bluetoothStatus;
    }

    public boolean getBluetoothStatus() {
        return bluetoothStatus;
    }

    public void setChargingStates(boolean chargingStates) {
        this.chargingStates = chargingStates;
    }

    public boolean getChargingStates() {
        return chargingStates;
    }


}
