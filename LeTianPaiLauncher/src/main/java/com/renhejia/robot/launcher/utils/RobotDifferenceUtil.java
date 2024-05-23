package com.renhejia.robot.launcher.utils;

import android.util.Log;

import java.util.Locale;

/**
 * Launcher 差异化管理器
 * @author liujunbin
 */
public class RobotDifferenceUtil {
    private static String PRC_LAUNGUAGE = "zh";

    /**
     * ZxingView是否使用海外版布局
     *
     *
     * @return
     */
    public static boolean isUseOverseaLayout(){
        return false;
    }

    public static boolean isChinese(){
        String language = Locale.getDefault().getLanguage();
        Log.e("isChinese","isChinese: "+language);
        if (language.equals(PRC_LAUNGUAGE)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 是否显示海外版图标
     * @return
     */
    public static boolean isDisplayIconButton(){
        return  isUseOverseaLayout() && (!isChinese());
    }



}
