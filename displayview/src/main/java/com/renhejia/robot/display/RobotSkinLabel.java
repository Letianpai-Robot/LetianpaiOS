package com.renhejia.robot.display;

import android.graphics.Rect;

public class RobotSkinLabel {


    private String text;
    private int color;
    private int align;
    private int origSize;
    private int dispSize;
    private int style;

    private Rect origRect;
    private Rect dispRect;
    private String dataFormat;
    private String languageFormat;// 显示中英文
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getOrigSize() {
        return origSize;
    }

    public void setOrigSize(int origSize) {
        this.origSize = origSize;
    }

    public int getDispSize() {
        return dispSize;
    }

    public void setDispSize(int dispSize) {
        this.dispSize = dispSize;
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

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getLanguageFormat() {
        return languageFormat;
    }

    public void setLaunguageFormat(String launguageFormat) {
        this.languageFormat = launguageFormat;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
