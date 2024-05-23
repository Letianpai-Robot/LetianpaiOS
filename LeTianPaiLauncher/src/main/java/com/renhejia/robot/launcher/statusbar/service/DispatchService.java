package com.renhejia.robot.launcher.statusbar.service;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.renhejia.robot.commandlib.consts.MCUCommandConsts.COMMAND_SET_SHOW_TIME;
import static com.renhejia.robot.launcher.dispatch.mode.ViewModeConsts.VM_AUTO_MODE;
import static com.renhejia.robot.launcher.dispatch.mode.ViewModeConsts.VM_AUTO_PLAY_MODE;
import static com.renhejia.robot.launcher.dispatch.mode.ViewModeConsts.VM_DISPLAY_MODE;
import static com.renhejia.robot.launcher.dispatch.mode.ViewModeConsts.VM_FUNCTION_MODE;
import static com.renhejia.robot.launcher.dispatch.mode.ViewModeConsts.VM_STANDBY_MODE;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.letianpai.robot.letianpaiservice.LtpAppCmdCallback;
import com.letianpai.robot.letianpaiservice.LtpLongConnectCallback;
import com.letianpai.robot.letianpaiservice.LtpRobotStatusCallback;
import com.letianpai.robot.notice.alarm.receiver.AlarmCallback;
import com.letianpai.robot.notice.general.parser.GeneralInfo;
import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.commandlib.consts.RobotRemoteConsts;
import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.commandlib.parser.power.PowerMotion;
import com.renhejia.robot.commandlib.parser.time.ServerTime;
import com.renhejia.robot.commandlib.parser.timezone.TimeZone;
import com.renhejia.robot.commandservice.consts.RGestureConsts;
import com.renhejia.robot.gesturefactory.manager.GestureCenter;
import com.renhejia.robot.gesturefactory.parser.GestureData;
import com.renhejia.robot.gesturefactory.util.GestureConsts;
import com.renhejia.robot.guidelib.ble.BleService;
import com.renhejia.robot.guidelib.ble.callback.BleConnectStatusCallback;
import com.renhejia.robot.guidelib.ble.callback.GuideFunctionCallback;
import com.renhejia.robot.guidelib.manager.LTPGuideConfigManager;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;
import com.renhejia.robot.launcher.audioservice.AudioCmdResponseManager;
import com.renhejia.robot.launcher.dispatch.command.CommandResponseCallback;
import com.renhejia.robot.launcher.dispatch.gesture.GestureCallback;
import com.renhejia.robot.launcher.dispatch.remote.RemoteCmdCallback;
import com.renhejia.robot.launcher.dispatch.service.StartServiceCallback;
import com.renhejia.robot.launcher.main.activity.LauncherActivity;
import com.renhejia.robot.launcher.main.activity.LeTianPaiMainActivity;
import com.renhejia.robot.launcher.mode.DeviceBindInfo;
import com.renhejia.robot.launcher.mode.DeviceBindInfoData;
import com.renhejia.robot.launcher.nets.GeeUINetResponseManager;
import com.renhejia.robot.launcher.nets.GeeUiNetManager;
import com.renhejia.robot.launcher.system.LetianpaiFunctionUtil;
import com.renhejia.robot.launcher.system.SystemFunctionUtil;
import com.renhejia.robot.launcherbaselib.storage.manager.LauncherConfigManager;
import com.renhejia.robot.launcherbaselib.timer.TimerKeeperCallback;
import com.renhejia.robot.letianpaiservice.ILetianpaiService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author liujunbin
 */
public class DispatchService extends Service {
    private static final String TAG = "letianpai";
    private boolean isConnectService = false;
    private ILetianpaiService iLetianpaiService;
    private Gson gson;
    private static final int SHOW_GESTURE_STR = 2;
    private static final int SHOW_GESTURES_STR = 3;
    private static final int SHOW_GESTURES_WITH_ID = 4;
    private static final int SHOW_GESTURE_STR_OBJECT = 5;
    private GestureHandler mHandler;
    private CountDownTimer mainCountDownTimer;

    public final static String COMMAND_TYPE_SHUTDOWN = "shutdown";
    public final static String COMMAND_TYPE_SHUTDOWN_STEERING_ENGINE = "ShutDownSteeringEngine";
    private GuideFunctionCallback.RobotShutDownListener robotShutDownListener;

    public final static String COMMAND_TYPE_POWER_ON_CHARGING = "power_on_charging";
    public final static String COMMAND_TYPE_CLOSE_STEERING_ENGINE = "power_on_charging";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    private void init() {
        gson = new Gson();
        mHandler = new GestureHandler(DispatchService.this);
        initConnectService();
        addCommandListener();
        //addModeChangeListeners();
        initCountDownTimer();
        getNetInfo();
        initShutDownListener();
        registerRobotShutDownListener();
        registerNetChangeListener();

        //调用接口判断设备是否绑定
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDeviceBindInfo();
            }
        }).start();
    }

    private void getDeviceBindInfo(){
        if (!WIFIConnectionManager.getInstance(DispatchService.this).isNetworkAvailable(DispatchService.this)) {
            return;
        }
        GeeUiNetManager.getDeviceBindInfo(DispatchService.this, SystemUtil.isChinese(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {
                    DeviceBindInfo deviceBindInfo = null;
                    String info = response.body().string();
                    try {
                        if (info != null) {
                            deviceBindInfo = new Gson().fromJson(info, DeviceBindInfo.class);
                            Log.e("----", "response11: " +deviceBindInfo.toString());
                            DeviceBindInfoData data = deviceBindInfo.getData();
                            if (data!=null && data.getBindStatus() == 0){
                                if(LTPGuideConfigManager.getInstance(DispatchService.this).isActivated()){
                                    //设备已解绑
                                    removeDevice();
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void registerNetChangeListener() {

        BleConnectStatusCallback.getInstance().registerBleConnectStatusListener(new BleConnectStatusCallback.BleConnectStatusChangedListener() {
            @Override
            public void onBleConnectStatusChanged(int connectStatus) {
                if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS) {
                    Log.e("letianpai_network", "networkStatus11111: ====== 1 ");
                    updateRobotTime();
                }
            }
        });
    }

    private void updateRobotTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("letianpai_network", "networkStatus11111: ====== 2 ");
                updateTime();
            }
        }).start();
    }


    private void updateTime() {
        GeeUiNetManager.getTimeStamp(DispatchService.this, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    ServerTime serverTime = null;
                    String info = "";
                    if (response != null && response.body() != null) {
                        info = response.body().string();
                        Log.e("letianpai_network", "networkStatus11111: ====== 3: " + info);
                    }
                    try {
                        if (info != null) {
                            serverTime = new Gson().fromJson(info, ServerTime.class);
                            if (serverTime != null && serverTime.getCode() == 0 && serverTime.getData() != null && serverTime.getData().getTimestamp() != 0) {
                                changeTime(serverTime.getData().getTimestamp() * 1000);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void changeTime(long time) {
        boolean status = SystemClock.setCurrentTimeMillis(time * 1000);
        Log.i(TAG, "changeTime---status:"+status + "---System.currentTimeMillis()::"+ System.currentTimeMillis());
    }

    private void initShutDownListener() {
        robotShutDownListener = new GuideFunctionCallback.RobotShutDownListener() {
            @Override
            public void onShutDown() {
                sendShutdownCmd();
            }

            @Override
            public void onShutDownSteeringEngine() {
                sendShutDownSteeringEngineCmd();

            }
        };
    }

    private void registerRobotShutDownListener() {
        GuideFunctionCallback.getInstance().registerRobotShutDownListener(robotShutDownListener);
    }

    private void unregisterRobotShutDownListener() {
        if (robotShutDownListener != null) {
            GuideFunctionCallback.getInstance().unregisterRobotShutDownListener(robotShutDownListener);
        }
    }

    private void sendShutdownCmd() {
        try {
            iLetianpaiService.setAppCmd(COMMAND_TYPE_SHUTDOWN, COMMAND_TYPE_SHUTDOWN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendShutDownSteeringEngineCmd() {
        try {
            iLetianpaiService.setAppCmd(COMMAND_TYPE_SHUTDOWN_STEERING_ENGINE, COMMAND_TYPE_SHUTDOWN_STEERING_ENGINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getNetInfo() {
        LogUtils.logi("letianpai_1234", "getNetInfo: ~~~~~0");
        if (WIFIConnectionManager.getInstance(DispatchService.this).isNetworkAvailable(DispatchService.this) && SystemUtil.hasHardCode()) {
            GeeUINetResponseManager.getInstance(DispatchService.this).getDisplayInfo();

        } else if (WIFIConnectionManager.getInstance(DispatchService.this).isNetworkAvailable(DispatchService.this) && !SystemUtil.hasHardCode()) {

        }

    }

    private void cancelAll() {
        cancelMainCountDownTimer();
    }

    private void initCountDownTimer() {

        mainCountDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                //设备未激活，返回
                if (!LTPGuideConfigManager.getInstance(DispatchService.this).isActivated()){
                    return;
                }
                if (LTPGuideConfigManager.getInstance(DispatchService.this).isActivated()){
                    sendPowerOnCharging();
                    //思必驰语音独立出来了，启动放在RobotService即可
                    if (!LetianpaiFunctionUtil.isFactoryRom()) {
                        startGeeUIOtaService();
                    }
                }
            }
        };
    }

    //等待设备起来之后，再去打开otaservice
    private void startGeeUIOtaService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                ComponentName cn = new ComponentName("com.letianpai.otaservice", "com.letianpai.otaservice.ota.GeeUpdateService");
                intent.setComponent(cn);
                startService(intent);
            }
        }).start();
    }

    private void sendPowerOnCharging() {
        try {
            iLetianpaiService.setAppCmd(COMMAND_TYPE_POWER_ON_CHARGING, COMMAND_TYPE_POWER_ON_CHARGING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeSteeringEngine() {
        try {
            iLetianpaiService.setAppCmd(COMMAND_TYPE_CLOSE_STEERING_ENGINE, COMMAND_TYPE_CLOSE_STEERING_ENGINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startCountDownTimer() {
        cancelMainCountDownTimer();
    }

    public void startMainCountDownTimer() {
        mainCountDownTimer.start();
    }

    public void cancelMainCountDownTimer() {
        mainCountDownTimer.cancel();
    }

    private void addCommandListener() {
        CommandResponseCallback.getInstance().setCommandReceivedListener(new CommandResponseCallback.CommandResponseListener() {
            @Override
            public void onCommandReceived(String commandFrom, String commandType, Object commandData) {
                Log.e("letianpai_test", "========= addCommandListener 0 ========");
                AudioCmdResponseManager.commandResponse(DispatchService.this, commandFrom, commandType, commandData, iLetianpaiService);
            }
        });

        CommandResponseCallback.getInstance().setLTPCommandResponseListener(new CommandResponseCallback.LTPCommandResponseListener() {
            @Override
            public void onLTPCommandReceived(String command, String data) {
                if (command.equals(MCUCommandConsts.COMMAND_SET_APP_MODE)) {
                    Log.e("letianpai_test111", "========= 0 ========");
                    try {
                        iLetianpaiService.setAppCmd(MCUCommandConsts.COMMAND_SET_APP_MODE, data);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else if(command.equals(COMMAND_SET_SHOW_TIME)){
                    if (LTPGuideConfigManager.getInstance(DispatchService.this).isActivated()){
                        sendPowerOnCharging();
                        //思必驰语音独立出来了，启动放在RobotService即可
                        if (!LetianpaiFunctionUtil.isFactoryRom()) {
                            startGeeUIOtaService();
                        }
                    }
                } else {
                    try {
                        iLetianpaiService.setMcuCommand(command, data);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        GestureCallback.getInstance().setGestureListener(new GestureCallback.GestureResponseListener() {
            @Override
            public void onGestureReceived(String gesture) {
                showGesture(gesture);
            }

            @Override
            public void onGestureReceived(String gesture, int gestureId) {
                showGesture(gesture, gestureId);
            }

            @Override
            public void onGesturesReceived(ArrayList<GestureData> list, int taskId) {
                showGestures(list, taskId);
            }

            @Override
            public void onGesturesReceived(GestureData gestureData) {
                showGesture(gestureData);
            }

        });

        AlarmCallback.getInstance().registerAlarmTimeListener(new AlarmCallback.AlarmTimeListener() {
            @Override
            public void onAlarmTimeOut(int hour, int minute) {
                Log.i("letianpai_timer", "updateAlarm: " + hour + "_" + minute);
//                RobotModeManager.getInstance(DispatchService.this).setAlarming(true);
                GestureCallback.getInstance().setGesture(GestureConsts.GESTURE_CLOCK);
            }
        });

        StartServiceCallback.getInstance().setStartServiceListener(new StartServiceCallback.StartServiceListener() {
            @Override
            public void onServiceStart() {
                startMainCountDownTimer();
            }
        });

        RemoteCmdCallback.getInstance().setRemoteCmdReceivedListener(new RemoteCmdCallback.RemoteCmdListener() {
            @Override
            public void onRemoteCmdReceived(String commandType, Object commandData) {
                Log.e("letianpai123456789", "commandType: " + commandType);
                Log.e("letianpai123456789", "commandData: " + commandData.toString());
                GeeUINetResponseManager.getInstance(DispatchService.this).dispatchTask(commandType, commandData);
            }
        });
    }

    private void initConnectService() {
        connectService();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "乐天派 Launcher 完成AIDLService服务");
            iLetianpaiService = ILetianpaiService.Stub.asInterface(service);
            try {
                iLetianpaiService.registerLCCallback(ltpLongConnectCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isConnectService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "乐天派 无法绑定aidlserver的AIDLService服务");
            isConnectService = false;
        }
    };

    //链接服务端
    private void connectService() {
        Intent intent = new Intent();
        intent.setPackage("com.renhejia.robot.letianpaiservice");
        intent.setAction("android.intent.action.LETIANPAI");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private final LtpLongConnectCallback.Stub ltpLongConnectCallback = new LtpLongConnectCallback.Stub() {
        @Override
        public void onLongConnectCommand(String command, String data) throws RemoteException {
            Log.e("letianpai0508_2", "command: " + command + " /data: " + data);
            if (TextUtils.isEmpty(command)) {
                return;
            }
            if (RobotRemoteConsts.COMMAND_VALUE_REMOVE_DEVICE.equals(command)) {
                removeDevice();
            } else if (RobotRemoteConsts.COMMAND_TYPE_APP_DISPLAY_SWITCH_CONFIG.equals(command)) {
                GeeUINetResponseManager.getInstance(DispatchService.this).updateGeneralInfo();
            } else if (RobotRemoteConsts.COMMAND_VALUE_DEVICE_GUIDE_FINISH.equals(command)) {
                // deviceGuideFinish();
            } else if (RobotRemoteConsts.COMMAND_UPDATE_DEVICE_TIME_ZONE.equals(command)) {
                updateDeviceTimeZone(data);
            }
        }
    };

    private void updateDeviceTimeZone(String data) {
        TimeZone timeZone = gson.fromJson(data, TimeZone.class);
        if (timeZone != null && !TextUtils.isEmpty(timeZone.getZone())){
            SystemFunctionUtil.setTimeZone(DispatchService.this,timeZone.getZone());
        }
    }


    /**
     * 小程序点击了引导的完成按钮
     * 这边跳转到mainactivity
     */
    private void deviceGuideFinish() {
        GuideFunctionCallback.getInstance().closeGuide();
    }

    public static final String ROBOT_PACKAGE_NAME = "com.geeui.face";
    public static final String PACKAGE_NAME_IDENT = "com.ltp.ident";
    public static final String PACKAGE_NAME_SPLIT = "__";

    public static final String COMMAND_VALUE_KILL_PROCESS = "killProcess";

    /**
     * 解除绑定
     */
    private void removeDevice() {
        LTPGuideConfigManager.getInstance(DispatchService.this).setActivated(false);
        LTPGuideConfigManager.getInstance(DispatchService.this).commit();
        SystemUtil.setRobotInactive();
        LogUtils.logd("DispatchService", "removeDevice: " + LTPGuideConfigManager.getInstance(DispatchService.this).isActivated());
        // 关闭机器人
        try {
            iLetianpaiService.setAppCmd(COMMAND_VALUE_KILL_PROCESS, ROBOT_PACKAGE_NAME + PACKAGE_NAME_SPLIT + PACKAGE_NAME_IDENT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //舵机卸力
        if (iLetianpaiService != null) {
            try {
                iLetianpaiService.setMcuCommand(MCUCommandConsts.COMMAND_TYPE_POWER_CONTROL, new PowerMotion(3, 0).toString());
                iLetianpaiService.setMcuCommand(MCUCommandConsts.COMMAND_TYPE_POWER_CONTROL, new PowerMotion(5, 0).toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        //关闭语音助手
        stopSpeechAudioService();
        stopAmazonAudioService();
        //解绑的时候，不需要清除WiFi 11月21日加
        // try {
        //     WIFIAutoConnectionService.stop(DispatchService.this);
        //     WIFIConnectionManager.getInstance(DispatchService.this).clearWifiPasswords();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(DispatchService.this, LauncherActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                //关闭上一个页面
                Intent stopIntent = new Intent(DispatchService.this, LeTianPaiMainActivity.class);
                stopIntent.setAction("stop");
                stopIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(stopIntent);
                rebootRobot();
            }
        }, 1500);

        //启动蓝牙service，
        startService(new Intent(this, BleService.class));
    }
    private void rebootRobot() {
        SystemFunctionUtil.reboot(DispatchService.this);
    }

    // 获取所有正在运行的进程信息
    private List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getRunningAppProcesses();
    }

    // 根据进程名获取应用程序信息
    private ApplicationInfo getAppInfo(Context context, String processName) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> appInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo : appInfos) {
            if (appInfo.processName.equals(processName)) {
                return appInfo;
            }
        }

        return null;
    }

    private void stopSpeechAudioService() {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.rhj.speech", "com.rhj.audio.service.LTPAudioService");
        intent.setComponent(cn);
        stopService(intent);
    }

    private void stopAmazonAudioService() {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.geeui.lex", "com.geeui.lex.services.BotService");
        intent.setComponent(cn);
        stopService(intent);
    }

    private final LtpRobotStatusCallback.Stub ltpRobotStatusCallback = new LtpRobotStatusCallback.Stub() {
        @Override
        public void onRobotStatusChanged(String command, String data) throws RemoteException {

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterRobotShutDownListener();
    }

    public void showGesture(String gesture) {
        Message message = new Message();
        message.what = SHOW_GESTURE_STR;
        message.obj = gesture;
        mHandler.sendMessage(message);
    }

    public void showGesture(String gesture, int gId) {
        Message message = new Message();
        message.what = SHOW_GESTURES_WITH_ID;
        message.obj = gesture;
        message.arg1 = gId;
        mHandler.sendMessage(message);
    }

    public void showGestures(ArrayList<GestureData> list, int taskId) {
        Message message = new Message();
        message.what = SHOW_GESTURES_STR;
        message.obj = list;
        message.arg1 = taskId;
        mHandler.sendMessage(message);
    }

    public void showGesture(GestureData gestureData) {
        Message message = new Message();
        message.what = SHOW_GESTURE_STR_OBJECT;
        message.obj = gestureData;
        mHandler.sendMessage(message);
    }

    private class GestureHandler extends android.os.Handler {

        private final WeakReference<Context> context;

        public GestureHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SHOW_GESTURE_STR) {
                if (msg.obj != null) {
                    AudioCmdResponseManager.getInstance(DispatchService.this).responseGestures(((String) msg.obj), iLetianpaiService);
                }

            } else if (msg.what == SHOW_GESTURES_STR) {
                if (msg.obj != null) {
                    AudioCmdResponseManager.getInstance(DispatchService.this).responseGestures(((ArrayList<GestureData>) msg.obj), msg.arg1, iLetianpaiService);
                }
            } else if (msg.what == SHOW_GESTURE_STR_OBJECT) {
                if (msg.obj != null) {
                    AudioCmdResponseManager.getInstance(DispatchService.this).responseGesture(((GestureData) msg.obj), iLetianpaiService);
                }
            } else if (msg.what == SHOW_GESTURES_WITH_ID) {
                if (msg.obj != null && msg.arg1 != 0) {
                    AudioCmdResponseManager.getInstance(DispatchService.this).responseGestures(((String) msg.obj), msg.arg1, iLetianpaiService);
                }
            }
        }
    }
}
