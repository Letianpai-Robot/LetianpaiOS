package com.renhejia.robot.launcher.displaymode.callback;


import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.general.GeneralInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;

/**
 * Created by liujunbin
 */

public class WeatherInfoUpdateCallback {

    private WeatherUpdateListener mWeatherUpdateListener;

    private static class RobotInfoUpdateCallbackHolder {
        private static WeatherInfoUpdateCallback instance = new WeatherInfoUpdateCallback();
    }

    private WeatherInfoUpdateCallback() {

    }

    public static WeatherInfoUpdateCallback getInstance() {
        return RobotInfoUpdateCallbackHolder.instance;
    }

    public interface WeatherUpdateListener {
        void updateWeather(WeatherInfo weatherInfo);
    }

    public void setWeatherUpdateListener(WeatherUpdateListener listener) {
        this.mWeatherUpdateListener = listener;
    }


    public void updateWeather(WeatherInfo weatherInfo) {
        if (mWeatherUpdateListener != null) {
            mWeatherUpdateListener.updateWeather(weatherInfo);
        }
    }





}
