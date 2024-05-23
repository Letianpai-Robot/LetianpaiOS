package com.renhejia.robot.display;

import android.graphics.Point;
import android.graphics.Rect;

public class RobotSkinAnchor {

    private Rect origRect;
    private Rect dispRect;
    private Rect origTouchRect;
    private Rect dispTouchRect;

    private String imgFile;

    private Point origAnchor;
    private Point dispAnchor;

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

    public Point getOrigAnchor() {
        return origAnchor;
    }

    public void setOrigAnchor(Point origAnchor) {
        this.origAnchor = origAnchor;
    }

    public Point getDispAnchor() {
        return dispAnchor;
    }

    public void setDispAnchor(Point dispAnchor) {
        this.dispAnchor = dispAnchor;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public Rect getOrigTouchRect() {
        return origTouchRect;
    }

    public void setOrigTouchRect(Rect origTouchRect) {
        this.origTouchRect = origTouchRect;
    }

    public Rect getDispTouchRect() {
        return dispTouchRect;
    }

    public void setDispTouchRect(Rect dispTouchRect) {
        this.dispTouchRect = dispTouchRect;
    }
}
