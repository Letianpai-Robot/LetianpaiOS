package com.renhejia.robot.launcher.displaymode.callback;


import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.general.GeneralInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;

/**
 * Created by liujunbin
 */

public class CountDownInfoUpdateCallback {

    private CountDownInfoListener mRobotInfoListener;

    private static class RobotInfoUpdateCallbackHolder {
        private static CountDownInfoUpdateCallback instance = new CountDownInfoUpdateCallback();
    }

    private CountDownInfoUpdateCallback() {

    }

    public static CountDownInfoUpdateCallback getInstance() {
        return RobotInfoUpdateCallbackHolder.instance;
    }

    public interface CountDownInfoListener {
        void updateCountDown(CountDownListInfo countDownListInfo);
    }

    public void seCountDownInfoUpdateListener(CountDownInfoListener listener) {
        this.mRobotInfoListener = listener;
    }

    public void updateCountDown(CountDownListInfo countDownListInfo) {
        if (mRobotInfoListener != null) {
            mRobotInfoListener.updateCountDown(countDownListInfo);
        }
    }


}
