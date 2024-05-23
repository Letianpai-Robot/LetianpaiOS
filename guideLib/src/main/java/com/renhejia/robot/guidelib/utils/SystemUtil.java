package com.renhejia.robot.guidelib.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.renhejia.robot.commandlib.log.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * @author liujunbin
 */
public class SystemUtil {
    private static Method sysPropGet;
    private static Method sysPropGetInt;
    private static Method sysPropSet;
    private static final String SN = "ro.serialno";
    private static final String MCU_VERSION = "persist.sys.mcu.version";
    public static final String REGION_LANGUAGE = "persist.sys.region.language";
//    private static final String ROBOT_STATUS = "ro.robot.status";
    private static final String ROBOT_STATUS = "persist.sys.robot.status";
    private static final String ROBOT_STATUS_TRUE = "true";
    private static final String ROBOT_STATUS_FALSE = "false";
    public  static final String HARD_CODE = "persist.sys.hardcode";
    public  static final String DEVICE_SIGN = "persist.sys.device.sign";

    static {
        try {
            Class<?> S = Class.forName("android.os.SystemProperties");
            Method M[] = S.getMethods();
            for (Method m : M) {
                String n = m.getName();
                if (n.equals("get")) {
                    sysPropGet = m;
                } else if (n.equals("getInt")) {
                    sysPropGetInt = m;
                } else if (n.equals("set")) {
                    sysPropSet = m;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String get(String name, String default_value) {
        try {
            return (String) sysPropGet.invoke(null, name, default_value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return default_value;
    }

    public static void set(String name, String value) {
        try {
            sysPropSet.invoke(null, name, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public static void setRobotActivated() {
        set(ROBOT_STATUS,ROBOT_STATUS_TRUE);
    }

    public static boolean getRobotActivateStatus(){
        String status = get(ROBOT_STATUS,null);
        if (status.equals(ROBOT_STATUS_TRUE)){
            return true;
        }
        return false;
    }
    public static boolean isRobotInChinese(){
        String pro = SystemUtil.get(SystemUtil.REGION_LANGUAGE, "zh");
        if ("zh".equals(pro)) {
            return true;

        } else {
            return false;
        }
    }
    public static boolean isRobotInChineseStatus(){
        String pro = getRobotInChineseStatus();
        if(TextUtils.isEmpty(pro)){
            if (isChinese()){
                return true;
            }else{
                return false;
            }

        }else{
            return isRobotInChinese();
        }

    }

    private static String PRC_LAUNGUAGE = "zh";
    public static boolean isChinese(){

        String language = Locale.getDefault().getLanguage();
        if (language.equals(PRC_LAUNGUAGE)){
            return true;
        }else{
            return false;
        }
    }

    public static String getRobotInChineseStatus(){
        String pro = SystemUtil.get(SystemUtil.REGION_LANGUAGE, "zh");
        return pro;
    }

    public static String getLtpSn(){
//        return get(SN,null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Build.getSerial();
        }else{
            return get(SN,null);
        }

    }

    public static String getLtpLastSn(){
        String sn = getLtpSn();
        if (TextUtils.isEmpty(sn)){
            return null;
        }else{
            return sn.substring(sn.length()-4);
        }
    }

    public static String getHardCode(){
        return get(HARD_CODE,null);
    }

    public static void setHardCode(String hardCode){
//        set(HARD_CODE,hardCode);
        SystemUtil.set(SystemUtil.HARD_CODE, hardCode);
    }

    public static boolean hasHardCode(){
        if (TextUtils.isEmpty(getHardCode())){
            return false;
        }
        return true;
    }

    public static void setDeviceSign(String deviceSign){
        set(DEVICE_SIGN,deviceSign);
    }

    public static String getDeviceSign(){
        return get(DEVICE_SIGN,null);
    }

    public static boolean hadDeviceSign(){
        if (TextUtils.isEmpty(getDeviceSign())){
            return false;
        }
        return true;
    }

    public static void setRobotInactive() {
        set(ROBOT_STATUS, ROBOT_STATUS_FALSE);

    }
    public static String getWlanMacAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.getName().equals("wlan0")) {
                    StringBuilder mac = new StringBuilder();
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    String hex = Integer.toHexString(hardwareAddress[0] & 0xff);
                    if (hex.length() == 1) {
                        mac.append('0');
                    }
                    mac.append(hex);
                    for (int i = 1; i < hardwareAddress.length; ++i) {
                        mac.append(':');
                        hex = Integer.toHexString(hardwareAddress[i] & 0xff);
                        if (hex.length() == 1) {
                            mac.append('0');
                        }
                        mac.append(hex);
                    }
                    return mac.toString();
                }
            }
        } catch (SocketException ex) {

        }
        return null;
    }

    public static String getMcu() {
        return get(MCU_VERSION, null);
    }

    public static String getIp(Context context) {

        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();

            String ipAddress = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
            return ipAddress;
        }

        return null;
    }
    public static final String REGION_LANGUAGE_ZH = "zh";
    public static final String REGION_LANGUAGE_EN = "en";
    public static String getLanguage() {
        return get(REGION_LANGUAGE, REGION_LANGUAGE_ZH);
    }

    public static boolean isChineseLanguage() {
        if (getLanguage().equals(REGION_LANGUAGE_ZH)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param context
     * @param language
     */
    public static void setDefaultLanguage(Context context, String language) {
        if (TextUtils.isEmpty(language)) {
            return;
        }

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        Locale loc = Locale.CHINA;
        if (language.equals(REGION_LANGUAGE_EN)) {
            loc = Locale.ENGLISH;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(loc);
        } else {
            configuration.locale = loc;
        }

        context.getResources().updateConfiguration(configuration, metrics);
    }

    /**
     * @param context
     */
    public static void setAppLanguage(Context context) {
        if (isInChinese()){
            setDefaultLanguage(context,"zh");
        }else{
            setDefaultLanguage(context,REGION_LANGUAGE_EN);
        }
    }


    public static boolean isInChinese() {
        String pro = SystemUtil.get(SystemUtil.REGION_LANGUAGE, "zh");
        if ("zh".equals(pro)) {
            return true;

        } else {
            return false;
        }
//        return false;
    }



}



