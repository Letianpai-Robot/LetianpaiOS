package com.renhejia.robot.commandlib.parser.displaymodes.weather;

/**
 * 天气信息
 * @author liujunbin
 */
public class WeatherInfo {
    private int code;
    private WeatherData data;
    private String msg;

    public int getCode() {
        return code;
    }

    public WeatherData getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(WeatherData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}

