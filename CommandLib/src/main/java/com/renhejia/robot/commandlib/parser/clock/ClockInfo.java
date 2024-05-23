package com.renhejia.robot.commandlib.parser.clock;

import java.util.Arrays;

public class ClockInfo {

    private int clock_id;
    private int clock_hour;
    private String clock_time;
    private String clock_title;
    private int is_on;
    private int [] repeat_method;
    private String repeat_method_label;


    public int getClock_id() {
        return clock_id;
    }

    public void setClock_id(int clock_id) {
        this.clock_id = clock_id;
    }

    public int getClock_hour() {
        return clock_hour;
    }

    public void setClock_hour(int clock_hour) {
        this.clock_hour = clock_hour;
    }

    public String getClock_time() {
        return clock_time;
    }

    public void setClock_time(String clock_time) {
        this.clock_time = clock_time;
    }

    public String getClock_title() {
        return clock_title;
    }

    public void setClock_title(String clock_title) {
        this.clock_title = clock_title;
    }

    public int getIs_on() {
        return is_on;
    }

    public void setIs_on(int is_on) {
        this.is_on = is_on;
    }

    public int[] getRepeat_method() {
        return repeat_method;
    }

    public void setRepeat_method(int[] repeat_method) {
        this.repeat_method = repeat_method;
    }

    public String getRepeat_method_label() {
        return repeat_method_label;
    }

    public void setRepeat_method_label(String repeat_method_label) {
        this.repeat_method_label = repeat_method_label;
    }


    @Override
    public String toString() {
        return "ClockInfo{" +
                "clock_id=" + clock_id +
                ", clock_hour=" + clock_hour +
                ", clock_time='" + clock_time + '\'' +
                ", clock_title='" + clock_title + '\'' +
                ", is_on=" + is_on +
                ", repeat_method=" + Arrays.toString(repeat_method) +
                ", repeat_method_label='" + repeat_method_label + '\'' +
                '}';
    }
}
