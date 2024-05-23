package com.renhejia.robot.launcher.dispatch.gesture;

import com.renhejia.robot.gesturefactory.parser.GestureData;

import java.util.ArrayList;

/**
 * @author liujunbin
 */
public class GestureCallback {

    private GestureResponseListener mGestureResponseListener;
    private GestureCompleteListener mGestureCompleteListener;
    private GestureCompleteListener mOneShotGestureCompleteListener;

    private static class GestureCallbackHolder {
        private static final GestureCallback instance = new GestureCallback();
    }

    private GestureCallback() {

    }

    public static GestureCallback getInstance() {
        return GestureCallbackHolder.instance;
    }

    public interface GestureResponseListener {
        void onGestureReceived(String gesture);

        void onGestureReceived(String gesture, int gestureId);

        void onGesturesReceived(ArrayList<GestureData> list, int taskId);

        void onGesturesReceived(GestureData gestureData);
    }

    public void setGestureListener(GestureResponseListener listener) {
        this.mGestureResponseListener = listener;
    }

    public void setGestureCompleteListener(GestureCompleteListener listener) {
        this.mGestureCompleteListener = listener;
    }

    public void setOneShotGestureCompleteListener(GestureCompleteListener mOneShotGestureCompleteListener) {
        this.mOneShotGestureCompleteListener = mOneShotGestureCompleteListener;
    }

    public void removeOneShotGestureCompleteListener(GestureCompleteListener mOneShotGestureCompleteListener) {
        this.mOneShotGestureCompleteListener =null;
    }


    public void setGesture(String gesture) {
        if (mGestureResponseListener != null) {
            mGestureResponseListener.onGestureReceived(gesture);
        }
    }

    public void setGesture(String gesture, int geTaskId) {
        if (mGestureResponseListener != null) {
            mGestureResponseListener.onGestureReceived(gesture, geTaskId);
        }
    }

    public void setGestures(ArrayList<GestureData> list, int taskId) {
        if (mGestureResponseListener != null) {
            mGestureResponseListener.onGesturesReceived(list, taskId);
        }
    }

    public void setGesture(GestureData gestureData) {
        if (mGestureResponseListener != null) {
            mGestureResponseListener.onGesturesReceived(gestureData);
        }
    }

    public interface GestureCompleteListener {
        void onGestureCompleted(String gesture, int geTaskId);
    }

    public void setGesturesComplete(String gesture, int geTaskId) {
        if (mGestureCompleteListener != null) {
            mGestureCompleteListener.onGestureCompleted(gesture, geTaskId);
        }
        if (mOneShotGestureCompleteListener != null) {
            mOneShotGestureCompleteListener.onGestureCompleted(gesture, geTaskId);
        }
    }

}
