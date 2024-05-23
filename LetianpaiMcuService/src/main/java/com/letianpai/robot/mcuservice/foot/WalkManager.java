package com.letianpai.robot.mcuservice.foot;

import android.content.Context;

public class WalkManager {


    public static String MOTOR_NUM_1 = "1";
    public static String MOTOR_NUM_2 = "2";
    public static String MOTOR_NUM_3 = "3";
    public static String MOTOR_NUM_4 = "4";
    public static String MOTOR_NUM_5 = "5";
    public static String MOTOR_NUM_6 = "6";

    private static WalkManager instance;
    private Context mContext;


    private WalkManager(Context context){
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public static WalkManager getInstance(Context context){
        synchronized (WalkManager.class){
            if (instance == null){
                instance = new WalkManager(context.getApplicationContext());
            }
            return instance;
        }

    };

//    public float getMarginByAngle(){
//
//    }



}
