package com.renhejia.robot.gesturefactory.parser.gesture;

/**
 * @author liujunbin
 */
public class GestureSoundEffect {

    private String sound;
    private String sound_name;
    private int times;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getSound_name() {
        return sound_name;
    }

    public void setSound_name(String sound_name) {
        this.sound_name = sound_name;
    }

    @Override
    public String toString() {
        return "{" +
                "sound='" + sound + '\'' +
                ", sound_name='" + sound_name + '\'' +
                ", times=" + times +
                '}';
    }
}
