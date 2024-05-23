package com.renhejia.robot.commandlib.parser.antennamotion;

/**
 * ear_cmd:
 * 1 双耳左转 90
 * 2 双耳右转 90
 * 3 双耳左右转 90 (默认参数：step=3,delay=60)
 * <p>
 * <p>
 * ear_step: 1 表示动作指令连续执行次数1次
 * <p>
 * speed: 1 表示动作连续间的时间间隔 单位1ms
 * angle:30 表示动作幅度单位°（0°~90°，>90° 默认90°；不传此参数 幅度默认90°）
 * 举例：
 * AT+EARW,3,3,60,30\r\n 双耳左右转3次，速度60ms,幅度30°
 */
public class AntennaMotion {

    private int cmd;
    private int step;
    private int speed;
    private int angle;

    public AntennaMotion(int cmd) {
        this.cmd = cmd;
    }


    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "{" +
                '\"' + "cmd" + '\"' + ":" + cmd +
                "," + '\"' + "step" + '\"' + ":" + step +
                "," + '\"' + "speed" + '\"' + ":" + speed +
                "," + '\"' + "angle" + '\"' + ":" + angle +
                '}';
    }

    //    @Override
//    public String toString() {
//        return "{" +
//                '\"' + "antenna_motion" + '\"' + ":" + '\"' + antenna_motion + '\"' +
//                "," + '\"' + "number" + '\"' + ":" + number +
//                "," + '\"' + "step" + '\"' + ":" + step +
//                "," + '\"' + "delay" + '\"' + ":" + delay +
//                '}';
//    }
//
}
