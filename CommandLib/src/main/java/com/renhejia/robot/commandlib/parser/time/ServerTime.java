package com.renhejia.robot.commandlib.parser.time;

/**
 * @author liujunbin
 */
public class ServerTime {

    private int code;
    private String msg;
    private TimeData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TimeData getData() {
        return data;
    }

    public void setData(TimeData data) {
        this.data = data;
    }

    // {"code":0,"data":{"timestamp":1688544743},"msg":"success"}




}
