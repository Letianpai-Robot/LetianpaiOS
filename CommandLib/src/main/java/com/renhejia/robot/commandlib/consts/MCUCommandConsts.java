package com.renhejia.robot.commandlib.consts;

/**
 * @author liujunbin
 */
public class MCUCommandConsts {

    /**
     * ======================================== 命令类型 ========================================
     */

    /**
     * 表情
     */
    public final static String COMMAND_TYPE_FACE = "controlFace";

    public static final String COMMAND_TYPE_SHOW_TIME = "showTIme";

    /**
     * 动作控制
     */
    public final static String COMMAND_TYPE_MOTION = "controlMotion";

    /**
     * 声音控制
     */
    public final static String COMMAND_TYPE_SOUND = "controlSound";

    /**
     * 天线控制
     */
    public final static String COMMAND_TYPE_ANTENNA_MOTION = "controlAntennaMotion";

    /**
     * 天线光控制
     */
    public final static String COMMAND_TYPE_ANTENNA_LIGHT = "controlAntennaLight";

    /**
     * 电源控制
     */
    public static final String COMMAND_TYPE_POWER_CONTROL = "powerControl";

    /**
     * 天线光控制
     */
    public final static String COMMAND_TYPE_ANTENNA_LIGHT_VALUE_ON = "on";
    public final static String COMMAND_TYPE_ANTENNA_LIGHT_VALUE_OFF = "off";

    /**
     * Video call
     */
    public final static String COMMAND_TYPE_TRTC = "trtc";
    public final static String COMMAND_TYPE_UPDATE_WAKEUP_CONFIG = "updateAwakeConfig";

    /**
     * 天线光控制
     */
    public final static String COMMAND_TYPE_OPEN_MCU = "open_mcu";

    public final static String COMMAND_TYPE_CLOSE_MCU = "close_mcu";

    /**
     *
     */
    public final static String COMMAND_VALUE_ANTENNA_MOTION = "turn";
    /**
     *
     */
    public final static String  COMMAND_VALUE_ANTENNA_MOTION_DIY = "diy";


    /**
     * ======================================== 声音类型 ================ start ========================
     */

    /**
     * 失落
     */
    public final static String COMMAND_VALUE_SOUND_LOSE = "lose";

    /**
     * 生气
     */
    public final static String COMMAND_VALUE_SOUND_ANGRY = "angry";

    /**
     * 搞怪
     */
    public final static String COMMAND_VALUE_SOUND_FUNNY = "funny";

    /**
     * 愤怒
     */
    public final static String COMMAND_VALUE_SOUND_ANGER = "anger";

    /**
     * 哭泣
     */
    public final static String COMMAND_VALUE_SOUND_CRY = "cry";

    /**
     * 撒娇
     */
    public final static String COMMAND_VALUE_SOUND_SPOILED = "spoiledChild";

    /**
     * 开心
     */
    public final static String COMMAND_VALUE_SOUND_HAPPY = "happy";

    /**
     * 苦笑
     */
    public final static String COMMAND_VALUE_SOUND_WRY_SMILE = "wrySmile";

    /**
     * 伤心
     */
    public final static String COMMAND_VALUE_SOUND_SAD = "sad";


    public final static String COMMAND_VALUE_SOUND_AVOIDANCE = "avoidance";
    public final static String COMMAND_VALUE_SOUND_CLICK = "click";
    public final static String COMMAND_VALUE_SOUND_MISTAKE = "mistake";
    public final static String COMMAND_VALUE_SOUND_FINISH = "finish";
    public final static String COMMAND_VALUE_SOUND_FIND_PEOPLE = "findPerson";
    public final static String COMMAND_VALUE_SOUND_CLOCK = "clock";
    public final static String COMMAND_VALUE_SOUND_SHUT_DOWN = "shutdown";
    public final static String COMMAND_VALUE_SOUND_STARTUP = "startUp";
    public final static String COMMAND_VALUE_SOUND_LOW_POWER = "lowPower";
    public final static String COMMAND_VALUE_SOUND_CHARGE = "charge";
    public final static String COMMAND_VALUE_SOUND_MAINTOFACE = "mainToFace";
    public final static String COMMAND_VALUE_SOUND_DIZZINESS = "dizziness";
    public final static String COMMAND_VALUE_SOUND_WAKE = "wake";
    public final static String COMMAND_VALUE_SOUND_SLEEP = "sleep";
    public final static String COMMAND_VALUE_SOUND_PANT = "pant";
    public final static String COMMAND_VALUE_SOUND_TIRED = "tired";
    public final static String COMMAND_VALUE_SOUND_FATIGUE = "fatigue";
    public final static String COMMAND_VALUE_SOUND_SHY = "shy";
    public final static String COMMAND_VALUE_SOUND_SURPRISE = "surprise";
    public final static String COMMAND_VALUE_SOUND_FEAR = "fear";
    public final static String COMMAND_VALUE_SOUND_PANIC = "panic";
    public final static String COMMAND_VALUE_SOUND_CONFUSE = "confuse";
    public final static String COMMAND_VALUE_SOUND_DISGUSTED = "disgusted";
    public final static String COMMAND_VALUE_SOUND_HATE = "hate";
    public final static String COMMAND_VALUE_SOUND_COMFORTABLE = "comfortable";
    public final static String COMMAND_VALUE_SOUND_SNEAKPEEK = "sneakPeek";
    public final static String COMMAND_VALUE_SOUND_RESPOND = "respond";


    /**
     * ======================================== 声音类型 ================ end ========================
     */


    /**
     * ======================================== 音效类型 ================ start ========================
     */

    /**
     * 生日
     */
    public final static String COMMAND_VALUE_SOUND_BIRTHDAY = "birthday";

    public final static String COMMAND_AUDIO_TURN_AROUND = "audio_turn_around";

    public final static String COMMAND_SET_APP_MODE = "set_app_mode";
    public final static String COMMAND_SET_SHOW_TIME = "showTime";
    /**
     *
     */
    public final static String COMMAND_SHOW_TEXT = "show_text";
    /**
     *
     */
    public final static String COMMAND_HIDE_TEXT = "hide_text";

    /**
     * 锁定全局状态栏文字控制
     */
    public final static String COMMAND_LOCK_CONTROL_TEXT = "lock_control_text";

    /**
     * 全局状态栏文字
     */
    public final static String COMMAND_LOCK_CONTROL_FULL_TEXT = "lock_control_full_text";

    /**
     * ======================================== 音效类型 ================ end ========================
     */


    /**
     *  ======================================== 表情类型 ========================================
     */

    /**
     * 苦笑
     */
    public final static String COMMAND_VALUE_FACE_WRY_SMILE = "wrySmile";

    /**
     * 生气
     */
    public final static String COMMAND_VALUE_FACE_ANGRY = "angry";

    /**
     * 伤心
     */
    public final static String COMMAND_VALUE_FACE_SAD = "sad";

    /**
     * 愤怒
     */
    public final static String COMMAND_VALUE_FACE_ANGER = "anger";

    /**
     * 无聊
     */
    public final static String COMMAND_VALUE_FACE_BORED = "bored";

    /**
     * 兴奋
     */
    public final static String COMMAND_VALUE_FACE_EXCITING = "exciting";

    /**
     * 哭泣
     */
    public final static String COMMAND_VALUE_FACE_CRY = "cry";

    /**
     * 失落
     */
    public final static String COMMAND_VALUE_FACE_LOSE = "lose";

    /**
     * 高兴
     */
    public final static String COMMAND_VALUE_FACE_HAPPY = "happy";

    /**
     * 高兴
     */
    public final static String COMMAND_VALUE_FACE_STAND = "stand_yellow";

    /**
     * 生日
     */
    public final static String COMMAND_VALUE_BIRTHDAY = "HappyBirthday_Loop";
    /**
     * 生日
     */
    public final static String COMMAND_VALUE_BIRTHDAY2 = "birthday2";

    public final static String COMMAND_VALUE_COMMONWINK = "commonWink";
    public final static String COMMAND_VALUE_BIG_SMALL_EYE = "BigSmallEye";
    public final static String COMMAND_VALUE_SINGLE_BLINK = "singleBlink";
    public final static String COMMAND_VALUE_PEEP = "peep";
    public final static String COMMAND_VALUE_SQUINT = "squint";
    public final static String COMMAND_VALUE_SINGLE_EXPECT = "expect";
    public final static String COMMAND_VALUE_GLANCE_LEFT_RIGHT = "glanceLeftRight";
    public final static String COMMAND_VALUE_SQUINT_RIGHT_UP = "squintRightUp";
    public final static String COMMAND_VALUE_SQUINT_LEFT_UP = "squintLfetUp";
    public final static String COMMAND_VALUE_SQUINT_RIGHT_DOWN = "squintRightDown";
    public final static String COMMAND_VALUE_SQUINT_LEFT_DOWN = "squintLeftDown";
    public final static String COMMAND_VALUE_SQUINT_LOOK_DOWN = "lookDown";
    public final static String COMMAND_VALUE_CONFUSE = "confuse";
    public final static String COMMAND_VALUE_SHAKE_HEAD = "shakeHead";

    public final static String COMMAND_VALUE_THINK = "think";
    public final static String COMMAND_VALUE_BORED = "bored";
    public final static String COMMAND_VALUE_LISTEN_LEFT = "listenLeft";
    public final static String COMMAND_VALUE_LISTEN_RIGHT = "listenRight";
    public final static String COMMAND_VALUE_LISTEN_HAPPY = "happy";
    public final static String COMMAND_VALUE_BIG_LAUGH = "bigLaugh";
    public final static String COMMAND_VALUE_EXCITING = "exciting";
    public final static String COMMAND_VALUE_LOVE = "h0027";
    public final static String COMMAND_VALUE_WRONGED = "wronged";
    public final static String COMMAND_VALUE_FROWN = "frown";
    public final static String COMMAND_VALUE_LOSE = "lose";
    public final static String COMMAND_VALUE_SAD = "sad";
    public final static String COMMAND_VALUE_CRY = "cry";
    public final static String COMMAND_VALUE_ASHAMED = "ashamed";
    public final static String COMMAND_VALUE_UNWEL = "unwell";

    public final static String COMMAND_VALUE_PAIN = "pain";
    public final static String COMMAND_VALUE_SORRY = "sorry";
    public final static String COMMAND_VALUE_FURIOUS = "furious";
    public final static String COMMAND_VALUE_RESIST = "resist";
    public final static String COMMAND_VALUE_SHUT_UP = "shutUp";
    public final static String COMMAND_VALUE_ENVY = "envy";
    public final static String COMMAND_VALUE_ARROGNT = "arrogant";
    public final static String COMMAND_VALUE_ANGER = "anger";
    public final static String COMMAND_VALUE_SUPRISE = "surprise";
    public final static String COMMAND_VALUE_BARBIQ = "barbieQ";
    public final static String COMMAND_VALUE_SUSPECT = "suspect";
    public final static String COMMAND_VALUE_FEAR = "fear";
    public final static String COMMAND_VALUE_PANTING = "panting";
    public final static String COMMAND_VALUE_YAWN = "yawn";
    public final static String COMMAND_VALUE_WEAK_UP = "wakeUp";
    public final static String COMMAND_VALUE_SLEEP = "sleep";
    public final static String COMMAND_VALUE_SLEEP_OPEN_EYE = "sleepOpenEye";
    public final static String COMMAND_VALUE_DIZZINESS = "dizziness";
    public final static String COMMAND_VALUE_TREMBINE = "trembline";
    public final static String COMMAND_VALUE_CLOCK = "clock";
    public final static String COMMAND_VALUE_PHOTO = "photo";
    public final static String COMMAND_VALUE_WUNAI = "haveNoChoice";


    /**
     *  ======================================== 动作类型 ========================================
     */

    /**
     * 向前
     */
    public final static String COMMAND_VALUE_MOTION_FORWARD = "forward";

    /**
     * 向后
     */
    public final static String COMMAND_VALUE_MOTION_BACKEND = "backend";

    /**
     * 向左
     */
    public final static String COMMAND_VALUE_MOTION_LEFT = "left";

    /**
     * 向右
     */
    public final static String COMMAND_VALUE_MOTION_RIGHT = "right";

    /**
     * 向左转
     */
    public final static String COMMAND_VALUE_MOTION_LEFT_ROUND = "leftRound";

    /**
     * 向右转
     */
    public final static String COMMAND_VALUE_MOTION_RIGHT_ROUND = "rightRound";

    /**
     * 回正
     */
    public final static String COMMAND_VALUE_MOTION_SET_STRAIGHT = "setStraight";

    /**
     * 转圈
     */
    public final static String COMMAND_VALUE_MOTION_TURN_ROUND = "turnRound";

    /**
     * 撒娇
     */
    public final static String COMMAND_VALUE_MOTION_PETTISH = "pettish";

    /**
     * 生气
     */
    public final static String COMMAND_VALUE_MOTION_ANGRY = "angry";

    /**
     * 奔跑
     */
    public final static String COMMAND_VALUE_MOTION_RUN = "run";

    /**
     * 奔跑
     */
    public final static String COMMAND_VALUE_MOTION_CHEERS = "cheers";

    /**
     * 疲惫
     */
    public final static String COMMAND_VALUE_MOTION_TRIED = "tried";

    /**
     * 抖腿
     */
    public final static String COMMAND_VALUE_MOTION_SHAKE_LEG = "shakeLeg";

    /**
     *  ======================================== 动作类型 ========================================
     */

    /**
     * 天线灯开
     */
    public final static String COMMAND_VALUE_ANTENNA_LIGHT_ON = "on";

    /**
     * 天线灯关
     */
    public final static String COMMAND_VALUE_ANTENNA_LIGHT_OFF = "off";

    /**
     * 天线闪烁
     */
    public final static String COMMAND_VALUE_ANTENNA_LIGHT_TWINKLE = "twinkle";

    /**
     * 天线闪烁
     */
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_RED = 1;
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_GREEN = 2;
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLUE = 3;
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_ORANGE = 4;
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_WHITE = 5;
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_YELLOW = 6;
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_PURPLE = 7;
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_CYAN = 8;
    public final static int COMMAND_VALUE_ANTENNA_LIGHT_COLOR_BLACK = 9;


    /**
     * 天线旋转
     */
    public final static int COMMAND_VALUE_ANTENNA_MOTION_3 = 3;





}
