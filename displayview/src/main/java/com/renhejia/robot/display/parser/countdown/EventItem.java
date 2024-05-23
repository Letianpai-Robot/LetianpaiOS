package com.renhejia.robot.display.parser.countdown;

public class EventItem {

    private long event_time;
    private String event_title;
    private int remain_days;

    public long getEvent_time() {
        return event_time;
    }

    public void setEvent_time(long event_time) {
        this.event_time = event_time;
    }

    public int getRemain_days() {
        return remain_days;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setRemain_days(int remain_days) {
        this.remain_days = remain_days;
    }


}
