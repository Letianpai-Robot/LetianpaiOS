package com.renhejia.robot.commandlib.consts;

public class RobotRemoteConsts {


    /**
     * OTA升级
     */
    public final static String COMMAND_TYPE_OTA = "otaUpgrade";

    /**
     * 更新wifi配置
     */
    public final static String COMMAND_TYPE_UPDATE_WIFI_CONFIG = "updateWifiConfig";

    /**
     * 更新ble配置
     */
    public final static String COMMAND_TYPE_UPDATE_BLE_CONFIG = "updateBleConfig";

    /**
     * 更新显示模式配置
     */
    public final static String COMMAND_TYPE_UPDATE_SHOW_MODE_CONFIG = "updateShowModeConfig";
    /**
     * 绑定到米家
     */
    public final static String COMMAND_TYPE_BIND_MIIOT = "startBindMijia";


    /**
     * 更新睡眠模式配置
     */
    public final static String COMMAND_TYPE_UPDATE_SLEEP_MODE_CONFIG = "updateSleepModeConfig";

    /**
     * 更新睡眠模式配置
     */
    public final static String COMMAND_TYPE_UPDATE_AWAKE_CONFIG = "updateAwakeConfig";

    /**
     * 更新通用配置
     */
    public final static String COMMAND_TYPE_UPDATE_GENERAL_CONFIG = "updateGeneralConfig";

    /**
     * 更新日期配置
     */
    public final static String COMMAND_TYPE_UPDATE_DATE_CONFIG = "updateDateConfig";

    /**
     * 更新日历配置
     */
    public final static String COMMAND_TYPE_UPDATE_CALENDAR_CONFIG = "updateCalendarConfig";

    /**
     * 更新粉丝配置
     */
    public final static String COMMAND_TYPE_UPDATE_FANS_CONFIG = "updateFansConfig";

    /**
     * 更新倒计时配置
     */
    public final static String COMMAND_TYPE_UPDATE_COUNT_DOWN_CONFIG = "updateCountDownConfig";

    /**
     * 更新显示模式
     */
    public final static String COMMAND_TYPE_APP_DISPLAY_SWITCH_CONFIG = "updateDisplaySwitchConfig";

    /**
     * 更新显示模式 --天气页面
     */
    public final static String COMMAND_TYPE_UPDATE_WEATHER_CONFIG = "updateWeatherConfig";

    /**
     * 更换显示模块
     */
    public final static String COMMAND_TYPE_CHANGE_SHOW_MODULE = "changeShowModule";

    /**
     * 更换显示模块
     */
    public static final  String COMMAND_VALUE_CHANGE_SHOW_MODULE_EVENT = "event";
    /**
     * 更换显示模块
     */
    public static final  String COMMAND_VALUE_CHANGE_SHOW_MODULE_WEATHER = "weather";

    public static final  String COMMAND_VALUE_CHANGE_SHOW_MODULE_TIME = "time";

    public static final  String COMMAND_VALUE_CHANGE_SHOW_MODULE_FANS = "fans";

    /**
     * 闹钟信息
     */
    public static final  String COMMAND_TYPE_UPDATE_CLOCK_DATA = "updateClockData";

    /**
     * 悬空开始
     */
    public static final  String COMMAND_TYPE_CONTROL_PRECIPICE_START_DATA = "controlStartPrecipice";
    /**
     * 悬空结束
     */
    public static final  String COMMAND_TYPE_CONTROL_PRECIPICE_STOP_DATA = "controlStopPrecipice";
    /**
     * 倒下开始
     */
    public static final  String COMMAND_TYPE_CONTROL_FALL_DOWN_START_DATA = "controlStartFallDown";
    /**
     * 倒下结束
     */
    public static final  String COMMAND_TYPE_CONTROL_FALL_DOWN_STOP_DATA = "controlStopFallDown";

    /**
     * 单击
     */
    public static final  String COMMAND_TYPE_CONTROL_TAP_DATA = "controlTap";

    /**
     * 双击
     */
    public static final  String COMMAND_TYPE_CONTROL_DOUBLE_TAP_DATA = "controlDoubleTap";
    /**
     * 长按
     */
    public static final  String COMMAND_TYPE_CONTROL_LONG_PRESS_DATA = "controlLongPressTap";
    /**
     * 防跌落，往后退
     */
    public static final  String COMMAND_TYPE_CONTROL_FALL_BACKEND = "fallBackend";
    /**
     * 防跌落，往前走
     */
    public static final  String COMMAND_TYPE_CONTROL_FALL_FORWARD = "fallForward";
    public static final  String COMMAND_TYPE_CONTROL_FALL_LEFT = "fallLeft";
    public static final  String COMMAND_TYPE_CONTROL_FALL_RIGHT = "fallRight";

    /**
     * 选择图片
     */
    public static final  String COMMAND_TYPE_CONTROL_SEND_PIC = "controlSendPic";

    /**
     * 文字
     */
    public static final  String COMMAND_TYPE_CONTROL_SEND_WORD = "controlSendWord";

    /**
     * 重置机器
     */
    public static final  String COMMAND_TYPE_RESET_DEVICE_INFO = "resetDeviceInfo";

    public static String ROBOT_UPDATE_VERSION_IS_READY = "latest";

    /**
     * 机器人模式状态切换
     */
    public static final  String COMMAND_TYPE_CHANGE_MODE = "changeMode";

    public static final  String COMMAND_VALUE_CHANGE_MODE_TRANSFORM = "transform";
    public static final  String COMMAND_VALUE_CHANGE_MODE_SHOW = "show";
    public static final  String COMMAND_VALUE_CHANGE_MODE_SLEEP = "sleep";
    public static final  String COMMAND_VALUE_CHANGE_MODE_AUTO = "auto";
    public static final  String COMMAND_VALUE_CHANGE_MODE_DEMO = "demo";
    public static final  String COMMAND_VALUE_CHANGE_MODE_RESET = "reset"; // 恢复
    public static final  String COMMAND_VALUE_CHANGE_MODE_STATIC = "static"; // 静止模式
    public static final  String COMMAND_VALUE_CHANGE_MODE_ROBOT = "robot"; // 机器人模式

    /**
     * 机器人模式状态切换
     */
    public static final  String COMMAND_TYPE_ADD_FACE_FEATURE = "addFaceFeature";

    /**
     * 控制音量
     */
    public static final  String COMMAND_TYPE_CONTROL_SOUND_VOLUME = "controlSoundVolume";

    /**
     * 演示模式显示切换
     */
    public static final  String COMMAND_TYPE_CONTROL_DISPLAY_MODE = "controlDisplayMode";

    public static final  String COMMAND_VALUE_CONTROL_DISPLAY_TIME = "time";
    public static final  String COMMAND_VALUE_CONTROL_DISPLAY_WEATHER = "weather";
    public static final  String COMMAND_VALUE_CONTROL_DISPLAY_COUNTDOWN = "countdown";
    public static final  String COMMAND_VALUE_CONTROL_DISPLAY_FANS = "fans";
    public static final  String COMMAND_VALUE_CONTROL_DISPLAY_SCHEDULE = "schedule";
    public static final  String COMMAND_VALUE_CONTROL_DISPLAY_EMPTY = "empty";
    public static final  String COMMAND_VALUE_CONTROL_DISPLAY_BLACK = "darkScreen";
    public static final  String COMMAND_VALUE_CONTROL_DISPLAY_EXT_BLACK = "exitDarkScreen";

    // public static final  String LOCAL_COMMAND_VALUE_CONTROL_DISPLAY_TIME = "display/time";
    // public static final  String LOCAL_COMMAND_VALUE_CONTROL_DISPLAY_WEATHER = "display/weather";
    // public static final  String LOCAL_COMMAND_VALUE_CONTROL_DISPLAY_COUNTDOWN = "display/countdown";
    // public static final  String LOCAL_COMMAND_VALUE_CONTROL_DISPLAY_FANS = "display/fans";
    // public static final  String LOCAL_COMMAND_VALUE_CONTROL_DISPLAY_SCHEDULE = "display/notice";
    // public static final  String LOCAL_COMMAND_VALUE_CONTROL_DISPLAY_EMPTY = "display/empty";
    public static final  String LOCAL_COMMAND_VALUE_IDENT_FACE_RESULT = "identFaceResult";

    /**
     * 演示模式自动动作
     */
    public static final  String COMMAND_TYPE_CONTROL_AUTO_MODE = "controlAutoMode";
    public static final  String COMMAND_VALUE_CONTROL_AUTO_MODE_FOLLOW = "follow";
    public static final  String COMMAND_VALUE_CONTROL_AUTO_MODE_EXT_FOLLOW = "exitFollow";
    public static final  String COMMAND_VALUE_CONTROL_AUTO_MODE_RANDOM = "random";
    /**
     * 删除设备
     */
    public static final  String COMMAND_VALUE_REMOVE_DEVICE = "removeDevice";

    /**
     * 引导完成按钮点击
     */
    public static final  String COMMAND_VALUE_DEVICE_GUIDE_FINISH = "deviceGuideFinish";

    /**
     * 更新时区
     */
    public static final  String COMMAND_UPDATE_DEVICE_TIME_ZONE = "updateDeviceTimeZone";

    /**
     *
     */
    public final static String COMMAND_SET_APP_MODE = "set_app_mode";

    /**
     *
     */
    public final static String COMMAND_SHOW_TEXT = "show_text";

    /**
     *
     */
    public final static String COMMAND_SHOW_CHARGING = "show_charging";

    /**
     *
     */
    public final static String COMMAND_SHOW_ALL = "show_all";

    /**
     *
     */
    public final static String COMMAND_HIDE_TEXT = "hide_text";

    public final static String COMMAND_GET_DEVICE_CHANNEL_LOGO = "get_channel_logo";



}
