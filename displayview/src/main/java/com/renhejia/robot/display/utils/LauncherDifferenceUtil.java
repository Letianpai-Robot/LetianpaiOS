package com.renhejia.robot.display.utils;

import android.os.Build;

/**
 * Launcher 差异化管理器
 */
public class LauncherDifferenceUtil  {
    private static int CHINA_TELECOM = 1;
    private static String A10 = "WA10";
    private static String W918 = "W918";
    private static String WA11 = "WA11";
    private static String W913O = "W913O";
    private static String W919 = "W919";
    private static String W919T = "W919T";
    private static String WA12 = "WA12";
    private static String WA12M = "WA12M";
    private static String WA13 = "WA13";
    private static String W925 = "W925";
    private static String W920O = "W920O";
    private static String PRC_LAUNGUAGE = "zh";
    private static String NO_SUPPORT_CHANGE_THEME = "0";
    private static String SUPPORT_CHANGE_THEME = "1";

    /**
     * 是否为第内存设备
     *
     * @return
     */
    public static boolean isLowMemoryDevice(){
        String device = Build.MODEL;
        if (device.equals(A10)){
            return true;
        }else if(device.equals(W918)){
            return true;
        }else if(device.equals(WA11)){
            return true;
        }else if(device.equals(WA13)){
            return true;
        }else if(device.equals(W913O)){
            return true;
        }else if(device.equals(WA12)){
            return true;
        }else if(device.equals(WA12M)){
            return true;
        }else if(device.equals(W919)){
            return true;
        }else{
            return false;
        }
    }


}
