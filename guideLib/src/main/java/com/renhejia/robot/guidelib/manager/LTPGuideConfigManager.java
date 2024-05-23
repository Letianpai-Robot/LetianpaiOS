package com.renhejia.robot.guidelib.manager;

import android.content.Context;


/**
 * Launcher 偏好设置管理器
 */
public class LTPGuideConfigManager implements GuideConfigConst {

    private static LTPGuideConfigManager mLTPGuideConfigManager;
    private GuideSharedPreference mGuideSharedPreference;
    private Context mContext;


    private LTPGuideConfigManager(Context context) {
        this.mContext = context;
        this.mGuideSharedPreference = new GuideSharedPreference(context,
                GuideSharedPreference.SHARE_PREFERENCE_NAME, GuideSharedPreference.ACTION_INTENT_CONFIG_CHANGE);
    }

    /**
     * 增加偏好设置初始化逻辑
     * (暂时没有用，预留给将来手表需要初始化一些状态值时使用)
     */
    private void initKidSmartConfigState() {

    }

    public static LTPGuideConfigManager getInstance(Context context) {
        if (mLTPGuideConfigManager == null) {
            mLTPGuideConfigManager = new LTPGuideConfigManager(context);
            mLTPGuideConfigManager.initKidSmartConfigState();
            mLTPGuideConfigManager.commit();
        }
        return mLTPGuideConfigManager;

    }

    public boolean commit() {
        return mGuideSharedPreference.commit();
    }


    public String getSSID() {
        String skinPath = mGuideSharedPreference.getString(KEY_SSID, "");
        return skinPath;
    }

    public void setSSID(String ssid) {
        mGuideSharedPreference.putString(KEY_SSID, ssid);
    }

    public String getPassword() {
        String skinPath = mGuideSharedPreference.getString(KEY_PASSWORD, "");
        return skinPath;
    }

    public void setPassword(String ssid) {
        mGuideSharedPreference.putString(KEY_PASSWORD, ssid);
    }



    public boolean isActivated() {
        return mGuideSharedPreference.getBoolean(KEY_ACTIVATED, false);
    }

    public void setActivated(boolean activated) {
        mGuideSharedPreference.putBoolean(KEY_ACTIVATED, activated);
    }

    public boolean isRandom() {
        boolean isActivated = mGuideSharedPreference.getBoolean(KEY_RANDOM, false);
        return isActivated;
    }

    public void setRandom(boolean activated) {
        mGuideSharedPreference.putBoolean(KEY_RANDOM, activated);
    }





}
