package com.renhejia.robot.launcher.mode;

import com.google.gson.annotations.SerializedName;

public class DeviceBindInfo {
    private int code;
    private DeviceBindInfoData data;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DeviceBindInfoData data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DeviceBindInfoData getData() {
        return data;
    }


    @Override
    public String toString() {
        return "{" +
                "code:" + code +
                ", data:" + data +
                ", msg:'" + msg + '\'' +
                '}';
    }
}


