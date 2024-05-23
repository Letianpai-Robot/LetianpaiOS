package com.renhejia.robot.commandlib.parser.wakeup;

import com.google.gson.annotations.SerializedName;

public class WakeUp {


    @SerializedName("xiaole_switch")
    private int xiaoleSwitch;
    @SerializedName("xiaopai_switch")
    private int xiaopaiSwitch;
    @SerializedName("selected_voice_id")
    private String selectedVoiceId;
    @SerializedName("selected_voice_name")
    private String selectedVoiceName;
    @SerializedName("boy_voice_switch")
    private int boyVoiceSwitch;
    @SerializedName("girl_voice_switch")
    private int girlVoiceSwitch;
    @SerializedName("boy_child_voice_switch")
    private int boyChildVoiceSwitch;
    @SerializedName("girl_child_voice_switch")
    private int girlChildVoiceSwitch;
    @SerializedName("robot_voice_switch")
    private int robotVoiceSwitch;

    @Override
    public String toString() {
        return "WakeUp{" +
                "xiaoleSwitch=" + xiaoleSwitch +
                ", xiaopaiSwitch=" + xiaopaiSwitch +
                ", selectedVoiceId='" + selectedVoiceId + '\'' +
                ", selectedVoiceName='" + selectedVoiceName + '\'' +
                ", boyVoiceSwitch=" + boyVoiceSwitch +
                ", girlVoiceSwitch=" + girlVoiceSwitch +
                ", boyChildVoiceSwitch=" + boyChildVoiceSwitch +
                ", girlChildVoiceSwitch=" + girlChildVoiceSwitch +
                ", robotVoiceSwitch=" + robotVoiceSwitch +
                '}';
    }

    public int getXiaoleSwitch() {
        return xiaoleSwitch;
    }

    public void setXiaoleSwitch(int xiaoleSwitch) {
        this.xiaoleSwitch = xiaoleSwitch;
    }

    public int getXiaopaiSwitch() {
        return xiaopaiSwitch;
    }

    public void setXiaopaiSwitch(int xiaopaiSwitch) {
        this.xiaopaiSwitch = xiaopaiSwitch;
    }

    public String getSelectedVoiceId() {
        return selectedVoiceId;
    }

    public void setSelectedVoiceId(String selectedVoiceId) {
        this.selectedVoiceId = selectedVoiceId;
    }

    public String getSelectedVoiceName() {
        return selectedVoiceName;
    }

    public void setSelectedVoiceName(String selectedVoiceName) {
        this.selectedVoiceName = selectedVoiceName;
    }

    public int getBoyVoiceSwitch() {
        return boyVoiceSwitch;
    }

    public void setBoyVoiceSwitch(int boyVoiceSwitch) {
        this.boyVoiceSwitch = boyVoiceSwitch;
    }

    public int getGirlVoiceSwitch() {
        return girlVoiceSwitch;
    }

    public void setGirlVoiceSwitch(int girlVoiceSwitch) {
        this.girlVoiceSwitch = girlVoiceSwitch;
    }

    public int getBoyChildVoiceSwitch() {
        return boyChildVoiceSwitch;
    }

    public void setBoyChildVoiceSwitch(int boyChildVoiceSwitch) {
        this.boyChildVoiceSwitch = boyChildVoiceSwitch;
    }

    public int getGirlChildVoiceSwitch() {
        return girlChildVoiceSwitch;
    }

    public void setGirlChildVoiceSwitch(int girlChildVoiceSwitch) {
        this.girlChildVoiceSwitch = girlChildVoiceSwitch;
    }

    public int getRobotVoiceSwitch() {
        return robotVoiceSwitch;
    }

    public void setRobotVoiceSwitch(int robotVoiceSwitch) {
        this.robotVoiceSwitch = robotVoiceSwitch;
    }
}
