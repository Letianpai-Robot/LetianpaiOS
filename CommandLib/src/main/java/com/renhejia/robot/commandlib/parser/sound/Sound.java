package com.renhejia.robot.commandlib.parser.sound;

public class Sound {
    private String sound;
    private int id;
    private String desc;

    public Sound(String sound) {
        this.sound = sound;
    }

    public Sound(String sound, String desc) {
        this.sound = sound;
        this.desc = desc;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String showLog() {
        return " " + sound;
    }
    @Override
    public String toString() {
        return "{" +
                "sound='" + sound + '\'' +
                ", id=" + id +
                ", desc='" + desc + '\'' +
                '}';
    }
}
