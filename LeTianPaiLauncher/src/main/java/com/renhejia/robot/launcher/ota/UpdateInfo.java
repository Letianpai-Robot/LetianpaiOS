package com.renhejia.robot.launcher.ota;

/**
 * Created by binzo on 2018/4/2.
 */

public class UpdateInfo {
    private Integer code;
    private Data data;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("data: {\n");
        sb.append("    code: ").append(code).append("\n");
        sb.append("    msg: ").append(msg).append("\n");
        if (data == null) sb.append("    data: null");
        else sb.append(data.toString());
        sb.append("}\n");
        return sb.toString();
    }
}
