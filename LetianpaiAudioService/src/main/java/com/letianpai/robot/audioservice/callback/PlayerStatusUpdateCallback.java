package com.letianpai.robot.audioservice.callback;


/**
 * 播放器回调
 * Created by liujunbin
 */

public class PlayerStatusUpdateCallback {

    private PlayerStatusUpdateListener mPlayerStatusUpdateListener;

    private static class PlayerStatusUpdateCallbackHolder {
        private static PlayerStatusUpdateCallback instance = new PlayerStatusUpdateCallback();
    }

    private PlayerStatusUpdateCallback() {

    }

    public static PlayerStatusUpdateCallback getInstance() {
        return PlayerStatusUpdateCallbackHolder.instance;
    }

    public interface PlayerStatusUpdateListener {
        void onPlayStatusUpdate(int playStatus);
        void onPlayCompletion(int laveLoop);
        void onPlayError();
    }

    public void setPlayerStatusUpdateListener(PlayerStatusUpdateListener listener) {
        this.mPlayerStatusUpdateListener = listener;
    }

    public void setPlayStatusUpdate(int playStatus) {
        if (mPlayerStatusUpdateListener != null) {
            mPlayerStatusUpdateListener.onPlayStatusUpdate(playStatus);
        }
    }
    public void setPlayComplete(int laveLoop) {
        if (mPlayerStatusUpdateListener != null) {
            mPlayerStatusUpdateListener.onPlayCompletion(laveLoop);
        }
    }
    public void setPlayError() {
        if (mPlayerStatusUpdateListener != null) {
            mPlayerStatusUpdateListener.onPlayError();
        }
    }



}
