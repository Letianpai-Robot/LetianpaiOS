package com.renhejia.robot.launcher.mode;

import com.google.gson.annotations.SerializedName;

public class DeviceBindInfoData{
    //0:未绑定，1:已绑定
    @SerializedName("bind_status")
    private int bindStatus;

    @SerializedName("bind_time")
    private int bindTime;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("user_id")
    private int userId;

    public int getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(int bindStatus) {
        this.bindStatus = bindStatus;
    }

    public int getBindTime() {
        return bindTime;
    }

    public void setBindTime(int bindTime) {
        this.bindTime = bindTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
                "bindStatus:" + bindStatus +
                ", bindTime:" + bindTime +
                ", clientId:'" + clientId + '\'' +
                ", userId:'" + userId + '\'' +
                '}';
    }
}
