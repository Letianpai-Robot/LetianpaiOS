package com.renhejia.robot.launcher.displaymode.callback;


import com.renhejia.robot.launcher.displaymode.display.DisplayMode;

/**
 * Created by liujunbin
 */

public class DisplayInfoUpdateCallback {

    private DisplayInfoUpdateListener mDisplayInfoUpdateListener;

    private static class DisplayInfoUpdateCallbackHolder {
        private static DisplayInfoUpdateCallback instance = new DisplayInfoUpdateCallback();
    }

    private DisplayInfoUpdateCallback() {

    }

    public static DisplayInfoUpdateCallback getInstance() {
        return DisplayInfoUpdateCallbackHolder.instance;
    }

    public interface DisplayInfoUpdateListener {
        void updateDisplayList(DisplayMode displayMode);
    }

    public void seDisplayInfoUpdateListener(DisplayInfoUpdateListener listener) {
        this.mDisplayInfoUpdateListener = listener;
    }

    public void updateDisplayViewInfo(DisplayMode displayMode) {
        if (mDisplayInfoUpdateListener != null) {
            mDisplayInfoUpdateListener.updateDisplayList(displayMode);
        }
    }





}
