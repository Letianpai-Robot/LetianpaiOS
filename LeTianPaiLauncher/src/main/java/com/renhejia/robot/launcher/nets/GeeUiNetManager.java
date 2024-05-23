package com.renhejia.robot.launcher.nets;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;
import com.renhejia.robot.launcher.encryption.EncryptionUtils;
import com.renhejia.robot.launcher.mode.SleepModeManager;
import com.renhejia.robot.launcher.system.SystemFunctionUtil;
import com.renhejia.robot.launcherbaselib.battery.ChargingUpdateCallback;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 */
public class GeeUiNetManager {

    /**
     * 获取通用信息列表
     *
     * @param context
     * @param callback
     */
    public static void getDeviceBindInfo(Context context, boolean isChinese, Callback callback) {
        get(context, isChinese, GeeUINetworkConsts.BIND_INFO, callback);
    }

    /**
     * 获取日历列表
     *
     * @param context
     * @param callback
     */
    public static void getCalendarList(Context context, Callback callback) {
        GeeUINetworkUtil.get1(context, GeeUINetworkConsts.CALENDAR_LIST, callback);
    }

    /**
     * 获取倒计时列表
     *
     * @param context
     * @param callback
     */
    public static void getCountDownList(Context context, Callback callback) {
        GeeUINetworkUtil.get1(context, GeeUINetworkConsts.COUNTDOWN_LIST, callback);
    }

    /**
     * 获取粉丝信息
     *
     * @param context
     * @param callback
     */
    public static void getFansInfoList(Context context, Callback callback) {
        GeeUINetworkUtil.get1(context, GeeUINetworkConsts.FANS_INFO_LIST, callback);
    }

    /**
     * 获取通用信息列表
     *
     * @param context
     * @param callback
     */
    public static void getGeneralInfoList(Context context, boolean isChinese, Callback callback) {
        get(context, isChinese, GeeUINetworkConsts.GENERAL_INFO, callback);
    }

    /**
     * 获取天气信息
     *
     * @param context
     * @param callback
     */
    public static void getWeatherInfo(Context context, Callback callback) {
        GeeUINetworkUtil.get1(context, GeeUINetworkConsts.WEATHER_INFO, callback);
    }

    /**
     * 获取闹钟列表
     *
     * @param context
     * @param callback
     */
    public static void getClockList(Context context, Callback callback) {
        GeeUINetworkUtil.get1(context, GeeUINetworkConsts.CLOCK_LIST, callback);
    }

    /**
     * 获取唤醒词列表
     *
     * @param context
     * @param callback
     */
    public static void getTipsList(Context context, Callback callback) {
        GeeUINetworkUtil.get1(context, GeeUINetworkConsts.GET_COMMON_CONFIG, callback);
    }

    /**
     * 获取机器人全部配置
     *
     * @param context
     * @param callback
     */
    public static void getAllConfig(Context context, Callback callback) {
        GeeUINetworkUtil.get1(context, GeeUINetworkConsts.GET_ALL_CONFIG, callback);
    }

    /**
     * 获取股票信息
     *
     * @param context
     * @param callback
     */
    public static void getDeviceInfo(Context context, Callback callback) {
        String ts = EncryptionUtils.getTs();
        String auth = EncryptionUtils.getHardCodeSign(ts);
        GeeUINetworkUtil.get11(context, auth, ts, GeeUINetworkConsts.GET_SN_BY_MAC, callback);

    }

    /**
     * 获取设置渠道的LOGO
     * 
     * @param context
     * @param callback
     */
    public static void getDeviceChannelLogo(Context context, boolean isChinese, Callback callback) {
        get(context, isChinese, GeeUINetworkConsts.GET_DEVICE_CHANNELLOGO, callback);
    }

    // /**
    // * 更新机器人状态
    // * @param context
    // * @param callback
    // */
    // public static void uploadStatus(Context context, Callback callback) {
    // GeeUINetworkUtil.uploadStatus(context,callback);
    // }

    /**
     * @param context
     * @param uri
     * @param callback
     */
    public static void get(Context context, boolean isChinese, String uri, Callback callback) {
        if (!WIFIConnectionManager.getInstance(context).isNetworkAvailable(context)) {
            Log.e("apprequest ", "网络未连接");
            return;
        }
        String ts = EncryptionUtils.getTs();
        String sn = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            sn = Build.getSerial();
        }

        String hardCode = SystemUtil.getHardCode();
        String auth = EncryptionUtils.getRobotSign(sn, hardCode, ts);

        get(context, isChinese, auth, sn, ts, uri, callback);
    }

    private static final String AUTHORIZATION = "Authorization";
    private static final String COUNTRY = "country";
    private static final String CN = "cn";
    private static final String GLOBAL = "global";

    public static void get(Context context, boolean isChinese, String auth, String sn, String ts, String uri,
            Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder httpBuilder;

        if (isChinese) {
            httpBuilder = HttpUrl.parse("https://yourservice.com" + uri).newBuilder();
        } else {
            httpBuilder = HttpUrl.parse("https://yourservice.com" + uri).newBuilder();
        }

        // httpBuilder.addQueryParameter("sn", "2001013760426");
        httpBuilder.addQueryParameter("sn", sn);

        httpBuilder.addQueryParameter("ts", ts);

        Log.e("apprequest", "" + httpBuilder.toString());
        String country = CN;
        if (!isChinese) {
            country = GLOBAL;
        }
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader(AUTHORIZATION, auth)
                .addHeader(COUNTRY, country)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * @param context
     * @param callback
     */
    public static void getTimeStamp(Context context, Callback callback) {
        GeeUINetworkUtil.get(context, GeeUINetworkConsts.GET_SERVER_TIME_STAMP, callback);
    }

}
