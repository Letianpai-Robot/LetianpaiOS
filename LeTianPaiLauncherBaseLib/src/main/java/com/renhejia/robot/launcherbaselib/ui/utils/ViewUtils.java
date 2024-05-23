package com.renhejia.robot.launcherbaselib.ui.utils;

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
     * @param context
     * @param view
     */
    public static void resizeLinearViewSize(Activity context, View view) {
        int screenWidth = context.getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams bannerlayout = (LinearLayout.LayoutParams) (view.getLayoutParams());
        bannerlayout.width = screenWidth * 2 / 3;
//        bannerlayout.height = screenWidth * height / width;

        view.setLayoutParams(bannerlayout);

    }

    /**
     * @param context
     * @param view
     * @param width
     * @param height
     */
    public static void resizeLinearLayoutViewSize0(Activity context, View view, int width, int height) {
        LinearLayout.LayoutParams bannerlayout = (LinearLayout.LayoutParams) (view.getLayoutParams());
        bannerlayout.width = width;
        bannerlayout.height = height;
        view.setLayoutParams(bannerlayout);
    }
    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeLinearLayoutViewSize(View view, int width, int height,int left,int top,int right,int bottom) {
        LinearLayout.LayoutParams bannerlayout =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerlayout.width = width;
        bannerlayout.height = height;
        bannerlayout.setMargins(left,top,right,bottom);
        view.setLayoutParams(bannerlayout);
    }

    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeLinearLayoutViewSize(LinearLayout view, int width, int height) {
        LinearLayout.LayoutParams bannerlayout = (LinearLayout.LayoutParams) (view.getLayoutParams());
        bannerlayout.width = width;
        bannerlayout.height = height;
        view.setLayoutParams(bannerlayout);
    }

    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeGridLayoutViewSize(LinearLayout view, int width, int height) {
//        GridLayout.LayoutParams bannerlayout = (GridLayout.LayoutParams) (view.getLayoutParams());
        GridLayout.LayoutParams bannerlayout = new GridLayout.LayoutParams();
        bannerlayout.width = width;
        bannerlayout.height = height;
        view.setLayoutParams(bannerlayout);
    }

    /**
     * @param view
     * @param width
     */
    public static void resizeGridLayoutViewSize(LinearLayout view, int width) {
        GridLayout.LayoutParams bannerlayout = new GridLayout.LayoutParams();
        bannerlayout.width = width;
        view.setLayoutParams(bannerlayout);
    }


    /**
     * @param view
     * @param height
     */
    public static void resizeLinearLayoutViewSize(View view, int height) {
        LinearLayout.LayoutParams bannerlayout = (LinearLayout.LayoutParams) (view.getLayoutParams());
        bannerlayout.height = height;
        view.setLayoutParams(bannerlayout);
    }

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
     * @param width
     * @param height
     */
    public static void resizeRelativeLayoutViewSize0(View view, int width, int height) {
        RelativeLayout.LayoutParams bannerlayout =  new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerlayout.width = width;
        bannerlayout.height = height;
        view.setLayoutParams(bannerlayout);
    }
    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeRelativeLayoutViewSize0(View view, int width, int height,int left,int top) {
        RelativeLayout.LayoutParams bannerlayout =  new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerlayout.width = width;
        bannerlayout.height = height;
        bannerlayout.setMargins(left,top,0,0);
        view.setLayoutParams(bannerlayout);
    }

    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeLinearLayoutViewSize0(View view, int width, int height,int left,int top) {
        LinearLayout.LayoutParams bannerlayout =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerlayout.width = width;
        bannerlayout.height = height;
        bannerlayout.setMargins(left,top,0,0);
        view.setLayoutParams(bannerlayout);
    }
    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeLinearLayoutViewSize(View view, int width, int height) {
        LinearLayout.LayoutParams bannerlayout =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerlayout.width = width;
        bannerlayout.height = height;
        view.setLayoutParams(bannerlayout);
    }

    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeViewSize(View view, int width, int height) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        params.width = width;
        view.setLayoutParams(params);
    }

    /**
     * @param view
     */
    public static void resizeRelativeLayoutViewSize(RelativeLayout view, int size) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        params.width = size;
        view.setLayoutParams(params);
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
     */
    public static void resizeRelativeLayoutViewWidthSize(RelativeLayout view, int width) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
    }
    /**
     * @param view
     */
    public static void resizeLinearLayoutViewHeightSize(LinearLayout view, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    /**
     * @param view
     */
    public static void resizeLinearLayoutViewHeightSize1(LinearLayout view, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getLayoutParams());
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
     * @param view
     * @param width
     * @param height
     */
    public static void resizeTextViewSize(TextView view, int width, int height) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        params.width = width;
        view.setLayoutParams(params);
    }
    /**
     * @param view
     * @param height
     */
    public static void resizeTextViewSize(TextView view,int height) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeImageViewSize(ImageView view, int width, int height,int left,int right) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        params.width = width;
        view.setLayoutParams(params);
    }

    /**
     * @param view
     * @param width
     * @param height
     */
    public static void resizeButtonViewSize(Button view, int width, int height) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        params.width = width;
        view.setLayoutParams(params);
    }


    /**
     * 重置dialogView 大小
     *
     * @param width
     * @param height
     */
    public static void resizeViewSize(Context context, Window window, int width, int height) {

        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        Display display = manager.getDefaultDisplay();
        params.width =  width;
        params.height = height;
        window.setAttributes(params);
    }

    /**
     * 重置dialogView 所在View短边大小
     *
     * @param width
     * @param height
     */
    public static int getAnswerDialogDisplaySize(Context context, Window window, float width, float height) {
        int shortSize = 0;
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        Display display = manager.getDefaultDisplay();
        int displayWidth = (int) (display.getWidth() * width);
        int displayHeight = (int) (display.getHeight() * height);
        if (displayWidth > displayHeight) {
            shortSize = displayHeight;
        } else {
            shortSize = displayWidth;
        }
        return shortSize;
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


//    /**
//     *
//     * @param context
//     * @param view
//     */
//    public static void resizeViewSize(Activity context, View view) {
//        int screenWidth = context.getWindowManager().getDefaultDisplay().getWidth();
//        RelativeLayout.LayoutParams bannerlayout = (RelativeLayout.LayoutParams) (view.getLayoutParams());
//        bannerlayout.width = screenWidth * 2 / 3;
//
//        view.setLayoutParams(bannerlayout);
//
//    }

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
    /**
     * 填充titlebar
     */
    public static void resizeStatusAndTitlebar(Context context, RelativeLayout relativeLayout){
        int height = DensityUtil.dip2px(context,55);
        resizeStatusAndTitlebar(context,relativeLayout,height);
    }


    public static void moveRelateLayout(int left, int top, ViewGroup relativeLayout) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, top, 0, 0);
        relativeLayout.setLayoutParams(lp);
    }

    public static void moveLinearLayout(int left, int top, LinearLayout linearLayout) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(linearLayout.getLayoutParams());
        lp.setMargins(0, top, 0, 0);
        linearLayout.setLayoutParams(lp);
    }

    public static void moveLinearLayout0(int left, int top, LinearLayout linearLayout) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, top, 0, 0);
        linearLayout.setLayoutParams(lp);
    }

    public static void moveLinearImageView(int left, int top, ImageView imageView) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getLayoutParams());
        lp.setMargins(0, top, 0, 0);
        imageView.setLayoutParams(lp);
    }

    public static void moveRelativeImageView(int left, int top, ImageView imageView) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(imageView.getLayoutParams());
        lp.setMargins(left, top, 0, 0);
        imageView.setLayoutParams(lp);
    }



}
