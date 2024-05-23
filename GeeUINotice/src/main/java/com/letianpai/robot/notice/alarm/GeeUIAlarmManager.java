package com.letianpai.robot.notice.alarm;

import static java.security.AccessController.getContext;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.letianpai.robot.notice.alarm.receiver.AlarmReceiver;

import java.util.Calendar;

public class GeeUIAlarmManager {
    private AlarmManager alarmManager;

    private static GeeUIAlarmManager instance;
    private Context mContext;
//    private Gson gson;

    private GeeUIAlarmManager(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//        gson = new Gson();
    }

    public static GeeUIAlarmManager getInstance(Context context) {
        synchronized (GeeUIAlarmManager.class) {
            if (instance == null) {
                instance = new GeeUIAlarmManager(context.getApplicationContext());
            }
            return instance;
        }
    }

    // 删除闹钟
    public void createAlarm(int lateTime) {
        Calendar calendar_now = Calendar.getInstance();
        calendar_now.setTimeInMillis(System.currentTimeMillis());
        calendar_now.set(Calendar.HOUR_OF_DAY, calendar_now.get(Calendar.HOUR_OF_DAY));
        calendar_now.set(Calendar.MINUTE, calendar_now.get(Calendar.MINUTE) + lateTime);
        calendar_now.set(Calendar.SECOND, 0);
        calendar_now.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        //正常设置
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar_now.getTimeInMillis(), sender);
        //设置循环
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar_now.getTimeInMillis(), 60* 60* 1000 *24,sender);

    }

    // 创建闹钟
    public void createAlarm(int hour, int minute) {
        Calendar calendar_now = Calendar.getInstance();
        calendar_now.setTimeInMillis(System.currentTimeMillis());
        calendar_now.set(Calendar.HOUR_OF_DAY, hour);
        calendar_now.set(Calendar.MINUTE, minute);
        calendar_now.set(Calendar.SECOND, 0);
        calendar_now.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am;
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        //正常设置
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar_now.getTimeInMillis(), sender);
        //设置循环
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar_now.getTimeInMillis(), 60* 60* 1000 *24,sender);
    }


    // 删除闹钟
    public void createRepeatingAlarm(int lateTime, long intervalMillis) {
        Calendar calendar_now = Calendar.getInstance();
        calendar_now.setTimeInMillis(System.currentTimeMillis());
        calendar_now.set(Calendar.HOUR_OF_DAY, calendar_now.get(Calendar.HOUR_OF_DAY));
        calendar_now.set(Calendar.MINUTE, calendar_now.get(Calendar.MINUTE) + lateTime);
        calendar_now.set(Calendar.SECOND, 0);
        calendar_now.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am;
        am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        //正常设置
//        am.set(AlarmManager.RTC_WAKEUP, calendar_now.getTimeInMillis(), sender);
        //设置循环
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar_now.getTimeInMillis(), intervalMillis, sender);

    }

    // 删除闹钟
    public void createAlarm(Context context, int hour, int minute) {

        AlarmManager alarmManager;
        PendingIntent pendingIntent;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 创建一个 Intent 对象，用于指定闹钟触发时需要执行的操作
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        // 获取当前时间和日期
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // 计算闹钟触发时间
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (currentHour > hour || (currentHour == hour && currentMinute >= 0)) {
            // 如果当前时间已经过了 2:00，就将闹钟设置为第二天的 2:00
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // 设置闹钟
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    // 删除闹钟
    private void deleteAlarm(int position) {
//         把闹钟从闹钟列表移除
//         移除闹钟
//        alarmManager.cancel(PendingIntent.getBroadcast(mContext,ad.getId(),new Intent(getContext(), AlarmReceiver.class),0));

    }


}