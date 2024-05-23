package com.renhejia.robot.launcher.nets;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.launcher.encryption.EncryptionUtils;
import com.renhejia.robot.launcher.net.GeeUINetConsts;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author liujunbin
 */
public class GeeUINetworkUtil {

    private static GeeUINetworkUtil instance;
    private Context mContext;
    private Gson gson;
    private static final String AUTHORIZATION = "Authorization";

    private GeeUINetworkUtil(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
    }

    public static GeeUINetworkUtil getInstance(Context context) {
        synchronized (GeeUINetworkUtil.class) {
            if (instance == null) {
                instance = new GeeUINetworkUtil(context.getApplicationContext());
            }
            return instance;
        }

    }

    public static void get(String uri, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url("https://yourservice.com" + uri).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void get1(Context context, String uri, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://yourservice.com" + uri).newBuilder();
        String sn = SystemUtil.getLtpSn();
        httpBuilder.addQueryParameter("sn", sn);

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void get(Context context, String uri, HashMap hashMap, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://yourservice.com" + uri).newBuilder();
        String sn = SystemUtil.getLtpSn();

        //
        //
        // for(HashMap.Entry<String,String> entry : hashMap.entrySet()){
        //
        // }
        //

        httpBuilder.addQueryParameter("sn", sn);

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    // 创建一个 HashMap 对象
    HashMap<String, Integer> map = new HashMap<>();

    // 添加一些键值对
    // map.put("key1", 1);
    // map.put("key2", 2);
    // map.put("key3", 3);
    //
    //// 循环遍历 HashMap
    // for (Map.Entry<String, Integer> entry : map.entrySet()) {
    // // 获取键和值
    // String key = entry.getKey();
    // int value = entry.getValue();
    //
    // // 输出键值对
    // System.out.println("Key: " + key + ", Value: " + value);
    // }

    public static void get(Context context, String uri, Callback callback) {
        String sn = SystemUtil.getLtpSn();
        String authorization = "";
        get1(context, sn, authorization, uri, callback);
    }

    public static void get1(Context context, String sn, String key, String uri, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://yourservice.com" + uri).newBuilder();

        httpBuilder.addQueryParameter("sn", sn);
        httpBuilder.addQueryParameter("sn", sn);

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void post(String uri, HashMap hashMap, Callback callback) {
        ArrayList<Object> list = new ArrayList<>();
        OkHttpClient client = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            client = new OkHttpClient.Builder().connectTimeout(Duration.ofMinutes(10l))
                    .readTimeout(Duration.ofMinutes(10l)).callTimeout(Duration.ofMinutes(10l)).build();
        }
        String url = "https://yourservice.com" + uri;
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        list.add(hashMap);
        RequestBody body = RequestBody.create(new Gson().toJson(hashMap), mediaType);
        Request request = new Request.Builder().url(url).method("POST", body)
                .addHeader("Content-Type", "application/json").build();
        client.newCall(request).enqueue(callback);

    }

    public static void get11(Context context, String auth, String sn, String ts, String uri, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://yourservice.com" + uri).newBuilder();

        httpBuilder.addQueryParameter("sn", sn);
        httpBuilder.addQueryParameter("ts", ts);

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader(AUTHORIZATION, auth)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void get11(Context context, String auth, String ts, String uri, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://yourservice.com" + uri).newBuilder();
        String mac = EncryptionUtils.getRobotMac();

        httpBuilder.addQueryParameter("mac", mac);
        httpBuilder.addQueryParameter("ts", ts);

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader(AUTHORIZATION, auth)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

}
