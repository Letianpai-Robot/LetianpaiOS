package com.renhejia.robot.launcherbaselib.init;

import android.content.Context;

import com.renhejia.robot.launcherbaselib.broadcast.LauncherBroadcastReceiverManager;

/**
 *
 */
public class WatchClockRegManager {
    private static WatchClockRegManager instance;
    private Context mContext;

    private WatchClockRegManager(Context context){
        this.mContext = context;
        init(context);

    }

    public static WatchClockRegManager getInstance(Context context){
        synchronized (WatchClockRegManager.class){
            if (instance == null){
                instance = new WatchClockRegManager(context.getApplicationContext());
            }
            return instance;
        }

    };


    private void init(Context context) {


    }
}
