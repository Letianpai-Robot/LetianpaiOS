package com.renhejia.robot.display;

import android.graphics.Rect;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RobotSkinNumber {

    private Rect origRect;
    private Rect dispRect;

    private Rect origTouchRect;
    private Rect dispTouchRect;

    private String filePrefix;
    private int fileSpace;
    private String dataFormat;

    private int align;
    private static int INVALID_INDEX = 99;


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

    private static String getBestDateTimePattern(String skeleton){
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), skeleton);

        return pattern;
    }
    public String getTimeString(Date date, int hourFormat) {

        try {//Todo format 'u'在jdk1.6不支持
            String strFormat = dataFormat;

            if (dataFormat.equals("u")) {

                Calendar now = Calendar.getInstance();
                now.setTime(date);
                // boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
                int weekDay = now.get(Calendar.DAY_OF_WEEK);
                String weekStr = "";
                switch (weekDay) {
                    case Calendar.SUNDAY:
                        //                        weekStr = "7";
                        weekStr = "g";
                        break;
                    case Calendar.MONDAY:
                        //                        weekStr = "1";
                        weekStr = "a";
                        break;
                    case Calendar.TUESDAY:
                        //                        weekStr = "2";
                        weekStr = "b";
                        break;
                    case Calendar.WEDNESDAY:
                        //                        weekStr = "3";
                        weekStr = "c";
                        break;
                    case Calendar.THURSDAY:
                        //                        weekStr = "4";
                        weekStr = "d";
                        break;
                    case Calendar.FRIDAY:
                        //                        weekStr = "5";
                        weekStr = "e";
                        break;
                    case Calendar.SATURDAY:
                        //                        weekStr = "6";
                        weekStr = "f";
                        break;
                    default:
                        break;
                }

                return weekStr;
            }

            if (hourFormat == RobotClockView.SPINE_CLOCK_HOURS_24) {
                strFormat = strFormat.replace("hh", "HH");


            }else if (hourFormat == RobotClockView.SPINE_CLOCK_HOURS_12){
                strFormat = strFormat.replace("HH", "hh");

            }

            if (strFormat.length() > 0) {
                int weekIndex = getWeekFormatIndex(strFormat);
                SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.getDefault());
                String strDate = format.format(date);
                if (weekIndex != INVALID_INDEX){
                    strDate = convertToCorrectDateFormat(weekIndex,strDate,date);
                }

                return strDate;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    private String convertToCorrectDateFormat(int index,String strDate,Date date) {

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        switch (weekDay) {
            case Calendar.SUNDAY:
                weekStr = "g";
                break;
            case Calendar.MONDAY:
                weekStr = "a";
                break;
            case Calendar.TUESDAY:
                weekStr = "b";
                break;
            case Calendar.WEDNESDAY:
                weekStr = "c";
                break;
            case Calendar.THURSDAY:
                weekStr = "d";
                break;
            case Calendar.FRIDAY:
                weekStr = "e";
                break;
            case Calendar.SATURDAY:
                weekStr = "f";
                break;
            default:
                break;
        }
        String newStrDate = strDate.substring(0,index) + weekStr + strDate.substring(index + 1, strDate.length());
        return newStrDate;
    }

    private int getWeekFormatIndex( String strFormat) {
        char WEEK_INDICATOR = 'u';
        for (int i = 0; i < strFormat.length();i++){
            if (WEEK_INDICATOR == strFormat.charAt(i)){
                return i;
            }
        }
        return INVALID_INDEX;


    }



    public String getImgFilename(String str) {
        return filePrefix + RobotSkinFileMap.getFilePostfix(str) + ".png";
    }

    public String getImgFilenameFlash(String str,int millisecond) {
        return filePrefix + RobotSkinFileMap.getFilePostfixWith(str,millisecond) + ".png";
    }

    public int getFileSpace() {
        return fileSpace;
    }

    public void setFileSpace(int fileSpace) {
        this.fileSpace = fileSpace;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
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
