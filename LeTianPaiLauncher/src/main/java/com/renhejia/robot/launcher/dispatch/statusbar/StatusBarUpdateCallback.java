package com.renhejia.robot.launcher.dispatch.statusbar;

/**
 * 表情切换
 * @author liujunbin
 */
public class StatusBarUpdateCallback {

    private StatusBarChangeListener mStatusBarChangeListener;

    private static class StatusBarUpdateCallbackHolder {
        private static StatusBarUpdateCallback instance = new StatusBarUpdateCallback();
    }

    private StatusBarUpdateCallback() {

    }

    public static StatusBarUpdateCallback getInstance() {
        return StatusBarUpdateCallbackHolder.instance;
    }

    public interface StatusBarChangeListener {
        void onStatusBarTextChanged(String viewName);
    }

    public void setStatusBarTextChangeListener(StatusBarChangeListener listener) {
        this.mStatusBarChangeListener = listener;
    }

    public void setStatusBarText(String viewName) {
        if (mStatusBarChangeListener != null) {
            mStatusBarChangeListener.onStatusBarTextChanged(viewName);
        }
    }


}
