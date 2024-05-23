package com.renhejia.robot.display.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.renhejia.robot.display.R;
import com.renhejia.robot.display.SpineSkinResPool;

import java.util.Timer;
import java.util.TimerTask;

public class RobotDisplayModeView extends RelativeLayout {

    private RobotDisplayView displayView;
    private Context mContext;
    private static String SKIN_PATH = "display/time";
    private String [] mSkinList;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private int currentIndex;


    public RobotDisplayModeView(Context context) {
        super(context);
        init();
    }


    public RobotDisplayModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotDisplayModeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(mContext,R.layout.view_display,this);
        initData();
        initView();
        fillDataAndUpdate();


    }

    private void fillDataAndUpdate() {
        loadDisplayView();
        updateDisplayViews();

    }


    private void initData() {
        mSkinList = SpineSkinResPool.getDisplayList(mContext);
    }

    private void initView() {
        displayView = findViewById(R.id.rdv_display);
    }


    private void loadDisplayView() {
        displayView.loadSkin(SKIN_PATH);
    }

    private void loadDisplayView(int position) {
        displayView.loadSkin(mSkinList[position]);
    }

    public void updateDisplayViews() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                currentIndex = currentIndex +1;
                if(currentIndex >=mSkinList.length){
                    currentIndex = 0;
                }
                loadDisplayView(currentIndex);

            }
        };
//        mTimer.schedule(mTimerTask, 5000, 5000);
        mTimer.schedule(mTimerTask, 5000, 5000);
    }

}
