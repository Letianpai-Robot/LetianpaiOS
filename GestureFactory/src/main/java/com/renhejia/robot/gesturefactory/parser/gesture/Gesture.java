package com.renhejia.robot.gesturefactory.parser.gesture;

/**
 * @author liujunbin
 */
import java.util.Arrays;

public class Gesture {

    private GestureInfo info;
    private Gestures[] gesture;

    public GestureInfo getInfo() {
        return info;
    }

    public void setGesture(Gestures[] gesture) {
        this.gesture = gesture;
    }

    public Gestures[] getGesture() {
        return gesture;
    }

    public void setInfo(GestureInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Gesture{" +
                "info=" + info +
                ", gesture=" + Arrays.toString(gesture) +
                '}';
    }
}
