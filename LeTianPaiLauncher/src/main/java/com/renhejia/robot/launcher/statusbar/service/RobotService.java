package com.renhejia.robot.launcher.statusbar.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import android.util.Log;
import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.commandlib.parser.face.Face;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.wifi.WIFIAutoConnectionService;
import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;
import com.renhejia.robot.launcher.nets.GeeUINetResponseManager;
import com.renhejia.robot.launcher.system.LetianpaiFunctionUtil;
import com.renhejia.robot.launcherbaselib.callback.NetworkChangingUpdateCallback;

public class RobotService extends Service {
    private Context mContext;
    private String TAG = "ble_viewpager";

    private boolean isWifiConnected = true;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = RobotService.this;
        handler = new Handler(getMainLooper());
        addListeners();
        startServices();

    }

    private void startServices() {
        if (LetianpaiFunctionUtil.isFactoryRom()){
            LetianpaiFunctionUtil.openFactoryApp(RobotService.this);
        }else{
            startResourceService();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLongConnectService();
                    startLogService();
                    String pro = SystemUtil.get(SystemUtil.REGION_LANGUAGE, "zh");
                    if (!"en".equals(pro)) {
                        startMIotService();
                    }

                    startMcuService();
                    startTaskService();
//                    if (WIFIConnectionManager.getInstance(mContext).isConnected()){
//                        startAppStoreService();
//                    }
                    startDispatchService();
                    startAlarmService();
                }
            }, 1000);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startSoundService();
                }
            }, 200);
        }
    }

    private void startAlarmService() {
        try {
            Intent intent = new Intent();
            ComponentName cn = new ComponentName("com.letianpai.robot.alarmnotice", "com.letianpai.robot.alarmnotice.service.AlarmService");
            intent.setComponent(cn);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startTaskService() {
        try{
            Intent intent = new Intent();
            ComponentName cn = new ComponentName("com.letianpai.robot.taskservice", "com.letianpai.robot.control.service.DispatchService");
            intent.setComponent(cn);
            startService(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void addListeners() {

//        BleConnectStatusCallback.getInstance().registerBleConnectStatusListener(new BleConnectStatusCallback.BleConnectStatusChangedListener() {
//            @Override
//            public void onBleConnectStatusChanged(int connectStatus) {
//                LogUtils.logi(TAG, " ====== RobotService 0 ======= BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS");
//                if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS) {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            LogUtils.logi(TAG, " ====== RobotService ======= BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS");
//                            Intent intent = new Intent(mContext, LeTianPaiMainActivity.class);
//                            startActivity(intent);
//                        }
//                    }, 50);
//                }
//
//            }
//        });

//        NetworkChangingUpdateCallback.getInstance().setchangingStatusUpdateListener((networkType, networkStatus) -> {
//            LogUtils.logd("RobotService", "addListeners: networkStatus: "+networkStatus);
//            if (networkType == NetworkChangingUpdateCallback.NETWORK_TYPE_DISABLED) {
//                LogUtils.logd("RobotService", "addListeners: 网络if ");
//            } else {
//                LogUtils.logd("RobotService", "addListeners: 网络 else");
//            }
//        });

        //监听网络变化
        NetworkChangingUpdateCallback.getInstance().registerChargingStatusUpdateListener(new NetworkChangingUpdateCallback.NetworkChangingUpdateListener() {
            @Override
            public void onNetworkChargingUpdateReceived(int networkType, int networkStatus) {
                Log.e("RobotService","networkType: "+ networkType);
                //网络变化断网了2分钟，在重新连接
                if (networkType == NetworkChangingUpdateCallback.NETWORK_TYPE_DISABLED) {
                    isWifiConnected = false;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //WiFi没有链接成功再去链接
                            if (!isWifiConnected){
                                WIFIConnectionManager.getInstance(mContext).connect();
                            }
                        }
                    },60000 * 2);

                }else{
                    isWifiConnected = true;
                    getLogo();
                    Log.e("RobotService","----网络连接- networkType ----: "+ networkType);
                }
            }
        });
    }


    private void getLogo(){
        GeeUINetResponseManager.getInstance(this).getLogoInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //链接服务端
    private void startService() {
        Intent intent = new Intent();
        intent.setPackage("com.renhejia.robot.launcher");
        intent.setAction("android.intent.action.LETIANPAI");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(intent);
        }
    }

    private void startLongConnectService() {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.letianpai.emqxservice", "com.letianpai.emqxservice.EmqxService");
        intent.setComponent(cn);
        startService(intent);
    }

    private void startSpeechRecognitionService() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.LTPAUDIO");
        intent.setPackage("com.letianpai.robot.audioservice");
        startService(intent);
    }

    private void startLogService() {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.BUGREPORT");
            intent.setPackage("com.letianpai.bugreportservice");
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startResourceService() {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.LETIANPAI.RESOURCE");
            intent.setPackage("com.letianpai.robot.geeuiresources");
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startMcuService() {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.letianpai.robot.mcuservice", "com.letianpai.robot.mcuservice.service.LTPMcuService");
        intent.setComponent(cn);
        startService(intent);
    }


    private void startMIotService() {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.geeui.miiot", "com.geeui.miiot.MiIotService");
        intent.setComponent(cn);
        startService(intent);
    }

    private void startSoundService() {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.letianpai.robot.audioservice", "com.letianpai.robot.audioservice.service.LTPAudioService");
        intent.setComponent(cn);
        startService(intent);
    }

    private void startAppStoreService() {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.letianpai.robot.appstore", "com.letianpai.robot.appstore.service.AppStoreService");
        intent.setComponent(cn);
        startService(intent);
    }

    private void startDispatchService() {
        Intent intent = new Intent(RobotService.this, DispatchService.class);
        startService(intent);
    }

}
