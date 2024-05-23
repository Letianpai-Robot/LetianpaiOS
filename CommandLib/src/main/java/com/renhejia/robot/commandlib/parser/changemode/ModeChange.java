package com.renhejia.robot.commandlib.parser.changemode;

public class ModeChange {

    private String mode;
    private int mode_status;

    public String getMode() {
        return mode;
    }

    public int getMode_status() {
        return mode_status;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setMode_status(int mode_status) {
        this.mode_status = mode_status;
    }

    @Override
    public String toString() {
        return "ModeChange{" +
                "mode='" + mode + '\'' +
                ", mode_status=" + mode_status +
                '}';
    }
}
