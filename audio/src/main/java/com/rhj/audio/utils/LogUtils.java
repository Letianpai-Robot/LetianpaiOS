package com.rhj.audio.utils;


import android.util.Log;

import com.rhj.audio.BuildConfig;


public class LogUtils {
    public static void logi(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void logw(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void logd(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void loge(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    private static final int showLength = 1000;

    public static void showLargeLog(String tag, String logContent) {
        if (logContent.length() > showLength) {
            String show = logContent.substring(0, showLength);
            logd(tag, show);
            /*剩余的字符串如果大于规定显示的长度，截取剩余字符串进行递归，否则打印结果*/
            if ((logContent.length() - showLength) > showLength) {
                String partLog = logContent.substring(showLength, logContent.length());
                showLargeLog(tag, partLog);
            } else {
                String printLog = logContent.substring(showLength, logContent.length());
                logd(tag, printLog);
            }
        } else {
            logd(tag, logContent);
        }
    }
}
