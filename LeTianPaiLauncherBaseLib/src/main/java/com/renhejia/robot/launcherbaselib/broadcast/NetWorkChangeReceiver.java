package com.renhejia.robot.launcherbaselib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.renhejia.robot.launcherbaselib.callback.NetworkChangingUpdateCallback;
import com.renhejia.robot.launcherbaselib.info.LauncherInfoManager;


/**
 *
 * @author liujunbin
 */
public class NetWorkChangeReceiver extends BroadcastReceiver {
    private WifiManager mWifiManager;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mNetworkinfo;
    private TelephonyManager mTelePhonyManager;


    @Override
    public void onReceive(Context context, Intent intent) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mTelePhonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            updateNetworkStatus(context, intent);
        } else if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {

        } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            switch (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)) {
                case WifiManager.WIFI_STATE_DISABLED:
                    LauncherInfoManager.getInstance(context).setWifiStates(false);
                    break;

                case WifiManager.WIFI_STATE_ENABLED:
                    LauncherInfoManager.getInstance(context).setWifiStates(true);
                    break;
            }

        } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                if (ConnectivityManager.TYPE_WIFI == activeNetworkInfo.getType()) {
                    NetworkChangingUpdateCallback.getInstance().setNetworkStatus(NetworkChangingUpdateCallback.NETWORK_TYPE_WIFI, 3);
                    //移动网络
                } else if (ConnectivityManager.TYPE_MOBILE == activeNetworkInfo.getType()) {
                    NetworkChangingUpdateCallback.getInstance().setNetworkStatus(NetworkChangingUpdateCallback.NETWORK_TYPE_MOBILE, -1);

                } else {
                    NetworkChangingUpdateCallback.getInstance().setNetworkStatus(NetworkChangingUpdateCallback.NETWORK_TYPE_DISABLED, -1);
                }
            } else {
                NetworkChangingUpdateCallback.getInstance().setNetworkStatus(NetworkChangingUpdateCallback.NETWORK_TYPE_DISABLED, -1);
            }
        }

    }

    private void updateNetworkStatus(Context context, Intent intent) {
        mNetworkinfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        if (mWifiManager.isWifiEnabled() && isWifiConnected(context)) {
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            int level = mWifiManager.calculateSignalLevel(wifiInfo.getRssi(), 3);
            NetworkChangingUpdateCallback.getInstance().setNetworkStatus(NetworkChangingUpdateCallback.NETWORK_TYPE_WIFI, level);
        } else {
            mNetworkinfo = mConnectivityManager.getActiveNetworkInfo();
            if (null == mNetworkinfo) {
                NetworkChangingUpdateCallback.getInstance().setNetworkStatus(NetworkChangingUpdateCallback.NETWORK_TYPE_DISABLED, -1);
            } else if (!mNetworkinfo.isAvailable() || !mNetworkinfo.isConnected()) {
                NetworkChangingUpdateCallback.getInstance().setNetworkStatus(NetworkChangingUpdateCallback.NETWORK_TYPE_DISABLED, -1);
            } else if (mNetworkinfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // 暂无移动网络需求，没有处理此部分逻辑
                }
            }
        }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable() && mWiFiNetworkInfo.isConnected();
            }
        }
        return false;
    }

}


