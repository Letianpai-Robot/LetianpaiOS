package com.renhejia.robot.launcher.encryption;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.renhejia.robot.guidelib.utils.SystemUtil;

/**
 * @author liujunbin
 */
public class EncryptionUtils {

    private Gson mGson;

    private static EncryptionUtils instance;
    private Context mContext;
    private static final String partSecretKey = "your partSecretKey";

    private EncryptionUtils(Context context) {
        this.mContext = context;
        init();
    }

    public static EncryptionUtils getInstance(Context context) {
        synchronized (EncryptionUtils.class) {
            if (instance == null) {
                instance = new EncryptionUtils(context.getApplicationContext());
            }
            return instance;
        }

    }

    private void init() {
        mGson = new Gson();

    }

    private void getHardcode() {

    }

    /**
     * 获取签名的方式
     * 
     * @param inputValue
     * @param ts
     * @return
     */
    private static String getDeviceSign(String inputValue, String ts) {
        String deviceSecretKey = MD5.encode(inputValue + ts + partSecretKey);
        Log.e("letianpai_encode", "deviceSecretKey: " + deviceSecretKey);
        String macSign = Sha256Utils.getSha256Str(inputValue + ts + deviceSecretKey);
        Log.e("letianpai_encode", "macSign: " + macSign);
        return macSign;

    }

    public static String getRobotSign() {
        String mac = getRobotMac();
        Log.e("letianpai_encode", "mac: " + mac);
        String ts = getTs();
        Log.e("letianpai_encode", "ts: " + ts);
        String robotSign = getDeviceSign(mac, ts);
        Log.e("letianpai_encode", "robotSign: " + robotSign);
        robotSign = "Bearer " + robotSign;
        Log.e("letianpai_encode", "robotSign1: " + robotSign);
        return robotSign;

    }

    public static String getHardCodeSign(String ts) {
        String mac = getRobotMac();
        Log.e("letianpai_encode", "mac: " + mac);
        Log.e("letianpai_encode", "ts: " + ts);
        String robotSign = getDeviceSign(mac, ts);
        Log.e("letianpai_encode", "robotSign: " + robotSign);
        robotSign = "Bearer " + robotSign;
        Log.e("letianpai_encode", "robotSign1: " + robotSign);
        return robotSign;

    }

    /**
     * 获取机器人签名
     * 
     * @param sn
     * @param hardcode
     * @param ts
     * @return
     */
    public static String getRobotSign(String sn, String hardcode, String ts) {
        Log.e("letianpai_encode", "ts: " + ts);
        String robotSign = getDeviceSign(sn + hardcode, ts);
        Log.e("letianpai_encode", "getRobotSign:======= " + robotSign);
        robotSign = "Bearer " + robotSign;
        Log.e("letianpai_encode", "getRobotSign1: ========== " + robotSign);
        return robotSign;

    }

    public static String getDeviceSign(String sn, String hardcode, String ts) {
        Log.e("letianpai_encode", "ts: " + ts);
        String robotSign = getDeviceSign(sn + hardcode, ts);
        Log.e("letianpai_encode", "robotSign: " + robotSign);
        return robotSign;

    }

    public static String getRobotMac() {
        return (SystemUtil.getWlanMacAddress()).toLowerCase();
    }

    public static String getTs() {
        return (long) (System.currentTimeMillis() / 1000) + "";
    }

}
