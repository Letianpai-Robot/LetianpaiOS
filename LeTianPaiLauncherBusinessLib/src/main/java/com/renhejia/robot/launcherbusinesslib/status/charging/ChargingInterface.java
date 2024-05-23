package com.renhejia.robot.launcherbusinesslib.status.charging;

public interface ChargingInterface {

    public void registerObserver(ChargingObserver observer);

    public void unregisterObserver(ChargingObserver Observer);

    public void notifyObservers();

}
