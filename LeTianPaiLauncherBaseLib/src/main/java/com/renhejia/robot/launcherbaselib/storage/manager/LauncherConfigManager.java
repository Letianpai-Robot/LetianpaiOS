package com.renhejia.robot.launcherbaselib.storage.manager;

import android.content.Context;
import android.util.Log;

import com.renhejia.robot.launcherbaselib.storage.Consts.RobotConfigConsts;


/**
 * Launcher 偏好设置管理器
 * @author liujunbin
 */
public class LauncherConfigManager implements RobotConfigConsts {
    private static final String TAG = LauncherConfigManager.class.getSimpleName();
    private static LauncherConfigManager mLauncherConfigManager;
    private RobotSharedPreference mRobotSharedPreference;
    private Context mContext;


    private LauncherConfigManager(Context context) {
        this.mContext = context;
        this.mRobotSharedPreference = new RobotSharedPreference(context,
                RobotSharedPreference.SHARE_PREFERENCE_NAME, RobotSharedPreference.ACTION_INTENT_CONFIG_CHANGE);
    }

    /**
     * 增加偏好设置初始化逻辑
     * (暂时没有用，预留给将来机器人需要初始化一些状态值时使用)
     */
    private void initRobotConfigState() {

    }

    public static LauncherConfigManager getInstance(Context context) {
        if (mLauncherConfigManager == null) {
            mLauncherConfigManager = new LauncherConfigManager(context);
            mLauncherConfigManager.initRobotConfigState();
            mLauncherConfigManager.commit();
        }
        return mLauncherConfigManager;

    }

    public boolean commit() {
        return mRobotSharedPreference.commit();
    }

    /**
     * 获取机器人主题
     *
     * @return
     */
    public int getRobotTheme() {
        return mRobotSharedPreference.getInt(KEY_ROBOT_THEME, VALUE_WATCH_THEME_DEFAULT);
    }

    /**
     * 设置机器人主题
     *
     * @return
     */
    public void setRobotTheme(int watchTheme) {
        mRobotSharedPreference.putInt(KEY_ROBOT_THEME, watchTheme);

    }

    /**
     * 获取手表主题
     *
     * @return
     */
    public String getWatchThemeName() {
        return mRobotSharedPreference.getString(KEY_ROBOT_THEME_NAME, "default");
    }

    /**
     * 获取手表主题
     *
     * @return
     */
    public void setWatchThemeName(String watchThemeName) {
        Log.d(TAG,"setWatchThemeName ===>> watchThemeName: " + watchThemeName);
        mRobotSharedPreference.putString(KEY_ROBOT_THEME_NAME, watchThemeName);
        if (!getHadSetTheme()){
            setHadSetTheme(true);
        }
    }

    /**
     * 设置天气更新时间
     *
     * @param updateTime
     */
    public void setWeatherUpdateTime(long updateTime) {

        mRobotSharedPreference.putLong(KEY_WATCH_WEATHER_UPDATE_TIME, updateTime);
    }

    /**
     * 获取天气更新时间
     *
     * @return
     */
    public long getWeatherUpdateTime() {
        return mRobotSharedPreference.getLong(KEY_WATCH_WEATHER_UPDATE_TIME, 0l);
    }

    /**
     * 设置手表启动时间
     *
     * @param updateTime
     */
    public void setStartTime(long updateTime) {

        mRobotSharedPreference.putLong(KEY_ROBOT_START_TIME, updateTime);
    }

    /**
     * 获取手表启动时间
     *
     * @return
     */
    public long getStartTime() {
        return mRobotSharedPreference.getLong(KEY_ROBOT_START_TIME, 0l);
    }

    /**
     * 获取手表高度
     *
     * @return
     */
    public int getRobotHeight() {
        return mRobotSharedPreference.getInt(KEY_ROBOT_HEIGHT, VALUE_ROBOT_HEIGHT_DEFAULT);
    }

    /**
     * 设置手表高度
     *
     * @return
     */
    public void setRobotHeight(int height) {
        mRobotSharedPreference.putInt(KEY_ROBOT_HEIGHT, height);

    }

    /**
     * 获取机器人View宽度
     *
     * @return
     */
    public int getRobotWidth() {
        return mRobotSharedPreference.getInt(KEY_ROBOT_WIDTH, VALUE_ROBOT_WIDTH_DEFAULT);
    }

    /**
     * 设置手表宽度
     *
     * @return
     */
    public void setRobotWidth(int width) {
        mRobotSharedPreference.putInt(KEY_ROBOT_WIDTH, width);
    }

    /**
     * 获取 天气信息
     *
     * @return
     */
    public String getWeather() {
        return mRobotSharedPreference.getString(KEY_WEATHER, null);
    }

    /**
     * 设置 天气信息
     *
     * @return
     */
    public void setWeather(String weather) {
        mRobotSharedPreference.putString(KEY_WEATHER, weather);
    }

    /**
     * 是否显示
     * @return
     */
    public boolean hadShowAutoShutdownView(){
        return mRobotSharedPreference.getBoolean(KEY_SHOW_AUTO_SHUTDOWN, false);
    }

    /**
     * 设置展示自动关机
     * @return
     */
    public void setShowAutoShutdownView(boolean showNoSimView){
        mRobotSharedPreference.putBoolean(KEY_SHOW_AUTO_SHUTDOWN, showNoSimView);
    }
    /**
     * 是否显示
     * @return
     */
    public boolean getHadSetTheme(){
        return mRobotSharedPreference.getBoolean(KEY_HAD_SET_THEME, false);
    }

    /**
     * 设置展示自动关机
     * @return
     */
    private void setHadSetTheme(boolean hadSetTheme){
        mRobotSharedPreference.putBoolean(KEY_HAD_SET_THEME, hadSetTheme);
    }

    /**
     * 是否已经显示引导页面
     * @return
     */
    public boolean hadShowGuideView(){
        return mRobotSharedPreference.getBoolean(KEY_SHOW_GUIDE_VIEW, false);
    }

    /**
     * 设置显示引导页面状态
     * @return
     */
    public void setShowGuideView(boolean showGuideView){
        mRobotSharedPreference.putBoolean(KEY_SHOW_GUIDE_VIEW, showGuideView);
    }

    /**
     * 是否已经显示视频通话
     * @return
     */
    public boolean getShowHideListStatus(String key){
        return mRobotSharedPreference.getBoolean(key, false);
    }

    /**
     * 设置显示引导页面状态
     * @return
     */
    public void setShowHideListStatus(String key, boolean showHideList){
        mRobotSharedPreference.putBoolean(key, showHideList);
    }

    /**
     * 获取Iccid次数
     *
     * @return
     */
    public int getSimIccidTimes() {
        return mRobotSharedPreference.getInt(SIMICCID_TIMES, 0);
    }

    /**
     * 设置Iccid次数
     *
     * @return
     */
    public void setSimIccidTimes(int times) {
        mRobotSharedPreference.putInt(SIMICCID_TIMES, times);
    }

    /**
     * 获取每天发送短信次数
     *
     * @return
     */
    public int getSMSSendDayTimes() {
        return mRobotSharedPreference.getInt(SMSSEND_DAY_TIMES, 0);
    }

    /**
     * 设置每天发送短信次数
     *
     * @return
     */
    public void setSMSSendDayTimes(int times) {
        mRobotSharedPreference.putInt(SMSSEND_DAY_TIMES, times);
    }

    /**
     * 获取Iccid
     *
     * @return
     */
    public String getSimIccid() {
        return mRobotSharedPreference.getString(SIMICCID, null);
    }

    /**
     * 设置Iccid
     *
     * @return
     */
    public void setSimIccid(String iccid) {
        mRobotSharedPreference.putString(SIMICCID, iccid);
    }

    /**
     * 获取短信发送次数
     *
     * @return
     */
    public int getSMSSendTimes() {
        return mRobotSharedPreference.getInt(SMSSEND_TIMES, 0);
    }

    /**
     * 设置短信发送次数
     *
     * @return
     */
    public void setSMSSendTimes(int times) {
        mRobotSharedPreference.putInt(SMSSEND_TIMES, times);
    }

    /**
     * 获取 记录时间
     *
     * @return
     */
    public String getRecordDate() {
        return mRobotSharedPreference.getString(RECORD_DATE, "");
    }

    /**
     * 设置 记录时间
     *
     * @return
     */
    public void setRecordDate(String date) {
        mRobotSharedPreference.putString(SERVERTIME, date);
    }

    /**
     * 获取 记录时间
     *
     * @return
     */
    public String getServerTime() {
        return mRobotSharedPreference.getString(SERVERTIME, null);
    }

    /**
     * 设置 severT
     *
     * @return
     */
    public void setServerTime(String serverTime) {
        mRobotSharedPreference.putString(SERVERTIME, serverTime);
    }

    /**
     * 获取 本地号码更新时间
     *
     * @return
     */
    public long getSelfNumberUpdateTime() {
        return mRobotSharedPreference.getLong(SELF_NUMBER_TIME, 0);
    }

    /**
     * 设置 本地号码更新时间
     *
     * @return
     */
    public void setSelfNumberUpdateTime(long updateNumberTime) {
        mRobotSharedPreference.putLong(SELF_NUMBER_TIME, updateNumberTime);
    }

    /**
     * 获取手表主题
     *
     * @return
     */
    public int getWatchAppListStyle() {
        return mRobotSharedPreference.getInt(KEY_WATCH_APP_LIST_STYLE, VALUE_WATCH_APP_LIST_STYLE_NO_SET);
    }

    /**
     * 获取手表主题
     *
     * @return
     */
    public void setWatchAppListStyle(int style) {
        mRobotSharedPreference.putInt(KEY_WATCH_APP_LIST_STYLE, style);

    }

    /**
     * 获取 应用列表
     *
     * @return
     */
    public String getAppList() {
        return mRobotSharedPreference.getString(APP_LIST, null);
    }

    /**
     * 设置 应用列表
     *
     * @return
     */
    public void setAppList(String appList) {
        mRobotSharedPreference.putString(APP_LIST, appList);
    }

    /**
     * 获取 最近使用应用列表
     *
     * @return
     */
    public String getRecentAppList() {
        return mRobotSharedPreference.getString(RECENT_APP_LIST, null);
    }

    /**
     * 设置 最近使用应用列表
     *
     * @return
     */
    public void setRecentAppList(String appList) {
        mRobotSharedPreference.putString(RECENT_APP_LIST, appList);
    }

    public void setRobotWeather(String weather) {
        mRobotSharedPreference.putString(RECENT_WEATHER, weather);
    }

    public String getRobotWeather() {
        return mRobotSharedPreference.getString(RECENT_WEATHER, null);
    }

    public void setRobotCalendar(String weather) {
        mRobotSharedPreference.putString(RECENT_CALENDAR, weather);
    }

    public String getRobotCalendar() {
        return mRobotSharedPreference.getString(RECENT_CALENDAR, null);
    }

    public void setRobotFansInfo (String fansInfo) {
        mRobotSharedPreference.putString(RECENT_FANS_INFO, fansInfo);
    }

    public String getRobotFansInfo() {
        return mRobotSharedPreference.getString(RECENT_FANS_INFO, null);
    }

    public void setRobotCountDown(String fansInfo) {
        mRobotSharedPreference.putString(RECENT_COUNT_DOWN, fansInfo);
    }

    public String getRobotCountDown() {
        return mRobotSharedPreference.getString(RECENT_COUNT_DOWN, null);
    }
    public void setUploadInfoTime( long uploadTime) {
        mRobotSharedPreference.putLong(UPLOAD_DATA_TIME, uploadTime);
    }

    public long getUploadInfoTime() {
        return mRobotSharedPreference.getLong(UPLOAD_DATA_TIME,0);
    }



}
