package com.renhejia.robot.guidelib.ble.ancs;


/**
 * a notice from iPhone ANCS<br>
 */
public class IOSNotification {
    /**
     * the unique identifier (UID) for the iOS notification
     */
    int uid;
    /**
     * title for the iOS notification
     */
    String title;
    /**
     * subtitle for the iOS notification
     */
    String subtitle;
    /**
     * message(content) for the iOS notification
     */
    String message;
    /**
     * size (how many byte) of message
     */
    String messageSize;
    /**
     * the time for the iOS notification
     */
    String date;
    String bundleId;
    public IOSNotification(){}
    public IOSNotification(String t, String s, String m, String ms, String d, String b) {
        title = t;
        subtitle = s;
        message = m;
        messageSize = ms;
        date = d;
        bundleId = b;
    }

    boolean isAllInit() {
        return title != null && subtitle != null && message != null && messageSize != null && date != null && bundleId != null;
    }
}
