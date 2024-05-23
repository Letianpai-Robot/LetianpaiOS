package com.renhejia.robot.commandlib.parser.face;

public class Face {

    private String face;
    private String filePrefix;
    private String times;
    private String desc;
    private int id;

    public int getId() {
        return id;
    }


    public String showLog() {
        return " " + face;
    }

    @Override
    public String toString() {
        return "{" +
                "face='" + face + '\'' +
                ", filePrefix='" + filePrefix + '\'' +
                ", times='" + times + '\'' +
                ", desc='" + desc + '\'' +
                ", id=" + id +
                '}';
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

    public Face(String face) {
        this.face = face;
    }

    public Face(String face, String desc) {
        this.face = face;
        this.desc = desc;
    }


    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    //    private String face;
//
//    public Face(String face){
//        this.face = face;
//    }
//
//    public String getFace() {
//        return face;
//    }
//
//    public void setFace(String face) {
//        this.face = face;
//    }
//
//    @Override
//    public String toString() {
//
//        return "{" +
//                '\"' + "face"+ '\"' +":" + '\"' +face + '\"' +
//                '}';
//    }


}
