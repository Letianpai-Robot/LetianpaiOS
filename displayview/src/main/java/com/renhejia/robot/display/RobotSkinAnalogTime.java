package com.renhejia.robot.display;

import android.graphics.Point;
import android.graphics.Rect;

public class RobotSkinAnalogTime {

    private Rect origRect;
    private Rect dispRect;
    private Rect origTouchRect;
    private Rect dispTouchRect;

    private String filePrefix;

    private Point origHourAnchor;
    private Point origMinuteAnchor;
    private Point origSecondAnchor;

    private Point dispHourAnchor;
    private Point dispMinuteAnchor;
    private Point dispSecondAnchor;

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

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }


    public String getHourFilename() {
        return filePrefix + "hour.png";
    }

    public String getMinuteFilename() {
        return filePrefix + "minute.png";
    }
    public String getSecondFilename() {
        return filePrefix + "second.png";
    }

    public Point getOrigHourAnchor() {
        return origHourAnchor;
    }

    public void setOrigHourAnchor(Point origHourAnchor) {
        this.origHourAnchor = origHourAnchor;
    }

    public Point getOrigMinuteAnchor() {
        return origMinuteAnchor;
    }

    public void setOrigMinuteAnchor(Point origMinuteAnchor) {
        this.origMinuteAnchor = origMinuteAnchor;
    }

    public Point getOrigSecondAnchor() {
        return origSecondAnchor;
    }

    public void setOrigSecondAnchor(Point origSecondAnchor) {
        this.origSecondAnchor = origSecondAnchor;
    }

    public Point getDispHourAnchor() {
        return dispHourAnchor;
    }

    public void setDispHourAnchor(Point dispHourAnchor) {
        this.dispHourAnchor = dispHourAnchor;
    }

    public Point getDispMinuteAnchor() {
        return dispMinuteAnchor;
    }

    public void setDispMinuteAnchor(Point dispMinuteAnchor) {
        this.dispMinuteAnchor = dispMinuteAnchor;
    }

    public Point getDispSecondAnchor() {
        return dispSecondAnchor;
    }

    public void setDispSecondAnchor(Point dispSecondAnchor) {
        this.dispSecondAnchor = dispSecondAnchor;
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
