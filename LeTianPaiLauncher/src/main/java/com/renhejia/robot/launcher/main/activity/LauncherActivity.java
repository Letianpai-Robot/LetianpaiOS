package com.renhejia.robot.launcher.main.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.guidelib.ble.BleService;
import com.renhejia.robot.guidelib.ble.callback.GuideFunctionCallback;
import com.renhejia.robot.guidelib.manager.LTPGuideConfigManager;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.dispatch.command.CommandResponseCallback;
import com.renhejia.robot.launcher.system.LetianpaiFunctionUtil;
import com.renhejia.robot.launcher.system.SystemFunctionUtil;
import com.renhejia.robot.launcherbusinesslib.ui.activity.LauncherBaseActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class LauncherActivity extends LauncherBaseActivity {
    private GuideFunctionCallback.GuideFunctionListener closeGuidedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getOTAStatus();
        SystemUtil.setAppLanguage(LauncherActivity.this);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        startService(new Intent(this, BleService.class));
        if (LTPGuideConfigManager.getInstance(LauncherActivity.this).isActivated()) {
            skipToMainView();
        } else {
//            setContentView(R.layout.activity_launcher_guide);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            inits();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimeFormat();
    }

    private void setTimeFormat() {
        String timeFormat = android.provider.Settings.System.getString(getContentResolver(), android.provider.Settings.System.TIME_12_24);
        if (timeFormat == null) {
            SystemFunctionUtil.set24HourFormat(LauncherActivity.this);
        }
    }


    private void skipToMainView() {
        Intent intent = new Intent(LauncherActivity.this, LeTianPaiMainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void inits() {
        SystemFunctionUtil.wakeUp(LauncherActivity.this);
        initListeners();
        registerCloseGuidedListener();
        //
        openWifiConnectView();
    }
    private static final String OPEN_FROM = "from";
    private static final String OPEN_FROM_START = "from_open_robot";

    private void openWifiConnectView() {
        String packageName = "com.letianpai.robot.wificonnet";
        String activityName = "com.letianpai.robot.wificonnet.MainActivity";
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityName));
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    private void initListeners() {
        closeGuidedListener = new GuideFunctionCallback.GuideFunctionListener() {
            @Override
            public void onCloseGuideReceived() {
                skipToMainView();
            }
        };
    }

    private void registerCloseGuidedListener() {
//        GuideFunctionCallback.getInstance().registerGuideFunctionListener(closeGuidedListener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterCloseGuidedListener();
    }

    private void unregisterCloseGuidedListener() {
//        if (closeGuidedListener != null) {
//            GuideFunctionCallback.getInstance().unregisterCloseGuidedListener(closeGuidedListener);
//        }
    }
}
