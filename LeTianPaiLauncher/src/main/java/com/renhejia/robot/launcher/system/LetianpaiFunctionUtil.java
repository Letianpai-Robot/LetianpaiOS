package com.renhejia.robot.launcher.system;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.renhejia.robot.commandlib.log.LogUtils;

import com.google.gson.Gson;
import com.renhejia.robot.launcher.LTPConfigConsts;
import com.renhejia.robot.launcher.main.activity.LauncherActivity;
import com.renhejia.robot.launcher.main.activity.LeTianPaiMainActivity;
import com.renhejia.robot.launcher.ota.UpdateInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liujunbin
 */
public class LetianpaiFunctionUtil {



    /**
     * 打开工厂app
     * @param context
     */
    public static void openFactoryApp(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName( "com.letianpai.ltp_factory_test2","com.letianpai.ltp_factory_test2.LauncherActivity"));
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK );
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP );
        context.startActivity(intent);

    }


    public static final String LAUNCHER_CLASS_NAME = "com.renhejia.robot.launcher.main.activity.LeTianPaiMainActivity";

    /**
     * @param context
     * @return
     */
    public static boolean isLauncherOnTheTop(Context context) {
        String activityName = getTopActivityName(context);
        Log.e("letianpai_","");
        if (activityName != null && activityName.equals(LAUNCHER_CLASS_NAME)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取顶部 Activity
     *
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks != null && runningTasks.size() > 0) {
            ActivityManager.RunningTaskInfo taskInfo = runningTasks.get(0);
            ComponentName componentName = taskInfo.topActivity;
            if (componentName != null && componentName.getClassName() != null) {
                return componentName.getClassName();
            }
        }
        return null;
    }

    /**
     * 开启闹钟
     * @param context
     */
    public static void openAlarm(Context context,int hour,int minute,String type) {
        Intent intent = new Intent();
        intent.putExtra(LTPConfigConsts.TIME_HOUR,hour);
        intent.putExtra(LTPConfigConsts.TIME_MINUTE,minute);
        intent.putExtra(LTPConfigConsts.ALARM_TYPE,type);
        intent.setComponent(new ComponentName( "com.letianpai.robot.alarm","com.letianpai.robot.alarm.MainActivity"));
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK );
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP );
        context.startActivity(intent);

    }


    public static boolean is24HourFormat(Context context) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            String timeFormat = android.provider.Settings.System.getString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24);

            if (timeFormat == null){
                Log.e("letianpai_time","timeFormat is null");
            }
            if (timeFormat != null && timeFormat.equals("24")) {
                return true;
//                android.provider.Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24, "12");
//                DateFormat.is24HourFormat(context); // 更新时间格式
            } else {
                return false;
//                android.provide
//                r.Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24, "24");
//                DateFormat.is24HourFormat(context); // 更新时间格式
            }
        }else{
            return true;
        }
    }

    /**
     *
     * @return
     */
    public static boolean isFactoryRom() {
        String displayVersion = Build.DISPLAY;
        Log.e("letianpai_test","displayVersion: "+ displayVersion);
        if (displayVersion.endsWith("f")){
            return true;
        }else{
            return false;
        }
    }

}
