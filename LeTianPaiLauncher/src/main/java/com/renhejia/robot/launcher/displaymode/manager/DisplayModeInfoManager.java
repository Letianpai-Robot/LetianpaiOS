package com.renhejia.robot.launcher.displaymode.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.letianpai.robot.notice.general.parser.GeneralInfo;
import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;
import com.renhejia.robot.expression.locale.LocaleUtils;
import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;
import com.renhejia.robot.launcher.displaymode.callback.CalendarNoticeInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.callback.CountDownInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.callback.FansInfoCallback;
import com.renhejia.robot.launcher.displaymode.callback.GeneralInfoCallback;
import com.renhejia.robot.launcher.displaymode.callback.WeatherInfoUpdateCallback;
import com.renhejia.robot.launcher.nets.GeeUINetResponseManager;
import com.renhejia.robot.launcher.nets.GeeUiNetManager;
import com.renhejia.robot.launcherbaselib.storage.manager.LauncherConfigManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author liujunbin
 */
public class DisplayModeInfoManager {

    private static DisplayModeInfoManager instance;
    private Context mContext;


    private DisplayModeInfoManager(Context context) {
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        getDisplayInfo();
    }

    public static DisplayModeInfoManager getInstance(Context context) {
        synchronized (DisplayModeInfoManager.class) {
            if (instance == null) {
                instance = new DisplayModeInfoManager(context.getApplicationContext());
            }
            return instance;
        }
    }

    private void getDisplayInfo() {
        if (WIFIConnectionManager.getInstance(mContext).isNetworkAvailable(mContext)) {
//            getCalendarList();
//            getCountDownList();
//            getFansInfos();
            GeeUINetResponseManager.getInstance(mContext).updateGeneralInfo();
//            getClockList();
//            getWeather();
        }
    }


}