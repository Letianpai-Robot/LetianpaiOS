package com.renhejia.robot.launcher.dispatch.service;

/**
 * 模式切换状态回调
 *
 * @author liujunbin
 */
public class StartServiceCallback {

    private StartServiceListener mStartServiceListener;

    private static class StartServiceCallbackHolder {
        private static StartServiceCallback instance = new StartServiceCallback();
    }

    private StartServiceCallback() {

    }

    public static StartServiceCallback getInstance() {
        return StartServiceCallbackHolder.instance;
    }

    public interface StartServiceListener {
        void onServiceStart();
    }

    public void setStartServiceListener(StartServiceListener listener) {
        this.mStartServiceListener = listener;
    }

    public void startService() {
        if (mStartServiceListener != null) {
            mStartServiceListener.onServiceStart();
        }
    }
}
