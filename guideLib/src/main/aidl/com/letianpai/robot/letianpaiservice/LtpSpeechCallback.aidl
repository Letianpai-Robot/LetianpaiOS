// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpSpeechCallback {

    void onSpeechCommandReceived(String command,String data);

}