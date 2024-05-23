package com.renhejia.robot.launcherbaselib.app;

import android.app.Application;

/**
 *  人和家系统app入口
 *
 */
public class RHJApp extends Application {
    private static RHJApp rhjApp;
    @Override
    public void onCreate() {
        super.onCreate();
        this.rhjApp = this;
    }

    public static RHJApp getRhjApp(){
        return rhjApp;
    }

    //TODO 初始化需要的 service
    //TODO 初始化需要的 观察者对象
    //TODO 初始化


}
