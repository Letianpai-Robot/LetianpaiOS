package com.renhejia.robot.launcherbaselib.facedetect;

import android.content.Context;

import com.renhejia.robot.launcherbaselib.broadcast.LauncherBroadcastReceiverManager;

/**
 * 人脸识别
 */
public class FaceDetectManager {

    private static FaceDetectManager instance;
    private Context mContext;

    public FaceDetectManager(Context context) {
        this.mContext = context;
    }

    public static FaceDetectManager getInstance(Context context) {
        synchronized (FaceDetectManager.class) {
            if (instance == null) {
                instance = new FaceDetectManager(context.getApplicationContext());
            }
            return instance;
        }
    }

    public String detectObject(int type, Object object){

        return null;

    }

}