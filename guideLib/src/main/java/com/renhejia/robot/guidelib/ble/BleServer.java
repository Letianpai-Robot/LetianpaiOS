package com.renhejia.robot.guidelib.ble;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;

import com.renhejia.robot.commandlib.log.LogUtils;

import android.util.Log;
import android.widget.TextView;

import com.renhejia.robot.guidelib.ble.util.CRC8;
import com.renhejia.robot.guidelib.ble.util.IntByteStringHexUtil;
import com.renhejia.robot.guidelib.ble.callback.BleConnectStatusCallback;
import com.renhejia.robot.guidelib.manager.LTPGuideConfigManager;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.wifi.WIFIAutoConnectionService;
import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;
import com.renhejia.robot.guidelib.wifi.WIFIStateReceiver;
import com.renhejia.robot.guidelib.wifi.callback.GuideWifiConnectCallback;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class BleServer {

    public Context mContext;
    private static BleServer instance;
    private static final String NET_SSID = "ssid=";
    private static final String NET_PASSWORD = "password=";
    private static final String NET_SPLIT = "_;;_";
    private static final String NETWORK_CONNECT_ENTERING = "network_connect_prepare";
    private static final String NETWORK_CONNECTING = "network_connecting";
    private static final String NETWORK_CONNECT_SUCCESS = "network_connect_success";
    private static final String NETWORK_CONNECT_FAILED = "network_connect_failed";
    private static final String NETWORK_CONNECT_TEST = "network_connect_test";
    private static final String REQUEST_MAC_ADDRESS = "requestMacAddress";
    private static final String REQUEST_SYSTEM_VERSION = "requestSystemVersion";
    private static final String MAC_START = "//;;__";
    private static final String MAC_END = "__;;//";

    // MAC_START+ macAddress +MAC_END
    // macAddress: 88:12:AC:54:4A:A2

    // NET_SSID + "Wi-Fi名"+ NET_SPLIT + NET_PASSWORD+ "password"
    // "ssid=LETIANPAI-5G_;;_password=Renhejia0801"

    public static BleServer getInstance(Context context) {

        synchronized (BleServer.class) {
            if (instance == null) {
                instance = new BleServer(context.getApplicationContext());
            }
            return instance;
        }
    }

    public BleServer(Context context) {
        this.mContext = context;
        setWifiChangedListener();
        enableBluetooth(context);
        // initBleBlueTooth(context);
    }

    private void setWifiChangedListener() {
        BleConnectStatusCallback.getInstance()
                .registerBleConnectStatusListener(new BleConnectStatusCallback.BleConnectStatusChangedListener() {
                    @Override
                    public void onBleConnectStatusChanged(int connectStatus) {
                        if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT) {
                            LogUtils.logd("BleServer", "onBleConnectStatusChanged: BLE_STATUS_CONNECT_TO_CLIENT");
                            // enterWifiConnectMode();
                        } else if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS) {
                            LogUtils.logd("BleServer", "onBleConnectStatusChanged: BLE_STATUS_CONNECTED_NET_SUCCESS");
                            wifiConnectedSuccess();
                        } else if (connectStatus == BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED) {
                            LogUtils.logd("BleServer", "onBleConnectStatusChanged: BLE_STATUS_CONNECTED_NET_FAILED");
                            wifiConnectedFailed();
                        } else if (connectStatus == BleConnectStatusCallback.BLE_STATUS_DISCONNECT_FROM_CLIENT) {
                            LogUtils.logd("BleServer", "onBleConnectStatusChanged: BLE_STATUS_DISCONNECT_FROM_CLIENT");
                        }
                    }
                });
    }

    public static final String TAG = "ble_server";
    public static final UUID UUID_SERVICE = UUID.fromString("10000000-0000-0000-0000-000000000000"); // 自定义UUID
    public static final UUID UUID_CHAR_READ_NOTIFY = UUID.fromString("11000000-0000-0000-0000-000000000000");
    public static final UUID UUID_DESC_NOTITY = UUID.fromString("11100000-0000-0000-0000-000000000000");
    public static final UUID UUID_CHAR_WRITE = UUID.fromString("12000000-0000-0000-0000-000000000000");

    // 固定的值，flutter连网需要
    private static final UUID CCCD_ID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private TextView mTips;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser; // BLE广播
    private BluetoothGattServer mBluetoothGattServer; // BLE服务端

    private BluetoothDevice deviceNotify;
    private BluetoothDevice deviceNotifyRead;// 给付坤
    private BluetoothGattDescriptor descriptorNotify;
    private BluetoothGattCharacteristic characteristicRead;

    private static final int HANDLE_SET_NET_INFO = 99;

    // BLE广播Callback
    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            LogUtils.logi(TAG, "BLE广播开启成功");
        }

        @Override
        public void onStartFailure(int errorCode) {
            LogUtils.logi(TAG, "BLE广播开启失败,错误码:" + errorCode);
        }
    };

    public void enableBluetooth(Context context) {
        LogUtils.logi(TAG, "enableBluetooth ====== 0:");
        // 检查蓝牙开关
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            LogUtils.logi(TAG, "enableBluetooth ====== 1:");
            return;
        } else {
            LogUtils.logi(TAG, "enableBluetooth ====== 2:");
            if (!adapter.isEnabled()) {
                LogUtils.logi(TAG, "enableBluetooth ====== 3:");
                // 直接开启蓝牙
                adapter.enable();
                // 跳转到设置界面
                // startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                // 112);
            }
        }

        // 检查是否支持BLE蓝牙
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            LogUtils.logi(TAG, "enableBluetooth ====== 4:");
            return;
        }

        // Android 6.0动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.logi(TAG, "enableBluetooth ====== 5:");
            String[] permissions = { Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION };
            for (String str : permissions) {
                if (context.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    LogUtils.logi(TAG, "enableBluetooth ====== 6:" + str);
                    ((Activity) context).requestPermissions(permissions, 111);
                    break;
                }
            }
        }
    }

    private BluetoothGattServerCallback mBluetoothGattServerCallback = new BluetoothGattServerCallback() {

        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            LogUtils.logi(TAG, String.format("onConnectionStateChange:%s,%s,%s,%s", device.getName(),
                    device.getAddress(), status, newState));
            LogUtils.logi(TAG, String.format(
                    status == 0 ? (newState == 2 ? "与[%s]连接成功" : "与[%s]连接断开") : ("与[%s]连接出错,错误码:" + status), device));

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothGatt.STATE_CONNECTED) {// 连接成功
                    BleConnectStatusCallback.getInstance()
                            .setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECT_TO_CLIENT);
                    deviceNotifyRead = device;

                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {// 断开连接
                    BleConnectStatusCallback.getInstance()
                            .setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_DISCONNECT_FROM_CLIENT);
                }
            } else {
                LogUtils.logi(TAG, "connect fail");
            }
        }

        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            LogUtils.logi(TAG, String.format("onServiceAdded:%s,%s", status, service.getUuid()));
            LogUtils.logi(TAG,
                    String.format(status == 0 ? "添加服务[%s]成功" : "添加服务[%s]失败,错误码:" + status, service.getUuid()));
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset,
                BluetoothGattCharacteristic characteristic) {
            LogUtils.logi(TAG, String.format("onCharacteristicReadRequest:%s,%s,%s,%s,%s", device.getName(),
                    device.getAddress(), requestId, offset, characteristic.getUuid()));
            String response = "CHAR_" + (int) (Math.random() * 100); // 模拟数据
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset,
                    response.getBytes());// 响应客户端
            LogUtils.logi(TAG, "客户端读取Characteristic[" + characteristic.getUuid() + "]:\n" + response);
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId,
                BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset,
                byte[] requestBytes) {
            // 获取客户端发过来的数据
            ByteBuffer buffer = ByteBuffer.wrap(requestBytes);
            // 将ByteBuffer的内容转换为字符串
            String requestStr = StandardCharsets.UTF_16LE.decode(buffer).toString();
            // String requestStr = new String(requestBytes);
            // LogUtils.logi(TAG,
            // String.format("onCharacteristicWriteRequest:%s,%s,%s,%s,%s,%s,%s,%s",
            // device.getName(), device.getAddress(), requestId, characteristic.getUuid(),
            // preparedWrite, responseNeeded, offset, requestStr));

            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, requestBytes);// 响应客户端
            LogUtils.logi(TAG, "客户端写入Characteristic" + requestStr);

            sendMessageDelay(HANDLE_SET_NET_INFO, 10, requestStr);
            // receiveAppCmd(requestBytes);
        }

        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset,
                BluetoothGattDescriptor descriptor) {
            LogUtils.logi(TAG, "onDescriptorReadRequest =========== 1" + descriptor.getUuid() + "]:\n");
            LogUtils.logi(TAG, String.format("onDescriptorReadRequest:%s,%s,%s,%s,%s", device.getName(),
                    device.getAddress(), requestId, offset, descriptor.getUuid()));
            String response = "DESC_" + (int) (Math.random() * 100); // 模拟数据
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset,
                    response.getBytes()); // 响应客户端
            LogUtils.logi(TAG, "客户端读取Descriptor[" + descriptor.getUuid() + "]:\n" + response);
        }

        @Override
        public void onDescriptorWriteRequest(final BluetoothDevice device, int requestId,
                BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset,
                byte[] value) {
            LogUtils.logi(TAG, "onDescriptorReadRequest =========== 2" + descriptor.getUuid() + "]:\n");
            // 获取客户端发过来的数据
            String valueStr = Arrays.toString(value);
            LogUtils.logi(TAG,
                    String.format("onDescriptorWriteRequest:%s,%s,%s,%s,%s,%s,%s,%s", device.getName(),
                            device.getAddress(), requestId, descriptor.getUuid(), preparedWrite, responseNeeded, offset,
                            valueStr));
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);// 响应客户端
            LogUtils.logi(TAG, "客户端写入Descriptor[" + descriptor.getUuid() + "]:\n" + valueStr);

            // 简单模拟通知客户端Characteristic变化
            if (Arrays.toString(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE).equals(valueStr)) { // 是否开启通知

                deviceNotify = device;
                descriptorNotify = descriptor;
                if (descriptorNotify == null) {
                    LogUtils.logi("write_command", "descriptorNotify is null:  ============ 1 =============");
                } else {
                    LogUtils.logi("write_command", "descriptorNotify is not null: ============ 2 =============");
                }

                if (deviceNotify == null) {
                    LogUtils.logi("write_command", "deviceNotify is null:  ============ 1 =============");
                } else {
                    LogUtils.logi("write_command", "deviceNotify is not null: ============ 2 =============");
                }

            }
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            LogUtils.logi(TAG, String.format("onExecuteWrite:%s,%s,%s,%s", device.getName(), device.getAddress(),
                    requestId, execute));
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            LogUtils.logi(TAG,
                    String.format("onNotificationSent:%s,%s,%s", device.getName(), device.getAddress(), status));
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            LogUtils.logi(TAG, String.format("onMtuChanged:%s,%s,%s", device.getName(), device.getAddress(), mtu));
        }
    };

    public void initBleBlueTooth(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // ============启动BLE蓝牙广播(广告)
        // =================================================================================
        // 广播设置(必须)
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY) // 广播模式: 低功耗,平衡,低延迟
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH) // 发射功率级别: 极低,低,中,高
                .setTimeout(0).setConnectable(true) // 能否连接,广播分为可连接广播和不可连接广播
                .build();

        // 广播数据(必须，广播启动就会发送)
        AdvertiseData advertiseData = new AdvertiseData.Builder().setIncludeDeviceName(true) // 包含蓝牙名称
                // .setIncludeDeviceName(false) //包含蓝牙名称
                .setIncludeTxPowerLevel(true) // 包含发射功率级别
                .addManufacturerData(1, new byte[] { 68, 86 }) // 设备厂商数据，自定义
                .build();

        // 扫描响应数据(可选，当客户端扫描时才发送)
        AdvertiseData scanResponse = new AdvertiseData.Builder().addManufacturerData(2, new byte[] { 66, 66 }) // 设备厂商数据，自定义
                .addServiceUuid(new ParcelUuid(UUID_SERVICE)) // 服务UUID
                .build();

        mBluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        // setting关闭蓝牙之后，这里会空指针
        if (mBluetoothLeAdvertiser != null) {
            mBluetoothLeAdvertiser.startAdvertising(settings, advertiseData, scanResponse, mAdvertiseCallback);
        }
        // 注意：必须要开启可连接的BLE广播，其它设备才能发现并连接BLE服务端!
        // =============启动BLE蓝牙服务端=====================================================================================
        BluetoothGattService service = new BluetoothGattService(UUID_SERVICE,
                BluetoothGattService.SERVICE_TYPE_PRIMARY);

        // //添加可读+通知characteristic
        // BluetoothGattCharacteristic characteristicRead = new
        // BluetoothGattCharacteristic(UUID_CHAR_READ_NOTIFY,BluetoothGattCharacteristic.PROPERTY_READ
        // | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
        // BluetoothGattCharacteristic.PERMISSION_READ);
        // characteristicRead.addDescriptor(new
        // BluetoothGattDescriptor(UUID_DESC_NOTITY,
        // BluetoothGattCharacteristic.PERMISSION_WRITE));
        // service.addCharacteristic(characteristicRead);

        // 添加可读+通知characteristic
        characteristicRead = new BluetoothGattCharacteristic(UUID_CHAR_READ_NOTIFY,
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ);
        characteristicRead.addDescriptor(
                new BluetoothGattDescriptor(UUID_DESC_NOTITY, BluetoothGattCharacteristic.PERMISSION_WRITE));
        characteristicRead
                .addDescriptor(new BluetoothGattDescriptor(CCCD_ID, BluetoothGattCharacteristic.PERMISSION_READ));
        service.addCharacteristic(characteristicRead);

        // 添加可写characteristic
        BluetoothGattCharacteristic characteristicWrite = new BluetoothGattCharacteristic(UUID_CHAR_WRITE,
                BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_WRITE);
        service.addCharacteristic(characteristicWrite);

        if (bluetoothManager != null) {
            mBluetoothGattServer = bluetoothManager.openGattServer(context, mBluetoothGattServerCallback);
        }

        mBluetoothGattServer.addService(service);
    }

    public void unInit() {

        if (mBluetoothLeAdvertiser != null) {
            mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
        }

        if (mBluetoothGattServer != null) {
            mBluetoothGattServer.close();
        }
    }

    /***************************************************
     * ble指令解析
     ***********************************************************************/

    boolean isFullMessage = true;
    int totalMessageLength = 0;
    int curMessageLength = 0;
    String strFullMessage = "";
    boolean isEncrypted = false;

    private void receiveAppCmd(byte[] requestBytes) {

        String appCmd = IntByteStringHexUtil.byteArrayToHexStr(requestBytes);
        String a = IntByteStringHexUtil.hex2Str(appCmd);

        LogUtils.logi(TAG, "receiveAppCmd 客户端写入Characteristic HexStr ====== 0 :\n" + appCmd);
        LogUtils.logi(TAG, "receiveAppCmd 客户端写入Characteristic HexStr ====== 1 :\n" + a);

        if (isFullMessage) {

            String version_number = appCmd.substring(0, 4);
            String message_id = appCmd.substring(4, 6);
            String function = appCmd.substring(6, 8);
            String sub_function = appCmd.substring(8, 10);

            String data_length = appCmd.substring(10, 14);
            totalMessageLength = 14 + Integer.valueOf(data_length, 16) * 2 + 2;// 头+data+crc

            if (appCmd.length() < totalMessageLength) {
                LogUtils.logi(TAG, "receiveAppCmd isFullMessage=false");

                isFullMessage = false;
                strFullMessage = appCmd;
                curMessageLength = appCmd.length();
            } else {
                isFullMessage = true;
                strFullMessage = appCmd;
            }
        } else {
            curMessageLength += appCmd.length();

            strFullMessage += appCmd;

            if (curMessageLength >= totalMessageLength) {
                isFullMessage = true;
            }
        }

        if (isFullMessage) {
            LogUtils.logi(TAG, "strFullMessage =" + strFullMessage);
            LogUtils.logi(TAG, "客户端写入长指令(大于20字节) strFullMessage:\n" + strFullMessage);

            // crc校验
            String crc = strFullMessage.substring(strFullMessage.length() - 2);
            String message = strFullMessage.substring(0, strFullMessage.length() - 2);
            String verifyCrc = CRC8.calcCrc8Str(IntByteStringHexUtil.hexStrToByteArray(message));

            boolean ret = crc.toLowerCase().equals(verifyCrc.toLowerCase());

            LogUtils.logi(TAG, "verifyCrc ret=" + ret);

            if (ret) {
                parseAppCmd(strFullMessage);
            }
        }
    }

    private void parseAppCmd(String appCmd) {
        LogUtils.logi(TAG, "parseAppCmd appCmd=" + appCmd);

        if (appCmd.length() < 16) {
            return;
        }

        String version_number = appCmd.substring(0, 4);
        String message_id = appCmd.substring(4, 6);
        String function = appCmd.substring(6, 8);
        String sub_function = appCmd.substring(8, 10);
        LogUtils.logi(TAG, "parseAppCmd function=" + function + ",sub_function=" + sub_function);

        if (function.equals("01") && sub_function.equals("01")) {

        }
    }

    private void responseBleReceiveInfo(String requestStr) {
        LogUtils.logd("BleServer", "responseBleReceiveInfo: " + requestStr);

        if (requestStr.contains(NET_SSID) && requestStr.contains(NET_PASSWORD) && requestStr.contains(NET_SPLIT)) {
            // //需要配网了，因为是单例所以需要将原来的ssid清空
            // WIFIConnectionManager.getInstance(mContext).setCurrentSsid("");
            // registerWIFIStateReceiver();
            // WIFIConnectionManager.getInstance(mContext).setSetIncorrectPassword(false);
            // BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTING_NET);
            // LogUtils.logi(TAG, "responseNetInfo= === 3 ");
            // String[] netInfo = requestStr.split(NET_SPLIT);
            // if (netInfo != null && netInfo.length > 1) {
            // String wifiName = netInfo[0].replace(NET_SSID, "");
            // String wifiPassword = netInfo[1].replace(NET_PASSWORD, "");
            // LogUtils.logi(TAG, "responseNetInfo= === 5_1 " + wifiName);
            // GuideWifiConnectCallback.getInstance().setWifiInfo(wifiName, wifiPassword);
            // WIFIAutoConnectionService.start(mContext, wifiName, wifiPassword);
            // }
            // } else if (requestStr.equals(REQUEST_MAC_ADDRESS)) {
            // LogUtils.logi(TAG, "responseNetInfo= === 5 ");
            // String macInfo = MAC_START + SystemUtil.getWlanMacAddress() + MAC_END;
            //// writeCmdForFukun(macInfo);
            //
            // String mac = SystemUtil.getWlanMacAddress();
            // String ts = (System.currentTimeMillis() / 1000) + "";
            // String sn = SystemUtil.getLtpSn();
            // String deviceSign = getDeviceSign(mac, ts);
            // String json = "{" + "\"cmd\":\"requestMacAddress\"," + "\"mac\":\"" + mac +
            // "\"," + "\"ts\":" + ts + "," + "\"device_sign\":\"" + deviceSign + "\"," +
            // "\"sn\":\"" + sn + "\"" + "}";
            // writeKeyValueMini(json);
            // } else if (requestStr.equals(REQUEST_SYSTEM_VERSION)) {
            // LogUtils.logi(TAG, "responseNetInfo= === 5 ");
            // //TODO 此处为假的版本号，稍后建斌会提供真的版本号获取方式
            // String version = "1.0.0.1";
            // writeCmdForFukun(version);
        } else {
            fenfaBleData(requestStr);
        }
    }

    private void fenfaBleData(String bleReceiveData) {
        if (bleDataListener != null) {
            bleDataListener.onReceiveBleData(bleReceiveData);
        }
    }

    String partSecretKey = "your partSecretKey";

    public String getDeviceSign(String inputValue, String timeStr) {
        String deviceSecretKey = md5(inputValue + timeStr + partSecretKey);
        // deviceSecretKey: c6478a35ec15a395ac65ea295390846a
        String mac_sign = sha256(inputValue + timeStr + deviceSecretKey);
        return mac_sign;
        // deviceSign: cc5dc034069905a983e7f08be16e082d82dc23fa76b5aa1090af4e9e806ff9b6
    }

    public static String md5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            int tem;
            StringBuffer buffer = new StringBuffer();
            for (byte it : md.digest()) {
                tem = it;
                if (tem < 0) {
                    tem += 256;
                }
                if (tem < 16) {
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(tem));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void writeData(String response) {
        if (deviceNotifyRead == null) {
            LogUtils.logi("auto_connect", "deviceNotifyRead is null: " + response);
            return;
        }
        LogUtils.logd("BleServer", "writeData: 发送给他人数据:" + response);
        characteristicRead.setValue(response);
        mBluetoothGattServer.notifyCharacteristicChanged(deviceNotifyRead, characteristicRead, false);
    }

    // NET_SSID + "Wi-Fi名"+ NET_SPLIT + NET_PASSWORD+ "password"
    private void writeCmdForFukun(String response) {

        if (deviceNotifyRead == null) {
            LogUtils.logi("auto_connect", "deviceNotifyRead is null: " + response);
            return;
        }
        ;
        characteristicRead.setValue(response);
        mBluetoothGattServer.notifyCharacteristicChanged(deviceNotifyRead, characteristicRead, false);
        LogUtils.logi("auto_connect", "writeCmd_fk: " + response);
    }

    private void writeKeyValueMini(String response) {

        if (deviceNotifyRead == null) {
            LogUtils.logi("auto_connect", "deviceNotifyRead is null: " + response);
            return;
        }
        characteristicRead.setValue(response);
        mBluetoothGattServer.notifyCharacteristicChanged(deviceNotifyRead, characteristicRead, false);
        LogUtils.logd("BleServer", "writeKeyValueMini: end: " + response);
    }

    public void wifiConnectedSuccess() {
        writeCmdForFukun(NETWORK_CONNECT_SUCCESS);
        Log.i(TAG, "BleServer wifiConnectedSuccess: ");
        // LTPGuideConfigManager.getInstance(mContext).setActivated(true);
        // LTPGuideConfigManager.getInstance(mContext).commit();
        // SystemUtil.setRobotActivated();
        // 关闭蓝牙
        // mHandler.postDelayed(() -> unInit(), 3 * 1000);
        unregisterWIFIStateReceiver();
    }

    public void wifiConnectedFailed() {
        LogUtils.logi("auto_connect", "================wifiConnectedFailed================");
        // writeCmd(NETWORK_CONNECT_FAILED);
        writeCmdForFukun(NETWORK_CONNECT_FAILED);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                switch (msg.what) {
                    case HANDLE_SET_NET_INFO:
                        responseBleReceiveInfo((String) msg.obj);
                        break;
                }
            }
        }
    };

    private void sendMessageDelay(int handleid, long delay, String netInfo) {

        if (mHandler != null) {
            mHandler.removeMessages(handleid);
            Message message = new Message();
            message.what = handleid;
            message.obj = netInfo;
            mHandler.sendMessageDelayed(message, delay);
        }
    }

    private WIFIStateReceiver mWIFIStateReceiver;

    public void registerWIFIStateReceiver() {
        if (mWIFIStateReceiver == null) {
            mWIFIStateReceiver = new WIFIStateReceiver(mContext);
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            mContext.registerReceiver(mWIFIStateReceiver, filter);
        }
    }

    public void unregisterWIFIStateReceiver() {
        if (mWIFIStateReceiver != null) {
            mContext.unregisterReceiver(mWIFIStateReceiver);
            mWIFIStateReceiver = null;
        }

    }

    private BleDataListener bleDataListener;

    public void setBleDataListener(BleDataListener bleDataListener) {
        this.bleDataListener = bleDataListener;
    }

    interface BleDataListener {
        void onReceiveBleData(String bleData);
    }
}
