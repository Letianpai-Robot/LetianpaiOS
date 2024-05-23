package com.renhejia.robot.commandlib.parser.tts;

public class Tts {

    private String tts;

    public Tts(String ttsInfo) {
        this.tts = ttsInfo;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String ttsInfo) {
        this.tts = ttsInfo;
    }

    @Override
    public String toString() {

        return "{" +
                '\"' + "tts" + '\"' + ":" + '\"' + tts + '\"' +
                '}';

    }
}
