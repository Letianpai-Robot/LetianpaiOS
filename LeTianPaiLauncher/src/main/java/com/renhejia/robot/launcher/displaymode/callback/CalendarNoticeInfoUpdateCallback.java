package com.renhejia.robot.launcher.displaymode.callback;


import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.general.GeneralInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;

/**
 * Created by liujunbin
 */

public class CalendarNoticeInfoUpdateCallback {

    private RobotInfoListener mRobotInfoListener;

    private static class RobotInfoUpdateCallbackHolder {
        private static CalendarNoticeInfoUpdateCallback instance = new CalendarNoticeInfoUpdateCallback();
    }

    private CalendarNoticeInfoUpdateCallback() {

    }

    public static CalendarNoticeInfoUpdateCallback getInstance() {
        return RobotInfoUpdateCallbackHolder.instance;
    }

    public interface RobotInfoListener {
        void updateNotice(CalenderInfo calenderInfo);
    }

    public void setCalendarInfoListener(RobotInfoListener listener) {
        this.mRobotInfoListener = listener;
    }


    public void updateNotice(CalenderInfo calenderInfo) {
        if (mRobotInfoListener != null) {
            mRobotInfoListener.updateNotice(calenderInfo);
        }
    }




}
