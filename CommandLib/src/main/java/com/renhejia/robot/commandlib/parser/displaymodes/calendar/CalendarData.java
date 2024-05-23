package com.renhejia.robot.commandlib.parser.displaymodes.calendar;

import java.util.Arrays;

public class CalendarData {

    private int event_total;
    private boolean has_more;
    private CalenderItem[] memo_list;

    public CalenderItem[] getMemo_list() {
        return memo_list;
    }

    public int getEvent_total() {
        return event_total;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setEvent_total(int event_total) {
        this.event_total = event_total;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public void setMemo_list(CalenderItem[] memo_list) {
        this.memo_list = memo_list;
    }

    @Override
    public String toString() {
        return "{" +
                "event_total=" + event_total +
                ", has_more=" + has_more +
                ", memo_list=" + Arrays.toString(memo_list) +
                '}';
    }
}
