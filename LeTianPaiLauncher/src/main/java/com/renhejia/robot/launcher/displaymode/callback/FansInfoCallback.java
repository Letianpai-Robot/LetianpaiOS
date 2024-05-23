package com.renhejia.robot.launcher.displaymode.callback;

import com.letianpai.robot.notice.general.parser.GeneralInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;

/**
 * @author liujunbin
 */
public class FansInfoCallback {

    private FansInfoUpdateListener mFansInfoUpdateListener;

    private static class FansInfoCallbackHolder {
        private static FansInfoCallback instance = new FansInfoCallback();
    }

    private FansInfoCallback() {

    }

    public static FansInfoCallback getInstance() {
        return FansInfoCallbackHolder.instance;
    }

    public interface FansInfoUpdateListener {
        void onFansInfoUpdate(FansInfo fansInfo);

    }

    public void setFansInfoUpdateListener(FansInfoUpdateListener listener) {
        this.mFansInfoUpdateListener = listener;
    }

    public void setFansInfo(FansInfo fansInfo) {
        if (mFansInfoUpdateListener != null) {
            mFansInfoUpdateListener.onFansInfoUpdate(fansInfo);
        }
    }

}
