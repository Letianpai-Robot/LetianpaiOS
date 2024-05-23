package com.renhejia.robot.launcherbaselib.battery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujunbin
 */

public class ChargingUpdateCallback {
    private int battery;
    private List<ChargingUpdateListener> mChargingListenerList = new ArrayList();

    private static class ChargingUpdateCallBackHolder {
        private static ChargingUpdateCallback instance = new ChargingUpdateCallback();
    }

    private ChargingUpdateCallback() {

    }

    public static ChargingUpdateCallback getInstance() {
        return ChargingUpdateCallBackHolder.instance;
    }

    public interface ChargingUpdateListener {
        void onChargingUpdateReceived(boolean changingStatus, int percent);
        void onChargingUpdateReceived(boolean changingStatus, int percent,int chargePlug);

    }
    public void registerChargingStatusUpdateListener(ChargingUpdateListener listener) {
        if(mChargingListenerList != null){
            mChargingListenerList.add(listener);
        }
    }
    public void unregisterChargingStatusUpdateListener(ChargingUpdateListener listener) {
        if(mChargingListenerList != null){
            mChargingListenerList.remove(listener);
        }
    }

    public void setChargingStatus(boolean changingStatus,int percent) {
        this.battery = percent;
        for (int i = 0;i<mChargingListenerList.size();i++){
            if(mChargingListenerList.get(i) != null){
                mChargingListenerList.get(i).onChargingUpdateReceived(changingStatus,percent);
            }
        }
    }

    public void setChargingStatus(boolean changingStatus,int percent,int chargePlug) {
        this.battery = percent;
        for (int i = 0;i<mChargingListenerList.size();i++){
            if(mChargingListenerList.get(i) != null){
                mChargingListenerList.get(i).onChargingUpdateReceived(changingStatus,percent,chargePlug);
            }
        }
    }

    public int getBattery() {
        return battery;
    }
}
