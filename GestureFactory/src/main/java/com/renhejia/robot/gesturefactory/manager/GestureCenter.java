package com.renhejia.robot.gesturefactory.manager;

import com.renhejia.robot.commandlib.consts.ATCmdConsts;
import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.commandlib.consts.RobotExpressionConsts;
import com.renhejia.robot.commandlib.consts.SoundEffect;
import com.renhejia.robot.commandlib.parser.antennalight.AntennaLight;
import com.renhejia.robot.commandlib.parser.antennamotion.AntennaMotion;
import com.renhejia.robot.commandlib.parser.face.Face;
import com.renhejia.robot.commandlib.parser.motion.Motion;
import com.renhejia.robot.commandlib.parser.sound.Sound;
import com.renhejia.robot.commandlib.parser.tts.Tts;
import com.renhejia.robot.gesturefactory.parser.GestureData;

import java.util.ArrayList;
import java.util.Random;

/**
 * 姿态中心
 *
 * @author liujunbin
 */
public class GestureCenter {

    //TODO 需要定义TTS的播放是结束之后菜播放动画

    /**
     * @return
     */
    public static ArrayList<GestureData> demoGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0020"));
        data.setEarAction(new AntennaMotion(3));
        data.setFootAction(new Motion(MCUCommandConsts.COMMAND_VALUE_MOTION_FORWARD, 2));
        data.setSoundEffects(new Sound(MCUCommandConsts.COMMAND_VALUE_SOUND_HAPPY));
        list.add(data);
        return list;
    }

    public static ArrayList<GestureData> resetStandGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setFootAction(new Motion(MCUCommandConsts.COMMAND_VALUE_MOTION_SET_STRAIGHT, 1));
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_FUNCTION_STOP));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLACK));//TODO  更新为蓝色,亮3秒
        data.setInterval(500);
        list.add(data);
        return list;
    }

    /**
     * 正常 normal
     *
     * @return
     */
    public static ArrayList<GestureData> normalGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_STAND, 1));
        data.setExpression(new Face(RobotExpressionConsts.L_FACE_MAIN_IMAGE));
        list.add(data);
        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> commonGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0020"));
        data.setExpressionTime(2);
        data.setInterval(500);
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_PUZZLE));

        list.add(data);
        GestureData data2 = new GestureData();
        data2.setExpression(new Face("h0019"));
        data2.setExpressionTime(2);
        data2.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_PUZZLE2)); //左顾右盼音效是什么？
        data.setInterval(500);

        list.add(data2);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> clockGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0008"));
        data.setInterval(1000 * 60);
//        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_SWAYING_UP_AND_DOWN1, 17,35));
        data.setFootAction(new Motion(null, 17, 35));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLUE));//TODO  更新为蓝色,亮3秒
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_CLOCK));
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> countdownGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setInterval(1000 * 60);
//        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_SWAYING_UP_AND_DOWN1, 17,35));
        data.setFootAction(new Motion(null, 17, 35));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLUE));//TODO  更新为蓝色,亮3秒
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_CLOCK));
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> standGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0020"));
        data.setExpressionTime(2);
        data.setInterval(3000);
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_PUZZLE));
        list.add(data);
        return list;
    }

    /**
     * 通用随机
     *
     * @return
     */
    public static ArrayList<GestureData> common01GestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0063"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_RIGHT_FOOT, 1));
        data.setInterval(500);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_STAND, 1));
        data.setInterval(500);
        list.add(data1);

        GestureData data2 = new GestureData();
        data2.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_LEFT_FOOT, 1));
        data.setInterval(500);
        list.add(data2);

        GestureData data3 = new GestureData();
        data3.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_STAND, 1));
        data.setInterval(500);
        list.add(data3);
        //TODO 待添加
        return list;
    }

    /**
     * 通用随机
     *
     * @return
     */
    public static ArrayList<GestureData> commonStandGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0063"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_34, 1));
        data.setInterval(500);
        list.add(data);


        //TODO 待添加
        return list;
    }

    /**
     * pair
     *
     * @return
     */
    public static ArrayList<GestureData> pairGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_STAND, 1));
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_COMFORTABLE));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_ORANGE));
        data.setInterval(4000);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLACK));
        data1.setInterval(1000);
        list.add(data);

        //TODO 待添加
        return list;
    }

    /**
     * 开机  startup
     *
     * @return
     */
    public static ArrayList<GestureData> startupGestureData2() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        //展示主表情
        data.setExpression(new Face("h0055"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_FORWARD, 1));
        data.setInterval(3000);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_BACK, 1));
        data.setInterval(3000);
        list.add(data1);

        GestureData data2 = new GestureData();
        data2.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_CRAB_STEP_LEFT, 1));
        data2.setInterval(2000);
        list.add(data2);

        GestureData data3 = new GestureData();
        data3.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_CRAB_STEP_RIGHT, 1));
        data3.setInterval(2000);
        list.add(data3);

        //TODO 待添加
        return list;
    }

    /**
     * 关机
     *
     * @return
     */
    public static ArrayList<GestureData> shutdownGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0055"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_STAND, 1));
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_FUNCTION_SHUTDOWN));
        list.add(data);
        //TODO 待添加
        return list;
    }

    /**
     * 低电量
     *
     * @return
     */
    public static ArrayList<GestureData> batteryLowerGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_RED));//TODO 颜色为红色
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_FUNCTION_BATTERY_LOW));
        data.setInterval(2000);
        //TODO 待添加
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLACK));//TODO 颜色为红色
        //TODO 待添加
        list.add(data1);
        return list;
    }


    public static ArrayList<GestureData> startChargingGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_FUNCTION_START_CHARGE));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLUE));//TODO  更新为蓝色,亮3秒
        data.setSoundEffects(new Sound("a0044"));
        data.setInterval(3000);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLACK));
        data1.setInterval(1000);
        list.add(data1);
        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> birthdayGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face(MCUCommandConsts.COMMAND_VALUE_BIRTHDAY));
        data.setEarAction(new AntennaMotion(3));
        data.setFootAction(new Motion(MCUCommandConsts.COMMAND_VALUE_MOTION_FORWARD, 2));
        data.setSoundEffects(new Sound(MCUCommandConsts.COMMAND_VALUE_SOUND_BIRTHDAY));
        data.setTtsInfo(new Tts("小乐祝你生日快乐! "));
        data.setInterval(14000);

        list.add(data);
        return list;
    }

    public static ArrayList<GestureData> volumeUpGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("44squintLfetUp"));
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_FUNCTION_FINISH));
        data.setInterval(3000);
        list.add(data);
        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> volumeDownGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0007"));
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_FUNCTION_FINISH));
        data.setInterval(3000);
        list.add(data);
        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> scanGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();

        GestureData data = new GestureData();
        data.setExpression(new Face("h0019"));
        data.setExpressionTime(3);
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_TURN_RIGHT, 2));
        data.setInterval(4000);

        list.add(data);

        GestureData data2 = new GestureData();
        data2.setExpression(new Face("h0045"));
        data2.setExpressionTime(1);
        data.setInterval(4000);

        list.add(data2);

        GestureData data3 = new GestureData();
        data3.setExpression(new Face("h0036"));
        data3.setExpressionTime(3);
        data3.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_TURN_LEFT, 2));
        data3.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_FOUND_PEOPLE));
        data.setInterval(4000);

        list.add(data3);

        GestureData data4 = new GestureData();
        data4.setExpression(new Face("h0042"));
        data4.setExpressionTime(1);
        data.setInterval(4000);

        list.add(data4);

        GestureData data5 = new GestureData();
        data5.setExpression(new Face("h0042"));//左顾右盼表情
        data5.setExpressionTime(3);
        data5.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_TURN_RIGHT, 2));
        data5.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_PUZZLE));
        data5.setInterval(4000);
        list.add(data5);
        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> foundOwnerGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0006"));
        data.setExpressionTime(2);
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_LEG, 1));
        data.setInterval(2000);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEG, 1));
        data1.setInterval(2000);
        list.add(data1);

        GestureData data2 = new GestureData();
        data2.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEG, 1));
        data2.setInterval(2000);
        list.add(data2);

        GestureData data3 = new GestureData();
        data3.setExpression(new Face(MCUCommandConsts.COMMAND_VALUE_LOVE));
        data3.setEarAction(new AntennaMotion(3));
        data1.setInterval(2000);
        list.add(data3);

        //TODO 待添加
        return list;
    }

    private static int[] foundPeopleMotion = new int[]{18, 23};
    private static String[] foundPeopleFace = new String[]{"h0020", "h0015", "h0036"};
    private static String[] foundPeopleSound = new String[]{"a0051", "a0095"};

    public static ArrayList<GestureData> foundPeoGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face(getRandomString(foundPeopleFace)));
        data.setFootAction(new Motion(getRandomMotion(foundPeopleMotion)));
        data.setSoundEffects(new Sound(getRandomString(foundPeopleSound)));
        data.setEarAction(new AntennaMotion(3));
        data.setInterval(3000);
        list.add(data);
        return list;
    }

    private static int[] foundNoPeopleMotion = new int[]{19, 43};
    private static String[] foundNoPeopleFace = new String[]{"h0022", "h0040"};
    private static String[] foundNoPeopleSound = new String[]{"a0036", "a0078"};

    public static ArrayList<GestureData> foundNoPeoGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face(getRandomString(foundNoPeopleFace)));
        data.setFootAction(new Motion(getRandomMotion(foundNoPeopleMotion)));
        data.setSoundEffects(new Sound(getRandomString(foundNoPeopleSound)));
//        data.setEarAction(new AntennaMotion(3));
        data.setInterval(3000);
        list.add(data);
        return list;
    }


    /**
     * @return
     */
    public static ArrayList<GestureData> robotFoundPeoGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        ArrayList<Motion> motionList = new ArrayList<>();
        Random random = new Random();
        ArrayList<Face> expressionList = new ArrayList<>();
        expressionList = initExpression2(expressionList);
        //TODO 需要更换 MontionList
        motionList = initMotionList(motionList);
        GestureData data = new GestureData();
        data.setExpression(getRandomExpression(random, expressionList));
        data.setFootAction(getRandomFoot(random, motionList));
//        data.setFootAction(new Motion(MCUCommandConsts.COMMAND_VALUE_MOTION_LEFT_ROUND,1));
        data.setExpressionTime(1);
        data.setInterval(2000);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setFootAction(new Motion(MCUCommandConsts.COMMAND_VALUE_MOTION_RIGHT_ROUND, 1));
        data1.setInterval(2000);
        list.add(data1);
        //TODO 待添加
        return list;
    }

    /**
     * @return
     */
    public static ArrayList<GestureData> robotStandGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        ArrayList<Motion> motionList = new ArrayList<>();
        Random random = new Random();
        ArrayList<Face> expressionList = new ArrayList<>();
        expressionList = initExpression3(expressionList);
        motionList = initMotionList2(motionList);

        GestureData data = new GestureData();
        data.setExpression(getRandomExpression(random, expressionList));
        data.setFootAction(getRandomFoot(random, motionList));
        data.setExpressionTime(1);
        data.setInterval(2000);
        list.add(data);

        //TODO 待添加
        return list;
    }

    /**
     * 待机
     * standby
     *
     * @return
     */
    public static ArrayList<GestureData> standByGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_STAND, 1));
        data.setSoundEffects(new Sound("a0050"));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_WHITE));
        data.setInterval(1000);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLACK));
        data1.setInterval(1000);
        list.add(data1);

        //TODO 待添加
        return list;
    }

    /**
     * 唤醒(未完成)
     * assistant
     *
     * @return
     */
    public static ArrayList<GestureData> assistantGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0015"));
        data.setSoundEffects(new Sound("a0025"));
        data.setExpressionTime(1);
        data.setInterval(2000);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setExpression(new Face("h0015"));
        data1.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEG, 1));
        data1.setInterval(2000);
        list.add(data1);

        //TODO 待添加
        return list;
    }


    private static final String[] wakeupListenFace = new String[]{"h0015", "h0019", "h0037", "h0038", "h0059"};

    /**
     * 唤醒监听中
     *
     * @return
     */
    public static ArrayList<GestureData> wakeupListenGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face(wakeupListenFace[getRandomIndex(wakeupListenFace.length)]));
        //停止音效播放
        gestureData.setSoundEffects(new Sound("stop"));
        Motion motion = new Motion();
        motion.setNumber(0);
        gestureData.setFootAction(motion);
        list.add(gestureData);
        return list;
    }

    public static ArrayList<GestureData> wakeupUnderstandGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face("h0050"));
        Motion motion = new Motion();
        motion.setNumber(34);
        gestureData.setFootAction(motion);
        list.add(gestureData);
        return list;
    }

    public static ArrayList<GestureData> wakeupSpeakGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face("h0006"));
        Motion motion = new Motion();
        motion.setNumber(25);
        gestureData.setFootAction(motion);
        list.add(gestureData);
        return list;
    }

    //落地
    private static final String[] fallGroundFace = new String[]{"h0054", "h0003", "h0029", "h0011"};
    private static final String[] fallGroundSound = new String[]{"a0083", "a0076", "a0064", "a0068", "a0078"};
    private static final int[] fallGroundMotion = new int[]{33, 34, 1, 2, 49, 60};

    /**
     * 落地姿态
     */
    public static ArrayList<GestureData> getFallGroundGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face(fallGroundFace[getRandomIndex(fallGroundFace.length)]));
        gestureData.setSoundEffects(new Sound(fallGroundSound[getRandomIndex(fallGroundSound.length)]));
        Motion downMotion = new Motion();
        downMotion.setNumber(fallGroundMotion[getRandomIndex(fallGroundMotion.length)]);
        gestureData.setFootAction(downMotion);
        // AntennaMotion antennaMotion = new AntennaMotion(fallEar[getRandomIndex(fallEar.length)]);
        // AntennaLight light = new AntennaLight("on", getRandomIndex(9));
        // gestureData.setAntennalight(light);
        // gestureData.setEarAction(antennaMotion);
        gestureData.setInterval(2000);
        list.add(gestureData);
        return list;
    }

    // 倒下
    private static final String[] fallDownFace = new String[]{"h0001", "h0005", "h0011", "h0016", "h0033", "h0052"};
    private static final String[] fallDownSound = new String[]{"a0003", "a0006", "a0098", "a0086", "a0082", "a0020"};
    private static final int[] fallDownMotion = new int[]{49, 59, 17, 48};

    /**
     * 倒下姿态
     */
    public static ArrayList<GestureData> getFallDownGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face(fallDownFace[getRandomIndex(fallDownFace.length)]));
        gestureData.setSoundEffects(new Sound(fallDownSound[getRandomIndex(fallDownSound.length)]));
        Motion downMotion = new Motion();
        downMotion.setNumber(fallDownMotion[getRandomIndex(fallDownMotion.length)]);
        gestureData.setFootAction(downMotion);
        // AntennaMotion antennaMotion = new AntennaMotion(fallEar[getRandomIndex(fallEar.length)]);
        // AntennaLight light = new AntennaLight("on", getRandomIndex(9));
        // gestureData.setAntennalight(light);
        // gestureData.setEarAction(antennaMotion);
        gestureData.setInterval(2000);
        list.add(gestureData);
        return list;
    }

    // 单击
    private static final String[] tapFace = new String[]{"h0009", "h0028", "h0022", "h0023", "h0024", "h0025", "h0045", "h0043"};
    private static final String[] tapSound = new String[]{"a0016", "a0023", "a0028", "a0051", "a0053", "a0071", "a0074", "a0092"};
    private static final int[] tapMotion = new int[]{31, 32, 37, 38, 44, 45, 46, 47};

    /**
     * 单击姿态
     */
    public static ArrayList<GestureData> getTapGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face(tapFace[getRandomIndex(tapFace.length)]));
        gestureData.setSoundEffects(new Sound(tapSound[getRandomIndex(tapSound.length)]));
        Motion downMotion = new Motion();
        downMotion.setNumber(tapMotion[getRandomIndex(tapMotion.length)]);
        gestureData.setFootAction(downMotion);
        // AntennaMotion antennaMotion = new AntennaMotion(fallEar[getRandomIndex(fallEar.length)]);
        AntennaLight light = new AntennaLight("on", getRandomIndex(9));
        gestureData.setAntennalight(light);
        // gestureData.setEarAction(antennaMotion);
        gestureData.setInterval(3000);
        list.add(gestureData);
        return list;
    }

    // 双击
    private static final String[] doubleTapFace = new String[]{"h0034", "h0052", "h0001", "h0011"};
    private static final String[] doubleTapSound = new String[]{"a0005", "a0020", "a0034", "a0065"};
    private static final int[] doubleTapMotion = new int[]{17, 49, 55, 56, 1, 2};

    /**
     * 双击姿态
     */
    public static ArrayList<GestureData> getdoubleTapGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face(doubleTapFace[getRandomIndex(doubleTapFace.length)]));
        gestureData.setSoundEffects(new Sound(doubleTapSound[getRandomIndex(doubleTapSound.length)]));
        Motion downMotion = new Motion();
        downMotion.setNumber(doubleTapMotion[getRandomIndex(doubleTapMotion.length)]);
        gestureData.setFootAction(downMotion);
        // AntennaMotion antennaMotion = new AntennaMotion(fallEar[getRandomIndex(fallEar.length)]);
        AntennaLight light = new AntennaLight("on", getRandomIndex(9));
        gestureData.setAntennalight(light);
        // gestureData.setEarAction(antennaMotion);
        gestureData.setInterval(3000);
        list.add(gestureData);
        return list;
    }

    // 长按
    private static final String[] longPressFace = new String[]{"h0054", "h0056", "h0010", "h0052", "h0001"};
    private static final String[] longPressSound = new String[]{"a0034", "a0049", "a0055", "a0077", "a0083"};
    private static final int[] longPressMotion = new int[]{50, 58, 48, 19};

    /**
     * 长按姿态
     */
    public static ArrayList<GestureData> getLongPressGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face(longPressFace[getRandomIndex(longPressFace.length)]));
        gestureData.setSoundEffects(new Sound(longPressSound[getRandomIndex(longPressSound.length)]));
        Motion downMotion = new Motion();
        downMotion.setNumber(longPressMotion[getRandomIndex(longPressMotion.length)]);
        gestureData.setFootAction(downMotion);
        // AntennaMotion antennaMotion = new AntennaMotion(fallEar[getRandomIndex(fallEar.length)]);
        AntennaLight light = new AntennaLight("on", getRandomIndex(9));
        gestureData.setAntennalight(light);
        // gestureData.setEarAction(antennaMotion);
        gestureData.setInterval(3000);
        list.add(gestureData);
        return list;
    }

    private static final String[] danglingFace = new String[]{"h0052"};
    private static final int[] danglingMotion = new int[]{17, 49, 43};

    /**
     * 悬空 (天线一直转)
     * assistant
     *
     * @return
     */
    public static ArrayList<GestureData> danglingGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data1 = new GestureData();
//        data1.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_FOOT1, 1));
        Motion motion = new Motion();
        motion.setNumber(danglingMotion[getRandomIndex(danglingMotion.length)]);
        data1.setFootAction(motion);
        data1.setExpression(new Face(danglingFace[getRandomIndex(danglingFace.length)]));
        data1.setSoundEffects(new Sound("a0076"));
        data1.setEarAction(new AntennaMotion(3));

        AntennaLight light = new AntennaLight("on", getRandomIndex(9));
        data1.setAntennalight(light);

        data1.setInterval(3000);
        list.add(data1);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> danglingStopGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data1 = new GestureData();
//        data1.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_FOOT1, 1));
        Motion motion = new Motion();
        motion.setNumber(34);
        motion.setStepNum(4);
        data1.setFootAction(motion);
        data1.setExpression(new Face("h0027"));
        data1.setSoundEffects(new Sound("click"));
        AntennaMotion antennaMotion = new AntennaMotion(3);
        data1.setEarAction(antennaMotion);
        data1.setInterval(1000);
        list.add(data1);
        return list;
    }

    /**
     * 放跌落表情随机其中之一
     */
    private static final String[] fallFace = new String[]{"h0016", "h0011", "h0028", "h0001", "h0049"};
    /**
     * 放跌落声音随机其中之一
     */
    private static final String[] fallSound = new String[]{"a0003", "a0012", "a0007", "a0019"};
    /**
     * 防跌落天线随机其中之一
     */
    private static final int[] fallEar = new int[]{1, 2, 3};

    /**
     * 防跌落统一表情与声音等
     *
     * @return
     */
    private static GestureData getFallGesture() {
        GestureData gestureData = new GestureData();
        gestureData.setExpression(new Face(fallFace[getRandomIndex(fallFace.length)]));
        gestureData.setSoundEffects(new Sound(fallSound[getRandomIndex(fallSound.length)]));
        AntennaMotion antennaMotion = new AntennaMotion(fallEar[getRandomIndex(fallEar.length)]);
        AntennaLight light = new AntennaLight("on", getRandomIndex(9));
        gestureData.setAntennalight(light);
        gestureData.setEarAction(antennaMotion);
        gestureData.setInterval(2000);
        return gestureData;
    }

    /**
     * 防跌落前进不调整动作，可以调整其他
     */
    public static ArrayList<GestureData> fallForwardGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data1 = getFallGesture();
        Motion motion = new Motion();
        motion.setNumber(63);
        motion.setStepNum(3);
        data1.setFootAction(motion);

        list.add(data1);
        return list;
    }

    /**
     * 防跌落后退不调整动作，可以调整其他
     */
    public static ArrayList<GestureData> fallBackendGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data1 = getFallGesture();
        Motion motion = new Motion();
        motion.setNumber(64);
        motion.setStepNum(3);
        data1.setFootAction(motion);
        list.add(data1);
        return list;
    }

    /**
     * 防跌落走左不调整动作，可以调整其他
     */
    public static ArrayList<GestureData> fallLeftGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data1 = getFallGesture();
        Motion motion = new Motion();
        motion.setNumber(6);
        motion.setStepNum(3);
        data1.setFootAction(motion);
        list.add(data1);
        return list;
    }

    /**
     * 防跌落右走不调整动作，可以调整其他
     */
    public static ArrayList<GestureData> fallRightGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data1 = getFallGesture();
        Motion motion = new Motion();
        motion.setNumber(5);
        motion.setStepNum(3);
        data1.setFootAction(motion);
        list.add(data1);
        return list;
    }

    /**
     * 防跌落
     * assistant
     *
     * @return
     */
    public static ArrayList<GestureData> fallPreventionGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0016"));
        data.setExpressionTime(1);
        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_FEAR));
        AntennaMotion antennaMotion = new AntennaMotion(3);
        antennaMotion.setSpeed(60);
        antennaMotion.setAngle(90);
        data.setEarAction(antennaMotion);
        data.setInterval(2000);
        list.add(data);

        //TODO 待添加
        return list;
    }

    /**
     * 落地
     * assistant
     *
     * @return
     */
    public static ArrayList<GestureData> whereaboutsGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();

        GestureData data = new GestureData();
        data.setExpression(new Face("h0029"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_LEG, 1));
        data.setInterval(1500);
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEG1, 1));
        data1.setInterval(1000);
        list.add(data1);

        GestureData data2 = new GestureData();
        data2.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_LEG, 1));
        data2.setInterval(1000);
        list.add(data2);

        //TODO 待添加
        return list;
    }


    public static ArrayList<GestureData> autoDisplayGestureData() {
        ArrayList<GestureData> list = new ArrayList<>();


        GestureData data3 = new GestureData();
        data3.setExpression(new Face("h0027"));
        data3.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_RED));
        data3.setInterval(1000);
        list.add(data3);

        GestureData data1 = new GestureData();
        data1.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLACK));
        data1.setInterval(1000);
        list.add(data1);

        return list;
    }

    private static final String[] face6To8 = new String[]{"h0069", "h0067", "h0068"};
    private static final String[] face8To10 = new String[]{"h0066", "h0075"};
    private static final String[] face10To12 = new String[]{"h0071", "h0070"};
    private static final String[] face12To14 = new String[]{"h0074"};
    private static final String[] face14To16 = new String[]{"h0073"};
    private static final String[] face16To18 = new String[]{"h0064"};
    private static final String[] face18To20 = new String[]{"h0072"};
    private static final String[] face20To22 = new String[]{"h0065", "h0071"};
    private static final String[] face22To6 = new String[]{"h0004", "h0039", "h0055"};

    /**
     * 24小时展示的
     *
     * @return
     */
    public static ArrayList<GestureData> hourGestureData(int hour) {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        String faceName = null;
        int movieDuration = 3;
        switch (hour) {
            case 6:
            case 7:
                faceName = getRandomString(face6To8);
                break;
            case 8:
            case 9:
                faceName = getRandomString(face8To10);
                break;
            case 10:
            case 11:
                faceName = getRandomString(face10To12);
                break;
            case 12:
            case 13:
                faceName = getRandomString(face12To14);
                break;
            case 14:
            case 15:
                faceName = getRandomString(face14To16);
                break;
            case 16:
            case 17:
                faceName = getRandomString(face16To18);
                break;
            case 18:
            case 19:
                faceName = getRandomString(face18To20);
                break;
            case 20:
            case 21:
                faceName = getRandomString(face20To22);
                break;
            case 22:
            case 23:
            case 24:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                faceName = getRandomString(face22To6);
                break;
        }

        switch (faceName) {
            case "h0064":
                movieDuration = 12;
                break;
            case "h0065":
                movieDuration = 13;
                break;
            case "h0066":
                movieDuration = 17;
                break;
            case "h0067":
                movieDuration = 10;
                break;
            case "h0068":
                movieDuration = 21;
                break;
            case "h0069":
                movieDuration = 12;
                break;
            case "h0070":
                movieDuration = 6;
                break;
            case "h0071":
                movieDuration = 10;
                break;
            case "h0072":
                movieDuration = 13;
                break;
            case "h0073":
                movieDuration = 10;
                break;
            case "h0074":
                movieDuration = 9;
                break;
            case "h0075":
                movieDuration = 13;
                break;
            case "h0004":
                movieDuration = 4;
                break;
            case "h0039":
                movieDuration = 11;
                break;
            case "h0055":
                movieDuration = 3;
                break;
        }
        data.setExpression(new Face(faceName));
        data.setFootAction(new Motion("null", 0));
        data.setInterval((movieDuration - 6) * 1000);
        data.setAntennalight(new AntennaLight("on", getRandomIndex(9)));
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setAntennalight(new AntennaLight("on", getRandomIndex(9)));
        data1.setInterval(2000);
        list.add(data1);
        GestureData data2 = new GestureData();
        data2.setAntennalight(new AntennaLight("on", getRandomIndex(9)));
        AntennaMotion antennaMotion = new AntennaMotion(3);
        antennaMotion.setSpeed(600);
        antennaMotion.setAngle(45);
        data2.setEarAction(antennaMotion);
        data2.setInterval(2000);
        list.add(data2);
        GestureData data3 = new GestureData();
        data3.setAntennalight(new AntennaLight("on", getRandomIndex(9)));
        data3.setInterval(2000);
        list.add(data3);

        return list;
    }

    public static ArrayList<GestureData> hourGestureDataWithName(String faceName) {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        int movieDuration = 3;

        switch (faceName) {
            case "h0064":
                movieDuration = 12;
                break;
            case "h0065":
                movieDuration = 13;
                break;
            case "h0066":
                movieDuration = 17;
                break;
            case "h004cloud":
                movieDuration = 10;
                break;
            case "h0068":
                movieDuration = 21;
                break;
            case "h0069":
                movieDuration = 12;
                break;
            case "h0070":
                movieDuration = 6;
                break;
            case "h0071":
                movieDuration = 10;
                break;
            case "h0072":
                movieDuration = 13;
                break;
            case "h0073":
                movieDuration = 10;
                break;
            case "h0074":
                movieDuration = 9;
                break;
            case "h0075":
                movieDuration = 13;
                break;
            case "h0004":
                movieDuration = 4;
                break;
            case "h0039":
                movieDuration = 11;
                break;
            case "h0055":
                movieDuration = 3;
                break;
        }
        data.setExpression(new Face(faceName));
        data.setFootAction(new Motion("null", 0));
        data.setInterval((movieDuration - 6) * 1000);
        data.setAntennalight(new AntennaLight("on", getRandomIndex(9)));
        list.add(data);

        GestureData data1 = new GestureData();
        data1.setAntennalight(new AntennaLight("on", getRandomIndex(9)));
        data1.setInterval(2000);
        data1.setFootAction(new Motion("null", 34));
        list.add(data1);
        GestureData data2 = new GestureData();
        data2.setAntennalight(new AntennaLight("on", getRandomIndex(9)));
        data2.setEarAction(new AntennaMotion(3));
        data2.setInterval(2000);
        list.add(data2);
        GestureData data3 = new GestureData();
        data3.setAntennalight(new AntennaLight("on", getRandomIndex(9)));
//        data3.setSoundEffects(new Sound(""));
        data3.setInterval(2000);
        list.add(data3);

        return list;
    }

    public static ArrayList<GestureData> getAllHour() {
        ArrayList<GestureData> list = new ArrayList<>();
        String[] allHourFace = new String[]{"h0064", "h0065", "h0066", "h004cloud", "h0068", "h0069", "h0070",
                "h0071", "h0072", "h0073", "h0074", "h0075"};
        for (int i = 0; i < allHourFace.length; i++) {
            list.addAll(hourGestureDataWithName(allHourFace[i]));
        }
        return list;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<GestureData> test_GestureData1() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
//        data.setExpression(new Face(MCUCommandConsts.COMMAND_VALUE_FACE_ANGRY));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_STAMP_LEFT_FOOT1, 2));
        data.setInterval(3000);
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_RED));
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> test_GestureData2() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
//        data.setExpression(new Face(MCUCommandConsts.COMMAND_VALUE_FACE_SAD));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_STAMP_RIGHT_FOOT1, 2));
        data.setInterval(3000);
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_GREEN));
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> test_GestureData3() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0001"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_LEANING1, 2));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLUE));
        data.setInterval(3000);
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> test_GestureData4() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("55wronged"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_LEANING1, 2));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_ORANGE));
        data.setInterval(3000);
//        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_BASHFUL));
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> test_GestureData5() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
//        data.setExpression(new Face(MCUCommandConsts.COMMAND_VALUE_FACE_BORED));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_FOOT1, 2));
        data.setInterval(3000);
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_WHITE));
//        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_BASHFUL));
        list.add(data);

        //TODO 待添加
        return list;
    }


    public static ArrayList<GestureData> test_GestureData6() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
//        data.setExpression(new Face(MCUCommandConsts.COMMAND_VALUE_FACE_EXCITING));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_FOOT1, 2));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_YELLOW));
        data.setInterval(3000);
//        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_BASHFUL));
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> test_GestureData7() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0010"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_RIGHT_FOOT1, 2));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_PURPLE));
        data.setInterval(3000);
//        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_BASHFUL));
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> test_GestureData8() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
//        data.setExpression(new Face(MCUCommandConsts.COMMAND_VALUE_FACE_LOSE));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_LEFT_FOOT1, 2));
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_CYAN));
        data.setInterval(3000);
//        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_BASHFUL));
        list.add(data);

        //TODO 待添加
        return list;
    }

    public static ArrayList<GestureData> test_GestureData0() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData data = new GestureData();
        data.setExpression(new Face("h0027"));
        data.setFootAction(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_SWAYING_UP_AND_DOWN1, 2));
        data.setInterval(2000);
        data.setAntennalight(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLACK));
//        data.setSoundEffects(new Sound(SoundEffect.COMMAND_VALUE_SOUND_BASHFUL));
        list.add(data);

        //TODO 待添加
        return list;
    }


    private ArrayList<Face> expressionList = new ArrayList<>();
    private ArrayList<Sound> soundList = new ArrayList<>();
    private ArrayList<Motion> motionList = new ArrayList<>();
    private ArrayList<AntennaLight> lightList = new ArrayList<>();
    private ArrayList<AntennaMotion> earList = new ArrayList<>();
    private ArrayList<AntennaLight> lightStatusList = new ArrayList<>();
    private Random random;

    private void initData() {
        random = new Random();
    }


    private static ArrayList<AntennaLight> initStatusLight(ArrayList<AntennaLight> lightStatusList) {
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_RED));
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_OFF, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_GREEN));
        return lightStatusList;

    }

    private static ArrayList<AntennaMotion> initEarList(ArrayList<AntennaMotion> earList) {
        earList.add(new AntennaMotion(3));
        earList.add(null);
        return earList;

    }

    private static ArrayList<AntennaLight> intLightList(ArrayList<AntennaLight> lightStatusList) {
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_RED));
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_GREEN));
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLUE));
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_ORANGE));
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_WHITE));
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_YELLOW));
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_PURPLE));
        lightStatusList.add(new AntennaLight(MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_ON, MCUCommandConsts.COMMAND_VALUE_ANTENNA_LIGHT_COLOR_CYAN));
        return lightStatusList;
    }

    //Done
    private static ArrayList<Motion> initMotionList(ArrayList<Motion> motionList) {
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_HEAD1, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_SWINGS_FROM_SIDE_TO_SIDE1, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_STAND_AT_EASE1, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_LEFT_FOOT1, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_RIGHT_FOOT1, 1));
        return motionList;

    }

    //Done 找人前
    private static ArrayList<Motion> initMotionList2(ArrayList<Motion> motionList) {

        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_29, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_30, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_31, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_HEAD1, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_CROSS_LEFT_FOOT1, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_SHAKE_SWINGS_FROM_SIDE_TO_SIDE1, 1));
        return motionList;

    }

    //Done random
    private static ArrayList<Motion> initMotionList3(ArrayList<Motion> motionList) {

        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_34, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_54, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_48, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_49, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_50, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_31, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_32, 1));

        return motionList;

    }

    //Done
    private static ArrayList<Motion> initMotionList4(ArrayList<Motion> motionList) {

        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_39, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_40, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_41, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_42, 1));
        motionList.add(new Motion(ATCmdConsts.AT_STR_MOVEW_31, 1));
        return motionList;

    }

    //Done
    private static ArrayList<Sound> initSoundList(ArrayList<Sound> soundList) {
        soundList.add(new Sound(SoundEffect.COMMAND_VALUE_SOUND_RESPOND));
        soundList.add(new Sound(SoundEffect.COMMAND_VALUE_SOUND_HAPPY));
        soundList.add(new Sound(SoundEffect.COMMAND_VALUE_SOUND_PANIC));
        soundList.add(new Sound(SoundEffect.COMMAND_VALUE_SOUND_SURPRISE));
        soundList.add(new Sound(SoundEffect.COMMAND_VALUE_SOUND_TIRED));
        return soundList;
    }


    private static ArrayList<Face> initExpression1(ArrayList<Face> expressionList) {
//        expressionList.add(new Face(MCUCommandConsts.COMMAND_VALUE_EXCITING));
        expressionList.add(new Face("h0033"));
        expressionList.add(new Face("h0015"));
        expressionList.add(new Face("h0041"));
        expressionList.add(new Face("h0030"));


        return expressionList;

    }

    private static ArrayList<Face> initExpression2(ArrayList<Face> expressionList) {

        expressionList.add(new Face("h0021"));
        expressionList.add(new Face("h0011"));
        expressionList.add(new Face("h0017"));
        expressionList.add(new Face("h0044"));

        return expressionList;

    }

    private static ArrayList<Face> initExpression3(ArrayList<Face> expressionList) {
        expressionList.add(new Face("h0044"));
        expressionList.add(new Face("h0042"));
        expressionList.add(new Face("h0025"));
        expressionList.add(new Face("h0024"));
        expressionList.add(new Face("h0019"));

        return expressionList;

    }

    private static Motion getRandomFoot(Random random, ArrayList<Motion> motionList) {
        int randNum = random.nextInt(motionList.size());
        Motion motion = motionList.get(randNum);
        return motionList.get(randNum);
    }

    private static Sound getRandomSoundEffect(Random random, ArrayList<Sound> soundList) {
        int randNum = random.nextInt(soundList.size());
        return soundList.get(randNum);
    }

    private static Face getRandomExpression(Random random, ArrayList<Face> expressionList) {
        int randNum = random.nextInt(expressionList.size());
        return expressionList.get(randNum);
    }


    private static AntennaLight getRandomLight(Random random, ArrayList<AntennaLight> lightList) {
        int randNum = random.nextInt(lightList.size());
        return lightList.get(randNum);
    }

    private static AntennaMotion getRandomEarList(Random random, ArrayList<AntennaMotion> earList) {
        int randNum = random.nextInt(earList.size());
        return earList.get(randNum);
    }

    public static ArrayList<GestureData> getRandomGesture() {
        ArrayList<GestureData> list = new ArrayList<>();
        Random random = new Random();
        ArrayList<Face> expressionList = new ArrayList<>();
        ArrayList<Sound> soundList = new ArrayList<>();
        ArrayList<Motion> motionList = new ArrayList<>();
        ArrayList<AntennaLight> lightList = new ArrayList<>();
        ArrayList<AntennaMotion> earList = new ArrayList<>();
        ArrayList<AntennaLight> lightStatusList = new ArrayList<>();

        earList.add(new AntennaMotion(3));
        earList.add(null);
        expressionList = initExpression1(expressionList);
        motionList = initMotionList3(motionList);
        soundList = initSoundList(soundList);
        lightList = intLightList(lightList);

        lightStatusList = initStatusLight(lightStatusList);

        GestureData data = new GestureData();
        data.setExpression(getRandomExpression(random, expressionList));
        data.setFootAction(getRandomFoot(random, motionList));
        data.setAntennalight(getRandomLight(random, lightList));
        data.setSoundEffects(getRandomSoundEffect(random, soundList));
        data.setEarAction(getRandomEarList(random, earList));
        data.setInterval(2500);
        list.add(data);
        return list;
    }

    public static ArrayList<GestureData> youPinGestures() {
        ArrayList<GestureData> list = new ArrayList<>();
        GestureData gestureData10 = new GestureData();
        gestureData10.setTtsInfo(new Tts("小米的朋友们，大家好，我是乐天派！很高兴认识你们！"));
        gestureData10.setExpression(new Face("h0027"));
        gestureData10.setInterval(5000);
        GestureData gestureData20 = new GestureData();
        gestureData20.setTtsInfo(new Tts("这还是我第一次离开乐天派的办公室见到这么多的好朋友，有点害羞~"));
        gestureData20.setExpression(new Face("h0037"));
        gestureData20.setInterval(5000);
        GestureData gestureData30 = new GestureData();
        gestureData30.setTtsInfo(new Tts("下面我给大家做个自我介绍吧"));
        gestureData30.setExpression(new Face("h0025"));
        gestureData30.setInterval(5000);
        GestureData gestureData40 = new GestureData();
        gestureData40.setTtsInfo(new Tts("我能歌善舞"));
        gestureData40.setExpression(new Face("h0043"));
        gestureData40.setInterval(3000);
        GestureData gestureData50 = new GestureData();
        gestureData50.setTtsInfo(new Tts("我可以提醒你的日程：叮~~欣然姐，今年我们有约会哦，3点不见不散"));
        gestureData50.setExpression(new Face("h0008"));
        gestureData50.setInterval(5000);
        GestureData gestureData60 = new GestureData();
        gestureData60.setTtsInfo(new Tts("如果你爽约了，我会不高兴，但是你可以摸摸我的头~快来安慰我吧"));
        gestureData60.setExpression(new Face("h0011"));
        gestureData60.setInterval(5000);
        GestureData gestureData70 = new GestureData();
        gestureData70.setTtsInfo(new Tts("除此之外，我还能做好多事情"));
        gestureData70.setExpression(new Face("h0023"));
        gestureData70.setInterval(5000);
        GestureData gestureData80 = new GestureData();
        gestureData80.setTtsInfo(new Tts("我接入了米家APP，可以和智能家居联动"));
        gestureData80.setExpression(new Face("h0047"));
        gestureData80.setInterval(5000);
        GestureData gestureData90 = new GestureData();
        gestureData90.setTtsInfo(new Tts("以上就是我的自我介绍，希望你们喜欢我，谢谢！"));
        gestureData90.setExpression(new Face("h0027"));
        gestureData90.setInterval(5000);

        list.add(gestureData10);
        list.add(gestureData20);
        list.add(gestureData30);
        list.add(gestureData40);
        list.add(gestureData50);
        list.add(gestureData60);
        list.add(gestureData70);
        list.add(gestureData80);
        list.add(gestureData90);

        return list;
    }

    private static int getRandomIndex(int length) {
        Random r = new Random();
        return r.nextInt(length);
    }

    private static String getRandomString(String[] group) {
        Random r = new Random();
        return group[r.nextInt(group.length)];
    }

    private static int getRandomMotion(int[] group) {
        Random r = new Random();
        return group[r.nextInt(group.length)];
    }
}
