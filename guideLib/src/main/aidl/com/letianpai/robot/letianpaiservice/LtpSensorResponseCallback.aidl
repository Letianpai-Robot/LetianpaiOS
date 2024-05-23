// LtpCommandCallback.aidl
package com.letianpai.robot.letianpaiservice;
// Declare any non-default types here with import statements

interface LtpSensorResponseCallback {

    void onSensorResponse(String command,String data);


}