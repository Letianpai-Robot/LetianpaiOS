package com.renhejia.robot.commandlib.parser.displaymodes.weather;
/**
 * 天气信息
 * @author liujunbin
 */
public class HourWeather {

    private String hour;
    private String wea;
    private String wea_img;
    private String tem;

    public String getHour() {
        return hour;
    }

    public String getTem() {
        return tem;
    }

    public String getWea() {
        return wea;
    }

    public String getWea_img() {
        return wea_img;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    @Override
    public String toString() {
        return "{" +
                "hour='" + hour + '\'' +
                ", wea='" + wea + '\'' +
                ", wea_img='" + wea_img + '\'' +
                ", tem='" + tem + '\'' +
                '}';
    }
}
