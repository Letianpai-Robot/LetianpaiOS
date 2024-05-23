// LtpCommandCallback.aidl
package com.renhejia.robot.letianpaiservice;
import com.renhejia.robot.letianpaiservice.LtpCommand;
// Declare any non-default types here with import statements

interface LtpCommandCallback {

    void onRobotStatusChanged(int status);

    void onCommandReceived(inout LtpCommand ltpCommand);


}