package com.renhejia.robot.display.utils;

import android.content.Context;


/**
 * @author liujunbin
 */
public class SkinStatusProvider {
    /**
     *
     * @param context
     * @return
     */
    public static boolean is12HourFormat(Context context){
        return !is24HourFormat(context);
    }

    public static boolean is24HourFormat(Context context) {
        return android.text.format.DateFormat.is24HourFormat(context);
    }

}
