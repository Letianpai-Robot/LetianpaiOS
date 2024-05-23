package com.renhejia.robot.display;

import com.renhejia.robot.display.parser.CalendarInfo;
import com.renhejia.robot.display.parser.CountdownInfo;
import com.renhejia.robot.display.parser.FanInfo;
import com.renhejia.robot.display.parser.WeatherInfoItem;

import java.util.ArrayList;

/**
 * 机器人 信息结构体
 * @author liujunbin
 */
public class RobotDisplayInfo {

    private int batteryLevel;               //电池电量
    private int volume;                     //音量
    private boolean wifiStates;             //wifi状态
    private boolean chargingStates;         //充电状态
    private boolean bluetoothStatus;        //蓝牙状态
    private WeatherInfoItem weatherInfoItem;    //天气信息
    private ArrayList<CountdownInfo> countdownInfos;    //事件倒计时
    private ArrayList<CalendarInfo> calendarInfos;    //日历事件
    private FanInfo fanInfo;                            //粉丝信息

    public boolean isWifiStates() {
        return wifiStates;
    }

    public boolean isChargingStates() {
        return chargingStates;
    }

    public boolean isBluetoothStatus() {
        return bluetoothStatus;
    }

    public WeatherInfoItem getWeatherInfoItem() {
        return weatherInfoItem;
    }

    public void setWeatherInfoItem(WeatherInfoItem weatherInfoItem) {
        this.weatherInfoItem = weatherInfoItem;
    }

    public ArrayList<CountdownInfo> getCountdownInfos() {
        return countdownInfos;
    }

    public void setCountdownInfos(ArrayList<CountdownInfo> countdownInfos) {
        this.countdownInfos = countdownInfos;
    }

    public ArrayList<CalendarInfo> getCalendarInfos() {
        return calendarInfos;
    }

    public void setCalendarInfos(ArrayList<CalendarInfo> calendarInfos) {
        this.calendarInfos = calendarInfos;
    }

    public FanInfo getFanInfo() {
        return fanInfo;
    }

    public void setFanInfo(FanInfo fanInfo) {
        this.fanInfo = fanInfo;
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
