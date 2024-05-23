package com.renhejia.robot.display;

import android.graphics.Point;

public class RobotSkinImageWithAngle extends RobotSkinImage {

    private int intervalAngle;
    private int imageAngle;
    private int startAngle;
    private int total;

    private Point origAnchor;
    private Point displayAnchor;

    public int getTotal() {
        return total;
    }

    public void setTotal(int totalBatteryCount) {
        this.total = totalBatteryCount;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getIntervalAngle() {
        return intervalAngle;
    }

    public void setIntervalAngle(int intervalAngle) {
        this.intervalAngle = intervalAngle;
    }

    public Point getOrigAnchor() {
        return origAnchor;
    }

    public void setOrigAnchor(Point origAnchor) {
        this.origAnchor = origAnchor;
    }

    public Point getDisplayAnchor() {
        return displayAnchor;
    }

    public void setDisplayAnchor(Point displayAnchor) {
        this.displayAnchor = displayAnchor;
    }

    public int getImageAngle() {
        return imageAngle;
    }

    public void setImageAngle(int imageAngle) {
        this.imageAngle = imageAngle;
    }
}
