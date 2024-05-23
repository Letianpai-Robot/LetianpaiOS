package com.renhejia.robot.guidelib.ble.ancs;

import android.app.Service;
import android.bluetooth.*;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.*;
import android.util.Log;
import com.renhejia.robot.guidelib.ble.BleServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class ANCSService implements Handler.Callback {

    public static final String TAG = "ANCSService";

    public ANCSParser mANCSParser;
    private final Context mContext;
    private Handler serverHandler;
    private HandlerThread handlerThread;

    private BondDeviceReceiver mBondDeviceReceiver;
    //bt
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mIphoneDevice;
    private final LocalBluetoothGattCallback mGattCallback = new LocalBluetoothGattCallback();
    private BluetoothGatt mConnectedGatt;
    private BluetoothGattService mANCSService;
    private BluetoothGattCharacteristic mNotificationSourceChar;
    private BluetoothGattCharacteristic mPointControlChar;
    private BluetoothGattCharacteristic mDataSourceChar;

    //BLE GATT Server
    private BluetoothGattServer bluetoothGattServer;
    private final BluetoothGattServerCallback bluetoothGattServerCallback = new LocalBluetoothGattServerCallback();

    public ANCSService(Context context) {
        this.mContext = context;
        mANCSParser = new ANCSParser();
        onCreate();
    }

    public void onCreate() {
        handlerThread = new HandlerThread("ServerHandlerThread");
        handlerThread.start();
        mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = mBluetoothManager.getAdapter();

        //server adv
        if (mBluetoothAdapter.isEnabled()) {
            initGATTServer();
        } else {
            mBluetoothAdapter.enable();
        }

        serverHandler = new Handler(handlerThread.getLooper(), this);
        mBondDeviceReceiver = new BondDeviceReceiver(serverHandler);
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mContext.registerReceiver(mBondDeviceReceiver, intentFilter);
    }

    public void onDestroy() {
        closeGattServer();
        handlerThread.getLooper().quit();
        handlerThread = null;
        //蓝牙线程终止
        serverHandler = null;
        mContext.unregisterReceiver(mBondDeviceReceiver);
        Log.e("<<<<<", "onDestroy");
    }

    private void sendNotifationData(String bleReceiveData) {
        if (ancsBleDataListener != null) {
            ancsBleDataListener.onReceiveBleData(bleReceiveData);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case GlobalDefine.BLUETOOTH_DISCONNECT: {
                break;
            }
            case GlobalDefine.BLUETOOTH_BONDED:
                if (mIphoneDevice != null) {
                    Log.d(TAG, "connect gatt");
                    mIphoneDevice.connectGatt(mContext.getApplicationContext(), false, mGattCallback);
                }
                break;
            case GlobalDefine.BLUETOOTH_ON:
                initGATTServer();
                break;

            case GlobalDefine.BLUETOOTH_DISPLAY_INFO:
                byte[] data = (byte[]) message.obj;
                mANCSParser.onDSNotification(data);
                String bundleId = mANCSParser.getmCurrentANCSData().notification.bundleId;
                String title = mANCSParser.getmCurrentANCSData().notification.title;
                String subtitle = mANCSParser.getmCurrentANCSData().notification.subtitle;
                String msg = mANCSParser.getmCurrentANCSData().notification.message;
                String time = mANCSParser.getmCurrentANCSData().notification.date;
                if (time != null) {
                    //处理时间格式
                    DateTimeFormatter inputFormatter = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
                        // 将输入日期时间字符串解析为 LocalDateTime 对象
                        LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);
                        // 定义输出日期时间的格式
                        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        // 格式化 LocalDateTime 对象为指定格式的字符串
                        time = dateTime.format(outputFormatter);
                    }
                } else {
                    time = "";
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("title", title);
                    jsonObject.put("msg", msg);
                    jsonObject.put("time", time);
                    jsonObject.put("package", bundleId);
                    String jsonStr = jsonObject.toString();
                    //发送数据
                    sendNotifationData("003" + jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // Log.i(TAG,"详细数据："+StringTools.Bytes2HexString(get_data,0,get_data.length));
                // Log.i(TAG,"详细数据："+new String(get_data, StandardCharsets.UTF_8));
                //发出一个通知
                /*
                CommandID：为0；

                NotificationUID：对应之前请求的UID；

                AttributeList：查询结果列表，每一项的格式都是：ID/16bit  Length/Value，每个attribute都是一个字符串，其长度由Length指定，但是此字符串不是以NULL结尾。若找不到对应的Attribute，则Length为0；
                */

                //00
                //00000000
                //01
                //0600E5BEAEE4BFA1032000E5BEAEE4BFA1E694AFE4BB983A20E5BEAEE4BFA1E694AFE4BB98E587ADE8AF81
                // Log.i(TAG,"CommandID ="+data[0]);
                // Log.i(TAG,"NotificationUID ="+ StringTools.Bytes2HexString(data,1,4));

                //         int NotificationUID = 0 ;
                //         if (data.length>1){
                //             NotificationUID += (data[1] & 0x000000ff);
                //         }else if (data.length>2){
                //             NotificationUID += (data[2] & 0x000000ff) << 8;
                //         }else if (data.length>3){
                //             NotificationUID += (data[3] & 0x000000ff) << 16;
                //         }else if (data.length>4){
                //             NotificationUID += (data[4] & 0x000000ff) << 24;
                //         }
                //         String title;
                //         String msg;
                //
                //         if (data.length>5){
                //             int tagIndex = 5;
                //             byte tag = data[tagIndex];
                //             int len=0;
                //
                //             if (tag==0x01){
                //                 len = data[tagIndex+1] + data[tagIndex+2]*256;
                //                 title = new String(data,tagIndex+3,len);
                //                 Log.i(TAG,"title ="+ title);
                //                 tagIndex =tagIndex + 3 + len;
                //                 Log.i(TAG,"len ="+len);
                //             }
                //             try {
                //                 tag = data[tagIndex];
                //                 if (tag==0x03){
                //                     len = data[tagIndex+1] + data[tagIndex+2]*256;
                //                     Log.i(TAG,"len ="+len);
                //                     msg = new String(data,tagIndex+3, data.length);
                //                     Log.i(TAG,"message ="+msg);
                //                 }
                //             }catch (Exception e){
                //                 e.printStackTrace();
                //                 msg = e.getMessage();
                //             }
                //         }
                //
                break;
            case GlobalDefine.BLUETOOTH_GET_MORE_INFO:
                byte[] data2 = (byte[]) message.obj;
                // mANCSParser.onNotification(nsData);
                retrieveMoreInfo(data2);

                break;
            default:
                break;
        }

        return false;
    }


    @SuppressWarnings("unchecked")
    public boolean createBond(@SuppressWarnings("rawtypes") Class btClass, BluetoothDevice btDevice) {
        Method createBondMethod = null;
        Boolean returnValue = null;
        try {
            createBondMethod = btClass.getMethod("createBond");
            returnValue = (Boolean) createBondMethod.invoke(btDevice);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return returnValue.booleanValue();
    }


    private void closeGattServer() {
        if (bluetoothGattServer != null) {
            bluetoothGattServer.clearServices();
            bluetoothGattServer.close();
        }
    }

    public void initGATTServer() {
        bluetoothGattServer = mBluetoothManager.openGattServer(mContext, bluetoothGattServerCallback);
    }

    public void retrieveMoreInfo(byte[] nid) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        bout.write((byte) 0);

        bout.write(nid[0]);
        bout.write(nid[1]);
        bout.write(nid[2]);
        bout.write(nid[3]);


        bout.write(1);
        bout.write(50);
        bout.write(0);
        // subtitle
        bout.write(2);
        bout.write(100);
        bout.write(0);

        // message
        bout.write(3);
        bout.write(500);
        bout.write(0);

        // message size
        bout.write(4);
        bout.write(10);
        bout.write(0);
        // date
        bout.write(5);
        bout.write(10);
        bout.write(0);

        byte[] getNotificationAttribute = bout.toByteArray();

//         byte[] getNotificationAttribute = {
//                 (byte) 0x00,
//                 //UID
//                 nid[0], nid[1], nid[2], nid[3],
//
//                 //title
//                 (byte) 0x01, (byte) 0xff, (byte) 0xff,
//                 //subtitle
// //                (byte) 0x02, (byte) 0xff, (byte) 0xff,
//                 //message
//                 (byte) 0x03, (byte) 0xff, (byte) 0xff
//         };


        // Log.i(TAG, "get information detail::" + StringTools.Bytes2HexString(getNotificationAttribute, 0, getNotificationAttribute.length));
        //如果已经绑定，而且此时未断开
        if (mConnectedGatt != null) {
            BluetoothGattService service = mConnectedGatt.getService(UUID.fromString(GlobalDefine.service_ancs));
            if (service == null) {
                Log.d(TAG, "cant find service");
            } else {
                // Log.d(TAG, "find service");
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(GlobalDefine.characteristics_control_point));
                if (characteristic == null) {
                    Log.d(TAG, "cant find chara");
                } else {
                    // Log.d(TAG, "find chara");
                    characteristic.setValue(getNotificationAttribute);
                    mConnectedGatt.writeCharacteristic(characteristic);
                }
            }
        }
    }

    private void setNotificationEnabled(BluetoothGattCharacteristic characteristic) {
        mConnectedGatt.setCharacteristicNotification(characteristic, true);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(GlobalDefine.descriptor_config));
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mConnectedGatt.writeDescriptor(descriptor);
        }
    }


    private class LocalBluetoothGattCallback extends BluetoothGattCallback {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG, "connected");
                mConnectedGatt = gatt;
                gatt.discoverServices();
            }
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                //外设主动断开
                Log.d(TAG, "disconnected");
                initGATTServer();
                mConnectedGatt = null;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> services = gatt.getServices();
            for (BluetoothGattService service : services) {
                Log.d(TAG, "---service uuid--" + service.getUuid());
            }

            if (status == BluetoothGatt.GATT_SUCCESS) {
                BluetoothGattService ancsService = gatt.getService(UUID.fromString(GlobalDefine.service_ancs));
                if (ancsService == null) {
                    Log.d(TAG, "ANCS cannot find");
                } else {
                    Log.d(TAG, "ANCS find");
                    mANCSService = ancsService;
                    mDataSourceChar = ancsService.getCharacteristic(UUID.fromString(GlobalDefine.characteristics_data_source));
                    mPointControlChar = ancsService.getCharacteristic(UUID.fromString(GlobalDefine.characteristics_control_point));
                    mNotificationSourceChar = ancsService.getCharacteristic(UUID.fromString(GlobalDefine.characteristics_notification_source));
                    setNotificationEnabled(mDataSourceChar);
                }
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.d(TAG, " onDescriptorWrite:: " + status);
            // Notification source
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (descriptor.getCharacteristic().getUuid().equals(UUID.fromString(GlobalDefine.characteristics_data_source))) {
                    setNotificationEnabled(mNotificationSourceChar);
                    Log.d(TAG, "data_source 订阅成功 ");
                }
                if (descriptor.getCharacteristic().getUuid().equals(UUID.fromString(GlobalDefine.characteristics_notification_source))) {
                    Log.d(TAG, "notification_source　订阅成功 ");
                }
            }

        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.d(TAG, "onCharacteristicWrite");
            if (GlobalDefine.characteristics_control_point.equals(characteristic.getUuid().toString())) {
                Log.d(TAG, "control_point  Write successful");

            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (GlobalDefine.characteristics_notification_source.equals(characteristic.getUuid().toString())) {
                // Log.d(TAG, "notification_source Changed");
                byte[] nsData = characteristic.getValue();

                if (nsData[0] == 0x02) {
                    // Log.i(TAG,"通知被iphone删除");
                } else {
                    byte[] NotificationUID = new byte[4];
                    System.arraycopy(nsData, 4, NotificationUID, 0, 4);
                    if (serverHandler!=null){
                        Message msg = serverHandler.obtainMessage();
                        msg.what = GlobalDefine.BLUETOOTH_GET_MORE_INFO;
                        msg.obj = NotificationUID;
                        serverHandler.sendMessage(msg);
                    }
                }
            }
            if (GlobalDefine.characteristics_data_source.equals(characteristic.getUuid().toString())) {
                Log.d(TAG, "characteristics_data_source changed");
                if (serverHandler != null){
                    byte[] get_data = characteristic.getValue();
                    Message msg = serverHandler.obtainMessage();
                    msg.what = GlobalDefine.BLUETOOTH_DISPLAY_INFO;
                    msg.obj = get_data;
                    serverHandler.sendMessage(msg);
                }
            }

            if (GlobalDefine.characteristics_control_point.equals(characteristic.getUuid().toString())) {
                Log.d(TAG, "characteristics_control_point changed");
                byte[] cpData = characteristic.getValue();
                Log.i(TAG, "控制数据：" + StringTools.Bytes2HexString(cpData, 0, cpData.length));
            }
        }

    }

    private class LocalBluetoothGattServerCallback extends BluetoothGattServerCallback {

        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            Log.i(TAG, String.format("ANCS--onConnectionStateChange:%s,%s,%s,%s", device.getName(), device.getAddress(), status, newState));

            mIphoneDevice = device;
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                closeGattServer();
                String MacAddress = mIphoneDevice.getAddress();
                Log.i(TAG, "已连接设备MAC：" + MacAddress);

                if (mIphoneDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    mIphoneDevice.connectGatt(mContext.getApplicationContext(), false, mGattCallback);
                } else {
                    createBond(device.getClass(), device);
                }
            }
        }
    }

    private ANCSBleDataListener ancsBleDataListener;

    public void setBleDataListener(ANCSBleDataListener bleDataListener) {
        this.ancsBleDataListener = bleDataListener;
    }

    public interface ANCSBleDataListener {
        void onReceiveBleData(String bleData);
    }
}
