package com.renhejia.robot.launcher.dispatch.mode;

/**
 * 模式常量
 * @author liujunbin
 */
public interface ViewModeConsts {

    public static final int VM_AUTO_MODE = 1;
    public static final int VM_STANDBY_MODE = 2;
    public static final int VM_DISPLAY_MODE = 3;
    public static final int VM_CHARGING_MODE = 4;
    public static final int VM_SLEEP_MODE = 5;
    public static final int VM_FUNCTION_MODE = 6;
    public static final int VM_CHAT_GPT_MODE = 7;
    public static final int VM_REMOTE_CONTROL_MODE = 8;
    public static final int VM_DEMOSTRATE_MODE = 9;
    public static final int VM_AUTO_PLAY_MODE = 10;
    public static final int VM_AUTO_NEW_PLAY_MODE = 11;
    /**
     * 语音唤醒
     */
    public static final int VM_AUDIO_WAKEUP_MODE=12;
    /**
     * 一次性执行
     */
    public static final int VM_ONESHOT_MODE=13;


    public static final int VIEW_MODE_IN = 1;
    public static final int VIEW_MODE_OUT = 0;

}
