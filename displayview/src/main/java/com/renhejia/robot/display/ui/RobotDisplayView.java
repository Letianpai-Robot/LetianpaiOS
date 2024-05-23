package com.renhejia.robot.display.ui;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Point;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import com.renhejia.robot.commandlib.log.LogUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.display.RobotClockSkin;
import com.renhejia.robot.display.RobotClockView;
import com.renhejia.robot.display.RobotPlatformState;
import com.renhejia.robot.display.RobotSkinInfoItem;
import com.renhejia.robot.display.SpineSkinResPool;
import com.renhejia.robot.display.callback.RobotPlatformInfoUpdateCallback;
import com.renhejia.robot.display.manager.LauncherRobotSkinInfoManager;
import com.renhejia.robot.display.utils.SpineSkinUtils;

/**
 * 机器人显示页面
 */
public class RobotDisplayView extends ViewGroup {

    private static final String TAG = RobotDisplayView.class.getSimpleName();

    private SpineSkinResPool mResPool;

    final static public int ANIM_GIF = 0;
    final static public int ANIM_MP4 = 1;
    final static public int ANIM_LOTTIE = 2;

    private RobotClockView mClockView;
    //private SpineLottieView mLottieView;
    private int mAnimType = ANIM_MP4;
    private boolean m12HourFormat = false;
    private Context mContext;
    private RobotSkinInfoItem infoItem;
    private String skinPathName;
    private boolean isRegisterMode = false;


    public RobotDisplayView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public RobotDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        initView();
    }

    public RobotDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    ContentObserver mSettingsObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
//            LogUtil.d(TAG, "is24HourFormat onChanged:" + Settings.System.getString(getContext().getContentResolver(), Settings.System.TIME_12_24));
            set12HourFormat(!DateFormat.is24HourFormat(getContext()));
            postInvalidate();
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        LogUtil.d(TAG, "registerContentObserver.....");
        getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.TIME_12_24), true, mSettingsObserver);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        LogUtil.d(TAG, "unregisterContentObserver.....");
        getContext().getContentResolver().unregisterContentObserver(mSettingsObserver);
    }

    public void set12HourFormat(boolean is12HourFormat) {
        m12HourFormat = is12HourFormat;
        if (mClockView != null) {
            mClockView.setHourFormat(m12HourFormat ? RobotClockView.SPINE_CLOCK_HOURS_12 : RobotClockView.SPINE_CLOCK_HOURS_24);
        }
    }

    public boolean is12HourFormat() {
        return m12HourFormat;
    }

    public void initView() {
        infoItem = LauncherRobotSkinInfoManager.getInstance(this.getContext()).getSpineSkinInfoItem();
        mResPool = new SpineSkinResPool(this.getContext());
        mClockView = new RobotClockView(this.getContext());

        this.addView(mClockView);
        //[niu][20191211]Format 12/24 hour time base on system setting
        m12HourFormat = !DateFormat.is24HourFormat(getContext());
        //--end [niu][20191211]....
        set12HourFormat(m12HourFormat);
        initPlatformListener();
    }


    private void unregisterPlatformListener() {

    }

    private void registerPlatformListener() {

    }

    private void setWatchClockStatus(RobotSkinInfoItem infoItem) {
        RobotPlatformState platState = new RobotPlatformState();

//        platState.airQuality = infoItem.getWeatherInfoItem().getAirQuality();
//        platState.currentTemp = infoItem.getWeatherInfoItem().getCurrentTemp();
//        platState.weatherState = infoItem.getWeatherInfoItem().getWeatherState();
        platState.batteryLevel = infoItem.getBatteryLevel();
        platState.batteryCharging = infoItem.getChargingStates();
        platState.bluetoothEnabled = infoItem.getBluetoothStatus();
        platState.mediaVolume = infoItem.getVolume();
        platState.stepNumber = infoItem.getStepCount();
        platState.wifiEnabled = infoItem.getWifiStatus();
        mClockView.updateAll(platState);

    }


    public void loadSkin(String pathName) {
        skinPathName = pathName;

        if (!mResPool.isValidSpineSkin(pathName)) {
            LogUtils.logi("Clock_Skin", "skinPathName: " + skinPathName);
            skinPathName = SpineSkinUtils.getDefaultSkin(mContext);
        }
        LogUtils.logi("Clock_Skin", "skinPathName: " + skinPathName);

        mResPool.reset();
        RobotClockSkin skin = mResPool.createSkin(skinPathName);

        if (skin != null) {
            mClockView.setSkin(skin);
            infoItem = LauncherRobotSkinInfoManager.getInstance(this.getContext()).getSpineSkinInfoItem();
            setWatchClockStatus(infoItem);
            mClockView.invalidate();

        }

    }

    public void setRefreshStatus(boolean isRefresh) {
        mClockView.setRefreshNow(isRefresh);
    }

    public void onDoubleClick(Point pt) {
        Toast.makeText(getContext(), "onDoubleClick x=" + pt.x + ", y=" + pt.y, Toast.LENGTH_SHORT).show();
    }

    public void onLongPress(Point pt) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(0, 0, r, b);
        }
    }

    /**
     * 返回对应控件ID
     *
     * @param x
     * @param y
     * @return
     */
    public int getCtrlId(int x, int y) {
        //TODO : 增加相关逻辑
        return 0;
    }

    public void updateClockView() {
        if (mClockView != null) {
            mClockView.invalidate();
        }
    }


    /**
     * @param x
     * @param y
     * @return
     */
    public int onClick(int x, int y) {
//        int ctrlId = mClockView.getCtrlId(x, y);
//
//        if (ctrlId == SpineClockSkin.CTRL_NONE_ID) {
//            //mSpineSkinView.playNextAnim();
//        }

        return RobotClockSkin.CTRL_NONE_ID;
    }


    private void initPlatformListener() {
        RobotPlatformInfoUpdateCallback.getInstance().setSpinePlatformListener(new RobotPlatformInfoUpdateCallback.SpinePlatformListener() {
            @Override
            public void updateBluetoothEnabled(boolean isBluetoothEnabled) {
                mClockView.updateBluetoothEnabled(isBluetoothEnabled);
            }

            @Override
            public void updateBatteryLevel(int batteryLevel) {
                mClockView.updateBatteryLevel(batteryLevel);
            }

            @Override
            public void updateBatteryCharging(boolean isBatteryCharging) {
                mClockView.updateBatteryCharging(isBatteryCharging);
            }

            @Override
            public void updateWifiEnabled(boolean isWifiEnabled) {
                mClockView.updateWifiEnabled(isWifiEnabled);
            }

            @Override
            public void updateMediaVolume(int batteryLevel) {
                mClockView.updateMediaVolume(batteryLevel);
            }

            @Override
            public void updateStepNumber(int stepNumber) {
                mClockView.updateStepNumber(stepNumber);
            }

            @Override
            public void updateWeather(int weatherState, int currentTemp, int airQuality) {
                mClockView.updateWeather(weatherState, currentTemp, airQuality);
            }
        });
    }

}