package com.renhejia.robot.launcherbaselib.ui.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtil {
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);  
    }


    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);  
    }


    //获取屏幕density
    public final static float getScreenDensity(Context context) {
        final DisplayMetrics m = context.getResources().getDisplayMetrics();
        return m.density;
    }


    /****
     * 获取屏幕宽度
     * @param context
     */
    public final static int getScreenWidthDimension(Context context) {
        final DisplayMetrics m = context.getResources().getDisplayMetrics();
        return m.widthPixels;
    }


    /****
     * 获取屏幕高度
     * @param context
     */
    public final static int getScreenHeightDimension(Context context) {
        final DisplayMetrics m = context.getResources().getDisplayMetrics();
        return m.heightPixels;
    }


}

