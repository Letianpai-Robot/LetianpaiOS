package com.renhejia.robot.gesturefactory.parser.gesture;

/**
 * @author liujunbin
 *
 */
public class Gestures {
    private GestureTTS tts;
    private GestureFace face;
    private GestureFootMotion foot;
    private GestureEarMotion ear;
    private GestureLight light;
    private GestureSoundEffect sound;
    private int interval;


    public GestureFace getFace() {
        return face;
    }

    public void setFace(GestureFace face) {
        this.face = face;
    }

    public GestureFootMotion getFoot() {
        return foot;
    }

    public void setFoot(GestureFootMotion foot) {
        this.foot = foot;
    }

    public GestureEarMotion getEar() {
        return ear;
    }

    public void setEar(GestureEarMotion ear) {
        this.ear = ear;
    }

    public GestureLight getLight() {
        return light;
    }

    public void setLight(GestureLight light) {
        this.light = light;
    }

    public GestureTTS getTts() {
        return tts;
    }


    public void setTts(GestureTTS tts) {
        this.tts = tts;
    }


    public GestureSoundEffect getSound() {
        return sound;
    }

    public void setSound(GestureSoundEffect sound) {
        this.sound = sound;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
