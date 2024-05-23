package com.letianpai.robot.mcuservice.manager;

import android.content.Context;
import android.text.TextUtils;

import com.renhejia.robot.commandlib.log.LogUtils;

import android.widget.Toast;

import com.letianpai.robot.mcuservice.foot.consts.MCUConsts;
import com.letianpai.robot.mcuservice.foot.MCUWalkConsts;
import com.letianpai.robot.mcuservice.foot.Servo;
import com.letianpai.robot.mcuservice.utils.ConvertUtils;

import java.util.ArrayList;

import top.keepempty.sph.library.SerialPortConfig;
import top.keepempty.sph.library.SerialPortFinder;
import top.keepempty.sph.library.SerialPortHelper;
import top.keepempty.sph.library.SphCmdEntity;
import top.keepempty.sph.library.SphResultCallback;

/**
 *
 */
public class McuControlManager {

    private SerialPortHelper serialPortHelper;
    private SerialPortFinder mSerialPortFinder;
    private String[] entryValues;
    private boolean isOpen;

    private static McuControlManager instance;
    private Context mContext;

    private Servo leftLeg;
    private Servo rightLeg;
    private Servo leftFoot;
    private Servo rightFoot;
    private Servo leftEar;
    private Servo rightEar;


    private McuControlManager(Context context) {
        this.mContext = context;
        init(context);
    }

    public static McuControlManager getInstance(Context context) {
        synchronized (McuControlManager.class) {
            if (instance == null) {
                instance = new McuControlManager(context.getApplicationContext());
            }
            return instance;
        }

    }

    ;

    private void init(Context context) {
        initSerialPortHelper();
        initServo();
    }

    private void initServo() {
        rightLeg = new Servo(MCUConsts.MOTOR_NUM_4, false);
        rightFoot = new Servo(MCUConsts.MOTOR_NUM_2, false);
        leftLeg = new Servo(MCUConsts.MOTOR_NUM_3, false);
        leftFoot = new Servo(MCUConsts.MOTOR_NUM_1, false);
        leftEar = new Servo(MCUConsts.MOTOR_NUM_6, false);
        rightEar = new Servo(MCUConsts.MOTOR_NUM_5, false);

    }

    private void initSerialPortHelper() {
        mSerialPortFinder = new SerialPortFinder();
        entryValues = mSerialPortFinder.getAllDevicesPath();

    }

    public void sendCommand(String command) {
        if (TextUtils.isEmpty(command)) {
            Toast.makeText(mContext, "请输入命令不能为空！", Toast.LENGTH_LONG).show();
            return;
        }

        String hexCommand = ConvertUtils.convertToHexString(command);
        serialPortHelper.addCommands(hexCommand);

    }

    /**
     * 打开串口
     */
    private void openSerialPort() {

        /**
         * 串口参数
         */
        SerialPortConfig serialPortConfig = new SerialPortConfig();
        serialPortConfig.mode = 0;
        serialPortConfig.path = "/dev";
        serialPortConfig.baudRate = 115200;
        serialPortConfig.dataBits = 8;
        serialPortConfig.parity = 'N';
        serialPortConfig.stopBits = 1;

        // 初始化串口
//        serialPortHelper = new SerialPortHelper(16);
        serialPortHelper = new SerialPortHelper(64);
        // 设置串口参数
        serialPortHelper.setConfigInfo(serialPortConfig);
        // 开启串口
        isOpen = serialPortHelper.openDevice();
        if (!isOpen) {
            Toast.makeText(mContext, "串口打开失败！", Toast.LENGTH_LONG).show();
        }
        serialPortHelper.setSphResultCallback(new SphResultCallback() {
            @Override
            public void onSendData(SphCmdEntity sendCom) {
                LogUtils.logi("", "发送命令：" + sendCom.commandsHex);
            }

            @Override
            public void onReceiveData(SphCmdEntity data) {
//                LogUtils.logi(TAG, "收到命令：" + data.commandsHex);
                String a = ConvertUtils.decodeHexString(data.commandsHex);
                LogUtils.logi("TAG", "收到命令转换后：" + a);
//                receiveTxt.append(data.commandsHex).append("\n");
//                mShowReceiveTxt.setText(receiveTxt.toString());
            }

            @Override
            public void onComplete() {
                LogUtils.logi("", "完成");
            }
        });
    }

    public void run(int totalSteps, float rate) {
        for (int i = 0; i < totalSteps; i++) {
            bigFootWalk("", i, totalSteps, rate);
        }
    }

    /**
     * @param pos        方向
     * @param curStep
     * @param totalSteps
     * @param rate       // 50- 100
     */
    public void bigFootWalk(String pos, int curStep, int totalSteps, float rate) {
        // to be confirmed
        int angleLeftLeg = 10;
        int angleRightLeg = 10;
        int angleLeftFoot = 0;  // TODO 需要确认
        int angleRightFoot = 0; // TODO 需要确认

        if (pos.equals(MCUWalkConsts.DIRECTION_BACK)) {

            angleLeftFoot = 30;
            angleRightFoot = 30;
        } else if (pos.equals(MCUWalkConsts.DIRECTION_BACK_LEFT)) {
            angleLeftFoot = -5;
            angleRightFoot = -25;
        } else if (pos.equals(MCUWalkConsts.DIRECTION_BACK_RIGHT)) {
            angleLeftFoot = -25;
            angleRightFoot = -5;
        } else if (pos.equals(MCUWalkConsts.DIRECTION_RIGHT)) {
            angleLeftFoot = 5;
            angleRightFoot = 25;
        } else if (pos.equals(MCUWalkConsts.DIRECTION_LEFT)) {
            angleLeftFoot = 25;
            angleRightFoot = 5;
        } else {
            angleLeftFoot = -25;
            angleRightFoot = -25;
        }
        //TODO 功能未外完整
//        Servo[] servoList1 = [leftLeg,rightLeg];
        Servo[] servoList1 = new Servo[2];
        servoList1[0] = leftLeg;
        servoList1[1] = rightLeg;

        float[] angleList = new float[2];
        angleList[0] = -(angleLeftLeg);
        angleList[1] = -angleRightLeg;

//        multiRelateAngleAction([leftLeg,rightLeg],[-(angleLeftLeg),-angleRightLeg],rate);
//        multiRelateAngleAction(servoList1,[-angleLeftLeg,-angleRightLeg],rate);
        multiRelateAngleAction(servoList1, angleList, rate);
        int angleLeftFoot1;
        int angleRightFoot1;
        int angleLeftFootBack;
        int angleRightFootBack;

        if (curStep == 0) {
            angleLeftFoot1 = angleLeftFoot;
            angleRightFoot1 = angleRightFoot;
        } else {
            angleLeftFoot1 = 2 * angleLeftFoot;
            angleRightFoot1 = 2 * angleRightFoot;
        }

        Servo[] servoList2 = new Servo[2];
        servoList2[0] = leftFoot;
        servoList2[1] = rightFoot;

        float[] angleList2 = new float[2];
        angleList2[0] = angleLeftFoot1;
        angleList2[1] = angleRightFoot1;

        multiRelateAngleAction(servoList2, angleList2, rate);


        Servo[] servoList3 = new Servo[2];
        servoList3[0] = leftLeg;
        servoList3[1] = rightLeg;

        float[] angleList3 = new float[2];
        angleList3[0] = angleLeftLeg;
        angleList3[1] = angleRightLeg;
        //  # 左腿向内30度回正，右腿向外30度回正
        multiRelateAngleAction(servoList3, angleList3, rate);
        //   # 左腿向内30度，右腿向外30度
        multiRelateAngleAction(servoList3, angleList3, rate);

        if (curStep == totalSteps - 1) {
            angleLeftFootBack = angleLeftFoot;
            angleRightFootBack = angleRightFoot;
        } else {
            angleLeftFootBack = 2 * angleLeftFoot;
            angleRightFootBack = 2 * angleRightFoot;
        }

        Servo[] servoList4 = new Servo[2];
        servoList4[0] = leftFoot;
        servoList4[1] = rightFoot;

        float[] angleList4 = new float[2];
        angleList4[0] = -angleLeftFootBack;
        angleList4[1] = -angleRightFootBack;

        multiRelateAngleAction(servoList4, angleList4, rate);

        Servo[] servoList5 = new Servo[2];
        servoList5[0] = leftLeg;
        servoList5[1] = rightLeg;

        float[] angleList5 = new float[2];
        angleList5[0] = -angleLeftLeg;
        angleList5[1] = -(angleRightLeg);

        multiRelateAngleAction(servoList5, angleList5, rate);


    }

    //    /**
//     *
//     * @param pos 方向
//     * @param curStep
//     * @param totalSteps
//     * @param rate // 50- 100
//     */
//    public void bigFootWalk(String pos,int curStep,int totalSteps,float rate){
//        // to be confirmed
//        int angleLeftLeg = 10;
//        int angleRightLeg = 10;
//        int angleLeftFoot = 0;  // TODO 需要确认
//        int angleRightFoot = 0; // TODO 需要确认
//
//        if (pos.equals(MCUWalkConsts.DIRECTION_BACK)){
//            angleLeftFoot = 30;
//            angleRightFoot = 30;
//        }else if(pos.equals(MCUWalkConsts.DIRECTION_BACK_LEFT)){
//            angleLeftFoot = -5;
//            angleRightFoot = -25;
//        }else if(pos.equals(MCUWalkConsts.DIRECTION_BACK_RIGHT)){
//            angleLeftFoot = -25;
//            angleRightFoot = -5;
//        }else if(pos.equals(MCUWalkConsts.DIRECTION_RIGHT)){
//            angleLeftFoot = 5;
//            angleRightFoot = 25;
//        }else if(pos.equals(MCUWalkConsts.DIRECTION_LEFT)){
//            angleLeftFoot = 25;
//            angleRightFoot = 5;
//        }else{
//            angleLeftFoot = -25;
//            angleRightFoot = -25;
//        }
//        //TODO 功能未外完整
////        Servo[] servoList1 = [leftLeg,rightLeg];
//        Servo[] servoList1 = new Servo[2];
//        servoList1[0]= leftLeg;
//        servoList1[1]= rightLeg;
//
//        float[] angleList = new float[2];
//        angleList[0] = -(angleLeftLeg);
//        angleList[1] = -angleRightLeg;
//
////        multiRelateAngleAction([leftLeg,rightLeg],[-(angleLeftLeg),-angleRightLeg],rate);
////        multiRelateAngleAction(servoList1,[-angleLeftLeg,-angleRightLeg],rate);
//        multiRelateAngleAction(servoList1,angleList,rate);
//        int angleLeftFoot1;
//        int angleRightFoot1;
//        int angleLeftFootBack;
//        int angleRightFootBack;
//
//        if(curStep == 0){
//            angleLeftFoot1 = angleLeftFoot;
//            angleRightFoot1 = angleRightFoot;
//        }else{
//            angleLeftFoot1 = 2 * angleLeftFoot;
//            angleRightFoot1 = 2 * angleRightFoot;
//        }
//
//        Servo[] servoList2 = new Servo[2];
//        servoList2[0]= leftFoot;
//        servoList2[1]= rightFoot;
//
//        float[] angleList2 = new float[2];
//        angleList2[0] = angleLeftFoot1;
//        angleList2[1] = angleRightFoot1;
//
//        multiRelateAngleAction(servoList2,angleList2,rate);
//
//
//        Servo[] servoList3 = new Servo[2];
//        servoList3[0]= leftLeg;
//        servoList3[1]= rightLeg;
//
//        float[] angleList3 = new float[2];
//        angleList3[0] = angleLeftLeg;
//        angleList3[1] = angleRightLeg;
//        //  # 左腿向内30度回正，右腿向外30度回正
//        multiRelateAngleAction(servoList3,angleList3,rate);
//        //   # 左腿向内30度，右腿向外30度
//        multiRelateAngleAction(servoList3,angleList3,rate);
//
//        if(curStep == totalSteps -1){
//            angleLeftFootBack = angleLeftFoot;
//            angleRightFootBack = angleRightFoot;
//        }else{
//            angleLeftFootBack = 2 * angleLeftFoot;
//            angleRightFootBack = 2 * angleRightFoot;
//        }
//
//        Servo[] servoList4 = new Servo[2];
//        servoList4[0]= leftFoot;
//        servoList4[1]= rightFoot;
//
//        float[] angleList4 = new float[2];
//        angleList4[0] = -angleLeftFootBack;
//        angleList4[1] = -angleRightFootBack;
//
//        multiRelateAngleAction(servoList4,angleList4,rate);
//
//        Servo[] servoList5 = new Servo[2];
//        servoList5[0]= leftLeg;
//        servoList5[1]= rightLeg;
//
//        float[] angleList5 = new float[2];
//        angleList5[0] = -angleLeftLeg;
//        angleList5[1] = -(angleRightLeg);
//
//        multiRelateAngleAction(servoList5,angleList5,rate);
//
//
//
//    }
    public void multiRelateAngleAction(Servo[] servoList, float[] angleList, float rate) {
        if (servoList.length != angleList.length) {
            return;
        }
        if (servoList.length == 0) {
            return;
        }

//
//        float [] startMarginList = null;
//        float [] endMarginList = null;
//        float [] rateList = null;
        float maxAngle = 0;
        int maxIndex = 0;


        float[] startMarginList = new float[servoList.length];
        float[] endMarginList = new float[servoList.length];
        float[] rateList = new float[servoList.length];
        ;
        float[] absAngleList = new float[angleList.length];
        ;
        for (int i = 0; i < servoList.length; i++) {
            startMarginList[i] = servoList[i].getMargin();
            endMarginList[i] = servoList[i].getMargin() + angleList[i] * Servo.getMarginPerAngle();
        }

        for (int j = 0; j < angleList.length; j++) {
            if (Math.abs(angleList[j]) > maxAngle) {
                maxAngle = Math.abs(angleList[j]);
                maxIndex = j;
            }
        }
        for (int i = 0; i < servoList.length; i++) {
            float adjustRate = rate * (Math.abs(angleList[i]) / Math.abs((maxAngle)));
            if (angleList[i] < 0) {
                adjustRate = -adjustRate;

            }
            rateList[i] = adjustRate;

        }
        ArrayList<Float> rangeList = getFloatRangeList(startMarginList[maxIndex],
                endMarginList[maxIndex], rateList[maxIndex]);

        //TODO

        for (int i = 0; i < rangeList.size(); i++) {

            for (int j = 0; j < servoList.length; j++) {
                servoList[j].moveMargin(startMarginList[j] + (i + 1) * rateList[j]);
            }
        }


    }

    public ArrayList<Float> getFloatRangeList(float start, float stop, float steps) {
        ArrayList<Float> resultList = new ArrayList<>();
        if (steps < 0) {
            while (stop < start) {
                resultList.add(start);
                start = start + steps;
            }

        } else {
            while (start < stop) {
                resultList.add(start);
                start = start + steps;
            }
        }
        return resultList;
    }

//    //舵机校准
//    public void allTurnAngle(int angle, float[] offsetList){
//        if(offsetList.length == 4){
////            leftLegOffset = offsetList[0];
////            rightLegOffset = offsetList[1];
////            leftFootOffset = offsetList[2];
////            rightFootOffset = offsetList[3];
//
//            leftLeg.moveAbsAngle((int)(angle + offsetList[0]));
//            rightLeg.moveAbsAngle((int)(angle + offsetList[1]));
//            leftFoot.moveAbsAngle((int)(angle + offsetList[2]));
//            rightFoot.moveAbsAngle((int)(angle + offsetList[3]));
//        }
//    }

    //舵机校准
    public void allTurnAngle(int angle, ArrayList<Integer> offsetList) {
        if (offsetList.size() == 4) {

            leftLeg.moveAbsAngle((int) (angle + offsetList.get(0)));
            rightLeg.moveAbsAngle((int) (angle + offsetList.get(1)));
            leftFoot.moveAbsAngle((int) (angle + offsetList.get(2)));
            rightFoot.moveAbsAngle((int) (angle + offsetList.get(3)));
        }
    }


//    调用：校准四个舵机到归零状态
//            offsetList = (0, -16, -6, 7)
//    allTurnAngle(90, offsetList)


}
