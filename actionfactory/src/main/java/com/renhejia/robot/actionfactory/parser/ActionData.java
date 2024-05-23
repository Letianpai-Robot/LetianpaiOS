package com.renhejia.robot.actionfactory.parser;

/**
 * @author liujunbin
 */
public class ActionData {

    private String type;
    private Object actionData;
    private String ttsInfo;
    private int expression;
    private int footAction;
    private int earAction;

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

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }

    public String getTtsInfo() {
        return ttsInfo;
    }

    public void setTtsInfo(String ttsInfo) {
        this.ttsInfo = ttsInfo;
    }

    public int getEarAction() {
        return earAction;
    }

    public void setEarAction(int earAction) {
        this.earAction = earAction;
    }

    public int getFootAction() {
        return footAction;
    }

    public void setFootAction(int footAction) {
        this.footAction = footAction;
    }
}
