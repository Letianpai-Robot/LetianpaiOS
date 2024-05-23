package com.renhejia.robot.commandlib.parser.displaymodes.fans;

import java.util.Arrays;

/**
 * @author liujunbin
 */
public class FansInfo {
    private int code;
    private FansData[] data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public FansData[] getData() {
        return data;
    }

    public void setData(FansData[] data) {
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
                ", data=" + Arrays.toString(data) +
                ", msg='" + msg + '\'' +
                '}';
    }
}
