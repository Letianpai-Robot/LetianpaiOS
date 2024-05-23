package com.renhejia.robot.display.callback;

/**
 * 动画表盘更新回调
 */
public class RobotClockViewUpdateCallback {

    private WatchSpineClockViewUpdateListener mWatchSpineClockViewUpdateListener;

    private static class WatchSpineClockViewUpdateCallbackkHolder {
        private static RobotClockViewUpdateCallback instance = new RobotClockViewUpdateCallback();
    }

    private RobotClockViewUpdateCallback() {

    }

    public static RobotClockViewUpdateCallback getInstance() {
        return WatchSpineClockViewUpdateCallbackkHolder.instance;
    }

    public interface WatchSpineClockViewUpdateListener {
        void onWatchSpineClockViewUpdate(boolean isSpine, String skinPath);

    }

    public void setWatchSpineClockViewUpdateListener(WatchSpineClockViewUpdateListener listener) {
        this.mWatchSpineClockViewUpdateListener = listener;
    }

    public void setWatchSpineClockViewUpdate(boolean isSpine,String skinPath) {
        if (mWatchSpineClockViewUpdateListener != null) {
            mWatchSpineClockViewUpdateListener.onWatchSpineClockViewUpdate(isSpine,skinPath);
        }
    }


}
