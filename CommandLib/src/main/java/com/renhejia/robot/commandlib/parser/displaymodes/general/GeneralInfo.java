package com.renhejia.robot.commandlib.parser.displaymodes.general;

public class GeneralInfo {
    private int code;
    private GeneralData data;
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

    public void setData(GeneralData data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public GeneralData getData() {
        return data;
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
