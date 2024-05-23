package com.renhejia.robot.commandlib.parser.clock;

public class ClockData {
    private int clock_id;
    private String action;

    public int getClock_id() {
        return clock_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setClock_id(int clock_id) {
        this.clock_id = clock_id;
    }
}
