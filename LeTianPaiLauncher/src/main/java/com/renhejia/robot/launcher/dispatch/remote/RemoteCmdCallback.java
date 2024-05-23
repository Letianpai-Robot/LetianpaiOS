package com.renhejia.robot.launcher.dispatch.remote;

/**
 *
 * @author liujunbin
 */
public class RemoteCmdCallback {

    private RemoteCmdListener mRemoteCmdListener;

    private static class RemoteCmdCallbackHolder {
        private static RemoteCmdCallback instance = new RemoteCmdCallback();
    }

    private RemoteCmdCallback() {

    }

    public static RemoteCmdCallback getInstance() {
        return RemoteCmdCallbackHolder.instance;
    }

    public interface RemoteCmdListener {
        void onRemoteCmdReceived(String commandType, Object commandData);
    }

    public void setRemoteCmdReceivedListener(RemoteCmdListener listener) {
        this.mRemoteCmdListener = listener;
    }

    public void setRemoteCmd(String commandType, Object commandData) {
        if (mRemoteCmdListener != null) {
            mRemoteCmdListener.onRemoteCmdReceived(commandType, commandData);
        }
    }


}
