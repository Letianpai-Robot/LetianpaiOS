package com.letianpai.foot;

import com.letianpai.ATCommandControler;

import top.keepempty.sph.library.LogUtil;

public class Servo {
    // 0度
    private static int marginAngle0 = 500;
    // 90度
    private static int marginAngle90 = 1500;
    // 180度
    private static int marginAngle180 = 2500;
    private int minAngle = 0;
    private static int maxAngle = 180;
    private int pwmScope = 2000;
    private float pwmMinPulse = 0.5f;  // 0度，0.5ms
    private float pwmMaxPulse = 2.5f; // 180度，2.5ms
    private int pwmPeriod = 20;       //  一个周期20ms
    // default
    private float margin = 1500;  // 90度
    private boolean reverse = false;
    private int servoNum =-1;//（1-6）

    public Servo (int servoNum,boolean reverse){
        this.servoNum = servoNum;
        this.reverse = reverse;
    }

    /**
     * 根据角度计算margin
     * @param angle
     * @return
     */
    private float getMarginByAngle( int angle){
        if((angle < minAngle) ||(angle>maxAngle)){
            return -1;
        }
        if (reverse){
            angle = maxAngle -angle;
        }
        return ((angle / maxAngle * (pwmMaxPulse - pwmMinPulse) + pwmMinPulse) * pwmScope )/ pwmPeriod;
    }


    /**
     *  根据绝对margin计算绝对角度
     */

    private float getAngleByMargin ( float margin){
        if((margin < marginAngle0) || (margin > marginAngle180)){
            return -1;
        }
        float angle =  (margin / pwmScope * pwmPeriod  - pwmMinPulse) / (pwmMaxPulse - pwmMinPulse) * maxAngle;
        if (angle < minAngle){
            angle = minAngle;
        }else if(angle > maxAngle){
            angle = maxAngle;
        }
        if (reverse){
            angle = maxAngle -angle;
        }
        return angle;

    }

    /**
     * 根据角度计算margin
     */
    public void moveMargin(float margin){
        if(margin <marginAngle0){
            margin = marginAngle0;
        }else if(margin > marginAngle180){
            margin = marginAngle180;
        }else{
            this.margin = margin;
        }
        if (reverse){
            this.margin = marginAngle180 -margin;
        }
        sendATCommand(margin);
    }

    /**
     * 发送AT命令
     * @param margin
     */
    private void sendATCommand( float margin){
        //callAtCommand("AT+MOTORW," + str(self.servoNum) + ",1," + str(int(margin)) )
        LogUtil.e("moveAbsAngle_margin_2: "+ margin);
        LogUtil.e("moveAbsAngle_margin_3: "+ margin);
        String command = "AT+MOTORW," + servoNum + ",1," +(int)margin+ "\\r\\n";
//        SerialPortJNI.writePort(command.getBytes());
        ATCommandControler.getInstance().addATCommand(command);

    }

    public float getMargin(){
        return margin;
    }

    public static int getMarginPerAngle(){
        return (marginAngle180 -marginAngle0)/maxAngle;
    }

    /**
     * 移动指绝对角度
     * @param angle
     */
    public void moveAbsAngle(int angle){
        margin = marginAngle0+ angle * getMarginPerAngle();
        LogUtil.e("moveAbsAngle_margin_1: "+ margin);
        moveMargin(margin);
    }


}
