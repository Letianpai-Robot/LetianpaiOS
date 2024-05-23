package com.renhejia.robot.display;

import android.graphics.Rect;

public class RobotSkinArc {


    private int color;
    private int align;
    private int startAngle;
    private int sweepAngle;
    private int strokeWidth;

    private Rect origRect;
    private Rect dispRect;
    private Rect origTouchRect;
    private Rect dispTouchRect;

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

    public int getColor() {
        return 0xFF000000 | color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
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

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
