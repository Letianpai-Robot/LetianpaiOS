package com.renhejia.robot.launcher.audioservice.parser;

/**
 * @author liujunbin
 */
public class AudioCommand {

    // {"number":"3","direction":"前","intentName":"走X步","skillId":"2023011300000095","skillName":"false","taskName":"转向"}
    private String number;
    private String direction;
    private String intentName;
    private String skillId;
    private String skillName;
    private String taskName;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
