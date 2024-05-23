package com.renhejia.robot.display.manager;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.renhejia.robot.display.R;
import com.renhejia.robot.display.RobotSkinInfoItem;
import com.renhejia.robot.display.callback.RobotPlatformInfoUpdateCallback;
import com.renhejia.robot.display.parser.CalendarInfo;
import com.renhejia.robot.display.parser.CountdownInfo;
import com.renhejia.robot.display.parser.FanInfo;
import com.renhejia.robot.display.parser.WeatherInfoItem;

import java.util.ArrayList;


/**
 * 表盘信息更新管理器
 */
public class RobotDisplayInfoManager {
    private Context mContext;

    private int batteryLevel;               //电池电量
    private int batteryLevelTemp;           //电池电量Temp
    private int usbStatus;                  //USB状态
    private int usbStatusTemp;              //USB状态Temp
    private int volume;                     //音量
    private int volumeTemp;                 //音量Temp
    private boolean wifiStates;             //wifi状态
    private boolean wifiStatesTemp;         //wifi状态Temp
    private boolean chargingStates;       //充电状态
    private boolean chargeinStatesTemp;    //充电状态Temp
    private boolean isBluetoothOn;
    private boolean isBluetoothStatusTemp;
    private int weatherState = -1;
    private int weatherStateTemp;
    private int currentTemp;
    private int currentTempTemp;
    private int airQuality;
    private int airQualityTemp;

    private WeatherInfoItem weatherInfoItem;    //天气信息
    private WeatherInfoItem weatherInfoItemTemp;    //天气信息
    private ArrayList<CountdownInfo> countdownInfo;    //事件倒计时
    private ArrayList<CountdownInfo> countdownInfoTemp;    //事件倒计时
    private ArrayList<CalendarInfo> calendarInfos;    //日历事件
    private ArrayList<CalendarInfo> calendarInfoTemp;    //日历事件
    private FanInfo fanInfo;                            //粉丝信息
    private FanInfo fanInfoTemp;                            //粉丝信息


    private static RobotDisplayInfoManager instance;

    private RobotDisplayInfoManager(Context context) {
        this.mContext = context;
//        getStepCount();
//        getWeatherStatus();

    }

    public static RobotDisplayInfoManager getInstance(Context context) {
        if (instance == null) {
            instance = new RobotDisplayInfoManager(context.getApplicationContext());
        }
        return instance;
    }

    public void setWeatherInfoItem(WeatherInfoItem weatherInfoItem) {
        this.weatherInfoItem = weatherInfoItem;
        if (weatherInfoItem != weatherInfoItemTemp) {
            weatherInfoItemTemp = weatherInfoItem;
            //TODO 增加天气刷新逻辑
            //RobotPlatformInfoUpdateCallback.getInstance().updateBatteryLevel(batteryLevel);
        }
    }

    /**
     * 更新电量等级
     *
     * @param batteryLevel
     */
    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
        if (batteryLevel != batteryLevelTemp) {
            batteryLevelTemp = batteryLevel;
            RobotPlatformInfoUpdateCallback.getInstance().updateBatteryLevel(batteryLevel);
        }
    }


    public int getBatteryLevel() {
//        batteryLevel = LauncherInfoManager.getInstance(mContext).getBatteryLevel();
        return batteryLevel;
    }

    /**
     * 更新音量
     *
     * @param volume
     */
    public void setVolume(int volume) {
        this.volume = volume;
        if (volume != volumeTemp) {
            volumeTemp = volume;
            RobotPlatformInfoUpdateCallback.getInstance().updateMediaVolume(volume);
        }
    }

    public int getVolume() {
//        volume = QSettings.getVolumeLevelRing(mContext);
        //TODO 增加获取系统音量的方法
        return 1;
//        return volume;
    }

    //TODO 需要确认连接状态

    /**
     * 更新蓝牙状态
     *
     * @param bluetoothConnected
     */
    public void setBluetoothStatus(boolean bluetoothConnected) {
        isBluetoothOn = bluetoothConnected;
        if (isBluetoothOn != isBluetoothStatusTemp) {
            isBluetoothStatusTemp = bluetoothConnected;
            RobotPlatformInfoUpdateCallback.getInstance().updateBluetoothEnabled(bluetoothConnected);
        }
    }

    /**
     * 获取蓝牙状态
     *
     * @param
     */
    public boolean getBluetoothStatus() {
        return isBluetoothOn;
    }

    /**
     * 更新wifi状态
     *
     * @param wifiStates
     */
    public void setWifiStates(boolean wifiStates) {
        this.wifiStates = wifiStates;
        if (wifiStates != wifiStatesTemp) {
            wifiStatesTemp = wifiStates;
            RobotPlatformInfoUpdateCallback.getInstance().updateWifiEnabled(wifiStates);

        }
    }

    /**
     * 获取wifi状态
     *
     * @return
     */
    public boolean getWifiStates() {
//        wifiStates = LauncherInfoManager.getInstance(mContext).getWifiStates();
        return wifiStates;
    }

    public void setWeatherState(int weatherState) {
        this.weatherState = weatherState;
    }

    //TODO 记步变化时，不会发通知

//    /**
//     * 获取计步信息
//     * @return
//     */
//    private int getStepsData() {
//        String KID_TODAY_STEPS = "kid_today_steps";
//        int mCurrentSteps = Settings.Global.getInt(mContext.getContentResolver(), KID_TODAY_STEPS, 0);
//        return mCurrentSteps;
//    }
//

    /**
     * 设置充电状态变化
     *
     * @param chargingStates 充电状态
     */
    public void setChargingStates(boolean chargingStates) {
        this.chargingStates = chargingStates;
        if (this.chargingStates != chargeinStatesTemp) {
            chargeinStatesTemp = chargingStates;
            RobotPlatformInfoUpdateCallback.getInstance().updateBatteryCharging(chargingStates);
        }
    }

    /**
     * 更新天气信息
     *
     * @param weatherState 天气状态码
     * @param currentTemp  当前天气
     * @param airQuality   当前天气质量
     */
    public void updateWeather(int weatherState, int currentTemp, int airQuality) {
        this.weatherState = weatherState;
        this.currentTemp = currentTemp;
        this.airQuality = airQuality;
        if ((weatherState != weatherStateTemp) || (currentTemp != currentTempTemp) || (airQuality != airQualityTemp)) {
            weatherStateTemp = weatherState;
            currentTempTemp = currentTemp;
            airQualityTemp = airQuality;

            RobotPlatformInfoUpdateCallback.getInstance().updateWeather(weatherState, currentTemp, airQuality);
        }
    }

    /**
     * 更新天气信息
     *
     * @param weatherState 天气状态码
     * @param currentTemp  当前天气
     * @param airQuality   当前天气质量
     */
    public void updateWeather(int weatherState, String currentTemp, int airQuality) {
        String temp = currentTemp.replace(mContext.getResources().getString(R.string.temperature_degrees),"");
        if (!TextUtils.isEmpty(temp)) {
            int temprature = Integer.valueOf(temp);
            updateWeather(weatherState, temprature, airQuality);

        }

    }

    private void getWeatherStatus() {
//        weatherState = LauncherWeatherInfoManager.getInstance(mContext).getRealtimeWeather();
//        String temp = LauncherWeatherInfoManager.getInstance(mContext).getCurrentTemperatureInt();
        String temp = null;
        if (!TextUtils.isEmpty(temp)){
            currentTemp = Integer.valueOf(temp);
        }else{
            currentTemp = -99;
        }

//        airQuality = LauncherWeatherInfoManager.getInstance(mContext).getPm25Int();


    }
    /**
     * 获取皮肤信息结构体
     *
     * @return
     */
    public RobotSkinInfoItem getSpineSkinInfoItem() {
        updateData();
        RobotSkinInfoItem spineSkinInfoItem = new RobotSkinInfoItem();
//        WeatherInfoItem weatherInfoItem = new WeatherInfoItem();
//        stepCount = (int) WolfPlatformManager.getInstance(mContext).getStepsData(mContext, 0, 0);
        spineSkinInfoItem.setBatteryLevel(batteryLevel);
//        LogUtils.logi("Mars111333","batteryLevel: " + batteryLevel);
        spineSkinInfoItem.setBluetoothStatus(isBluetoothOn);
//        LogUtils.logi("Mars111333","isBluetoothOn: " + isBluetoothOn);
        spineSkinInfoItem.setChargingStates(chargingStates);
//        LogUtils.logi("Mars111333","chargingStates: " + chargingStates);
        spineSkinInfoItem.setVolume(volume);
//        LogUtils.logi("Mars111333","volume: " + volume);
        spineSkinInfoItem.setWifiStates(wifiStates);
//        LogUtils.logi("Mars111333","wifiStates: " + wifiStates);
//        LogUtils.logi("Mars111333","stepCount: " + stepCount);
//        weatherInfoItem.setAirQuality(airQuality);
//        weatherInfoItem.setCurrentTemp(currentTemp);
//        weatherInfoItem.setWeatherState(weatherState);
//        spineSkinInfoItem.setWeatherInfoItem(weatherInfoItem);

        return spineSkinInfoItem;
    }

    private void updateData() {
        getBatteryLevel();
        getBluetoothStatus();
        getVolume();
        getWifiStates();
        getChargingStatus();
        getWeatherStatus();

    }

    private boolean getChargingStatus() {
        return chargingStates;
    }

}
