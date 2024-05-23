package com.letianpai.robot.notice.alarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    Calendar calendar_now;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println(" ~~~~~~~~~lPT 闹钟执行了 ~~~~~ 0 ~~~~ ");
        calendar_now = Calendar.getInstance();
        int hour = calendar_now.get(Calendar.HOUR_OF_DAY);
        int minute = calendar_now.get(Calendar.MINUTE);
        System.out.println(" ~~~~~~~~~lPT 闹钟执行了 ~~~~~ 1 ~~~~ ");

        AlarmCallback.getInstance().setAlarmTimeout(hour, minute);

    }
}
