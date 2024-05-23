package com.letianpai.robot.notice.alarm.receiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujunbin
 */

public class AlarmCallback {

    private List<AlarmTimeListener> mTimerKeeperUpdateListener = new ArrayList();

    private static class AlarmCallbackHolder {
        private static AlarmCallback instance = new AlarmCallback();
    }

    private AlarmCallback() {

    }

    public static AlarmCallback getInstance() {
        return AlarmCallbackHolder.instance;
    }

    public interface AlarmTimeListener {
        void onAlarmTimeOut(int hour, int minute);

    }

    public void registerAlarmTimeListener(AlarmTimeListener listener) {
        if (mTimerKeeperUpdateListener != null) {
            mTimerKeeperUpdateListener.add(listener);
        }
    }

    public void unregisterAlarmTimeListener(AlarmTimeListener listener) {
        if (mTimerKeeperUpdateListener != null) {
            mTimerKeeperUpdateListener.remove(listener);
        }
    }


    public void setAlarmTimeout(int hour, int minute) {
        for (int i = 0; i < mTimerKeeperUpdateListener.size(); i++) {
            if (mTimerKeeperUpdateListener.get(i) != null) {
                mTimerKeeperUpdateListener.get(i).onAlarmTimeOut(hour, minute);
            }
        }
    }

}
