package com.renhejia.robot.launcherbaselib.timer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujunbin
 */

public class TimerKeeperCallback {
    private List<TimerKeeperUpdateListener> mTimerKeeperUpdateListener = new ArrayList();

    private static class TimerKeeperUpdateCallBackHolder {
        private static TimerKeeperCallback instance = new TimerKeeperCallback();
    }

    private TimerKeeperCallback() {

    }

    public static TimerKeeperCallback getInstance() {
        return TimerKeeperUpdateCallBackHolder.instance;
    }

    public interface TimerKeeperUpdateListener {
        void onTimerKeeperUpdateReceived(int hour, int minute);

    }

    public void registerTimerKeeperUpdateListener(TimerKeeperUpdateListener listener) {
        if (mTimerKeeperUpdateListener != null) {
            mTimerKeeperUpdateListener.add(listener);
        }
    }

    public void unregisterTimerKeeperUpdateListener(TimerKeeperUpdateListener listener) {
        if (mTimerKeeperUpdateListener != null) {
            mTimerKeeperUpdateListener.remove(listener);
        }
    }


    public void setTimerKeeper(int hour, int minute) {
        for (int i = 0; i < mTimerKeeperUpdateListener.size(); i++) {
            if (mTimerKeeperUpdateListener.get(i) != null) {
                mTimerKeeperUpdateListener.get(i).onTimerKeeperUpdateReceived(hour, minute);
            }
        }
    }

}
