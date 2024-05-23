package com.renhejia.robot.commandlib.parser.motion;

public class Motion {

    public Motion(int number) {
        this.number = number;
    }

    public Motion(String motion, int number) {
        this.motion = motion;
        this.number = number;
    }
    public Motion(String motion, int number,int stepNum) {
        this.motion = motion;
        this.number = number;
        this.stepNum = stepNum;
    }

    public Motion() {
    }

    private String motion;
    /**
     * 发给MCU 的值
     */
    private int number;
    private int speed;
    private String desc;
    private int id;
    private int stepNum;

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }
    /**
     * 发给MCU 的值
     */
    public void setNumber(int number) {
        this.number = number;
    }
    /**
     * 发给MCU 的值
     */
    public int getNumber() {
        return number;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
    public String showLog() {
        return " " + number;
    }
    @Override
    public String toString() {
        return "{" +
                "motion='" + motion + '\'' +
                ", number=" + number +
                ", speed=" + speed +
                ", desc='" + desc + '\'' +
                ", id=" + id +
                ", stepNum=" + stepNum +
                '}';
    }
}
