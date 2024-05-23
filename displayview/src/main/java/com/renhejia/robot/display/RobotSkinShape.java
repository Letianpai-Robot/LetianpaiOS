package com.renhejia.robot.display;

import android.graphics.Rect;

public class RobotSkinShape {

    private int bgColor;
    private int fgColor;
    private int align;
//    private int origSize;
//    private int dispSize;

    private Rect origRect;
    private Rect dispRect;

    public Rect getOrigRect() {
        return origRect;
    }

    public void setOrigRect(Rect origRect) {
        this.origRect = origRect;
    }

    public Rect getDispRect() {
        return dispRect;
    }

    public void setDispRect(Rect dispRect) {
        this.dispRect = dispRect;
    }


    public int getBgColor() {
        return 0xFF000000 | bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getFgColor() {
        return 0xFF000000 | fgColor;
    }

    public void setFgColor(int fgColor) {
        this.fgColor = fgColor;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

//    public int getOrigSize() {
//        return origSize;
//    }
//
//    public void setOrigSize(int origSize) {
//        this.origSize = origSize;
//    }
//
//    public int getDispSize() {
//        return dispSize;
//    }
//
//    public void setDispSize(int dispSize) {
//        this.dispSize = dispSize;
//    }


}
