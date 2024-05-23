package com.renhejia.robot.commandlib.parser.displaymodes.calendar;

public class CalenderItem {

    private String memo_title;
    private long memo_time;
    private String memo_time_label;

    public long getMemo_time() {
        return memo_time;
    }

    public String getMemo_time_label() {
        return memo_time_label;
    }

    public String getMemo_title() {
        return memo_title;
    }

    public void setMemo_time(long memo_time) {
        this.memo_time = memo_time;
    }

    public void setMemo_time_label(String memo_time_label) {
        this.memo_time_label = memo_time_label;
    }

    public void setMemo_title(String memo_title) {
        this.memo_title = memo_title;
    }

    @Override
    public String toString() {
        return "{" +
                "memo_title='" + memo_title + '\'' +
                ", memo_time=" + memo_time +
                ", memo_time_label='" + memo_time_label + '\'' +
                '}';
    }
}
