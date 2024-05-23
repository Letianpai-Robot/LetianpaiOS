package com.renhejia.robot.launcher.app;

import android.content.Intent;

import com.renhejia.robot.launcher.statusbar.service.RobotService;
import com.renhejia.robot.launcherbaselib.init.LauncherInitManager;
import com.renhejia.robot.launcherbusinesslib.app.LeTianPaiLauncherBusinessApp;

/**
 * @author liu junbin
 */
public class LeTianPaiLauncherApp extends LeTianPaiLauncherBusinessApp {
    private static LeTianPaiLauncherApp ltpApp;
    private Intent serviceIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.ltpApp = this;
        serviceIntent = new Intent(this, RobotService.class);
        startService(serviceIntent);
        LauncherInitManager.getInstance(this);
    }
}
