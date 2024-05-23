// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpRobotStatusCallback {

    void onRobotStatusChanged(String command,String data);


}