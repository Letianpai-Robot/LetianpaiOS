package com.renhejia.robot.guidelib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by liujunbin
 */

public class ViewUtils {

    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeRelativeLayoutViewSize(View view, int width, int height) {
        RelativeLayout.LayoutParams bannerlayout =  new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerlayout.width = width;
        bannerlayout.height = height;
        view.setLayoutParams(bannerlayout);
    }

    /**
     * @param view
     */
    public static void resizeRelativeLayoutViewHeightSize(RelativeLayout view, int height) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeImageViewSize(ImageView view, int width, int height) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        params.width = width;
        view.setLayoutParams(params);
    }


    /**
     * 重置dialogView 所在View短边大小
     *
     */
    public static int getViewWidthSize(Context context, Window window) {
        int shortSize = 0;
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        Display display = manager.getDefaultDisplay();
        int displayWidth = (int) (display.getWidth());
        int displayHeight = (int) (display.getHeight());
//        LogUtils.k("displayWidth: "+ displayWidth);
//        LogUtils.k("displayHeight: "+ displayHeight);

        return displayWidth;
    }
    /**
     * 获取View高度
     *
     */
    public static int getViewHeightSize(Context context, Window window) {
        int shortSize = 0;
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        Display display = manager.getDefaultDisplay();
        int displayWidth = (int) (display.getWidth());
        int displayHeight = (int) (display.getHeight());
//        LogUtils.k("displayWidth: "+ displayWidth);
//        LogUtils.k("displayHeight: "+ displayHeight);

        return displayHeight;
    }

    /**
     * 填充titlebar
     * @param window
     */
    public static void fillTitleBar(Window window){
        window.requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 获取statusbar高度
     */
    public static int getStatusBarHeight(Context context){
        int statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusBarHeight2;
    }

    /**
     * 填充titlebar
     */
    public static void resizeStatusAndTitlebar(Context context, RelativeLayout relativeLayout, int viewHeight){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int statusBarHeight = ViewUtils.getStatusBarHeight(context);

            ViewUtils.resizeRelativeLayoutViewHeightSize(relativeLayout,viewHeight + statusBarHeight);
        }else{
            ViewUtils.resizeRelativeLayoutViewHeightSize(relativeLayout,viewHeight );
        }
    }




}
