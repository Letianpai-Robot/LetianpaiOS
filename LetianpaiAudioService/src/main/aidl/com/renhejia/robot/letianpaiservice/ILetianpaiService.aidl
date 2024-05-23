// ILetianpaiService.aidl
package com.renhejia.robot.letianpaiservice;
import com.renhejia.robot.letianpaiservice.LtpCommand;
import com.renhejia.robot.letianpaiservice.LtpCommandCallback;
import com.letianpai.robot.letianpaiservice.LtpLongConnectCallback;
import com.letianpai.robot.letianpaiservice.LtpMcuCommandCallback;
import com.letianpai.robot.letianpaiservice.LtpAudioEffectCallback;
import com.letianpai.robot.letianpaiservice.LtpExpressionCallback;
import com.letianpai.robot.letianpaiservice.LtpAppCmdCallback;
import com.letianpai.robot.letianpaiservice.LtpRobotStatusCallback;
import com.letianpai.robot.letianpaiservice.LtpTTSCallback;
import com.letianpai.robot.letianpaiservice.LtpSpeechCallback;
import com.letianpai.robot.letianpaiservice.LtpSensorResponseCallback;
import com.letianpai.robot.letianpaiservice.LtpMiCmdCallback;
// Declare any non-default types here with import statements

interface ILetianpaiService {

    int getRobotStatus();

    void setCommand(inout LtpCommand ltpCommand);

    void setRobotStatus(int status);

    void registerCallback(LtpCommandCallback cc);

    void unregisterCallback(LtpCommandCallback cc);


    void setLongConnectCommand(String command, String data);

    void registerLCCallback(LtpLongConnectCallback lcCallback);

    void unregisterLCCallback(LtpLongConnectCallback lcCallback);


    void setMcuCommand(String command, String data);

    void registerMcuCmdCallback(LtpMcuCommandCallback mcuCallback);

    void unregisterMcuCmdCallback(LtpMcuCommandCallback mcuCallback);


    void setAudioEffect(String command, String data);

    void registerAudioEffectCallback(LtpAudioEffectCallback aeCallback);

    void unregisterAudioEffectCallback(LtpAudioEffectCallback aeCallback);


    void setExpression(String command, String data);

    void registerExpressionCallback(LtpExpressionCallback expressionCallback);

    void unregisterExpressionCallback(LtpExpressionCallback expressionCallback);


    void setAppCmd(String command, String data);

    void registerAppCmdCallback(LtpAppCmdCallback appCallback);

    void unregisterAppCmdCallback(LtpAppCmdCallback appCallback);


    void setRobotStatusCmd(String command, String data);

    void registerRobotStatusCallback(LtpRobotStatusCallback statusCallback);

    void unregisterRobotStatusCallback(LtpRobotStatusCallback statusCallback);


    void setTTS(String command, String data);

    void registerTTSCallback(LtpTTSCallback ttsCallback);

    void unregisterTTSCallback(LtpTTSCallback ttsCallback);


    void setSpeechCmd(String command, String data);

    void registerSpeechCallback(LtpSpeechCallback speechCallback);

    void unregisterSpeechCallback(LtpSpeechCallback speechCallback);


    void setSensorResponse(String command, String data);

    void registerSensorResponseCallback(LtpSensorResponseCallback speechCallback);

    void unregisterSensorResponseCallback(LtpSensorResponseCallback speechCallback);


    void setMiCmd(String command, String data);

    void registerMiCmdResponseCallback(LtpMiCmdCallback miCmdCallback);

    void unregisterMiCmdResponseCallback(LtpMiCmdCallback miCmdCallback);

}