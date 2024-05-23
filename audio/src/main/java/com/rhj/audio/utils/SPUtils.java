package com.rhj.audio.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private final static String SP_NAME = "audiosetting";
    private SharedPreferences sharedPreferences = null;
    private static SPUtils spUtils = null;

    public static SPUtils getInstance(Context context) {
        if (spUtils == null) {
            return newInstance(context);
        }
        return spUtils;
    }

    private synchronized static SPUtils newInstance(Context context) {
        if (spUtils == null) {
            spUtils = new SPUtils(context);
        }
        return spUtils;
    }

    public SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    //sp存数据
    public void putString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    //sp取数据
    public String getString(String key) {
        return sharedPreferences.getString(key, "");
        //"暂无保存的数据"为该key为空时的默认返回值
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public Long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    private SPUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

}

