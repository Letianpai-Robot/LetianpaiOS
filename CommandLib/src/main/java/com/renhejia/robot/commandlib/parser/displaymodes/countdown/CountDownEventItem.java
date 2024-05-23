package com.renhejia.robot.commandlib.parser.displaymodes.countdown;

/**
 * @author liujunbin
 */
public class CountDownEventItem {

    private String event_title;
    private long event_time;
    private int remain_days;

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public long getEvent_time() {
        return event_time;
    }

    public void setEvent_time(long event_time) {
        this.event_time = event_time;
    }

    public int getRemain_days() {
        return remain_days;
    }

    public void setRemain_days(int remain_days) {
        this.remain_days = remain_days;
    }

    @Override
    public String toString() {
        return "{" +
                "event_title='" + event_title + '\'' +
                ", event_time=" + event_time +
                ", remain_days=" + remain_days +
                '}';
    }
}
