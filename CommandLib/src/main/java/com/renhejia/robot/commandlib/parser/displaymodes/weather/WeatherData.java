package com.renhejia.robot.commandlib.parser.displaymodes.weather;

import java.util.Arrays;
/**
 * 天气信息
 * @author liujunbin
 */
public class WeatherData {
    private String province;
    private String city;
    private String town;
    private String wea;
    private String wea_img;
    private String tem;
    private String tem_max;
    private String tem_min;
    private String win;
    private String win_speed;
    private HourWeather[] hourWeather;

    public String getWea_img() {
        return wea_img;
    }

    public String getWea() {
        return wea;
    }

    public String getTem() {
        return tem;
    }

    public HourWeather[] getHourWeather() {
        return hourWeather;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getTem_max() {
        return tem_max;
    }

    public String getTem_min() {
        return tem_min;
    }

    public String getTown() {
        return town;
    }

    public String getWin() {
        return win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHourWeather(HourWeather[] hourWeather) {
        this.hourWeather = hourWeather;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setTem_max(String tem_max) {
        this.tem_max = tem_max;
    }

    public void setTem_min(String tem_min) {
        this.tem_min = tem_min;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    @Override
    public String toString() {
        return "{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", town='" + town + '\'' +
                ", wea='" + wea + '\'' +
                ", wea_img='" + wea_img + '\'' +
                ", tem='" + tem + '\'' +
                ", tem_max='" + tem_max + '\'' +
                ", tem_min='" + tem_min + '\'' +
                ", win='" + win + '\'' +
                ", win_speed='" + win_speed + '\'' +
                ", hourWeather=" + Arrays.toString(hourWeather) +
                '}';
    }
}
