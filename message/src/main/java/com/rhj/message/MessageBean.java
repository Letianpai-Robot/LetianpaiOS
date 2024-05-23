package com.rhj.message;

public class MessageBean {
    public static final int TYPE_OUTPUT = 7;
    public static final int TYPE_INPUT = 1;
    public static final int TYPE_WIDGET_CONTENT = 2;
    public static final int TYPE_WIDGET_LIST = 3;
    public static final int TYPE_WIDGET_WEB = 4;
    /**
     * 音乐类的消息回调，如歌曲信息
     */
    public static final int TYPE_WIDGET_MEDIA = 5;
    public static final int TYPE_WIDGET_WEATHER = 6;

    private int message_type;

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

}
