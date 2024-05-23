package com.renhejia.robot.display.parser.countdown;

public class EventData {

    private EventItem[] event_list;
    private int event_total;
    private boolean has_more;

    public EventItem[] getEvent_list() {
        return event_list;
    }

    public void setEvent_list(EventItem[] event_list) {
        this.event_list = event_list;
    }

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




}
