package com.renhejia.robot.guidelib.ble.ancs;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.renhejia.robot.guidelib.BuildConfig;


public class BondDeviceReceiver extends BroadcastReceiver {

    private Handler mHandler;

    public BondDeviceReceiver(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BuildConfig.DEBUG) {
            Log.e("<<<<<<<", "action:" + intent.getAction());
        }
        if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(intent.getAction())) {
            BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (btDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                Log.e("<<<<<<<", "已经绑定 state=" + btDevice.getBondState());
                Message msg = mHandler.obtainMessage();
                msg.what = GlobalDefine.BLUETOOTH_BONDED;
                mHandler.sendMessage(msg);
            } else if (btDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                Log.e("<<<<<<<", "正在绑定 state=" + btDevice.getBondState());
                Message msg = mHandler.obtainMessage();
                msg.what = GlobalDefine.BLUETOOTH_BONDING;
                mHandler.sendMessage(msg);
            } else {
                Log.e("<<<<<<<", "解除绑定 state=" + btDevice.getBondState());
                Message msg = mHandler.obtainMessage();
                msg.what = GlobalDefine.BLUETOOTH_BONDNONE;
                mHandler.sendMessage(msg);
            }
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction())) {
            Log.e("<<<<<<<", "断开连接");
            Message msg = mHandler.obtainMessage();
            msg.what = GlobalDefine.BLUETOOTH_DISCONNECT;
            mHandler.sendMessage(msg);
        } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(intent.getAction())) {
            Log.e("<<<<<<<", "已连接");
            Message msg = mHandler.obtainMessage();
            msg.what = GlobalDefine.BLUETOOTH_CONNECT;
            mHandler.sendMessage(msg);
        } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
            Message msg = mHandler.obtainMessage();
            if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_ON) {
                Log.e("<<<<<<<", "蓝牙打开");
                msg.what = GlobalDefine.BLUETOOTH_ON;
            }
            if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_OFF) {
                Log.e("<<<<<<<", "蓝牙关闭");
                msg.what = GlobalDefine.BLUETOOTH_OFF;
            }
            mHandler.sendMessage(msg);
        } else if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(intent.getAction())) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            int pairingVariant = intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_VARIANT, BluetoothDevice.ERROR);
            // 处理配对请求
            if (pairingVariant == BluetoothDevice.PAIRING_VARIANT_PIN) {
                Log.e("<<<<<<<", "开始pin");
                // 配对需要PIN码
                // 在此处显示PIN码给用户输入，例如弹出对话框
                // String pin = "078616"; // 这里是示例PIN码，应根据实际情况生成
                // 在这里自动输入PIN码，示例中假设PIN码为6位数字
                // device.setPin(pin.getBytes());
                abortBroadcast(); // 取消广播，确保不会再弹出系统默认的配对请求对话框

            } else if (pairingVariant == BluetoothDevice.PAIRING_VARIANT_PASSKEY_CONFIRMATION) {
                Log.e("<<<<<<<", "开始passkey");
                // 配对需要用户确认Passkey
                this.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        device.setPairingConfirmation(true);
                    }
                }, 3000);

                // 在这里进行自定义处理，例如弹出对话框提示用户确认配对
                abortBroadcast(); // 取消广播，确保不会再弹出系统默认的配对请求对话框
            }
        }
    }
}
