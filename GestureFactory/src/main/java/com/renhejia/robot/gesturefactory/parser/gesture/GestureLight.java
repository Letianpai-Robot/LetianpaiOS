package com.renhejia.robot.gesturefactory.parser.gesture;

/**
 * @author liujunbin
 */
public class GestureLight {

    private int color;
    private String status;
    private int times;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
                '\"' + "color" + '\"' + ":" + color +
                "," + '\"' + "status" + '\"' + ":" + status +
                "," + '\"' + "times" + '\"' + ":" + times +
                '}';

    }

}
