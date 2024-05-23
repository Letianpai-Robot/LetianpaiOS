package com.renhejia.robot.guidelib.ble.callback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujunbin
 */
public class GuideFunctionCallback {


    private static class CloseGuideCallbackHolder {
        private static GuideFunctionCallback instance = new GuideFunctionCallback();
    }

    private GuideFunctionCallback() {

    }

    public static GuideFunctionCallback getInstance() {
        return CloseGuideCallbackHolder.instance;
    }

    private List<GuideFunctionListener>  guideFunctionListener = new ArrayList<>();
    private List<RobotShutDownListener>  robotShutDownListener = new ArrayList<>();

    public interface GuideFunctionListener {
        void onCloseGuideReceived();
    }
    public interface RobotShutDownListener {
        void onShutDown();
        void onShutDownSteeringEngine();
    }

    public void registerGuideFunctionListener(GuideFunctionListener listener) {
        if (listener != null){
            this.guideFunctionListener.add(listener);
        }
    }

    public void unregisterCloseGuidedListener(GuideFunctionListener listener) {
        if (listener != null){
            this.guideFunctionListener.remove(listener);
        }
    }
    public void registerRobotShutDownListener(RobotShutDownListener listener) {
        if (listener != null){
            this.robotShutDownListener.add(listener);
        }
    }

    public void unregisterRobotShutDownListener(RobotShutDownListener listener) {
        if (listener != null){
            this.robotShutDownListener.remove(listener);
        }
    }

    public void closeGuide() {
        for (int i = 0;i< guideFunctionListener.size();i++){
            if (guideFunctionListener.get(i) != null){
                guideFunctionListener.get(i).onCloseGuideReceived();
            }
        }
    }
    public void onShutDown() {
        for (int i = 0;i< robotShutDownListener.size();i++){
            if (robotShutDownListener.get(i) != null){
                robotShutDownListener.get(i).onShutDown();
            }
        }
    }
    public void shutDownSteeringEngine() {
        for (int i = 0;i< robotShutDownListener.size();i++){
            if (robotShutDownListener.get(i) != null){
                robotShutDownListener.get(i).onShutDownSteeringEngine();
            }
        }
    }



}
