// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpAppCmdCallback {

    void onAppCommandReceived(String command,String data);


}