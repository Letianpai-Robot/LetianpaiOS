package com.renhejia.robot.launcher.statusbar;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import com.renhejia.robot.commandlib.log.LogUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.dispatch.statusbar.StatusBarUpdateCallback;
import com.renhejia.robot.launcherbaselib.battery.ChargingUpdateCallback;

import java.lang.ref.WeakReference;

/**
 * 充电状态
 * @author liujunbin
 */
public class BatteryCharging extends RelativeLayout {

    private View progress;
    private View empty;
    private View redProgress;
    private ImageView ivCharging;
    private static final int SHOW_TIME = 110;
    private static final int SHOW_HI_XIAOLE = 111;
    private static final int UPDATE_ICON = 110;
    private static final int HIDE_TEXT = 112;

    public BatteryCharging(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.robot_charge,this);
        progress = findViewById(R.id.battery_progress);
        empty = findViewById(R.id.battery_empty);
        redProgress = findViewById(R.id.battery_low);
        ivCharging = findViewById(R.id.ivcharging);
        //addChargingCallback();
    }


    private void addChargingCallback() {
        ChargingUpdateCallback.getInstance().registerChargingStatusUpdateListener(new ChargingUpdateCallback.ChargingUpdateListener() {
            @Override
            public void onChargingUpdateReceived(boolean changingStatus, int percent) {
                LogUtils.logi("changingStatus","changingStatus: "+changingStatus);
                LogUtils.logi("changingStatus","percent: "+percent);
                setBatteryLevel(percent);
                if (changingStatus){
                    ivCharging.setVisibility(View.VISIBLE);
                }else{
                    ivCharging.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onChargingUpdateReceived(boolean changingStatus, int percent, int chargePlug) {

            }
        });
    }

    public void setBatteryLevel(float batteryLevel) {
        ivCharging.setVisibility(View.VISIBLE);
        redProgress.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT);
        params.weight = batteryLevel;//在此处设置weight
        progress.setLayoutParams(params);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT);
        params1.weight = 100 - batteryLevel;//在此处设置weight
        empty.setLayoutParams(params1);

    }

//    public void setBatteryLow() {
//        ivCharging.setVisibility(View.GONE);
//        redProgress.setVisibility(View.VISIBLE);
//        progress.setVisibility(View.GONE);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT);
//        params.weight = 10;//在此处设置weight
//        redProgress.setLayoutParams(params);
//
//        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT);
//        params1.weight = 90;//在此处设置weight
//        empty.setLayoutParams(params1);
//
//    }

    public void setBatteryLow(float batteryLevel) {
        ivCharging.setVisibility(View.GONE);
        redProgress.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT);
        params.weight = batteryLevel;//在此处设置weight
        redProgress.setLayoutParams(params);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT);
        params1.weight = 100 - batteryLevel;//在此处设置weight
        empty.setLayoutParams(params1);

    }

    public BatteryCharging(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BatteryCharging(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

}
