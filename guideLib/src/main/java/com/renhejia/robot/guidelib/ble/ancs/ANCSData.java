package com.renhejia.robot.guidelib.ble.ancs;

import android.util.Log;


public class ANCSData {
    protected static final String TAG = "ANCSData";

    long timeExpired;
    int curStep = 0;

    final byte[] notifyData; // 8 bytes

    IOSNotification notification;

    ANCSData(byte[] data) {
        notifyData = data;
        curStep = 0;
        timeExpired = System.currentTimeMillis();
        notification = new IOSNotification();
        initNotification();
    }


    int getUID() {
        return (0xff & notifyData[7] << 24) | (0xff & notifyData[6] << 16) | (0xff & notifyData[5] << 8) | (0xff & notifyData[4]);
    }

    private void initNotification() {

        logD(notifyData);
        if (notifyData.length < 5) {
            return;
        }
        // check if finished ?
        int cmdId = notifyData[0]; // should be 0 //0 commandID
        // if (cmdId != 0) {
        //     Log.i(TAG, "bad cmdId: " + cmdId);
        //     return;
        // }
        int uid = ((0xff & notifyData[4]) << 24) | ((0xff & notifyData[3]) << 16) | ((0xff & notifyData[2]) << 8) | ((0xff & notifyData[1]));
        // if (uid != mCurrentANCSData.getUID()) {
        //
        //     Log.i(TAG, "bad uid: " + uid + "->" + mCurrentANCSData.getUID());
        //     return;
        // }

        // read attributes
        notification.uid = uid;
        int curIdx = 5; // hard code
        while (!notification.isAllInit()) {

            if (notifyData.length < curIdx + 3) {
                return;
            }
            // attributes head
            int attrId = notifyData[curIdx];
            int attrLen = ((notifyData[curIdx + 1]) & 0xFF) | (0xFF & (notifyData[curIdx + 2] << 8));
            curIdx += 3;
            if (notifyData.length < curIdx + attrLen) {
                return;
            }
            String val = new String(notifyData, curIdx, attrLen);// utf-8 encode
            if (attrId == GlobalDefine.NotificationAttributeIDTitle) {
                notification.title = val;
            } else if (attrId == GlobalDefine.NotificationAttributeIDMessage) {
                notification.message = val;
            } else if (attrId == GlobalDefine.NotificationAttributeIDDate) {
                notification.date = val;
            } else if (attrId == GlobalDefine.NotificationAttributeIDSubtitle) {
                notification.subtitle = val;
            } else if (attrId == GlobalDefine.NotificationAttributeIDMessageSize) {
                notification.messageSize = val;
            } else if (attrId == GlobalDefine.NotificationAttributeIDBundleId) {
                notification.bundleId = val;
            }
            curIdx += attrLen;
        }
    }

    void logD(byte[] d) {
        StringBuffer sb = new StringBuffer();
        int len = d.length;
        for (int i = 0; i < len; i++) {
            sb.append(d[i] + ", ");
        }
        Log.i(TAG, "log Data size[" + len + "] : " + sb);
    }
}