package com.renhejia.robot.launcher.displaymode.callback;

import com.letianpai.robot.notice.general.parser.GeneralInfo;

/**
 * @author liujunbin
 */
public class GeneralInfoCallback {

    private GeneralInfoUpdateListener mGeneralInfoUpdateListener;

    private static class GeneralInfoCallbackHolder {
        private static GeneralInfoCallback instance = new GeneralInfoCallback();
    }

    private GeneralInfoCallback() {

    }

    public static GeneralInfoCallback getInstance() {
        return GeneralInfoCallbackHolder.instance;
    }

    public interface GeneralInfoUpdateListener {
        void onAtCmdResultReturn(GeneralInfo generalInfo);

    }

    public void setGeneralInfoUpdateListener(GeneralInfoUpdateListener listener) {
        this.mGeneralInfoUpdateListener = listener;
    }

    public void setGeneralInfo(GeneralInfo generalInfo) {
        if (mGeneralInfoUpdateListener != null) {
            mGeneralInfoUpdateListener.onAtCmdResultReturn(generalInfo);
        }
    }


}
