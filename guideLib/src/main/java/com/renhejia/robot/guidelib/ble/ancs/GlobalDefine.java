package com.renhejia.robot.guidelib.ble.ancs;

/**
 *
 */
public class GlobalDefine {

    public static final int NotificationAttributeIDBundleId = 0; // , (Needs to be followed by a 2-bytes
    public static final int NotificationAttributeIDTitle = 1; // , (Needs to be followed by a 2-bytes
    // max length parameter)
    public static final int NotificationAttributeIDSubtitle = 2; // , (Needs to be followed by a
    // 2-bytes max length parameter)
    public static final int NotificationAttributeIDMessage = 3; // , (Needs to be followed by a
    // 2-bytes max length parameter)
    public static final int NotificationAttributeIDMessageSize = 4; // ,
    public static final int NotificationAttributeIDDate = 5;

    public static final int BLUETOOTH_ON = 1001;
    public static final int BLUETOOTH_OFF = 1002;
    public static final int BLUETOOTH_CONNECT = 1003;
    public static final int BLUETOOTH_DISCONNECT = 1004;
    public static final int BLUETOOTH_BONDED = 1005;
    public static final int BLUETOOTH_BONDNONE = 1006;
    public static final int BLUETOOTH_BONDING = 1007;

    public static final int BLUETOOTH_GET_MORE_INFO = 1008;
    public static final int BLUETOOTH_DISPLAY_INFO = 1009;

    public static final String service_ancs = "7905f431-b5ce-4e99-a40f-4b1e122d00d0";
    public static final String characteristics_notification_source = "9fbf120d-6301-42d9-8c58-25e699a21dbd";
    public static final String characteristics_data_source = "22eac6e9-24d6-4bb5-be44-b36ace7c7bfb";
    public static final String characteristics_control_point = "69d1d8f3-45e1-49a8-9821-9bbdfdaad9d9";
    public static final String descriptor_config = "00002902-0000-1000-8000-00805f9b34fb";

    public static final String destoryActionString = "com.zhangbh.billchang.service.destory";
    public static final String androidResponseAction = "com.zhangbh.billchang.NotifyActionString";
    public static final String androidResponseActionCode = "com.zhangbh.billchang.androidResponseActionCode";
}
