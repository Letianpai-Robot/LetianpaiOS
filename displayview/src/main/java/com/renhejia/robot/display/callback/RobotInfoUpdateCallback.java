package com.renhejia.robot.display.callback;


import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.general.GeneralInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;

/**
 * Created by liujunbin
 */

public class RobotInfoUpdateCallback {

    private RobotInfoListener mRobotInfoListener;

    private static class RobotInfoUpdateCallbackHolder {
        private static RobotInfoUpdateCallback instance = new RobotInfoUpdateCallback();
    }

    private RobotInfoUpdateCallback() {

    }

    public static RobotInfoUpdateCallback getInstance() {
        return RobotInfoUpdateCallbackHolder.instance;
    }

    public interface RobotInfoListener {
        void updateGeneralInfo(GeneralInfo generalInfo);
        void updateWeather(WeatherInfo weatherInfo);
        void updateCountDown(CountDownListInfo countDownListInfo);
        void updateNotice(CalenderInfo calenderInfo);
        void updateFansInfo(FansInfo fansInfo);
    }

    public void setRobotInfoListener(RobotInfoListener listener) {
        this.mRobotInfoListener = listener;
    }

    public void updateGeneralInfo(GeneralInfo generalInfo) {
        if (mRobotInfoListener != null) {
            mRobotInfoListener.updateGeneralInfo(generalInfo);
        }
    }

    public void updateWeather(WeatherInfo weatherInfo) {
        if (mRobotInfoListener != null) {
            mRobotInfoListener.updateWeather(weatherInfo);
        }
    }

    public void updateCountDown(CountDownListInfo countDownListInfo) {
        if (mRobotInfoListener != null) {
            mRobotInfoListener.updateCountDown(countDownListInfo);
        }
    }

    public void updateNotice(CalenderInfo calenderInfo) {
        if (mRobotInfoListener != null) {
            mRobotInfoListener.updateNotice(calenderInfo);
        }
    }

    public void updateFansInfo(FansInfo fansInfo) {
        if (mRobotInfoListener != null) {
            mRobotInfoListener.updateFansInfo(fansInfo);
        }
    }




}
