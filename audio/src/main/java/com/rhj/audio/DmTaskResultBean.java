package com.rhj.audio;

public class DmTaskResultBean {

    private String from;
    private String sessionId;
    private String recordId;
    private String skillId;
    private Boolean skillName;
    private String taskId;
    private Boolean shouldEndSession;
    private String intentName;
    private String task;
    private String nlg;
    private String ssml;
    private String speakUrl;
    private Object widget;
    private String dmInput;
    private Object endSessionReason;
    private String display;
    private String watchId;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public Boolean getSkillName() {
        return skillName;
    }

    public void setSkillName(Boolean skillName) {
        this.skillName = skillName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getShouldEndSession() {
        return shouldEndSession;
    }

    public void setShouldEndSession(Boolean shouldEndSession) {
        this.shouldEndSession = shouldEndSession;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getNlg() {
        return nlg;
    }

    public void setNlg(String nlg) {
        this.nlg = nlg;
    }

    public String getSsml() {
        return ssml;
    }

    public void setSsml(String ssml) {
        this.ssml = ssml;
    }

    public String getSpeakUrl() {
        return speakUrl;
    }

    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }

    public Object getWidget() {
        return widget;
    }

    public void setWidget(Object widget) {
        this.widget = widget;
    }

    public String getDmInput() {
        return dmInput;
    }

    public void setDmInput(String dmInput) {
        this.dmInput = dmInput;
    }

    public Object getEndSessionReason() {
        return endSessionReason;
    }

    public void setEndSessionReason(Object endSessionReason) {
        this.endSessionReason = endSessionReason;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    @Override
    public String toString() {
        return "DmTaskResultBean{" +
                "from='" + from + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", recordId='" + recordId + '\'' +
                ", skillId='" + skillId + '\'' +
                ", skillName=" + skillName +
                ", taskId='" + taskId + '\'' +
                ", shouldEndSession=" + shouldEndSession +
                ", intentName='" + intentName + '\'' +
                ", task='" + task + '\'' +
                ", nlg='" + nlg + '\'' +
                ", ssml='" + ssml + '\'' +
                ", speakUrl='" + speakUrl + '\'' +
                ", widget=" + widget +
                ", dmInput='" + dmInput + '\'' +
                ", endSessionReason=" + endSessionReason +
                ", display='" + display + '\'' +
                ", watchId='" + watchId + '\'' +
                '}';
    }
}
