package com.renhejia.robot.guidelib.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;

import android.util.Log;
import com.renhejia.robot.commandlib.log.LogUtils;

import com.renhejia.robot.guidelib.ble.callback.BleConnectStatusCallback;
import com.renhejia.robot.guidelib.wifi.callback.GuideWifiConnectCallback;

import java.util.List;
import java.util.Timer;

/**
 * Wi-Fi 自动连接广播
 */
public class WIFIStateReceiver extends BroadcastReceiver {

    private static final String TAG = WIFIStateReceiver.class.getName();
    private Context mContext;
    private WifiManager mWifiManager;
    private boolean isConnected = false;
    private Handler handler;
    private boolean isTimeout = false;

    public WIFIStateReceiver(Context context) {
        this.mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.logd("WIFIStateReceiver", "onReceive---: " + intent.getAction());
        if (isConnected) {
            return;
        }
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION) || intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            // 网络状态变化
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID(); // 获取当前连接的 WiFi 网络的 SSID
                ssid = ssid.replace("\"", "");
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    // 已连接到 WiFi 网络
                    Log.e("letianpai_net","net_Connect:"+ssid);
                    String currentSsid = WIFIConnectionManager.getInstance(mContext).getCurrentSsid();
                    Log.e("letianpai_net","conneced-ssid:"+ ssid + "--currentSsid:"+currentSsid);
                    if (ssid.equals(currentSsid)){
                        updateNetworkStatus(context, intent);
                    }else {
                        if(!currentSsid.equals("")){
                            //切换网络失败
                            BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED);
                        }
                    }
                } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
                    Log.e("letianpai_net","net_Disconnect:"+ssid);
                    // WiFi 网络已断开连接
                    // BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED);
                }
            }

            // if (WIFIConnectionManager.getInstance(mContext).isConnected()) {
            //     LogUtils.logd("WIFIStateReceiver", "onReceive: isConnected true");

            // } else {
            //     LogUtils.logd("WIFIStateReceiver", "onReceive: isConnected flase");
            // }

        } else if (intent.getAction().equals(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)) {
            int linkWifiResult = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 123);
            LogUtils.logd("WIFIStateReceiver", "onReceive:linkWifiResult= " + linkWifiResult);
            if (linkWifiResult == WifiManager.ERROR_AUTHENTICATING) {
//                    密码错误时  清空networkId的相关信息
//                WIFIConnectionManager.getInstance(mContext).removeNetwork();
                if ((BleConnectStatusCallback.getInstance().getStatus() != BleConnectStatusCallback.BLE_STATUS_DEFAULT) && (!WIFIConnectionManager.getInstance(mContext).isSetIncorrectPassword())) {
                    BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED);
                    WIFIConnectionManager.getInstance(mContext).setSetIncorrectPassword(true);
                }
            }
        }
    }

    private void updateNetworkStatus(Context context, Intent intent) {
        if (mWifiManager.isWifiEnabled() && isWifiConnected(context)) {
            isConnected = true;
            GuideWifiConnectCallback.getInstance().setNetworkStatus(GuideWifiConnectCallback.NETWORK_TYPE_WIFI, true);
            BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS);
        }

    }

    private boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mWiFiNetworkInfo != null) {
                LogUtils.logd("WIFIStateReceiver", "isWifiConnected: " + (mWiFiNetworkInfo != null) + "  " + mWiFiNetworkInfo.isAvailable() + "  " + mWiFiNetworkInfo.isConnected());
                return mWiFiNetworkInfo.isAvailable() && mWiFiNetworkInfo.isConnected();
            }
        }
        LogUtils.logd("WIFIStateReceiver", "isWifiConnected: mWiFiNetworkInfo null");
        return false;
    }
}
