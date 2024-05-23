package com.renhejia.robot.guidelib.wifi.callback;

/**
 * Created by liujunbin
 */

public class GuideWifiConnectCallback {

    private NetworkChangingUpdateListener mNetworkChangingUpdateListener;

    public static final int NETWORK_TYPE_DISABLED = 0;
    public static final int NETWORK_TYPE_WIFI = 1;

    private static class NetworkChangingUpdateHolder {
        private static GuideWifiConnectCallback instance = new GuideWifiConnectCallback();
    }

    private GuideWifiConnectCallback() {

    }

    public static GuideWifiConnectCallback getInstance() {
        return NetworkChangingUpdateHolder.instance;
    }

    public interface NetworkChangingUpdateListener {
        void onNetworkChargingUpdateReceived(int networkType, boolean networkStatus);
        void onWifiInfoChanged(String ssid,String password);
    }

    public void setChangingStatusUpdateListener(NetworkChangingUpdateListener listener) {
        this.mNetworkChangingUpdateListener = listener;
    }

    public void setNetworkStatus(int networkType,boolean networkStatus) {
        if (mNetworkChangingUpdateListener != null) {
            mNetworkChangingUpdateListener.onNetworkChargingUpdateReceived(networkType,networkStatus);
        }
    }

    public void setWifiInfo(String ssid,String password) {
        if (mNetworkChangingUpdateListener != null) {
            mNetworkChangingUpdateListener.onWifiInfoChanged(ssid,password);
        }
    }


}
