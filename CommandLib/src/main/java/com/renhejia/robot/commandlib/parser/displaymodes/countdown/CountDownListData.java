package com.renhejia.robot.commandlib.parser.displaymodes.countdown;

import java.util.Arrays;

/**
 * @author liujunbin
 */
public class CountDownListData {

    private int event_total;
    private boolean has_more;
    private CountDownEventItem[] event_list;

    public int getEvent_total() {
        return event_total;
    }

    public void setEvent_total(int event_total) {
        this.event_total = event_total;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public CountDownEventItem[] getEvent_list() {
        return event_list;
    }

    public void setEvent_list(CountDownEventItem[] event_list) {
        this.event_list = event_list;
    }

    @Override
    public String toString() {
        return "{" +
                "event_total=" + event_total +
                ", has_more=" + has_more +
                ", event_list=" + Arrays.toString(event_list) +
                '}';
    }
}
