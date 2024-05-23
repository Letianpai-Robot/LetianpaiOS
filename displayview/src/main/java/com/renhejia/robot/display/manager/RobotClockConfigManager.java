package com.renhejia.robot.display.manager;

import android.content.Context;


/**
 * Launcher 偏好设置管理器
 */
public class RobotClockConfigManager implements ClockConfigConst {

    private static RobotClockConfigManager mLauncherConfigManager;
    private WatchSharedPreference mKidSharedPreference;
    private Context mContext;


    private RobotClockConfigManager(Context context) {
        this.mContext = context;
        this.mKidSharedPreference = new WatchSharedPreference(context,
                WatchSharedPreference.SHARE_PREFERENCE_NAME, WatchSharedPreference.ACTION_INTENT_CONFIG_CHANGE);
    }

    /**
     * 增加偏好设置初始化逻辑
     * (暂时没有用，预留给将来手表需要初始化一些状态值时使用)
     */
    private void initKidSmartConfigState() {

    }

    public static RobotClockConfigManager getInstance(Context context) {
        if (mLauncherConfigManager == null) {
            mLauncherConfigManager = new RobotClockConfigManager(context);
            mLauncherConfigManager.initKidSmartConfigState();
            mLauncherConfigManager.commit();
        }
        return mLauncherConfigManager;

    }

    public boolean commit() {
        return mKidSharedPreference.commit();
    }

    /**
     *
     * @return
     */
    public String getLTPClockSkinPath() {
        String skinPath = mKidSharedPreference.getString(KEY_LTP_CLOCK_SKIN_PATH, "");
        return skinPath;
    }

    /**
     */
    public void setLTPClockSkinPath(String skinPath) {
        mKidSharedPreference.putString(KEY_LTP_CLOCK_SKIN_PATH, skinPath);
    }

    /**
     * 获取手表高度
     *
     * @return
     */
    public int getWatchHeight() {
        return mKidSharedPreference.getInt(KEY_WATCH_HEIGHT, VALUE_WATCH_HEIGHT_DEFAULT);
    }

    /**
     * 设置手表高度
     *
     * @return
     */
    public void setWatchHeight(int height) {
        mKidSharedPreference.putInt(KEY_WATCH_HEIGHT, height);
    }

    /**
     * 获取手表宽度
     *
     * @return
     */
    public int getWatchWidth() {
        return mKidSharedPreference.getInt(KEY_WATCH_WIDTH, VALUE_WATCH_WIDTH_DEFAULT);
    }

    /**
     * 设置手表宽度
     *
     * @return
     */
    public void setWatchWidth(int width) {
        mKidSharedPreference.putInt(KEY_WATCH_WIDTH, width);
    }



}
