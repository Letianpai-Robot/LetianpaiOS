package com.letianpai.robot.mcuservice.foot.consts;

public class MCUConsts {

    public static String AT_COMMAND_SENSOR ="AT+AG";
    public static String AT_COMMAND_READ ="AT+MOTORR";
    public static String AT_COMMAND_MOTORW ="AT+MOTORW";
    public static String AT_COMMAND_CLIFFR ="AT+CLIFFR"; //读取悬崖传感器状态
    public static String AT_COMMAND_CLIFFD ="AT+CLIFFD"; //读取悬崖传感器状态
//    public static String AT_COMMAND_FMCGADDR ="AT+FMCGADDR"; //读取悬崖传感器状态
//    public static String AT_COMMAND_FMCR ="AT+FMCR"; //读取悬崖传感器状态
//    public static String AT_COMMAND_FMCW ="AT+FMCW"; //读取悬崖传感器状态
//    public static String AT_COMMAND_FMCDUMP ="AT+FMCDUMP"; //读取悬崖传感器状态
    public static String AT_COMMAND_VERR ="AT+VerR"; //读取悬崖传感器状态
    public static String AT_COMMAND_SNR ="AT+SNR"; //读取悬崖传感器状态
    public static String AT_COMMAND_AGID ="AT+AGID"; //读取悬崖传感器状态
    public static String AT_COMMAND_LEDON ="AT+LEDOn"; //Led 开
    public static String AT_COMMAND_LEDOFF ="AT+LEDOff"; //Led 关

    public static String AT_COMMAND_END ="\r\n";
    public static String AT_COMMAND_CONNECT =",";
    /**
     * 加速度
     */
    public static String SENSOR_TYPE_ACC ="0";
    /**
     * 陀螺仪
     */
    public static String SENSOR_TYPE_GYRO ="1";
    /**
     * 加速度 + 陀螺仪
     */
    public static String SENSOR_TYPE_AAC_GYRO ="2";
    /**
     *  ============= 舵机 start =============
     */
//    public static String MOTOR_NUM_1 = "1";
//    public static String MOTOR_NUM_2 = "2";
//    public static String MOTOR_NUM_3 = "3";
//    public static String MOTOR_NUM_4 = "4";
//    public static String MOTOR_NUM_5 = "5";
//    public static String MOTOR_NUM_6 = "6";
    public final static int MOTOR_NUM_1 = 1;
    public final static int MOTOR_NUM_2 = 2;
    public final static int MOTOR_NUM_3 = 3;
    public final static int MOTOR_NUM_4 = 4;
    public final static int MOTOR_NUM_5 = 5;
    public final static int MOTOR_NUM_6 = 6;

    public static String VALUE_TYPE_ANGLE = "0";
    public static String VALUE_TYPE_PULSE = "1";

    // ================================================ start ===================================================


//    public final static String  COMMAND_TYPE_FACE = "controlFace";
//    public final static String  COMMAND_TYPE_MOTION = "controlMotion";
//    public final static String  COMMAND_TYPE_SOUND = "controlSound";
//    public final static String  COMMAND_TYPE_ANTENNA_MOTION = "controlAntennaMotion";
//    public final static String  COMMAND_TYPE_ANTENNA_LIGHT = "controlAntennaLight";




    // ================================================ end ===================================================
    /**
     *  ============= 舵机 end =============
     */

    public static String RETURN_VALUE_SUCCESS = "AT+RES,ACK\\r\\n";
//    public static String RETURN_VALUE_FAILED = "AT+RES";

    /**
     * 获取传感器 AT命令
     * @param command AT命令
     * @param sensorType 传感器类型
     * @return
     */
    private static String getAGCommand(String command, String sensorType){
        return command + AT_COMMAND_CONNECT + sensorType + AT_COMMAND_END;
    }

    /**
     *
     * @param motorNum 舵机编号
     * @return
     */
    private static String getMOTORRCommand(String motorNum){
        return AT_COMMAND_READ + AT_COMMAND_CONNECT + motorNum + AT_COMMAND_END;
    }

    /**
     *
     * @param motorNum 舵机编号
     * @param valueType
     * @param value
     * @return
     */
    private static String getMOTORWCommand(String motorNum, String valueType,String value){
        return AT_COMMAND_MOTORW + AT_COMMAND_CONNECT
                + motorNum + AT_COMMAND_CONNECT
                + valueType + AT_COMMAND_CONNECT
                + value +  AT_COMMAND_END;
    }

    /**
     * 获取传感器 AT命令
     * @return
     */
    public static String getACCCommand(){
        return getAGCommand(AT_COMMAND_SENSOR,SENSOR_TYPE_ACC);
    }

    /**
     * 获取加速度 AT命令
     * @return
     */
    public static String getGYROCommand(){
        return getAGCommand(AT_COMMAND_SENSOR,SENSOR_TYPE_GYRO);
    }

    /**
     * 获取传感器加速度 AT命令
     * @return
     */
    private static String getACCGYROCommand(){
        return getAGCommand(AT_COMMAND_SENSOR,SENSOR_TYPE_AAC_GYRO);
    }






}
