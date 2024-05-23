// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpIdentifyCmdCallback{

    void onIdentifyCommandReceived(String command,String data);


}