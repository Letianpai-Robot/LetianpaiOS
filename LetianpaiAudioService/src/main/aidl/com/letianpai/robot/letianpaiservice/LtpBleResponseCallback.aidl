// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpBleResponseCallback {

    void onBleCmdResponsReceived(String command,String data);

}