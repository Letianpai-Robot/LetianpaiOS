package com.rhj.audio.observer;

import com.aispeech.dui.dds.DDS;
import com.aispeech.dui.dsk.duiwidget.CommandObserver;
import com.rhj.audio.Const;
import com.rhj.audio.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 客户端CommandObserver, 用于处理客户端动作的执行以及快捷唤醒中的命令响应.
 * 例如在平台配置客户端动作： command://call?phone=$phone$&name=#name#,
 * 那么在CommandObserver的onCall方法中会回调topic为"call", data为
 */
public class RhjCommandObserver implements CommandObserver {
    //    private static final String command_dance = "rhj.controller.dance";
//    private static final String command_navigation = "rhj.controller.navigation";
    private CommandCallback callback;

    public RhjCommandObserver() {
    }

    // 注册当前更新消息
    public void register(CommandCallback callback) {
        this.callback = callback;
        DDS.getInstance().getAgent().subscribe(new String[]{Const.Remind.Insert, Const.Remind.Remove, Const.RhjController.move, Const.RhjController.turn,
                Const.RhjController.show, Const.RhjController.flighty, Const.RhjController.angry, Const.RhjController.dance,
                Const.RhjController.takePhoto, Const.RhjController.openSitePose, Const.RhjController.closeSitePose,
                Const.RhjController.openDrink, Const.RhjController.closeDrink, Const.RhjController.openFitness,
                Const.RhjController.closeFitness, Const.RhjController.openSleep, Const.RhjController.closeSleep,
                Const.RhjController.openSedentary, Const.RhjController.closeSedentary, Const.RhjController.openBirthday,
                Const.RhjController.closeBirthday, Const.RhjController.openChildren, Const.RhjController.closeChildren,
                Const.DUIController.ShutDown, Const.DUIController.OpenBluetooth, Const.DUIController.OpenSettings, Const.DUIController.SetVolumeWithNumber,
                Const.DUIController.SystemUpgrade, Const.DUIController.CloseSettings, Const.DUIController.SoundsOpenMode,
                Const.DUIController.SoundsOpenMode, Const.DUIController.SoundsCloseMode, Const.DUIController.CloseBluetooth,
                Const.MediaController.Play,Const.MediaController.Pause,Const.MediaController.Stop,Const.MediaController.Switch,Const.MediaController.Next,Const.MediaController.Prev,
                Const.RhjController.enterChatGpt, Const.RhjController.quitChatGpt}, this);  }

    public void addSubscribe(String[] strings) {
        DDS.getInstance().getAgent().subscribe(strings, this);
    }

    // 注销当前更新消息
    public void unregister() {
        DDS.getInstance().getAgent().unSubscribe(this);
    }

    @Override
    public void onCall(String command, String data) {
        LogUtils.logd("DuiCommandObserver", "onCall: " + data);
        try {
            String intentName = new JSONObject(data).optString("intentName");
            String taskName = new JSONObject(data).optString("taskName");
            switch (command) {
                case Const.RhjController.dance:
                    if (callback != null) {
                        callback.onCommand(command, "意图： " + intentName);
                    }
                    break;
                case Const.RhjController.move:
                    if (callback != null) {
//                        String direction = new JSONObject(data).optString("direction");
//                        String number = new JSONObject(data).optString("number");
//                        callback.onCommand(command, "方向：" + direction + " 步数：" + number);
                        callback.onCommand(command,data);
                    }
                    break;
                default:
                    callback.onCommand(command, data);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
