package com.renhejia.robot.commandlib.parser.displaymodes.fans;

public class FansData {

    private String platform;
    private String avatar;
    private String nick_name;
    private String fans_count;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }

    @Override
    public String toString() {
        return "{" +
                "platform='" + platform + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", fans_count='" + fans_count + '\'' +
                '}';
    }

}
