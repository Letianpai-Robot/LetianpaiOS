package com.renhejia.robot.guidelib.wifi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import android.util.Log;
import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.guidelib.ble.callback.BleConnectStatusCallback;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.wifi.callback.GuideWifiConnectCallback;


import java.lang.reflect.Method;
import java.util.List;

/**
 * @author liujunbin
 */
public class WIFIConnectionManager {

    private static final String TAG = WIFIConnectionManager.class.getName();
    private static WIFIConnectionManager sInstance = null;
    private android.net.wifi.WifiManager mWifiManager;
    private int networkId;
    private Context mContext;
    private String currentSsid;
    private String currentPassword;
    public final static int WIFI_STATE_NONE = 0;
    public final static int WIFI_STATE_CONNECTING = 2;
    public final static int WIFI_STATE_CONNECTED = 4;
    private boolean isSetIncorrectPassword = false;


    public WIFIConnectionManager(Context context) {
        this.mContext = context;
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public static WIFIConnectionManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WIFIConnectionManager.class) {
                if (sInstance == null) {
                    sInstance = new WIFIConnectionManager(context);
                }
            }
        }
        return sInstance;
    }

    public String getCurrentSsid() {
        return currentSsid;
    }
    public void setCurrentSsid(String currentSsid) {
        this.currentSsid = currentSsid;
    }

    /**
     * 尝试连接指定wifi
     *
     * @param ssid     wifi名
     * @param password 密码
     * @return 是否连接成功
     */
    public boolean connect(@NonNull String ssid, @NonNull String password) {
        //将WiFi和密码存储起来
        // SystemUtil.set("persist.sys.ssid", ssid);
        // SystemUtil.set("persist.sys.password", password);

        this.currentSsid = ssid;
        this.currentPassword = password;
        LogUtils.logd("auto_connect", "connect() called with: ssid = [" + ssid + "], password = [" + password + "]");
        LogUtils.logd("auto_connect", "connect: wifi opened = " + openWifi());

        networkId = mWifiManager.addNetwork(newWifiConfig(ssid, password, true));
        boolean result = mWifiManager.enableNetwork(networkId, true);
        mWifiManager.reconnect();
        boolean isConnected = isConnected(ssid);//当前已连接至指定wifi
        boolean isNetWork = isNetworkAvailable(mContext);
        LogUtils.logd(TAG, "connect: is already connected = " + isConnected);
        if (isConnected && isNetWork) {
            GuideWifiConnectCallback.getInstance().setNetworkStatus(GuideWifiConnectCallback.NETWORK_TYPE_WIFI, true);
            BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS);
            return true;
        }
        LogUtils.logd("auto_connect", "connect: network enabled = " + result);
        return result;
    }

    /**
     * 尝试连接指定wifi
     *
     * @return 是否连接成功
     */
    public boolean connect() {
        Log.e("WIFIConnectionManager","currentSsid: "+ currentSsid + "---currentPassword::"+currentPassword);
        if (TextUtils.isEmpty(currentSsid) || TextUtils.isEmpty(currentPassword)) {
            currentSsid = SystemUtil.get("persist.sys.ssid", "");
            currentPassword = SystemUtil.get("persist.sys.password", "");
            if (TextUtils.isEmpty(currentSsid)){
                return false;
            }else{
                return connect(currentSsid, currentPassword);
            }
        }else{
            return connect(currentSsid, currentPassword);
        }
    }

    /**
     * 根据wifi名与密码配置 WiFiConfiguration, 每次尝试都会先断开已有连接
     *
     * @param isClient 当前设备是作为客户端,还是作为服务端, 影响SSID和PWD
     */
    @NonNull
    private WifiConfiguration newWifiConfig(String ssid, String password, boolean isClient) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        if (isClient) {//作为客户端, 连接服务端wifi热点时要加双引号
            config.SSID = "\"" + ssid + "\"";
            config.preSharedKey = "\"" + password + "\"";
        } else {//作为服务端, 开放wifi热点时不需要加双引号
            config.SSID = ssid;
            config.preSharedKey = password;
        }
        config.hiddenSSID = true;
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
        return config;
    }

    /**
     * @return 热点是否已开启
     */
    public boolean isWifiEnabled() {
        try {
            Method methodIsWifiApEnabled = WifiManager.class.getDeclaredMethod("isWifiApEnabled");
            return (boolean) methodIsWifiApEnabled.invoke(mWifiManager);
        } catch (Exception e) {
            LogUtils.logi(TAG, "isWifiEnabled: " + e.getMessage());
            return false;
        }
    }

    /**
     * 是否已连接指定wifi
     */
    public boolean isConnected(String ssid) {
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();

        if (wifiInfo == null) {
            return false;
        }

        LogUtils.logd("WIFIConnectionManager", "isConnected: state: " + wifiInfo.getSupplicantState());
        switch (wifiInfo.getSupplicantState()) {
            case AUTHENTICATING:
            case ASSOCIATING:
            case ASSOCIATED:
            case FOUR_WAY_HANDSHAKE:
            case GROUP_HANDSHAKE:
            case COMPLETED:
                LogUtils.logi("auto_connect", " wifiInfo.getSSID(): " + wifiInfo.getSSID().replace("\"", "").toString());
                return wifiInfo.getSSID().replace("\"", "").equals(ssid);
//            case SCANNING:
//                BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED);
//                return false;
            default:
                return false;
        }
    }

    /**
     * 是否已连接指定wifi
     */
    public boolean isConnected() {
        if (TextUtils.isEmpty(currentSsid)) {
            return false;
        } else {
            boolean isC = isConnected(currentSsid);
            boolean isN = isNetworkAvailable(mContext);
            LogUtils.logd("WIFIConnectionManager", "isConnected:isC " + isC + " isN " + isN);
            return isC;
        }
    }

    public static String getSSID(Context ctx) {
        WifiManager wifiManager =
                (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        return ssid.replaceAll("\"", "");
    }

    /**
     * 打开WiFi
     *
     * @return
     */
    public boolean openWifi() {
        boolean opened = true;
        if (!mWifiManager.isWifiEnabled()) {
            opened = mWifiManager.setWifiEnabled(true);
        }
        return opened;
    }

    /**
     * 关闭wifi
     *
     * @return
     */
    public boolean closeWifi() {
        boolean closed = true;
        if (mWifiManager.isWifiEnabled()) {
            closed = mWifiManager.setWifiEnabled(false);
        }
        return closed;
    }

    /**
     * 断开连接
     *
     * @return
     */
    public WIFIConnectionManager disconnect() {
        if (networkId != 0) {
            mWifiManager.disableNetwork(networkId);
        }
        mWifiManager.disconnect();
        return this;
    }

    /**
     * 删除网络
     *
     * @return
     */
    public boolean removeNetwork() {
        if (networkId != 0) {
            return mWifiManager.removeNetwork(networkId);
        }
        return false;
    }

    /**
     * 是否连接过指定Wifi
     */
    @Nullable
    public WifiConfiguration everConnected(String ssid) {
//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
////            return TODO;
//        }

        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        if (existingConfigs == null || existingConfigs.isEmpty()) {
            return null;
        }
        ssid = "\"" + ssid + "\"";
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals(ssid)) {
                return existingConfig;
            }
        }
        return null;
    }

    /**
     * 获取本机的ip地址
     */
    @Nullable
    public String getLocalIp() {
        return convertIp(mWifiManager.getConnectionInfo().getIpAddress());
    }

    private String convertIp(int ipAddress) {
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    public WifiManager getWifiManager() {
        return mWifiManager;
    }

    public int getConnectState(Context context, String SSID) {
        int connectState = WIFI_STATE_NONE;

        if (Build.VERSION.SDK_INT >= 26) {
            //高通8.0 GO
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (wifiInfo != null) {
                LogUtils.logi("", "getConnectState()..connectState: " + connectState + "；ossid: " + SSID + ":wifiinfo ssid: " + wifiInfo.getSSID());
                if (wifiInfo.getSSID().equals("\"" + SSID + "\"") || wifiInfo.getSSID().equals(SSID)) {

                    if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        LogUtils.logi("", "getConnectState().. network type: " + networkInfo.getType());
                        NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
                        //LogUtils.logd(TAG, ".SSID = " + wifiConfiguration.SSID + " " + detailedState + " status = " + wifiConfiguration.status + " rssi = " + rssi);
                        if (detailedState.equals(NetworkInfo.DetailedState.CONNECTING)
                                || detailedState.equals(NetworkInfo.DetailedState.AUTHENTICATING)
                                || detailedState.equals(NetworkInfo.DetailedState.OBTAINING_IPADDR)) {
                            connectState = WIFI_STATE_CONNECTED;
                        } else if (detailedState.equals(NetworkInfo.DetailedState.CONNECTED)
                                || detailedState.equals(NetworkInfo.DetailedState.CAPTIVE_PORTAL_CHECK)) {
                            connectState = WIFI_STATE_CONNECTED;
                        }

                        LogUtils.logi("", "getConnectState().. detailedState: " + detailedState);
                    }
                }
            }
        }
        return connectState;
    }

    private int findNetworkidBySsid(String ssid) {
        List<WifiConfiguration> wifiConfigs = mWifiManager.getConfiguredNetworks();
        int curNetworkId = -1;
        if (wifiConfigs != null) {
            for (WifiConfiguration existingConfig : wifiConfigs) {
                LogUtils.logd(TAG, "removeNetwork() wifiConfigs.. networkId: " + existingConfig.networkId);
                if (existingConfig.SSID.equals("\"" + ssid + "\"") || existingConfig.SSID.equals(ssid)) {
                    LogUtils.logd(TAG, "removeNetwork().. networkId: " + existingConfig.networkId);
                    curNetworkId = existingConfig.networkId;
                    break;
                }
            }
        }
        LogUtils.logd(TAG, "removeNetwork().. return networkId: " + curNetworkId);
        return curNetworkId;
    }

    public void removeNetwork(final String ssid) {
        int curNetworkId = -1;

        curNetworkId = findNetworkidBySsid(ssid);
        if (curNetworkId != -1) {
            mWifiManager.disconnect();
            boolean removeResult = mWifiManager.removeNetwork(curNetworkId);
            LogUtils.logd("auto_connect", "removeResult = " + removeResult);
        }

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            //如果仅仅是用来判断网络连接
            Network[] info = cm.getAllNetworks();
            if (info != null) {
                for (Network network : info) {
                    NetworkInfo networkInfo = cm.getNetworkInfo(network);
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setSetIncorrectPassword(boolean setIncorrectPassword) {
        isSetIncorrectPassword = setIncorrectPassword;
    }

    public boolean isSetIncorrectPassword() {
        return isSetIncorrectPassword;
    }

    public void clearWifiPasswords() {
        // 获取 WifiManager 实例
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        // 获取保存的 Wi-Fi 配置列表
        List<WifiConfiguration> wifiConfigurations = wifiManager.getConfiguredNetworks();

        // 遍历 Wi-Fi 配置列表，移除每个网络的密码
        if (wifiConfigurations != null) {
            for (WifiConfiguration wifiConfiguration : wifiConfigurations) {
                wifiManager.disableNetwork(wifiConfiguration.networkId);
                wifiManager.disconnect();
                wifiManager.removeNetwork(wifiConfiguration.networkId);
            }
        }
        // 保存配置更改
        wifiManager.saveConfiguration();
    }
}
