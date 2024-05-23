package com.renhejia.robot.launcherbusinesslib.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Launcher main activity基类
 *
 * @author liujunbin
 */
public abstract class LauncherBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_launcher_base);
        init(this);
    }

    /**
     * @param context
     */
    private void init(Context context) {
        //TODO 初始化数据，view ..

    }
}