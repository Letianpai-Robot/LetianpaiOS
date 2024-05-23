package com.renhejia.robot.launcherbaselib.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.renhejia.robot.launcherbaselib.info.LauncherInfoManager;

/**
 * @author liujunbin
 */

public class BatteryReceiver extends BroadcastReceiver {
    public static final String ACTION_BATTERY_UPDATE = "com.renhejia.robot.action.battery_update";

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        switch (intent.getAction()) {
            case Intent.ACTION_BATTERY_CHANGED:
                handleBatteryChanged(context, intent);
                break;

            case Intent.ACTION_POWER_DISCONNECTED:
                responseDisconnect();
                break;

            case Intent.ACTION_POWER_CONNECTED:
                responseConnect();
                break;
        }
    }

    private void responseDisconnect() {
        int percent = LauncherInfoManager.getInstance(mContext).getBatteryLevel();
        LauncherInfoManager.getInstance(mContext).setChargingMode(false);
        Log.e("letianpai","responseDisconnect_responseCharging");
        ChargingUpdateCallback.getInstance().setChargingStatus(false, percent);
    }

    private void responseConnect() {
        int percent = LauncherInfoManager.getInstance(mContext).getBatteryLevel();
        LauncherInfoManager.getInstance(mContext).setChargingMode(true);
        Log.e("letianpai","responseConnect_responseCharging");
        ChargingUpdateCallback.getInstance().setChargingStatus(true, percent);
    }

    private void handleBatteryChanged(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        Log.e("letianpai_1213","chargePlug: "+ chargePlug);
        if (null == bundle) {
            return;
        }

        // 获取当前电量
        int current = bundle.getInt(BatteryManager.EXTRA_LEVEL, 0);
        // 获取总电量
        int total = bundle.getInt(BatteryManager.EXTRA_SCALE, 100);

        if (total == 0) {
            return;
        }
        int percent = current * 100 / total;
        percent = Math.max(0, Math.min(percent, 100));
        LauncherInfoManager.getInstance(context).setBatteryLevel(percent);

        //TODO 电量低的时候，需要做关机提醒
//        if (percent < 4
////                && (! LauncherInfoManager.getInstance(context).hadShowCountdownDialog())
////                && (! LauncherInfoManager.getInstance(mContext).isChargingMode())
//        ){
////            LauncherInfoManager.getInstance(context).setHadShowCountdownDialog(true);
//            showCountdownDialog();
//        }

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
        if (percent <= 15) {
            showBatteryLowDialog(context);
        }

        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                responseCharging(context,percent);
                break;
             case BatteryManager.BATTERY_STATUS_FULL:
                 if(isCharging(chargePlug)){
                     responseCharging(context,percent);
                 }else{
                     responseDisCharging(context,percent);
                 }
                break;

            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                responseDisCharging(context,percent);
                responseDisCharging(context,percent,chargePlug);
                break;

            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                responseDisCharging(context,percent);
                responseDisCharging(context,percent,chargePlug);
                break;

            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                responseDisCharging(context,percent);
                responseDisCharging(context,percent,chargePlug);
                break;
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ACTION_BATTERY_UPDATE));
    }

    /**
     * 响应断冲
     * @param context
     * @param percent
     * @param chargePlug
     */
    private void responseDisCharging(Context context, int percent, int chargePlug) {

        //TODO 需要增加重复弹窗保护
        //TODO to be check and removed
        LauncherInfoManager.getInstance(context).setChargingMode(false);
        ChargingUpdateCallback.getInstance().setChargingStatus(false, percent,chargePlug);
        hideChargingView();
    }
    /**
     * 响应断冲
     * @param context
     * @param percent
     */
    private void responseDisCharging(Context context, int percent) {

        //TODO 需要增加重复弹窗保护
        //TODO to be check and removed
        LauncherInfoManager.getInstance(context).setChargingMode(false);
        ChargingUpdateCallback.getInstance().setChargingStatus(false, percent);
        hideChargingView();
    }



//
//    /**
//     * 响应充电
//     *
//     * @param context
//     * @param percent
//     */
//    private void responseCharging(Context context, int percent, int chargePlug) {
//        sendShowChargingDialog(context,percent);
//        LauncherInfoManager.getInstance(context).setChargingMode(true);
//        ChargingUpdateCallback.getInstance().setChargingStatus(true, percent);
//        showChargingView();
//        killThirdApps();
//    }
//
    /**
     * 响应充电
     *
     * @param context
     * @param percent
     */
    private void responseCharging(Context context, int percent) {
        sendShowChargingDialog(context,percent);
        LauncherInfoManager.getInstance(context).setChargingMode(true);
        ChargingUpdateCallback.getInstance().setChargingStatus(true, percent);
        showChargingView();
        killThirdApps();
    }

    /**
     * 显示充电弹窗
     * @param context
     * @param percent
     */
    private void sendShowChargingDialog(Context context,int percent) {
        //TODO 展示通电弹窗，此处通道接口，不做UI展示，只做消息传递，展示层做此逻辑

    }

    /**
     * 显示低电弹窗
     *
     * @param mContext
     */
    private void showBatteryLowDialog(Context mContext) {
        //TODO 需要做低点提醒
        // TODO 乐天派需要做分段电量提醒逻辑（20%， 10%， 5%）
    }

    /**
     *
     * @param context
     * @return
     */
    private boolean getScreenStatus(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return powerManager.isScreenOn();

    }

    private boolean isCharging(int chargePlug) {
        if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC
                || chargePlug == BatteryManager.BATTERY_PLUGGED_USB
                || chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS
                ){
            return true;
        }else {
            return false;
        }
    }

    private void showChargingView() {
        //TODO 显示正在弹窗页面
        //TODO 此处通道接口，不做UI展示，只做消息传递，展示层做此逻辑
    }

    private void hideChargingView() {
        //TODO 此处通道接口，不做UI展示，只做消息传递，展示层做此逻辑
    }

    /**
     * 倒计时关机接口
     */
    public void showCountdownDialog() {
        //TODO 此处通道接口，不做UI展示，只做消息传递，展示层做此逻辑
    }


    /**
     * 杀死限流列表的应用
     */
    private void killThirdApps() {
        //TODO 给framework层法消息，充电时，杀掉第三方进程
    }


}
