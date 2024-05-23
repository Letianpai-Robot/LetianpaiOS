package com.renhejia.robot.guidelib.wifi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.guidelib.ble.callback.BleConnectStatusCallback;
import com.renhejia.robot.guidelib.wifi.callback.GuideWifiConnectCallback;

import java.util.List;

/**
 * @author liujunbin
 */
public class WIFIAutoConnectionService extends Service {


    private static final String TAG = "www_wifi";
    private static final String KEY_SSID = "KEY_SSID";
    private static final String KEY_PWD = "KEY_PWD";
    public static final int SCAN_RESULT = 0x03;
    public static final int START_CONNECT = 0x04;

    /**
     * wifi名
     */
    private String mSsid = "";
    /**
     * 密码
     */
    private String mPwd = "";


    /**
     * 负责不断尝试连接指定wifi
     */
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_RESULT: {
                    if (mWifiManager == null) {
                        break;
                    }
                    List<ScanResult> scanResults = mWifiManager.getScanResults();
                    boolean isSSIDAvailable = false;
                    for (ScanResult scanResult : scanResults) {
                        LogUtils.logd("WIFIConnectionManager", "发现的 ssid 有: " + scanResult.SSID);
                        if (scanResult.SSID.equals(mSsid)) {
                            // 找到了指定的 SSID
                            isSSIDAvailable = true;
                            break;
                        }
                    }
                    // WIFIConnectionManager.getInstance(WIFIAutoConnectionService.this).connect(mSsid, mPwd);

                    if (isSSIDAvailable) {
                        //没有扫描到指定的wifi ssid 连接错误
                        LogUtils.logd("WIFIConnectionManager", "found ssid: " + mSsid);
                        WIFIConnectionManager.getInstance(WIFIAutoConnectionService.this).connect(mSsid, mPwd);
                    } else {
                        LogUtils.logd("WIFIConnectionManager", "not found ssid: " + mSsid);
                        //如果没有扫描到WiFi，都一直扫描
                        // mHandler.sendEmptyMessageAtTime(SCAN_RESULT, 3000);
                        //如果这里执行下面的代码，在没有扫描到的时候，就会提示配网失败。
                        BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED);
                    }
                    break;
                }
                case START_CONNECT: {
                    LogUtils.logi("auto_connect", "onClick_4_mSsid: " + mSsid);
                    LogUtils.logi("auto_connect", "onClick_4_mPwd: " + mPwd);
                    if (TextUtils.isEmpty(mSsid)) {
                        return false;
                    }
                    WIFIConnectionManager.getInstance(WIFIAutoConnectionService.this).connect(mSsid, mPwd);

                    boolean connected = (WIFIConnectionManager.getInstance(WIFIAutoConnectionService.this).isConnected(mSsid)) && (WIFIConnectionManager.getInstance(WIFIAutoConnectionService.this).isNetworkAvailable(WIFIAutoConnectionService.this));
                    if (connected) {
                        GuideWifiConnectCallback.getInstance().setNetworkStatus(GuideWifiConnectCallback.NETWORK_TYPE_WIFI, true);
                        BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_SUCCESS);
                        mHandler.removeCallbacksAndMessages(null);
                        stopSelf();
                    }
                    if (!connected && (!WIFIConnectionManager.getInstance(WIFIAutoConnectionService.this).isSetIncorrectPassword())) {
                        mHandler.sendEmptyMessageDelayed(0, 5000);//5s循环
                    }
                }
            }

            return true;
        }
    });
    private WifiManager mWifiManager;

    /**
     * 连接指定wifi热点, 失败后5s循环
     *
     * @param context 用于启动服务的上下文
     * @param ssid    默认HUD-WIFI
     * @param pwd     (WPA加密)默认12345678
     */
    public static void start(Context context, String ssid, String pwd) {
        LogUtils.logi("auto_connect", "onClick_2");
        LogUtils.logi("auto_connect", "ssid: " + ssid);
        LogUtils.logi("auto_connect", "pwd: " + pwd);
        Intent starter = new Intent(context, WIFIAutoConnectionService.class);
        starter.putExtra(KEY_SSID, ssid).putExtra(KEY_PWD, pwd);
        context.startService(starter);
    }

    public static void stop(Context context) {
        Intent starter = new Intent(context, WIFIAutoConnectionService.class);
        context.stopService(starter);
        Log.d(TAG, "stop: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * @return always null
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.logi("auto_connect", "onClick_3");
        mSsid = intent.getStringExtra(KEY_SSID);
        mPwd = intent.getStringExtra(KEY_PWD);
        handleConnect();
        return START_NOT_STICKY;
    }

    private void handleConnect() {
        if (TextUtils.isEmpty(mSsid)) {
            LogUtils.logd("WIFIAutoConnectionService", "handleConnect: wifi 名为空，不连接");
            BleConnectStatusCallback.getInstance().setBleConnectStatus(BleConnectStatusCallback.BLE_STATUS_CONNECTED_NET_FAILED);
            return;
        }
        mWifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
        mWifiManager.startScan();
        mHandler.sendEmptyMessageAtTime(SCAN_RESULT, 4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    public String getSsid() {
        return mSsid;
    }

    public String getPassword() {
        return mPwd;
    }

}
