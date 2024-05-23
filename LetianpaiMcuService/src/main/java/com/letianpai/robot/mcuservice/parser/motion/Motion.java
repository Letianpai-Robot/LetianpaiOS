package com.letianpai.robot.mcuservice.parser.motion;

public class Motion {

    public Motion(String motion, int number) {
        this.motion = motion;
        this.number = number;

    }

    private String motion;
    private int number;

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "{" +
                "motion='" + motion + '\'' +
                ", number=" + number +
                '}';
    }
}
