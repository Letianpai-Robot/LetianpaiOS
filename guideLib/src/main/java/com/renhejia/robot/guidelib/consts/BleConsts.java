package com.renhejia.robot.guidelib.consts;

public class BleConsts {
    /**
     * 消息提醒
     */
    public static final String BLE_CMD_MESSAGE_TIPS = "003";
    /**
     * 常规的控制指令
     */
    public static final String BLE_CMD_COMMON_CONTROL = "004";
    /**
     * 控制动作
     */
    public static final String BLE_CMD_CONTROL_MOTION = "005";

    /**
     * 切换ROM的地区，0061代表国内，0062代表海外
     */
    public static final String BLE_CMD_CHANGE_ROM_REGION = "006";
    public static final String ROM_REGION_CHINA = "1";
    public static final String ROM_REGION_GLOBAL = "2";
}
