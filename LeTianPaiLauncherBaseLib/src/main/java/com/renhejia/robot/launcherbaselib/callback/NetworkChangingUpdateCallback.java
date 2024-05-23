package com.renhejia.robot.launcherbaselib.callback;

import java.util.ArrayList;

/**
 * Created by liujunbin
 */

public class NetworkChangingUpdateCallback {

    public static final int NETWORK_TYPE_DISABLED = 0;
    public static final int NETWORK_TYPE_WIFI = 1;
    public static final int NETWORK_TYPE_MOBILE = 2;
    private int currentNetworkType;

    private ArrayList<NetworkChangingUpdateListener> mNetworkChangeUpdateListenerList = new ArrayList<>();

    private static class NetworkChangingUpdateHolder {
        private static NetworkChangingUpdateCallback instance = new NetworkChangingUpdateCallback();
    }

    private NetworkChangingUpdateCallback() {

    }

    public static NetworkChangingUpdateCallback getInstance() {
        return NetworkChangingUpdateHolder.instance;
    }

    public interface NetworkChangingUpdateListener {
        void onNetworkChargingUpdateReceived(int networkType, int networkStatus);
    }

    public void registerChargingStatusUpdateListener(NetworkChangingUpdateListener listener) {
        if (listener != null){
            this.mNetworkChangeUpdateListenerList.add(listener);
        }
    }

    public void unregisterChargingStatusUpdateListener(NetworkChangingUpdateListener listener) {
        if (listener != null && mNetworkChangeUpdateListenerList.size()>0){
            this.mNetworkChangeUpdateListenerList.remove(listener);
        }

    }

    public void setNetworkStatus(int networkType,int networkStatus) {
        for (int i = 0;i <mNetworkChangeUpdateListenerList.size(); i++){
            mNetworkChangeUpdateListenerList.get(i).onNetworkChargingUpdateReceived(networkType,networkStatus);
        }
    }

    public boolean isNetworkConnected() {
        return (currentNetworkType == NETWORK_TYPE_WIFI);
    }

}
