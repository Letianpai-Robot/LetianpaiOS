package com.rhj.audio;

public class Const {
    /**
     * 提醒命令
     * https://www.duiopen.com/docs/tixing_tyty
     */
    public static class Remind {
        public static final String Insert = "ai.dui.dskdm.reminder.insert";
        public static final String Remove = "ai.dui.dskdm.reminder.remove";
    }

    /**
     * 思必驰中控技能
     */
    public static class DUIController {
        public static final String ShutDown = "DUI.System.Shutdown";
        /**
         * command" : {
         * "param" : {
         * <p>
         * "volume" : "+"
         * },
         * "api" : "DUI.MediaController.SetVolume"
         * },
         * <p>
         * 声音跳到百分之二十的时候 volume："20"
         * 声音大一点 volume："+"
         * 声音小一点 volume："-"
         */
        public static final String SetVolumeWithNumber = "DUI.MediaController.SetVolume";
        public static final String OpenBluetooth = "DUI.System.Connectivity.OpenBluetooth";
        public static final String CloseBluetooth = "DUI.System.Connectivity.CloseBluetooth";
        public static final String SystemUpgrade = "DUI.System.Upgrade";
        public static final String OpenSettings = "DUI.System.OpenSettings";
        public static final String CloseSettings = "DUI.System.CloseSettings";
        /**
         * 静音模式
         */
        public static final String SoundsOpenMode = "DUI.System.Sounds.OpenMode";
        public static final String SoundsCloseMode = "DUI.System.Sounds.CloseMode";
    }

    /**
     * 播放控制
     * https://www.duiopen.com/docs/bofangkongzhi_tytycomm
     */
    public static class MediaController {
        public static final String Play = "DUI.MediaController.Play";
        public static final String Pause = "DUI.MediaController.Pause";
        public static final String Stop = "DUI.MediaController.Stop";
        public static final String Replay = "DUI.MediaController.Replay";
        public static final String Prev = "DUI.MediaController.Prev";
        public static final String Next = "DUI.MediaController.Next";
        public static final String Switch = "DUI.MediaController.Switch";
        public static final String SwitchPlayMode = "DUI.MediaController.SwitchPlayMode";
        public static final String AddCollectionList = "DUI.MediaController.AddCollectionList";
        public static final String RemoveCollectionList = "DUI.MediaController.RemoveCollectionList";
        public static final String PlayCollectionList = "DUI.MediaController.PlayCollectionList";
        public static final String OpenCollectionList = "DUI.MediaController.OpenCollectionList";
        public static final String CloseCollectionList = "DUI.MediaController.CloseCollectionList";
        public static final String SetPlayMode = "DUI.MediaController.SetPlayMode";
        public static final String Progress = "DUI.MediaController.Progress";
    }

    public static class RhjController {
        public static final String GoBack = "DUI.System.GoBack";
        public static final String GoHome = "DUI.System.GoHome";
        public static final String OpenMode = "DUI.System.UserMode.OpenMode";
        //生日快乐祝福
        public static final String congraturationBirthday = "rhj.controller.congraturation";
//        public static final String congraturationBirthday = "rhj.controller.congraturation";
        //转向
        public static final String move = "rhj.controller.navigation";//?direction=#方向#&number=#数值#
        public static final String turn = "rhj.controller.turn";//?direction=#方向#&number=#数值#
        public static final String show = "rhj.controller.show";//表演金鸡独立
        public static final String flighty = "rhj.controller.flighty";//撒娇
        public static final String angry = "rhj.controller.angry";

        //跳舞
        public static final String dance = "rhj.controller.dance";


        //打开关闭
        public static final String takePhoto = "rhj.controller.takephoto";
        public static final String openSitePose = "rhj.controller.opensitepose";
        public static final String closeSitePose = "rhj.controller.closesitepose";
        public static final String openDrink = "rhj.controller.opendrink";
        public static final String closeDrink = "rhj.controller.closedrink";
        public static final String openFitness = "rhj.controller.openfitness";
        public static final String closeFitness = "rhj.controller.closefitness";
        public static final String openSleep = "rhj.controller.opensleep";
        public static final String closeSleep = "rhj.controller.closesleep";
        public static final String openSedentary = "rhj.controller.opensedentary";//久坐提醒
        public static final String closeSedentary = "rhj.controller.closesedentary";
        public static final String openBirthday = "rhj.controller.openbirthday";
        public static final String closeBirthday = "rhj.controller.closebirthday";
        public static final String openChildren = "rhj.controller.openchildren";
        public static final String closeChildren = "rhj.controller.closechildren";

        public static final String enterChatGpt = "rhj.controller.chatgpt.enter";
        public static final String quitChatGpt = "rhj.controller.chatgpt.quit";
    }


}
