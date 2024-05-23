package com.renhejia.robot.launcherbusinesslib.status.charging;

import com.renhejia.robot.launcherbaselib.battery.ChargingUpdateCallback;

import java.util.ArrayList;

/**
 * 充电状态
 *
 * @author liujunbin
 */
public class ChargingStatus implements ChargingInterface {
    private static ChargingStatus instance;

    private ArrayList<ChargingObserver> observerList;
    private boolean chargeStatus;
    private int batteryPercent;


    public static ChargingStatus getInstance() {
        if (instance == null) {
            instance = new ChargingStatus();
        }
        return instance;
    }

    public ChargingStatus() {
        observerList = new ArrayList();
        addListener();
    }

    private void addListener() {
        ChargingUpdateCallback.getInstance().registerChargingStatusUpdateListener(new ChargingUpdateCallback.ChargingUpdateListener() {
            @Override
            public void onChargingUpdateReceived(boolean changingStatus, int percent) {

            }

            @Override
            public void onChargingUpdateReceived(boolean changingStatus, int percent, int chargePlug) {
                setChargingStatus(changingStatus,percent,chargePlug);
            }

        });
    }

    @Override
    public void registerObserver(ChargingObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void unregisterObserver(ChargingObserver observer) {
        if (observerList.size() > 0) {
            observerList.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observerList.size(); i++) {
            observerList.get(i).updateChargingStatus(chargeStatus, batteryPercent);
        }
    }


    public void chargingStatusChanged() {
        notifyObservers();
    }


    public void setChargingStatus(boolean chargingStatus, int percent, int chargePlug) {
        if ((chargingStatus != chargeStatus) || (percent != batteryPercent)){
            this.chargeStatus = chargingStatus;
            this.batteryPercent = percent;
            chargingStatusChanged();
        }
        // TODO to be removed 后续考虑是否要移除
    }

    public boolean getChargingStatus() {
        return chargeStatus;
    }

    public int getBatteryPercent() {
        return batteryPercent;
    }

}
