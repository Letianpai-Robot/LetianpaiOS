package com.letianpai;

import android.text.TextUtils;
import android.util.Log;

import com.renhejia.robot.commandlib.log.LogUtils;

import com.renhejia.robot.commandlib.consts.ATCmdConsts;

import top.keepempty.sph.library.SerialPortJNI;

public class McuResponseUtil {


    /**
     * 立正
     */
    public static void walkStand() {
        consumeATCommand(ATCmdConsts.AT_MOVEW_STAND, 1,1);
    }

    /**
     * 向前走
     * @param stepNum
     */
    public static void walkForward(int stepNum) {
        walkForward(stepNum,3);
    }

    /**
     * 向前走
     * @param stepNum
     * @param speed
     */
    public static void walkForward(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_FORWARD, stepNum,speed);
    }

    /**
     * 向后走
     * @param stepNum
     */
    public static void walkBackend(int stepNum) {
        walkBackend(stepNum,3);
    }

    /**
     * 向后走
     * @param stepNum
     * @param speed
     */
    public static void walkBackend(int stepNum,int speed) {

        consumeATCommand(ATCmdConsts.AT_MOVEW_BACK, stepNum,speed);
    }

    /**
     * 向左转
     * @param stepNum
     */
    public static void turnLeft(int stepNum) {
        turnLeft(stepNum,3);
    }

    /**
     * 向左转
     * @param stepNum
     * @param speed
     */
    public static void turnLeft(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_TURN_LEFT, stepNum,speed);
    }

    /**
     * 向右转
     * @param stepNum
     */
    public static void turnRight(int stepNum) {
        turnRight(stepNum,3);
    }

    /**
     * 向右转
     * @param stepNum
     * @param speed
     */
    public static void turnRight(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_TURN_RIGHT, stepNum,speed);
    }


    /**
     * 螃蟹步向左
     * @param stepNum
     */
    public static void crabStepLeft(int stepNum) {
        LogUtils.logi("letianpai", "COMMAND_VALUE_MOTION_LEFT:=====crabStepLeft====1=2 ");
        crabStepLeft(stepNum,3);
    }

    /**
     * 螃蟹步向左
     * @param stepNum
     * @param speed
     */
    public static void crabStepLeft(int stepNum,int speed) {

        consumeATCommand(ATCmdConsts.AT_MOVEW_CRAB_STEP_LEFT, stepNum,speed);
    }

    /**
     * 螃蟹步向右
     * @param stepNum
     */
    public static void crabStepRight(int stepNum) {
        crabStepRight(stepNum,3);
    }

    /**
     * 螃蟹步向右
     * @param stepNum
     * @param speed
     */
    public static void crabStepRight(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_CRAB_STEP_RIGHT, stepNum,speed);
    }

    /**
     * 抖左腿
     * @param stepNum
     */
    public static void shakeLeftLeg(int stepNum) {
        shakeLeftLeg(stepNum,2);
    }

    /**
     * 抖左腿
     * @param stepNum
     * @param speed
     */
    public static void shakeLeftLeg(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_LEFT_LEG, stepNum,speed);
    }

    /**
     * 抖右腿
     * @param stepNum
     */
    public static void shakeRightLeg(int stepNum) {
        shakeRightLeg(stepNum,2);
    }

    /**
     * 抖右腿
     * @param stepNum
     * @param speed
     */
    public static void shakeRightLeg(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_RIGHT_LEG, stepNum,speed);
    }

    /**
     * 抖左脚
     * @param stepNum
     */
    public static void shakeLeftFoot(int stepNum) {
        shakeLeftFoot(stepNum,3);
    }

    /**
     * 抖左脚
     * @param stepNum
     * @param speed
     */
    public static void shakeLeftFoot(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_LEFT_FOOT, stepNum,speed);
    }

    /**
     * 抖右腿
     * @param stepNum
     */
    public static void shakeRightFoot(int stepNum) {
        shakeRightFoot(stepNum,3);
    }

    /**
     * 抖右腿
     * @param stepNum
     * @param speed
     */
    public static void shakeRightFoot(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_RIGHT_FOOT, stepNum,speed);
    }


    /**
     * 左跷脚
     * @param stepNum
     */
    public static void shakeCrossLeftFoot(int stepNum) {
        shakeCrossLeftFoot(stepNum,3);
    }

    /**
     * 左跷脚
     * @param stepNum
     * @param speed
     */
    public static void shakeCrossLeftFoot(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_CROSS_LEFT_FOOT, stepNum,speed);
    }

    /**
     * 右跷脚
     * @param stepNum
     */
    public static void shakeCrossRightFoot(int stepNum) {
        shakeCrossRightFoot(stepNum,3);
    }

    /**
     * 右跷脚
     * @param stepNum
     * @param speed
     */
    public static void shakeCrossRightFoot(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_CROSS_RIGHT_FOOT, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void shakeLeftLeaning(int stepNum) {
        shakeLeftLeaning(stepNum,6);
    }

    /**
     * 右跷脚
     * @param stepNum
     * @param speed
     */
    public static void shakeLeftLeaning(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_LEFT_LEANING, stepNum,speed);
    }

    /**
     * 右倾身
     * @param stepNum
     */
    public static void shakeRightLeaning(int stepNum) {
        shakeRightLeaning(stepNum,6);
    }

    /**
     * 右倾身
     * @param stepNum
     * @param speed
     */
    public static void shakeRightLeaning(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_RIGHT_LEANING, stepNum,speed);
    }

    /**
     * 左跺脚
     * @param stepNum
     */
    public static void stampLeftFoot(int stepNum) {
        stampLeftFoot(stepNum,3);
    }

    /**
     * 左跺脚
     * @param stepNum
     * @param speed
     */
    public static void stampLeftFoot(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_STAMP_LEFT_FOOT, stepNum,speed);
    }



    /**
     * 左跺脚
     * @param stepNum
     */
    public static void stampRightFoot(int stepNum) {
        stampRightFoot(stepNum,3);
    }

    /**
     * 左跺脚
     * @param stepNum
     * @param speed
     */
    public static void stampRightFoot(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_STAMP_RIGHT_FOOT, stepNum,speed);
    }


    /**
     * 左跺脚
     * @param stepNum
     */
    public static void swayingUpdAndDown(int stepNum) {
        swayingUpdAndDown(stepNum,1);
    }

    /**
     * 左跺脚
     * @param stepNum
     * @param speed
     */
    public static void swayingUpdAndDown(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_SWAYING_UP_AND_DOWN, stepNum,speed);
    }

    /**
     * 左跺脚
     * @param stepNum
     */
    public static void swingsFromSideToSide(int stepNum) {
        swingsFromSideToSide(stepNum,1);
    }

    /**
     * 左跺脚
     * @param stepNum
     * @param speed
     */
    public static void swingsFromSideToSide(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_SWINGS_FROM_SIDE_TO_SIDE, stepNum,speed);
    }


    /**
     * 摇头
     * @param stepNum
     */
    public static void shakeHead(int stepNum) {
        shakeHead(stepNum,1);
    }

    /**
     * 摇头
     * @param stepNum
     * @param speed
     */
    public static void shakeHead(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_SHAKE_HEAD, stepNum,speed);
    }

    /**
     * 稍息
     * @param stepNum
     */
    public static void standAtEase(int stepNum) {
        standAtEase(stepNum,1);
    }

    /**
     * 稍息
     * @param stepNum
     * @param speed
     */
    public static void standAtEase(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_STAND_AT_EASE, stepNum,speed);
    }

    /**
     * 原地左转
     * @param stepNum
     */
    public static void localRoundLeft(int stepNum) {
        localRoundLeft(stepNum,3);
    }

    /**
     * 原地左转
     * @param stepNum
     * @param speed
     */
    public static void localRoundLeft(int stepNum,int speed) {
        LogUtils.logi("letianpai", "COMMAND_VALUE_MOTION_LEFT:=====crabStepLeft====1=2=3=4======5 ");
        consumeATCommand(ATCmdConsts.AT_MOVEW_LOCAL_ROUND_LEFT, stepNum,speed);
    }

    /**
     * 原地右转
     * @param stepNum
     * */
    public static void localRoundRight(int stepNum) {
        localRoundRight(stepNum,3);
    }

    /**
     * 原地右转
     * @param stepNum
     * @param speed
     */
    public static void localRoundRight(int stepNum,int speed) {
        LogUtils.logi("letianpai", "COMMAND_VALUE_MOTION_LEFT:=====crabStepLeft====1=2=3=4=========6 ");
        consumeATCommand(ATCmdConsts.AT_MOVEW_LOCAL_ROUND_RIGHT, stepNum,speed);
    }

    /**
     * 双抖脚
     * @param stepNum
     */
    public static void feetTremor(int stepNum) {
        feetTremor(3,1);
    }

    /**
     * 双抖脚
     * @param stepNum
     * @param speed
     */
    public static void feetTremor(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_FEET_TREMOR, stepNum,speed);
    }

    /**
     * 微颤
     * @param stepNum
     */
    public static void microTremor(int stepNum) {
        microTremor(1,3);
    }

    /**
     * 微颤
     * @param stepNum
     * @param speed
     */
    public static void microTremor(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_MICROTREMOR, stepNum,speed);
    }


    /**
     *
     * @param stepNum
     */
    public static void atMovEW29(int stepNum) {
        atMovEW29(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW29(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_29, stepNum,speed);
    }
    /**
     *
     * @param stepNum
     */
    public static void atMovEW30(int stepNum) {
        atMovEW30(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW30(int stepNum,int speed) {

        consumeATCommand(ATCmdConsts.AT_MOVEW_30, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW31(int stepNum) {
        LogUtils.logi("letianpai_test","AT_STR_MOVEW_31: ===================2  ");
        atMovEW31(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW31(int stepNum,int speed) {
        LogUtils.logi("letianpai_test","AT_STR_MOVEW_31: ===================3  ");
        consumeATCommand(ATCmdConsts.AT_MOVEW_31, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW32(int stepNum) {
        atMovEW32(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW32(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_32, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW33(int stepNum) {
        atMovEW33(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW33(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_33, stepNum,speed);
    }
    /**
     *
     * @param stepNum
     */
    public static void atMovEW34(int stepNum) {
        atMovEW34(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW34(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_34, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW35(int stepNum) {
        atMovEW35(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW35(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_35, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW36(int stepNum) {
        atMovEW36(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW36(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_36, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW37(int stepNum) {
        atMovEW37(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW37(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_37, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW38(int stepNum) {
        atMovEW38(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW38(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_38, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW39(int stepNum) {
        atMovEW39(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW39(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_39, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW40(int stepNum) {
        atMovEW40(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW40(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_40, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW41(int stepNum) {
        atMovEW41(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW41(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_41, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW42(int stepNum) {
        atMovEW42(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW42(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_42, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW43(int stepNum) {
        atMovEW43(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW43(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_43, stepNum,speed);
    }


    /**
     *
     * @param stepNum
     */
    public static void atMovEW44(int stepNum) {
        atMovEW44(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW44(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_44, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW45(int stepNum) {
        atMovEW45(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW45(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_45, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW46(int stepNum) {
        atMovEW46(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW46(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_46, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW47(int stepNum) {
        atMovEW47(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW47(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_47, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW48(int stepNum) {
        atMovEW48(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW48(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_48, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW49(int stepNum) {
        atMovEW49(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW49(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_49, stepNum,speed);
    }


    /**
     *
     * @param stepNum
     */
    public static void atMovEW50(int stepNum) {
        atMovEW50(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW50(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_50, stepNum,speed);
    }
    /**
     *
     * @param stepNum
     */
    public static void atMovEW51(int stepNum) {
        atMovEW51(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW51(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_51, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW52(int stepNum) {
        atMovEW52(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW52(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_52, stepNum,speed);
    }
    /**
     *
     * @param stepNum
     */
    public static void atMovEW53(int stepNum) {
        atMovEW53(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW53(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_53, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW54(int stepNum) {
        atMovEW54(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW54(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_54, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW55(int stepNum) {
        atMovEW55(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW55(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_55, stepNum,speed);
    }
    /**
     *
     * @param stepNum
     */
    public static void atMovEW56(int stepNum) {
        atMovEW56(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW56(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_56, stepNum,speed);
    }
    /**
     *
     * @param stepNum
     */
    public static void atMovEW57(int stepNum) {
        atMovEW57(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW57(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_57, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW58(int stepNum) {
        atMovEW58(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW58(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_58, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW59(int stepNum) {
        atMovEW59(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW59(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_59, stepNum,speed);
    }

    /**
     *
     * @param stepNum
     */
    public static void atMovEW60(int stepNum) {
        atMovEW60(stepNum,4);
    }

    /**
     *
     * @param stepNum
     * @param speed
     */
    public static void atMovEW60(int stepNum,int speed) {
        consumeATCommand(ATCmdConsts.AT_MOVEW_60, stepNum,speed);
    }











    public static void consumeATCommand(int commandType, int stepNum, int speed) {
        String command = String.format("AT+MOVEW,%d,%d,%d\\r\\n", commandType, stepNum,speed);
        LogUtils.logd("McuResponseUtil", "consumeATCommand:舵机执行命令 "+command);
        if (!TextUtils.isEmpty(command)){
            SerialPortJNI.writePort(command.getBytes());
//            ShellUtils.execCmd("at_tools " + atCom + "\\\\r\\\\n", false);
//           shellUtils(command);
        }

    }

    private static void shellUtils(String command) {
        ShellUtils.CommandResult result = ShellUtils.execCmd("at_tools " + command, false);
        Log.i("TAG===", "McuResponseUtil shellUtils: ====" + result);
    }


//    public static void consumeATCommand(String command) {
//        LogUtils.logi("letianpai", "consumeATCommand  ======= 1");
//        if (!TextUtils.isEmpty(command)) {
//            LogUtils.logi("letianpai", "consumeATCommand  ======= 2");
//            SerialPortJNI.writePort(command.getBytes());
//        }
//    }

}
