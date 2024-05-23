package com.renhejia.robot.launcher.callback;

/**
 * 模式切换状态回调
 *
 * @author liujunbin
 */
public class CommandResponseCallback {
    private LTPAppCmdResponseListener mLTPAppCmdResponseListener;

    private static class CommandResponseCallbackHolder {
        private static CommandResponseCallback instance = new CommandResponseCallback();
    }

    public static CommandResponseCallback getInstance() {
        return CommandResponseCallbackHolder.instance;
    }

    private CommandResponseCallback() {

    }

    public interface LTPAppCmdResponseListener {
        void onAppCmdReceived(String command, String data);
    }

    public void setAppCommand(String command,String data) {
        if (mLTPAppCmdResponseListener != null) {
            mLTPAppCmdResponseListener.onAppCmdReceived(command,data);
        }
    }


}
