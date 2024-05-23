package com.letianpai.robot.mcuservice.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.os.RemoteException;
import com.renhejia.robot.commandlib.log.LogUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.letianpai.AtCommandCallback;
import com.letianpai.ConvertUtils;
import com.letianpai.MainActivity;
import com.letianpai.McuCommandControlManager;
import com.letianpai.robot.mcuservice.R;
import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.letianpaiservice.ILetianpaiService;
import com.renhejia.robot.letianpaiservice.LtpCommand;
import com.renhejia.robot.letianpaiservice.LtpCommandCallback;

import top.keepempty.sph.library.SerialPortConfig;
import top.keepempty.sph.library.SerialPortFinder;
import top.keepempty.sph.library.SerialPortHelper;
import top.keepempty.sph.library.SphCmdEntity;
import top.keepempty.sph.library.SphResultCallback;

public class LTPMcuService extends Service {

    private static final String TAG = "letianpai";
    private boolean isConnectService = false;
    private ILetianpaiService iLetianpaiService;
    private SerialPortHelper serialPortHelper;
    private SerialPortFinder mSerialPortFinder;
    private boolean isOpen;
    private long start = 0;
    private static String COMMAND_TITLE = "AT+RES,ACK";
    private static String COMMAND_END = "AT+RES,end";
    private static String COMMAND_ERROR = "wrong message";
    private static String fullCommand = "";

    @Override
    public void onCreate() {
        super.onCreate();
        initSerialPort();
        openSerialPort();
        connectService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        createNotificationChannel();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initSerialPort() {
        mSerialPortFinder = new SerialPortFinder();
    }

    private void closeSerialPort(){
        LogUtils.logi("letianpian","closeSerialPort=== ");
        if (serialPortHelper != null){
            serialPortHelper.closeDevice();
        }

    }
    /**
     * 打开串口
     */
    private void openSerialPort() {
        LogUtils.logi("letianpian","openSerialPort=== ");

        /**
         * 串口参数
         */

        SerialPortConfig serialPortConfig = new SerialPortConfig();
        serialPortConfig.mode = 0;
        serialPortConfig.path = "/dev/ttyS5";
        serialPortConfig.baudRate = 115200;
        serialPortConfig.dataBits = 8;
        serialPortConfig.parity = 'N';
        serialPortConfig.stopBits = 1;

        // 初始化串口
        serialPortHelper = new SerialPortHelper(128);
        // 设置串口参数
        serialPortHelper.setConfigInfo(serialPortConfig);
        // 开启串口
        isOpen = serialPortHelper.openDevice();
        if (!isOpen) {
            LogUtils.logi(TAG, "串口打开失败！！！" );
        }
        serialPortHelper.setSphResultCallback(new SphResultCallback() {
            @Override
            public void onSendData(SphCmdEntity sendCom) {
                LogUtils.logi(TAG, "发送命令：" + sendCom.commandsHex);
            }

            @Override
            public void onReceiveData(SphCmdEntity data) {

                long middle = System.currentTimeMillis();
                long last = middle - start;
                LogUtils.logi("", "end____send_middle: " + last);
                LogUtils.logi(TAG, "收到命令：" + data.commandsHex);
                String a = ConvertUtils.decodeHexString(data.commandsHex);
                LogUtils.logi(TAG, "收到命令1111111：" + a);
                if (a.equals(COMMAND_TITLE)) {
                    LogUtils.logi(TAG, "收到命令转换后：是完整命令");
                } else if (a.contains(COMMAND_TITLE)  && a.contains(COMMAND_END) && (!a.contains(COMMAND_ERROR))) {
                    AtCommandCallback.getInstance().setAtCmdResultReturn(a);

                } else if (a.contains(COMMAND_TITLE)  && a.contains(COMMAND_END) && (a.contains(COMMAND_ERROR)) ) {
                    //TODO 增加容错

                } else if (a.contains(COMMAND_TITLE)) {
                    LogUtils.logi(TAG, "收到命令转换后：是命令头");
                    fullCommand = a;
                    LogUtils.logi("end_time", "是命令头1：" + fullCommand);

                } else if (a.contains("end")) {
                    long end = System.currentTimeMillis();
                    long last1 = end - start;
                    LogUtils.logi("", "end____send_last: " + last1);
                    LogUtils.logi(TAG, "收到命令：" + data.commandsHex);
//                    LogUtils.logi(TAG, "收到命令转换后：是结尾" );
                    fullCommand = fullCommand + a;
//                    LogUtils.logi(TAG, "收到命令转换后：全部命令："+ fullCommand );
                    LogUtils.logi("end_time", "收到命令转换后：全部命令：" + fullCommand);
//                    receiveTxt.append(data.commandsHex).append("\n");
                    //TODO 此处增加回调处理线序
                    if (fullCommand != null) {

                    }
                } else {
                    fullCommand = fullCommand + a;
                    LogUtils.logi(TAG, "收到命令转换后：不是结尾～～～");
                }

            }

            @Override
            public void onComplete() {
                LogUtils.logi(TAG, "完成");
            }
        });
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.logi(TAG, "乐天派 MCU 完成AIDLService服务");
            iLetianpaiService = ILetianpaiService.Stub.asInterface(service);
            try {
                iLetianpaiService.registerCallback(bookCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isConnectService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.logi(TAG, "乐天派 MCU 无法绑定aidlserver的AIDLService服务");
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

    //链接服务端
    private void responseCommand() {
        if (!isConnectService || iLetianpaiService == null){
            connectService();
            return;
        }

        try {
            LtpCommand ltpCommand = new LtpCommand();
            ltpCommand.setCommand("trtc");
            if(ltpCommand == null){
                LogUtils.logi("letianpai_client","ltpCommand is null");
            }else{
                LogUtils.logi("letianpai_client","ltpCommand is not null");
            }
            iLetianpaiService.setCommand(ltpCommand);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //实现回调接口获取相关数据
    private final LtpCommandCallback.Stub bookCallBack = new LtpCommandCallback.Stub() {

        @Override
        public void onRobotStatusChanged(int status) throws RemoteException {
            LogUtils.logi("letianpai", "mcu_onRobotStatusChanged1: " + status);
        }

        @Override
        public void onCommandReceived(LtpCommand ltpCommand) throws RemoteException {
            if ((ltpCommand.getCommand()).equals(MCUCommandConsts.COMMAND_TYPE_OPEN_MCU)) {
                LogUtils.logd("LTPMcuService", "onCommandReceived: 打开串口");
                openSerialPort();

            } else if ((ltpCommand.getCommand()).equals(MCUCommandConsts.COMMAND_TYPE_CLOSE_MCU)) {
                LogUtils.logd("LTPMcuService", "onCommandReceived: 关闭串口");
                closeSerialPort();
            }
//            McuCommandControlManager.getInstance(LTPMcuService.this).commandDistribute(ltpCommand.getCommand(),ltpCommand.getData());
            McuCommandControlManager.getInstance(LTPMcuService.this).commandDistribute(ltpCommand);


        }
    };




}
