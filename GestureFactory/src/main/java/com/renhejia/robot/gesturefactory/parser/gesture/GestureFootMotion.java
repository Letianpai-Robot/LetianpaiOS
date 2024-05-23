package com.renhejia.robot.gesturefactory.parser.gesture;

/**
 * @author liujunbin
 */
public class GestureFootMotion {

    private String motion;
    private String motion_name;
    private int number;
    private int speed;


    public GestureFootMotion(String motion, int number, int speed) {
        this.motion = motion;
        this.number = number;
        this.speed = speed;
    }

    public String getMotion_name() {
        return motion_name;
    }

    public void setMotion_name(String motion_name) {
        this.motion_name = motion_name;
    }

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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "{" +
                "motion='" + motion + '\'' +
                ", motion_name='" + motion_name + '\'' +
                ", number=" + number +
                ", speed=" + speed +
                '}';
    }

}
