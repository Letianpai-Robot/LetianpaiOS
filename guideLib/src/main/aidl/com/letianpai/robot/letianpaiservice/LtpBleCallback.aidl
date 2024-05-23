// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpBleCallback {

    void onBleCmdReceived(String command,String data,boolean isNeedResponse);

}