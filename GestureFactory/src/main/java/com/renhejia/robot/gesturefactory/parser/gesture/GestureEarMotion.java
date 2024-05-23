package com.renhejia.robot.gesturefactory.parser.gesture;

/**
 * @author liujunbin
 */
public class GestureEarMotion {

    private String motion;
    private int step;
    private int speed;
    private int times;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public String toString() {

        return "{" +
                '\"' + "motion" + '\"' + ":" + '\"' + motion + '\"' +
                "," + '\"' + "speed" + '\"' + ":" + speed +
                "," + '\"' + "step" + '\"' + ":" + step +
                "," + '\"' + "times" + '\"' + ":" + times +
                '}';

    }


}
