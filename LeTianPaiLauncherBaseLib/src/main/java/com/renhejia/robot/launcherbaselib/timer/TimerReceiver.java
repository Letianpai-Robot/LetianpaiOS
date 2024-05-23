package com.renhejia.robot.launcherbaselib.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class TimerReceiver extends BroadcastReceiver {
    Calendar calendar;
    @Override
    public void onReceive(Context context, Intent intent) {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        TimerKeeperCallback.getInstance().setTimerKeeper(hour,min);
    }
}
