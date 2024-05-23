package com.renhejia.robot.display;

public interface RobotPlatformListener {
    public void updateBatteryLevel(int batteryLevel);
    public void updateBatteryCharging(boolean batteryCharging);
    public void updateBluetoothEnabled(boolean bluetoothEnabled);
    public void updateWifiEnabled(boolean wifiEnabled);
    public void updateMediaVolume(int mediaVolume);
    public void updateStepNumber(int stepNumber);
    public void updateWeather(int weatherState, int currentTemp, int airQuality);
    public void updateAll(RobotPlatformState state);

}
