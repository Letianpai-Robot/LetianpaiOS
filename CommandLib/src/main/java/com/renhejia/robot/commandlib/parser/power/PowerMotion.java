package com.renhejia.robot.commandlib.parser.power;

public class PowerMotion {

    private int function;
    private int status;

    public int getStatus() {
        return status;
    }

    public int getFunction() {
        return function;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "{" +
                "function=" + function +
                ", status=" + status +
                '}';
    }
    public PowerMotion(int function,int status){
        this.function = function;
        this.status = status;
    }
}
