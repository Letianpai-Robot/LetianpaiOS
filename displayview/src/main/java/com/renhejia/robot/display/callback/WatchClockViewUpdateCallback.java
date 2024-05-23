package com.renhejia.robot.display.callback;

/**
 * 更新手表表盘状态回调
 */
public class WatchClockViewUpdateCallback {

    private WatchClockViewUpdateListener mWatchClockViewUpdateListener;

    private static class MessageListUpdateCallbackHolder {
        private static WatchClockViewUpdateCallback instance = new WatchClockViewUpdateCallback();
    }

    private WatchClockViewUpdateCallback() {

    }

    public static WatchClockViewUpdateCallback getInstance() {
        return MessageListUpdateCallbackHolder.instance;
    }

    public interface WatchClockViewUpdateListener {
        void onWatchClockViewUpdate(int updateModel, int status);

    }

    public void setMessageListUpdateListener(WatchClockViewUpdateListener listener) {
        this.mWatchClockViewUpdateListener = listener;
    }

    public void setWatchClockViewUpdate(int updateModel,int status) {
        if (mWatchClockViewUpdateListener != null) {
            mWatchClockViewUpdateListener.onWatchClockViewUpdate(updateModel,status);
        }
    }


}
