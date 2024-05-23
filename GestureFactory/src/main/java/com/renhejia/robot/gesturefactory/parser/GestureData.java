package com.renhejia.robot.gesturefactory.parser;

import com.renhejia.robot.commandlib.parser.antennalight.AntennaLight;
import com.renhejia.robot.commandlib.parser.antennamotion.AntennaMotion;
import com.renhejia.robot.commandlib.parser.face.Face;
import com.renhejia.robot.commandlib.parser.motion.Motion;
import com.renhejia.robot.commandlib.parser.sound.Sound;
import com.renhejia.robot.commandlib.parser.tts.Tts;

/**
 * 姿态数据对象类
 * @author liujunbin
 */
public class GestureData {

    private String type;
    private Object actionData;
    private Tts ttsInfo;
    private Face expression;
    private int expressionTime;
    private Motion footAction;
    private AntennaMotion earAction;
    private AntennaLight antennalight;
    private Sound soundEffects;
    private boolean endGesture;
    private long interval;

    public Object getActionData() {
        return actionData;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setActionData(Object actionData) {
        this.actionData = actionData;
    }

    public Face getExpression() {
        return expression;
    }

    public void setExpression(Face expression) {
        this.expression = expression;
    }

    public void setExpressionTime(int expressionTime) {
        this.expressionTime = expressionTime;
    }

    public int getExpressionTime() {
        return expressionTime;
    }

    public Tts getTtsInfo() {
        return ttsInfo;
    }

    public void setTtsInfo(Tts ttsInfo) {
        this.ttsInfo = ttsInfo;
    }

    public AntennaMotion getEarAction() {
        return earAction;
    }

    public void setEarAction(AntennaMotion earAction) {
        this.earAction = earAction;
    }

    public Motion getFootAction() {
        return footAction;
    }

    public void setFootAction(Motion footAction) {
        this.footAction = footAction;
    }

    public void setSoundEffects(Sound soundEffects) {
        this.soundEffects = soundEffects;
    }

    public Sound getSoundEffects() {
        return soundEffects;
    }

    public void setEndGesture(boolean endGesture) {
        this.endGesture = endGesture;
    }

    public boolean isEndGesture() {
        return endGesture;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getInterval() {
        return interval;
    }

    public void setAntennalight(AntennaLight antennalight) {
        this.antennalight = antennalight;
    }

    public AntennaLight getAntennalight() {
        return antennalight;
    }

    @Override
    public String toString() {
        return "GestureData{" +
                "type='" + type + '\'' +
                ", actionData=" + actionData +
                ", ttsInfo=" + ttsInfo +
                ", expression=" + expression +
                ", expressionTime=" + expressionTime +
                ", footAction=" + footAction +
                ", earAction=" + earAction +
                ", antennalight=" + antennalight +
                ", soundEffects=" + soundEffects +
                ", endGesture=" + endGesture +
                ", interval=" + interval +
                '}';
    }
}
