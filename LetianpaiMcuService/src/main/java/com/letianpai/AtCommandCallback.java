package com.letianpai;

/**
 * At命令回调
 */
public class AtCommandCallback {

    private AtCmdResultReturnListener mAtCmdResultReturnListener;

    private static class AtCommandCallbackHolder {
        private static AtCommandCallback instance = new AtCommandCallback();
    }

    private AtCommandCallback() {

    }

    public static AtCommandCallback getInstance() {
        return AtCommandCallbackHolder.instance;
    }

    public interface AtCmdResultReturnListener {
        void onAtCmdResultReturn(String atCmdResult);

    }

    public void setAtCmdResultReturnListener(AtCmdResultReturnListener listener) {
        this.mAtCmdResultReturnListener = listener;
    }

    public void setAtCmdResultReturn(String atCmdResult) {
        if (mAtCmdResultReturnListener != null) {
            mAtCmdResultReturnListener.onAtCmdResultReturn(atCmdResult);
        }
    }

}
