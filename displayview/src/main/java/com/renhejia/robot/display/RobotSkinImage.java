package com.renhejia.robot.display;


import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_CLOUDY;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_FOG;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_HAIL;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_HAZE;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_NO_INFO;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_RAIN;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_RAIN_HAIL;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_RAIN_SNOW;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_RAIN_THUNDER;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_SAND_DUST;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_SNOW;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_SUNNY;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_THUNDER;
import static com.renhejia.robot.display.RobotClockSkin.WEATHER_TYPE_WIND;

import android.graphics.Rect;


public class RobotSkinImage {

    private Rect origRect;
    private Rect dispRect;

    private Rect origTouchRect;
    private Rect dispTouchRect;

    private String filePrefix;
    private int align;

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


    public String getOnOffFilename(boolean enabled) {
        if (enabled) {
            return filePrefix + "on.png";
        } else {
            return filePrefix + "off.png";
        }
    }

    public String getNoticeFileName() {
        return filePrefix + "notice.png";
    }

    public String getFansIconFileName() {
        return filePrefix + "icon.png";
    }
    public String getFansHeadFileName() {
        return filePrefix + "head.png";
    }

    public String getWeatherFilename(int weatherId) {
        String strWeather = "no_info";


        switch (weatherId) {
            case WEATHER_TYPE_NO_INFO:
                strWeather = "no_info";
                break;
            case WEATHER_TYPE_SUNNY:
                strWeather = "sunny";
                break;
            case WEATHER_TYPE_CLOUDY:
                strWeather = "cloudy";
                break;
            case WEATHER_TYPE_RAIN:
                strWeather = "rain";
                break;
            case WEATHER_TYPE_SNOW:
                strWeather = "snow";
                break;
            case WEATHER_TYPE_HAZE:
                strWeather = "haze";
                break;
            case WEATHER_TYPE_SAND_DUST:
                strWeather = "sand_dust";
                break;
            case WEATHER_TYPE_WIND:
                strWeather = "wind";
                break;
            case WEATHER_TYPE_THUNDER:
                strWeather = "thunder";
                break;
            case WEATHER_TYPE_HAIL:
                strWeather = "hail";
                break;
            case WEATHER_TYPE_FOG:
                strWeather = "smog";
                break;
            case WEATHER_TYPE_RAIN_HAIL:
                strWeather = "rain_hail";
                break;
            case WEATHER_TYPE_RAIN_SNOW:
                strWeather = "rain_snow";
                break;
            case WEATHER_TYPE_RAIN_THUNDER:
                strWeather = "rain_thunder";
                break;
        }

        return filePrefix + strWeather + ".png";
    }

    public String getVolumeFilename(int volume) {
        return filePrefix + volume + ".png";
    }


    public String getBackgroundFilename() {
        if (filePrefix == null) {
            return "background.png";
        } else {
            return filePrefix + "background.png";
        }
    }

    public String getCustomizedBackgroundFilename() {

        return "customized_background.png";
    }

    public String getBatteryFilename(int batteryLevel) {
        int level = batteryLevel / 10 * 10;
        return filePrefix + level + ".png";
    }


    public String getForeground() {
        if (filePrefix == null) {
            return "foreground.png";
        } else {
            return filePrefix + "foreground.png";
        }
    }

    public String getMiddle() {
        if (filePrefix == null) {
            return "middle.png";
        } else {
            return filePrefix + "middle.png";
        }
    }

    public String getBatterySquaresFull() {
        return "battery_squares_full.png";
    }

    public String getBatterySquaresEmpty() {
        return "battery_squares_empty.png";
    }

    public String getStepSquaresFull() {
        return "step_squares_full.png";
    }

    public String getStepSquaresEmpty() {
        return "step_squares_empty.png";
    }

    public String getBatteryFanFull() {
        return "battery_fan_full.png";
    }

    public String getBatteryFanEmpty() {
        return "battery_fan_empty.png";
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

}
