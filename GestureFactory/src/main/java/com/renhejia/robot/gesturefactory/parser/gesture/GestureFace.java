package com.renhejia.robot.gesturefactory.parser.gesture;

/**
 * @author liujunbin
 */
public class GestureFace {

    private String face;
    private String face_name;
    private String filePrefix;
    private int times;

    public String getFace_name() {
        return face_name;
    }

    public void setFace_name(String face_name) {
        this.face_name = face_name;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public GestureFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return "{" +
                "face='" + face + '\'' +
                ", face_name='" + face_name + '\'' +
                ", filePrefix='" + filePrefix + '\'' +
                ", times=" + times +
                '}';
    }


}
