package com.renhejia.robot.display;

public class RobotPlatformState {
    public static int NO_TEMP = -99; // 无温度信息
    public static int NO_WEATHER = -99; // 无温度信息

    public int batteryLevel;  // 电池电量
    public int stepNumber;    // 记步
    public int mediaVolume;   // 媒体音量
    public boolean bluetoothEnabled; // 蓝牙开关
    public boolean wifiEnabled;      // wifi开关
    public boolean batteryCharging;  // 充电状态
    public int weatherState; // 天气情况
    public int currentTemp;  // 实时温度
    public String tempRange;  // 区间
    public int airQuality;   // 空气质量

    public RobotPlatformState() {
        batteryLevel = 80;
        stepNumber = 12345;
        mediaVolume = 3;
        bluetoothEnabled = false;
        wifiEnabled = true;
        batteryCharging = true;
        weatherState = 1;
        tempRange ="32° /10°";
//        currentTemp = NO_TEMP;
        currentTemp = 26;
        airQuality = 256;
    }
}
