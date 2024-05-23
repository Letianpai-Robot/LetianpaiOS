package com.renhejia.robot.display;

public class RobotSkinImageWithSpace extends RobotSkinImage {

    private int fileSpace;
    private int aligns;
    private int total;

    public int getFileSpace() {
        return fileSpace;
    }

    public void setFileSpace(int fileSpace) {
        this.fileSpace = fileSpace;
    }

    public int getAligns() {
        return aligns;
    }

    public void setAligns(int aligns) {
        this.aligns = aligns;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int totalBatteryCount) {
        this.total = totalBatteryCount;
    }
}
