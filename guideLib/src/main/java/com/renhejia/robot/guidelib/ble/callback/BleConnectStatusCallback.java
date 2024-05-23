package com.renhejia.robot.guidelib.ble.callback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujunbin
 */
public class BleConnectStatusCallback {

    public static final int BLE_STATUS_DEFAULT = 0;
    public static final int BLE_STATUS_CONNECT_TO_CLIENT = 1;
    public static final int BLE_STATUS_DISCONNECT_FROM_CLIENT = 2;
    public static final int BLE_STATUS_CONNECTING_NET = 6;
    public static final int BLE_STATUS_CONNECTED_NET_SUCCESS = 3;
    public static final int BLE_STATUS_CONNECTED_NET_FAILED = 4;
    public static final int BLE_STATUS_CONNECTED_ANIMATION_PLAY_END = 5;
    public int status = BLE_STATUS_DEFAULT;

    private static class WatchViewUpdateCallbackHolder {
        private static BleConnectStatusCallback instance = new BleConnectStatusCallback();
    }

    private BleConnectStatusCallback() {

    }

    public static BleConnectStatusCallback getInstance() {
        return WatchViewUpdateCallbackHolder.instance;
    }

    private List<BleConnectStatusChangedListener> bleConnectListeners = new ArrayList<>();

    public interface BleConnectStatusChangedListener {
        void onBleConnectStatusChanged(int connectStatus);
    }

    public void registerBleConnectStatusListener(BleConnectStatusChangedListener listener) {
        if (listener != null){
            this.bleConnectListeners.add(listener);
        }
    }

    public void unregisterBleConnectStatusListener(BleConnectStatusChangedListener listener) {
        if (listener != null){
            this.bleConnectListeners.remove(listener);
        }
    }

    public void setBleConnectStatus(int connectStatus) {
        this.status = connectStatus;
        for (int i = 0;i< bleConnectListeners.size();i++){
            if (bleConnectListeners.get(i) != null){
                bleConnectListeners.get(i).onBleConnectStatusChanged(connectStatus);
            }
        }
    }

    public int getStatus() {
        return status;
    }
}
