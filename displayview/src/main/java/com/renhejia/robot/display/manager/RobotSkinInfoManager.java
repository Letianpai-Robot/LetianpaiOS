package com.renhejia.robot.display.manager;

import android.content.Context;

/**
 * @author liujunbin
 */
public class RobotSkinInfoManager {
    private Context mContext;

    private static RobotSkinInfoManager instance;

    private RobotSkinInfoManager(Context context) {
        this.mContext = context;
    }

    public static RobotSkinInfoManager getInstance(Context context) {
        if (instance == null) {
            instance = new RobotSkinInfoManager(context.getApplicationContext());
        }
        return instance;
    }

}
