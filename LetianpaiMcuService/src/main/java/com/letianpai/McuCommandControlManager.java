package com.letianpai;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.renhejia.robot.commandlib.log.LogUtils;

import com.google.gson.Gson;
import com.renhejia.robot.commandlib.consts.ATCmdConsts;
import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.commandlib.parser.antennalight.AntennaLight;
import com.renhejia.robot.commandlib.parser.antennamotion.AntennaMotion;
import com.renhejia.robot.commandlib.parser.motion.Motion;
import com.renhejia.robot.letianpaiservice.LtpCommand;

import top.keepempty.sph.library.SerialPortJNI;

/**
 *
 */
public class McuCommandControlManager {
    private Gson mGson;


    private static McuCommandControlManager instance;
    private Context mContext;


    private McuCommandControlManager(Context context) {
        this.mContext = context;
        init(context);
    }

    public static McuCommandControlManager getInstance(Context context) {
        synchronized (McuCommandControlManager.class) {
            if (instance == null) {
                instance = new McuCommandControlManager(context.getApplicationContext());
            }
            return instance;
        }
    }

    private void init(Context context) {
        mGson = new Gson();
    }

    public void commandDistribute(LtpCommand ltpCommand) {
        commandDistribute(ltpCommand.getCommand(), ltpCommand.getData());
    }

    public void commandDistribute(String command, String data) {
        if (command.equals(MCUCommandConsts.COMMAND_TYPE_MOTION)) {
            LogUtils.logd("McuCommandControlManager", "mcu 执行动作：" + data);
            responseMotion(data);

        } else if (command.equals(MCUCommandConsts.COMMAND_TYPE_ANTENNA_LIGHT)) {
            LogUtils.logd("McuCommandControlManager", "mcu 执行天线灯光：" + data);
            responseAntennaLight(data);

        } else if (command.equals(MCUCommandConsts.COMMAND_TYPE_ANTENNA_MOTION)) {
            LogUtils.logd("McuCommandControlManager", "mcu 执行天线：" + data);
            responseAntennaMotion(data);
        }
    }

    private void responseAntennaMotion(String data) {
        AntennaMotion antennaMotion = mGson.fromJson(data,AntennaMotion.class);
        if ((antennaMotion == null) || (antennaMotion.getAntenna_motion() == null) ){
            return;
        }
        if (antennaMotion.getAntenna_motion().equals(MCUCommandConsts.COMMAND_VALUE_ANTENNA_MOTION_DIY)){
            if ((antennaMotion.getNumber() != 0) && (antennaMotion.getDelay()!= 0) && (antennaMotion.getStep() != 0)){
                turnAntennaMotion(antennaMotion.getNumber(),antennaMotion.getStep(),antennaMotion.getDelay());
            }

        }else if(antennaMotion.getAntenna_motion().equals(MCUCommandConsts.COMMAND_VALUE_ANTENNA_MOTION)){
            turnAntennaMotion();
        }

    }


    private void responseAntennaLight(String data) {
        AntennaLight light = mGson.fromJson(data, AntennaLight.class);
        if (light == null) {
            return;
        }
        String lightCommand = light.getAntenna_light();
        int color = light.getAntenna_light_color();

        if (lightCommand.equals(MCUCommandConsts.COMMAND_TYPE_ANTENNA_LIGHT_VALUE_ON)) {
            //TODO
            light(color);
        } else if (lightCommand.equals(MCUCommandConsts.COMMAND_TYPE_ANTENNA_LIGHT_VALUE_OFF)) {
            lightOff();
        }

    }

    private void responseMotion(String data) {
        LogUtils.logd("McuCommandControlManager", "responseMotion: " + data);
        Motion motion = mGson.fromJson(data, Motion.class);
        String motionType;

        int number = 1;
        if (motion != null) {
            motionType = motion.getMotion();
            number = motion.getNumber();
            if (number == 0) {
                number = 1;
            }
            if (TextUtils.isEmpty(motionType)) {
                return;
            }
            LogUtils.logd("McuCommandControlManager", "responseMotion: "+motionType);
            //number 不为零，默认是有id
            if (motionType==null||motionType.equals("null")) {
                int stepNum = 1;
                if (motion.getStepNum() > 0) {
                    stepNum = motion.getStepNum();
                }
                LogUtils.logd("McuCommandControlManager", "responseMotion: if=="+motion);
                McuResponseUtil.consumeATCommand(motion.getNumber(), stepNum, 3);
            } else {
                LogUtils.logd("McuCommandControlManager", "responseMotion: else=="+motion);
                switch (motionType) {
                    case MCUCommandConsts.COMMAND_VALUE_MOTION_FORWARD:
                    case ATCmdConsts.AT_STR_MOVEW_FORWARD:
                        McuResponseUtil.walkForward(number);
                        break;
                    case MCUCommandConsts.COMMAND_VALUE_MOTION_BACKEND:

                    case ATCmdConsts.AT_STR_MOVEW_BACK:
                        McuResponseUtil.walkBackend(number);
                        break;
                    case MCUCommandConsts.COMMAND_VALUE_MOTION_LEFT:

                    case ATCmdConsts.AT_STR_MOVEW_CRAB_STEP_LEFT:
                        McuResponseUtil.crabStepLeft(number);

                        break;
                    case MCUCommandConsts.COMMAND_VALUE_MOTION_RIGHT:

                    case ATCmdConsts.AT_STR_MOVEW_CRAB_STEP_RIGHT:
                        //                walkRight(number);
                        McuResponseUtil.crabStepRight(number);

                        break;
                    case MCUCommandConsts.COMMAND_VALUE_MOTION_LEFT_ROUND:
                    case ATCmdConsts.AT_STR_MOVEW_LOCAL_ROUND_LEFT:
                        McuResponseUtil.localRoundLeft(number);

                        break;
                    case MCUCommandConsts.COMMAND_VALUE_MOTION_RIGHT_ROUND:
                    case ATCmdConsts.AT_STR_MOVEW_TURN_RIGHT:
                    case ATCmdConsts.AT_STR_MOVEW_LOCAL_ROUND_RIGHT:
                        McuResponseUtil.localRoundRight(number);

                        break;
                    case MCUCommandConsts.COMMAND_VALUE_MOTION_SET_STRAIGHT:
                    case ATCmdConsts.AT_STR_MOVEW_STAND:
                        McuResponseUtil.walkStand();
                        //TODO 增加回正逻辑
                        break;

                    case ATCmdConsts.AT_STR_MOVEW_TURN_LEFT:
                        McuResponseUtil.turnLeft(number);
                        break;

                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_LEG:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_LEG1:
                        McuResponseUtil.shakeLeftLeg(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEG:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEG1:
                        McuResponseUtil.shakeRightLeg(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_FOOT:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_FOOT1:
                        McuResponseUtil.shakeLeftFoot(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_FOOT:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_FOOT1:
                        McuResponseUtil.shakeRightFoot(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_LEFT_FOOT:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_LEFT_FOOT1:
                        McuResponseUtil.shakeCrossLeftFoot(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_RIGHT_FOOT:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_RIGHT_FOOT1:
                        McuResponseUtil.shakeCrossRightFoot(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_LEANING:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_LEANING1:
                        McuResponseUtil.shakeLeftLeaning(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEANING:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEANING1:
                        McuResponseUtil.shakeRightLeaning(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_STAMP_LEFT_FOOT:
                    case ATCmdConsts.AT_STR_MOVEW_STAMP_LEFT_FOOT1:
                        McuResponseUtil.stampLeftFoot(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_STAMP_RIGHT_FOOT:
                    case ATCmdConsts.AT_STR_MOVEW_STAMP_RIGHT_FOOT1:
                        McuResponseUtil.stampRightFoot(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_SWAYING_UP_AND_DOWN:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_SWAYING_UP_AND_DOWN1:
                        McuResponseUtil.swayingUpdAndDown(number);

                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_SWINGS_FROM_SIDE_TO_SIDE:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_SWINGS_FROM_SIDE_TO_SIDE1:
                        McuResponseUtil.swingsFromSideToSide(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_HEAD:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_HEAD1:
                        McuResponseUtil.shakeHead(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_STAND_AT_EASE:
                    case ATCmdConsts.AT_STR_MOVEW_STAND_AT_EASE1:
                    case ATCmdConsts.AT_STR_MOVEW_SHAKE_LEG:
                    case ATCmdConsts.AT_STR_MOVEW_FEET_TREMOR:
                        McuResponseUtil.standAtEase(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_MICROTREMOR:
                        McuResponseUtil.microTremor(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_29:
                        McuResponseUtil.atMovEW29(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_30:
                        McuResponseUtil.atMovEW30(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_31:
                        McuResponseUtil.atMovEW31(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_32:
                        McuResponseUtil.atMovEW32(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_33:
                        McuResponseUtil.atMovEW33(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_34:
                        McuResponseUtil.atMovEW34(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_35:
                        McuResponseUtil.atMovEW35(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_36:
                        McuResponseUtil.atMovEW36(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_37:
                        McuResponseUtil.atMovEW37(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_38:
                        McuResponseUtil.atMovEW38(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_39:
                        McuResponseUtil.atMovEW39(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_40:
                        McuResponseUtil.atMovEW40(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_41:
                        McuResponseUtil.atMovEW41(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_42:
                        McuResponseUtil.atMovEW42(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_43:
                        McuResponseUtil.atMovEW43(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_44:
                        McuResponseUtil.atMovEW44(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_45:
                        McuResponseUtil.atMovEW45(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_46:
                        McuResponseUtil.atMovEW46(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_47:
                        McuResponseUtil.atMovEW47(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_48:
                        McuResponseUtil.atMovEW48(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_49:
                        McuResponseUtil.atMovEW49(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_50:
                        McuResponseUtil.atMovEW50(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_51:
                        McuResponseUtil.atMovEW51(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_52:
                        McuResponseUtil.atMovEW52(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_53:
                        McuResponseUtil.atMovEW53(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_54:
                        McuResponseUtil.atMovEW54(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_55:
                        McuResponseUtil.atMovEW55(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_56:
                        McuResponseUtil.atMovEW56(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_57:
                        McuResponseUtil.atMovEW57(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_58:
                        McuResponseUtil.atMovEW58(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_59:
                        McuResponseUtil.atMovEW59(number);
                        break;
                    case ATCmdConsts.AT_STR_MOVEW_60:
                        McuResponseUtil.atMovEW60(number);
                        break;
                }
            }
        }
        //walkBackend(2);

    }


    public void consumeATCommand(String command) {
        LogUtils.logi("letianpai", "consumeATCommand  ======= 1");
        if (!TextUtils.isEmpty(command)) {
            LogUtils.logi("letianpai", "consumeATCommand  ======= 2");
            SerialPortJNI.writePort(command.getBytes());
        }

    }

    private void walkForward(int stepNum) {
        walks(1, stepNum);
    }

    private void walkBackend(int stepNum) {
        walks(2, stepNum);
    }

    private void walkLeft(int stepNum) {
        walks(3, stepNum);
    }

    private void walkRight(int stepNum) {
        walks(4, stepNum);
    }

    private void walks(int moveCommand, int stepNum) {
        String command = String.format("AT+MOVEW,%d,%d,2\\r\\n", moveCommand, stepNum);
        LogUtils.logi("letianpai_walks", "command: " + command);
        consumeATCommand(command);
    }

    private void turnEarLeftRound() {
        consumeATCommand("AT+EARW,1,2,2\\r\\n");
    }

    private void turnEarRightRound() {
        consumeATCommand("AT+EARW,2,2,2\\r\\n");
    }

    public void light(int lightColor) {

        String command = String.format("AT+LEDOn,%d\\r\\n", lightColor);
        LogUtils.logi("letianpai_walks", "command: " + command);
        consumeATCommand(command);
    }

    public void lightOff() {

        String command = String.format("AT+LEDOff\\r\\n");
        LogUtils.logi("letianpai_walks", "command: " + command);
        consumeATCommand(command);
    }


    public void turnAntennaMotion() {

        String command = String.format("AT+EARW,3,1,600\\r\\n");
        LogUtils.logi("letianpai_ear", "turnAntennaMotion_command: " + command);
        consumeATCommand(command);
    }

    public void turnAntennaMotion(int cmd,int step,int delay) {

        String command = String.format("AT+EARW,%d,%d,%d\\r\\n",cmd,step,delay);
        LogUtils.logi("letianpai_ear", "turnAntennaMotion_command: " + command);
        consumeATCommand(command);
    }

    public void turnAntennaMotion(int color) {

        String command = String.format("AT+LEDOn,%d\\r\\n", color);
        LogUtils.logi("letianpai_ear", "turnAntennaMotion_command: " + command);
        consumeATCommand(command);
    }

}
