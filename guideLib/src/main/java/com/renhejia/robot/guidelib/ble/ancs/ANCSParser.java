package com.renhejia.robot.guidelib.ble.ancs;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ANCSParser {
    private ANCSData mCurrentANCSData;
    protected static final String TAG = "ANCSParser";

    public ANCSData getmCurrentANCSData() {
        return mCurrentANCSData;
    }

    public void setmCurrentANCSData(ANCSData mCurrentANCSData) {
        this.mCurrentANCSData = mCurrentANCSData;
    }

    public void onDSNotification(byte[] data) {
        mCurrentANCSData = new ANCSData(data);
        Log.i(TAG, "noti.title:" + mCurrentANCSData.notification.title);
        Log.i(TAG, "noti.message:" + mCurrentANCSData.notification.message);
        Log.i(TAG, "noti.date:" + mCurrentANCSData.notification.date);
        Log.i(TAG, "noti.subtitle:" + mCurrentANCSData.notification.subtitle);
        Log.i(TAG, "noti.messageSize:" + mCurrentANCSData.notification.messageSize);
        Log.i(TAG, "noti.bundleid:" + mCurrentANCSData.notification.bundleId);
        Log.i(TAG, "got a notification! data size = " + mCurrentANCSData.notifyData.length);
    }
}
