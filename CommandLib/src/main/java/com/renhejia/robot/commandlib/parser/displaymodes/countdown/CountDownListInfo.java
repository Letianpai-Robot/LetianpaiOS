package com.renhejia.robot.commandlib.parser.displaymodes.countdown;

/**
 * @author liujunbin
 */
public class CountDownListInfo {

    private int code;
    private CountDownListData data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CountDownListData getData() {
        return data;
    }

    public void setData(CountDownListData data) {
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
