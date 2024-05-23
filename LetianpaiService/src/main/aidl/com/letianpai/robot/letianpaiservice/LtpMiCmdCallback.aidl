// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpMiCmdCallback {

    void onMiCommandReceived(String command,String data);


}