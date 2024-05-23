package com.renhejia.robot.guidelib.ble;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.letianpai.robot.letianpaiservice.LtpBleResponseCallback;
import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.guidelib.ble.ancs.ANCSService;
import com.renhejia.robot.guidelib.consts.BleConsts;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.letianpaiservice.ILetianpaiService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BleService extends Service {

    private ANCSService ancsService;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        connectService();
    }
    private void init() {
        BleServer.getInstance(this).initBleBlueTooth(this);
        BleServer.getInstance(this).setBleDataListener(this::readDataFromDevice);
    }

    private void readDataFromDevice(String bleData) {
        if (bleData != null && bleData.length() > 3) {
            String cmd = getCmd(bleData);
            String data = getData(bleData);
            LogUtils.logd("BleService", "收到蓝牙消息：cmd: " + cmd + "  data: " + data);
            switch (cmd) {
                case BleConsts.BLE_CMD_MESSAGE_TIPS: {
                    sendMessageTips(data);
                    break;
                }
                case BleConsts.BLE_CMD_COMMON_CONTROL: {
                    sendCommonControl(data);
                    break;
                }
//                case BleConsts.BLE_CMD_CHANGE_ROM_REGION:{
//
//                    //将值保存到property里面
//                    Locale locale;
//                    if (data!=null && data.equals(BleConsts.ROM_REGION_CHINA)){
//                        locale = new Locale("zh");
//                        SystemUtil.set(SystemUtil.REGION_LANGUAGE, "zh");
//                    }else{
//                        locale = new Locale("en");
//                        SystemUtil.set(SystemUtil.REGION_LANGUAGE, "en");
//                    }
//                    //切换语言
//                    try {
//                        Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
//                        Method getDefault = activityManagerNative.getMethod("getDefault");
//                        Object iActivityManager = getDefault.invoke(activityManagerNative);
//
//                        Class<?> configurationClass = Class.forName("android.content.res.Configuration");
//                        Constructor<?> configurationConstructor = configurationClass.getConstructor();
//                        Object configuration = configurationConstructor.newInstance();
//
//                        Method setLocale = configurationClass.getMethod("setLocale", Locale.class);
//                        setLocale.invoke(configuration, locale);
//
//                        Field userSetLocale = configurationClass.getField("userSetLocale");
//                        userSetLocale.setBoolean(configuration, true);
//
//                        // Method updateConfiguration = iActivityManager.getClass().getMethod("updateConfiguration", configurationClass);
//                        // updateConfiguration.invoke(iActivityManager, configuration);
//
//                        Method updatePersistentConfiguration = iActivityManager.getClass().getMethod("updatePersistentConfiguration", configurationClass);
//                        updatePersistentConfiguration.invoke(iActivityManager, configuration);
//
//                        Class<?> backupManagerClass = Class.forName("android.app.backup.BackupManager");
//                        Method dataChanged = backupManagerClass.getMethod("dataChanged", String.class);
//                        dataChanged.invoke(null, "com.android.providers.settings");
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                }
                default: {
                    if (bleData.startsWith("{")) {
                        sendMessageTips(bleData);
                    } else {
                        LogUtils.logd("BleService", "收到消息没有进行处理：" + bleData);
                    }
                }
            }
        } else {
            LogUtils.logd("BleService", "readDataFromDevice: 收到消息没有进行分发：" + bleData);
        }
    }

    /**
     * 发送控制指令
     *
     * @param data
     */
    private void sendCommonControl(String data) {
        switch (data) {
            case "shutdown": {
                shutdownRobot(this);
                break;
            }
            case "reboot": {
                reboot(this);
                break;
            }
            case "start_ancs":{
                Log.d("<<<<", "启动iOS_ANCS");
                ancsService = new ANCSService(BleService.this);
                //ios 来的通知
                ancsService.setBleDataListener(BleService.this::readDataFromDevice);
                break;
            }
            case "stop_ancs":{
                Log.d("<<<<", "停止iOS_ANCS");
                if (ancsService!=null){
                    ancsService.onDestroy();
                    ancsService = null;
                }
                break;
            }
            default: {
                if (iLetianpaiService != null) {
                    try {
                        iLetianpaiService.setBleCmd(data, data, false);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void sendMessageTips(String data) {
        if (iLetianpaiService != null) {
            try {
//                String message = "{\"msg\":\"" + data + "\",\"time\":\"${System.currentTimeMillis()}\"}";
//                String m = "{\"title\":\"title\",\"msg\":\"" + data + "\",\"time\":\"2023-06-28 22:51:49\",\"package\":\"com.tencent.mobileqq\"}";
                iLetianpaiService.setBleCmd("msgPushEventData", data, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCmd(String bleData) {
        return bleData.substring(0, 3);
    }

    private String getData(String bleData) {
        return bleData.substring(3);
    }

    private void writeDataToDevice(String command, String data) {
        BleServer.getInstance(this).writeData(command + data);
    }

    private ILetianpaiService iLetianpaiService;
    private boolean isConnectService = false;
    private final LtpBleResponseCallback ltpBleResponseCallback = new LtpBleResponseCallback.Stub() {
        @Override
        public void onBleCmdResponsReceived(String command, String data) throws RemoteException {
            writeDataToDevice(command, data);
        }
    };
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("TAG", "乐天派 bleservice 完成AIDLService服务");
            iLetianpaiService = ILetianpaiService.Stub.asInterface(service);
            try {
                iLetianpaiService.registerBleResponseCallback(ltpBleResponseCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isConnectService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("TAG", "乐天派 bleservice 接触绑定AIDL 服务");
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    /**
     * 关机
     *
     * @param context
     */
    public static void shutdownRobot(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        Class clazz = pm.getClass();
        try {
            Method shutdown = clazz.getMethod("shutdown", boolean.class, String.class, boolean.class);
            shutdown.invoke(pm, false, "shutdown", false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 重启
     *
     * @param context
     */
    public static void reboot(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        Class clazz = pm.getClass();
        try {
            Method shutdown = clazz.getMethod("reboot", boolean.class, String.class, boolean.class);
            shutdown.invoke(pm, false, "reboot", false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    private void changeRomLanguage(){
        Locale locale = new Locale("zh");//en
        try{
            Class amnClass = Class.forName("android.app.ActivityManagerNative");
            Object amn = null;
            Configuration config = null;

            Method methodGetDefault = amnClass.getMethod("getDefault");
            methodGetDefault.setAccessible(true);
            amn = methodGetDefault.invoke(amnClass);

            // config = amn.getConfiguration();
            Method methodGetConfiguration = amnClass.getMethod("getConfiguration");
            methodGetConfiguration.setAccessible(true);
            config = (Configuration) methodGetConfiguration.invoke(amn);

            // config.userSetLocale = true;
            Class configClass = config.getClass();
            Field f = configClass.getField("userSetLocale");
            f.setBoolean(config, true);

            // set the locale to the new value
            config.locale = locale;

            // amn.updateConfiguration(config);
            Method methodUpdateConfiguration = amnClass.getMethod("updateConfiguration", Configuration.class);
            methodUpdateConfiguration.setAccessible(true);
            methodUpdateConfiguration.invoke(amn, config);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}



//package com.renhejia.robot.guidelib.ble;
//
//import android.app.Service;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattServer;
//import android.bluetooth.BluetoothGattServerCallback;
//import android.bluetooth.BluetoothGattService;
//import android.bluetooth.BluetoothManager;
//import android.bluetooth.le.AdvertiseCallback;
//import android.bluetooth.le.AdvertiseData;
//import android.bluetooth.le.AdvertiseSettings;
//import android.bluetooth.le.BluetoothLeAdvertiser;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.IBinder;
//import android.os.ParcelUuid;
//import android.os.RemoteException;
//import android.os.SystemClock;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import com.letianpai.robot.letianpaiservice.LtpBleResponseCallback;
//import com.renhejia.robot.commandlib.log.LogUtils;
//import com.renhejia.robot.guidelib.ble.util.CRC8;
//import com.renhejia.robot.guidelib.ble.util.IntByteStringHexUtil;
//import com.renhejia.robot.guidelib.consts.BleConsts;
//import com.renhejia.robot.letianpaiservice.ILetianpaiService;
//
//import java.util.Arrays;
//import java.util.UUID;
//
//public class BleService extends Service {
//    public static final String TAG = "BleService new";
//    public static final UUID UUID_SERVICE = UUID.fromString("10000000-0000-0000-0000-000000000000"); //自定义UUID
//    public static final UUID UUID_CHAR_READ_NOTIFY = UUID.fromString("11000000-0000-0000-0000-000000000000");
//    public static final UUID UUID_DESC_NOTITY = UUID.fromString("11100000-0000-0000-0000-000000000000");
//    public static final UUID UUID_CHAR_WRITE = UUID.fromString("12000000-0000-0000-0000-000000000000");
//
//    private BluetoothLeAdvertiser mBluetoothLeAdvertiser; // BLE广播
//    private BluetoothGattServer mBluetoothGattServer; // BLE服务端
//    private BluetoothGattCharacteristic characteristicRead;
//    // BLE广播Callback
//    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
//        @Override
//        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
//            logTv("BLE广播开启成功");
//        }
//
//        @Override
//        public void onStartFailure(int errorCode) {
//            logTv("BLE广播开启失败,错误码:" + errorCode);
//        }
//    };
//
//    // BLE服务端Callback
//    private BluetoothGattServerCallback mBluetoothGattServerCallback = new BluetoothGattServerCallback() {
//        @Override
//        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
//            Log.i(TAG, String.format("onConnectionStateChange:%s,%s,%s,%s", device.getName(), device.getAddress(), status, newState));
//            logTv(String.format(status == 0 ? (newState == 2 ? "与[%s]连接成功" : "与[%s]连接断开") : ("与[%s]连接出错,错误码:" + status), device));
//
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                if (newState == BluetoothGatt.STATE_CONNECTED) {// 连接成功
//                    deviceNotify = device;
//                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {// 断开连接
//
//                }
//            } else {
//                Log.i(TAG, "connect fail");
//            }
//        }
//
//        @Override
//        public void onServiceAdded(int status, BluetoothGattService service) {
//            Log.i(TAG, String.format("onServiceAdded:%s,%s", status, service.getUuid()));
//            logTv(String.format(status == 0 ? "添加服务[%s]成功" : "添加服务[%s]失败,错误码:" + status, service.getUuid()));
//        }
//
//        @Override
//        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
//            Log.i(TAG, String.format("onCharacteristicReadRequest:%s,%s,%s,%s,%s", device.getName(), device.getAddress(), requestId, offset, characteristic.getUuid()));
//            String response = "aCHAR_" + (int) (Math.random() * 100); //模拟数据
//            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, response.getBytes());// 响应客户端
////            deviceNotify = device;
//            logTv("客户端读取Characteristic[" + characteristic.getUuid() + "]:\n" + response);
//        }
//
//        @Override
//        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] requestBytes) {
//
//            Log.i(TAG, "BleServerActivity onCharacteristicWriteRequest: ====" + Arrays.toString(requestBytes));
//            // 获取客户端发过来的数据
//            String requestStr = new String(requestBytes);
//            Log.i(TAG, String.format("onCharacteristicWriteRequest:%s,%s,%s,%s,%s,%s,%s,%s", device.getName(), device.getAddress(), requestId, characteristic.getUuid(), preparedWrite, responseNeeded, offset, requestStr));
//
//            String response = "wCHAR_" + (int) (Math.random() * 100); //模拟数据
//            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, response.getBytes());// 响应客户端
//            logTv("客户端写入Characteristic[" + characteristic.getUuid() + "]:\n" + requestStr);
//
//            receiveAppCmd(requestBytes);
//        }
//
//        @Override
//        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
//            Log.i(TAG, String.format("onDescriptorReadRequest:%s,%s,%s,%s,%s", device.getName(), device.getAddress(), requestId, offset, descriptor.getUuid()));
//            String response = "DESC_" + (int) (Math.random() * 100); //模拟数据
//            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, response.getBytes()); // 响应客户端
//            logTv("客户端读取Descriptor[" + descriptor.getUuid() + "]:\n" + response);
//        }
//
//        @Override
//        public void onDescriptorWriteRequest(final BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
//            // 获取客户端发过来的数据
//            String valueStr = Arrays.toString(value);
//            Log.i(TAG, String.format("onDescriptorWriteRequest:%s,%s,%s,%s,%s,%s,%s,%s", device.getName(), device.getAddress(), requestId, descriptor.getUuid(), preparedWrite, responseNeeded, offset, valueStr));
//            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);// 响应客户端
//            logTv("客户端写入Descriptor[" + descriptor.getUuid() + "]:\n" + valueStr);
//
//            // 简单模拟通知客户端Characteristic变化
//            if (Arrays.toString(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE).equals(valueStr)) { //是否开启通知
//                final BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0; i < 5; i++) {
//                            SystemClock.sleep(3000);
//                            String response = "b_CHAR_" + (int) (Math.random() * 100); //模拟数据
//                            characteristicRead.setValue(response);
//                            mBluetoothGattServer.notifyCharacteristicChanged(device, characteristicRead, false);
//                            logTv("通知客户端改变Characteristic[" + characteristicRead.getUuid() + "]:\n" + response);
//                        }
//                    }
//                }).start();
//            }
//        }
//
//        @Override
//        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
//            Log.i(TAG, String.format("onExecuteWrite:%s,%s,%s,%s", device.getName(), device.getAddress(), requestId, execute));
//        }
//
//        @Override
//        public void onNotificationSent(BluetoothDevice device, int status) {
//            Log.i(TAG, String.format("onNotificationSent:%s,%s,%s", device.getName(), device.getAddress(), status));
//        }
//
//        @Override
//        public void onMtuChanged(BluetoothDevice device, int mtu) {
//            Log.i(TAG, String.format("onMtuChanged:%s,%s,%s", device.getName(), device.getAddress(), mtu));
//        }
//
//
//    };
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
////        initBleBlueTooth();
//        connectService();
//        init();
//    }
//
//    private void init() {
//        BleServer.getInstance(this).initBleBlueTooth(this);
//        BleServer.getInstance(this).setBleDataListener(new BleServer.BleDataListener() {
//            @Override
//            public void onReceiveBleData(String bleData) {
//                LogUtils.logd("BleService", "onReceiveBleData: "+bleData);
//            }
//        });
//    }
//
//    private void initBleBlueTooth() {
//        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
////        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
////        bluetoothAdapter.setName("乐天派桌面机器人");
//
//
//        // ============启动BLE蓝牙广播(广告) =================================================================================
//        //广播设置(必须)
//        AdvertiseSettings settings = new AdvertiseSettings.Builder().setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY) //广播模式: 低功耗,平衡,低延迟
//                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH) //发射功率级别: 极低,低,中,高
//                .setTimeout(0).setConnectable(true) //能否连接,广播分为可连接广播和不可连接广播
//                .build();
//
//        //广播数据(必须，广播启动就会发送)
//        AdvertiseData advertiseData = new AdvertiseData.Builder().setIncludeDeviceName(true) //包含蓝牙名称
////                .setIncludeDeviceName(false) //包含蓝牙名称
//                .setIncludeTxPowerLevel(true) //包含发射功率级别
//                .setIncludeDeviceName(true).addManufacturerData(1, new byte[]{68, 86}) //设备厂商数据，自定义
//                .build();
//
//        //扫描响应数据(可选，当客户端扫描时才发送)
//        AdvertiseData scanResponse = new AdvertiseData.Builder().addManufacturerData(2, new byte[]{66, 66}) //设备厂商数据，自定义
//                .addServiceUuid(new ParcelUuid(UUID_SERVICE)) //服务UUID
////                .addServiceData(new ParcelUuid(UUID_SERVICE), new byte[]{2}) //服务数据，自定义
//                .build();
//
//        mBluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
//        mBluetoothLeAdvertiser.startAdvertising(settings, advertiseData, scanResponse, mAdvertiseCallback);
//
//        // 注意：必须要开启可连接的BLE广播，其它设备才能发现并连接BLE服务端!
//        // =============启动BLE蓝牙服务端=====================================================================================
//        BluetoothGattService service = new BluetoothGattService(UUID_SERVICE, BluetoothGattService.SERVICE_TYPE_PRIMARY);
//
//        //添加可读+通知characteristic
//        characteristicRead = new BluetoothGattCharacteristic(UUID_CHAR_READ_NOTIFY, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
//        characteristicRead.addDescriptor(new BluetoothGattDescriptor(UUID_DESC_NOTITY, BluetoothGattCharacteristic.PERMISSION_WRITE));
//        service.addCharacteristic(characteristicRead);
//
//        //添加可写characteristic
//        BluetoothGattCharacteristic characteristicWrite = new BluetoothGattCharacteristic(UUID_CHAR_WRITE, BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_WRITE);
//        service.addCharacteristic(characteristicWrite);
//
//        if (bluetoothManager != null) {
//            mBluetoothGattServer = bluetoothManager.openGattServer(this, mBluetoothGattServerCallback);
//        }
//
//        mBluetoothGattServer.addService(service);
//    }
//
//    private BluetoothDevice deviceNotify;
//    boolean isFullMessage = true;
//    int totalMessageLength = 0;
//    int curMessageLength = 0;
//    String strFullMessage = "";
//
//    private void receiveAppCmd(byte[] requestBytes) {
//
//        String appCmd = IntByteStringHexUtil.byteArrayToHexStr(requestBytes);
//
//        Log.e(TAG, "receiveAppCmd 客户端写入Characteristic HexStr :\n" + appCmd);
//
//        if (isFullMessage) {
//
//            String version_number = appCmd.substring(0, 4);
//            String message_id = appCmd.substring(4, 6);
//            String function = appCmd.substring(6, 8);
//            String sub_function = appCmd.substring(8, 10);
//
//            String data_length = appCmd.substring(10, 14);
//            totalMessageLength = 14 + Integer.valueOf(data_length, 16) * 2 + 2;//头+data+crc
//
//            if (appCmd.length() < totalMessageLength) {
//                Log.e(TAG, "receiveAppCmd isFullMessage=false");
//
//                isFullMessage = false;
//                strFullMessage = appCmd;
//                curMessageLength = appCmd.length();
//            } else {
//                isFullMessage = true;
//                strFullMessage = appCmd;
//            }
//        } else {
//            curMessageLength += appCmd.length();
//
//            strFullMessage += appCmd;
//
//            if (curMessageLength >= totalMessageLength) {
//                isFullMessage = true;
//            }
//        }
//
//        if (isFullMessage) {
//            Log.e(TAG, "strFullMessage =" + strFullMessage);
//            logTv("客户端写入长指令(大于20字节) strFullMessage:\n" + strFullMessage);
//
//            //crc校验
//            String crc = strFullMessage.substring(strFullMessage.length() - 2);
//            String message = strFullMessage.substring(0, strFullMessage.length() - 2);
//            String verifyCrc = CRC8.calcCrc8Str(IntByteStringHexUtil.hexStrToByteArray(message));
//
//            boolean ret = crc.toLowerCase().equals(verifyCrc.toLowerCase());
//
//            Log.e(TAG, "verifyCrc ret=" + ret);
//
//            if (ret) {
//                parseAppCmd(strFullMessage);
//            }
//        }
//
//        writeCmd("device write:Hello");
//    }
//
//    private void parseAppCmd(String appCmd) {
//        Log.e(TAG, "parseAppCmd appCmd=" + appCmd);
//
//        if (appCmd.length() < 16) {
//            return;
//        }
//
//        String version_number = appCmd.substring(0, 4);
//        String message_id = appCmd.substring(4, 6);
//        String function = appCmd.substring(6, 8);
//        String sub_function = appCmd.substring(8, 10);
//
//        Log.e(TAG, "parseAppCmd function=" + function + ",sub_function=" + sub_function);
//
//        if (function.equals("01") && sub_function.equals("01")) {
//
//        }
//    }
//
//    private void readDataFromDevice(String bleData) {
//        if (bleData != null && bleData.length() > 3) {
//            String cmd = getCmd(bleData);
//            String data = getData(bleData);
//            LogUtils.logd("BleService", "收到蓝牙消息：cmd: " + cmd + "  data: " + data);
//            switch (cmd) {
//                case BleConsts.BLE_CMD_MESSAGE_TIPS: {
//                    sendMessageTips(data);
//                    break;
//                }
//                case BleConsts.BLE_CMD_COMMON_CONTROL: {
//                    sendCommonControl(data);
//                    break;
//                }
//                default: {
//                    sendMessageTips(bleData);
//                    LogUtils.logd("BleService", "收到消息没有进行处理：" + bleData);
//                }
//            }
//        } else {
//            LogUtils.logd("BleService", "readDataFromDevice: 收到消息没有进行分发：" + bleData);
//        }
//    }
//
//    /**
//     * 发送控制指令
//     *
//     * @param data
//     */
//    private void sendCommonControl(String data) {
//        if (iLetianpaiService != null) {
//            try {
//                iLetianpaiService.setAppCmd("control", data);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void sendMessageTips(String data) {
//        if (iLetianpaiService != null) {
//            try {
//                String message = "{\"msg\":\"" + data + "\",\"time\":\"${System.currentTimeMillis()}\"}";
//                String m = "{\"title\":\"title\",\"msg\":\"" + data + "\",\"time\":\"2023-06-28 22:51:49\",\"package\":\"com.tencent.mobileqq\"}";
//                iLetianpaiService.setBleCmd("msgPushEventData", message, true);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private String getCmd(String bleData) {
//        return bleData.substring(0, 3);
//    }
//
//    private String getData(String bleData) {
//        return bleData.substring(3);
//    }
//
//    private void writeDataToDevice(String command, String data) {
//        writeCmd(command + "," + data);
//    }
//
//    private void writeCmd(String response) {
//        if (deviceNotify == null) {
//            return;
//        }
//        characteristicRead.setValue(response);
//        mBluetoothGattServer.notifyCharacteristicChanged(deviceNotify, characteristicRead, false);
//    }
//
//    private ILetianpaiService iLetianpaiService;
//    private boolean isConnectService = false;
//    private final LtpBleResponseCallback ltpBleResponseCallback = new LtpBleResponseCallback.Stub() {
//        @Override
//        public void onBleCmdResponsReceived(String command, String data) throws RemoteException {
//            writeDataToDevice(command, data);
//        }
//    };
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.d("TAG", "乐天派 bleservice 完成AIDLService服务");
//            iLetianpaiService = ILetianpaiService.Stub.asInterface(service);
//            try {
//                iLetianpaiService.registerBleResponseCallback(ltpBleResponseCallback);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//            isConnectService = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.d("TAG", "乐天派 bleservice 接触绑定AIDL 服务");
//            isConnectService = false;
//        }
//    };
//
//    //链接服务端
//    private void connectService() {
//        Intent intent = new Intent();
//        intent.setPackage("com.renhejia.robot.letianpaiservice");
//        intent.setAction("android.intent.action.LETIANPAI");
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//    }
//
//    private void logTv(String s) {
//        Log.i(TAG, "BleService logTv: " + s);
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
