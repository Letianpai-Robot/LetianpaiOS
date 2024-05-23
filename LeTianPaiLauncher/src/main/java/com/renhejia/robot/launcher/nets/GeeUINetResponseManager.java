package com.renhejia.robot.launcher.nets;

import static com.renhejia.robot.guidelib.utils.SystemUtil.getHardCode;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.letianpai.robot.notice.general.parser.GeneralInfo;
import com.renhejia.robot.commandlib.consts.RobotRemoteConsts;
import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.logo.LogoInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;
import com.renhejia.robot.commandlib.parser.showmode.ChangeShowModule;
import com.renhejia.robot.expression.locale.LocaleUtils;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;
import com.renhejia.robot.launcher.displaymode.callback.CalendarNoticeInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.callback.CountDownInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.callback.DeviceChannelLogoCallBack;
import com.renhejia.robot.launcher.displaymode.callback.DisplayInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.callback.FansInfoCallback;
import com.renhejia.robot.launcher.displaymode.callback.GeneralInfoCallback;
import com.renhejia.robot.launcher.displaymode.callback.WeatherInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.display.DisplayMode;
import com.renhejia.robot.launcher.system.LetianpaiFunctionUtil;
import com.renhejia.robot.launcherbaselib.storage.manager.LauncherConfigManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author liujunbin
 */
public class GeeUINetResponseManager {

    private static GeeUINetResponseManager instance;
    private Context mContext;
    private Gson gson;
    private FansInfo fansInfo;
    private WeatherInfo weatherInfo;
    private GeneralInfo generalInfo;
    private CountDownListInfo countDownListInfo;
    private CalenderInfo calenderInfo;

    private  LogoInfo  logoInfo ;
    private GeeUINetResponseManager(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
    }

    public static GeeUINetResponseManager getInstance(Context context) {
        synchronized (GeeUINetResponseManager.class) {
            if (instance == null) {
                instance = new GeeUINetResponseManager(context.getApplicationContext());
            }
            return instance;
        }

    }

    public void getDisplayInfo() {
        if (WIFIConnectionManager.getInstance(mContext).isNetworkAvailable(mContext)) {
            updateGeneralInfo();
//            updateWeather();
//            updateCalendarList();
//            updateCountDownList();
//            updateFansInfo();
        }
    }

    public void dispatchTask(String cmd, Object data) {
        Log.e("letianpai123456789", "commandData: ======= 2 ");
        if (cmd == null) {
            return;
        }
        Log.e("letianpai123456789", "commandData: ======= 3 ");
        if (cmd.equals(RobotRemoteConsts.COMMAND_TYPE_UPDATE_GENERAL_CONFIG)) {
            updateGeneralInfo();

        } else if (cmd.equals(RobotRemoteConsts.COMMAND_TYPE_UPDATE_WEATHER_CONFIG)) {
            updateWeather();

        } else if (cmd.equals(RobotRemoteConsts.COMMAND_TYPE_UPDATE_CALENDAR_CONFIG)) {
            updateCalendarList();

        } else if (cmd.equals(RobotRemoteConsts.COMMAND_TYPE_UPDATE_COUNT_DOWN_CONFIG)) {
            updateCountDownList();

        } else if (cmd.equals(RobotRemoteConsts.COMMAND_TYPE_UPDATE_FANS_CONFIG)) {
            updateFansInfo();

        } else if (cmd.equals(RobotRemoteConsts.COMMAND_TYPE_APP_DISPLAY_SWITCH_CONFIG)) {
            updateDisplayViews(data);

        } else if (cmd.equals(RobotRemoteConsts.COMMAND_TYPE_CHANGE_SHOW_MODULE)) {
            Log.e("letianpai","changeShowModule === ======1");
            //responseChangeApp(data);
        }
    }

    private void updateDisplayViews(Object data) {
        DisplayMode displayMode = gson.fromJson((String) data, DisplayMode.class);
        if (displayMode != null){
            DisplayInfoUpdateCallback.getInstance().updateDisplayViewInfo(displayMode);
        }

    }

    /**
     *
     */
    public void updateGeneralInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (SystemUtil.isRobotInChinese()){
                    getGeneralInfoList(true);
                }else{
                    getGeneralInfoList(false);
                }
            }
        }).start();
    }

    /**
     *
     */
    public void updateWeather() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getWeather();
            }
        }).start();
    }

    /**
     *
     */
    private void updateCalendarList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getCalendarList();
            }
        }).start();
    }



    /**
     *
     */
    private void updateFansInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getFansInfos();
            }
        }).start();
    }

    /**
     *
     */
    private void updateCountDownList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getCountDownList();
            }
        }).start();
    }



    public void setLogoInfo(LogoInfo logoInfo) {
        this.logoInfo = logoInfo;
    }

    private void getWeather() {
        GeeUiNetManager.getWeatherInfo(mContext, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("letianpai123456789", "commandData: ======= 6 ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    WeatherInfo weatherInfo = null;
                    String info = "";
                    if (response != null && response.body() != null) {
                        info = response.body().string();

                    }
                    if (info != null) {
                        weatherInfo = gson.fromJson(info, WeatherInfo.class);
                        if (weatherInfo != null) {
                            LogUtils.logi("letianpai_1234567", "weatherInfo: " + weatherInfo.toString());
                            WeatherInfoUpdateCallback.getInstance().updateWeather(weatherInfo);
                            LauncherConfigManager.getInstance(mContext).setRobotWeather(weatherInfo.toString());
                            LauncherConfigManager.getInstance(mContext).commit();
                        }

                    }
                }


            }
        });
    }

    private void getCalendarList() {
        GeeUiNetManager.getCalendarList(mContext, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    CalenderInfo calenderInfo = null;
                    String info = "";

                    if (response != null && response.body() != null) {
                        info = response.body().string();
                        LogUtils.logi("letianpai", "response: " + response.toString());
                    }
                    if (info != null) {
                        calenderInfo = gson.fromJson(info, CalenderInfo.class);
                        if (calenderInfo != null) {
                            LogUtils.logi("letianpai_1234567", "Calendar: " + calenderInfo.toString());
                            CalendarNoticeInfoUpdateCallback.getInstance().updateNotice(calenderInfo);
                            LauncherConfigManager.getInstance(mContext).setRobotCalendar(calenderInfo.toString());
                            LauncherConfigManager.getInstance(mContext).commit();
                        }
                    }
                }
            }
        });
    }

    private void getClockList() {
        GeeUiNetManager.getClockList(mContext, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    Log.e("letianpai_123456", "response: " + response.toString());
                    Log.e("letianpai_123456", "response11: " + response.body().toString());
                    String info = response.body().string();

                    if (info != null) {
                        Log.e("letianpai_1234567", "getClockList: " + info.toString());
//                        fansInfo = new Gson().fromJson(info, FansInfo.class);
//                        Log.e("letianpai_1234", "FansInfo.getMsg(): " + fansInfo.getMsg());
//                        Log.e("letianpai_1234", "FansInfo.getCode(): " + fansInfo.getCode());
//                        Log.e("letianpai_1234", " fansInfo.getData()[0].getFans_count(): " + fansInfo.getData()[0].getFans_count());
//                        Log.e("letianpai_1234", " fansInfo.getData()[0].getAvatar(): " + fansInfo.getData()[0].getAvatar());
//                        Log.e("letianpai_1234", " fansInfo.getData()[0].getNick_name(): " + fansInfo.getData()[0].getNick_name());
                    }else{
                        Log.e("letianpai_123456", "info is null ");
                    }
                }
            }
        });
    }

    private void getFansInfos() {
        GeeUiNetManager.getFansInfoList(mContext, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    FansInfo fansInfo = null;
                    String info = response.body().string();

                    if (info != null) {
                        fansInfo = gson.fromJson(info, FansInfo.class);
                        LogUtils.logi("letianpai_1234567", "fansInfo: " + fansInfo.toString());
                        if (fansInfo != null) {
                            FansInfoCallback.getInstance().setFansInfo(fansInfo);
                            LauncherConfigManager.getInstance(mContext).setRobotFansInfo(fansInfo.toString());
                            LauncherConfigManager.getInstance(mContext).commit();
                        }

                    }
                }
            }
        });
    }

    private void getGeneralInfoList(boolean isChinese) {
        GeeUiNetManager.getGeneralInfoList(mContext, isChinese,new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    Log.e("letianpai_1234", "response: " + response.toString());
                    Log.e("letianpai_1234", "response11: " + response.body().toString());
                    GeneralInfo generalInfo = null;
                    String info = response.body().string();

                    if (info != null) {
                        try{
                            generalInfo = new Gson().fromJson(info, GeneralInfo.class);
                            if (generalInfo != null) {
                                LogUtils.logi("letianpai_1234567", "generalInfo: " + generalInfo.toString());
                                GeneralInfoCallback.getInstance().setGeneralInfo(generalInfo);
                                setGeneralInfo(generalInfo);
                                LauncherConfigManager.getInstance(mContext).commit();
                            } else {
                                Log.e("letianpai_1234", "generalInfo is null: ");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public LogoInfo getLogoInfo() {

            if (SystemUtil.isRobotInChinese()){
                getDeviceChannelLogo(true);
            }else{
                getDeviceChannelLogo(false);
            }

        return logoInfo;
    }


    private void getDeviceChannelLogo(boolean isChinese) {
        GeeUiNetManager.getDeviceChannelLogo(mContext, isChinese,new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {
                    LogoInfo generalInfo = null;
                    String info = response.body().string();

                    if (info != null) {
                        try{
                            generalInfo = new Gson().fromJson(info, LogoInfo.class);
                            if (generalInfo != null) {
                                LogUtils.logi("letianpai_1234567", "getDeviceChannelLogo: " + generalInfo.toString());
                                DeviceChannelLogoCallBack.getInstance().setDeviceChannelLogo(generalInfo);
                                setLogoInfo(generalInfo);
                                LauncherConfigManager.getInstance(mContext).commit();
                            } else {
                                Log.e("letianpai_1234", "getDeviceChannelLogo is null: ");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void getCountDownList() {
        GeeUiNetManager.getCountDownList(mContext, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    LogUtils.logi("letianpai_1234", "getCountDownList_response: " + response.toString());
                    LogUtils.logi("letianpai_1234", "getCountDownList_response11: " + response.body().toString());

                    CountDownListInfo countDownListInfo = null;
                    String info = "";

                    if (response != null && response.body() != null) {
                        info = response.body().string();
                        LogUtils.logi("letianpai", "response: info" + info);
                    }
                    if (info != null) {
                        countDownListInfo = gson.fromJson(info, CountDownListInfo.class);
                        if (countDownListInfo != null) {
                            LogUtils.logi("letianpai_1234567", "countDownListInfo: " + countDownListInfo.toString());
                            CountDownInfoUpdateCallback.getInstance().updateCountDown(countDownListInfo);
                            LauncherConfigManager.getInstance(mContext).setRobotCountDown(countDownListInfo.toString());
                            LauncherConfigManager.getInstance(mContext).commit();
                        }

                    }
                }
            }
        });
    }

    public void setFansInfo(FansInfo fansInfo) {
        this.fansInfo = fansInfo;
    }

    public void setCalenderInfo(CalenderInfo calenderInfo) {
        this.calenderInfo = calenderInfo;
    }

    public void setCountDownListInfo(CountDownListInfo countDownListInfo) {
        this.countDownListInfo = countDownListInfo;
    }

    public void setGeneralInfo(GeneralInfo generalInfo) {
        this.generalInfo = generalInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public CalenderInfo getCalenderInfo() {
        if (calenderInfo == null) {
            getCalendarList();
        }
        return calenderInfo;
    }

    public CountDownListInfo getCountDownListInfo() {
        if (countDownListInfo == null) {
            getCountDownList();
        }
        return countDownListInfo;
    }

    public GeneralInfo getGeneralInfo() {
        if (generalInfo == null) {
//            if (LocaleUtils.isChinese()){
            if (SystemUtil.isRobotInChinese()){
                getGeneralInfoList(true);
            }else{
                getGeneralInfoList(false);
            }

        }
        return generalInfo;
    }

    public WeatherInfo getWeatherInfo() {
        if (weatherInfo == null) {
            getWeather();
        }

        return weatherInfo;
    }

    public FansInfo getFansInfo() {
        if (fansInfo == null) {
            getFansInfos();
        }
        return fansInfo;
    }


}
