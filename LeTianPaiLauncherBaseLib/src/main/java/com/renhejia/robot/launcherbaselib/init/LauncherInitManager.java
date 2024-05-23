package com.renhejia.robot.launcherbaselib.init;

import android.content.Context;

import com.renhejia.robot.launcherbaselib.broadcast.LauncherBroadcastReceiverManager;

public class LauncherInitManager {
    private static LauncherInitManager instance;
    private Context mContext;

    private LauncherInitManager(Context context){
        this.mContext = context;
        init(context);

    }

    public static LauncherInitManager getInstance(Context context){
        synchronized (LauncherBroadcastReceiverManager.class){
            if (instance == null){
                instance = new LauncherInitManager(context.getApplicationContext());
            }
            return instance;
        }

    };

    private void init(Context context) {
        LauncherBroadcastReceiverManager.getInstance(context);
        //TODO 其他的初始化


    }



}
