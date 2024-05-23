package com.renhejia.robot.launcher.displaymode.display;

/**
 * 显示模式状态
 */
public class DisplayMode {

    private int general_display_switch;
    private int weather_display_switch;
    private int calendar_display_switch;
    private int countdown_display_switch;
    private int fans_display_switch;

    public int getCalendar_display_switch() {
        return calendar_display_switch;
    }

    public int getCountdown_display_switch() {
        return countdown_display_switch;
    }

    public int getFans_display_switch() {
        return fans_display_switch;
    }

    public int getGeneral_display_switch() {
        return general_display_switch;
    }

    public int getWeather_display_switch() {
        return weather_display_switch;
    }

    public void setCalendar_display_switch(int calendar_display_switch) {
        this.calendar_display_switch = calendar_display_switch;
    }

    public void setCountdown_display_switch(int countdown_display_switch) {
        this.countdown_display_switch = countdown_display_switch;
    }

    public void setFans_display_switch(int fans_display_switch) {
        this.fans_display_switch = fans_display_switch;
    }

    public void setGeneral_display_switch(int general_display_switch) {
        this.general_display_switch = general_display_switch;
    }

    public void setWeather_display_switch(int weather_display_switch) {
        this.weather_display_switch = weather_display_switch;
    }

    @Override
    public String toString() {
        return "DisplayMode{" +
                "general_display_switch=" + general_display_switch +
                ", weather_display_switch=" + weather_display_switch +
                ", calendar_display_switch=" + calendar_display_switch +
                ", countdown_display_switch=" + countdown_display_switch +
                ", fans_display_switch=" + fans_display_switch +
                '}';
    }
}
