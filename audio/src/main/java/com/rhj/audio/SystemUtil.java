package com.rhj.audio;

import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liujunbin
 */
public class SystemUtil {
    private static Method sysPropGet;
    private static Method sysPropGetInt;
    private static Method sysPropSet;
    private static final String SN = "ro.serialno";

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

    public static String getLtpSn(){
        return get(SN,null);
    }
    public static String getLtpLastSn(){
        String sn = getLtpSn();
        if (TextUtils.isEmpty(sn)){
            return null;
        }else{
            return sn.substring(sn.length()-4);
        }
    }

}
