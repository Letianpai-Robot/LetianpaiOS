// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpMcuCommandCallback {

    void onMcuCommandCommand(String command,String data);

}