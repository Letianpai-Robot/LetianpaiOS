package com.renhejia.robot.display;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import com.renhejia.robot.commandlib.log.LogUtils;

import com.google.gson.Gson;

import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.display.manager.RobotSkinJsonConversionTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;

public class SpineSkinResPool {
    private AssetManager mAssetManager;
    private String mPathName = "";
    private Gson gson = new Gson();
    private Context mContext;
    private RobotClockSkin skin;


    private String getJson(String fileName, Context context) {

        if (mPathName.length() > 0 && mPathName.startsWith("/")) {
            String jsonStr = null;
            try {
                File yourFile = new File(mPathName + "/" + fileName);
                FileInputStream stream = new FileInputStream(yourFile);

                try {
                    FileChannel fc = stream.getChannel();
                    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                    jsonStr = Charset.defaultCharset().decode(bb).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
            return jsonStr;
        }
        else {
            StringBuilder stringBuilder = new StringBuilder();
            String fullname = fileName;
            if (mPathName.length() > 0) {
                fullname = mPathName + "/" + fileName;
            }

            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open(fullname)));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
    }

    private static String getSkinListJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        String fullname = fileName;

        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fullname)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public RobotClockSkin createSkin(String pathName) {

        reset();

        setSkinPath(pathName);

        String jsonString = getJson("skin.json", mContext);
//        SpineClockSkin skin = gson.fromJson(jsonString, SpineClockSkin.class);
        skin = RobotSkinJsonConversionTools.getSpineClockSkin(jsonString);

        skin.setResPool(this);

        return skin;
    }
    public static  String [] getSkinList (Context context) {
        String [] mSkinList;
        String JSON_FILE_NAME = "skin_list.json";
        String jsonString = getSkinListJson(JSON_FILE_NAME, context);
        mSkinList = new Gson().fromJson(jsonString, String[].class);

        return mSkinList;
    }

    public static  String [] getDisplayList (Context context) {
        String [] mSkinList;
        String JSON_FILE_NAME = "display/display_list.json";
        String jsonString = getSkinListJson(JSON_FILE_NAME, context);
        mSkinList = new Gson().fromJson(jsonString, String[].class);
        return mSkinList;
    }

    public boolean isValidSpineSkin(String spineSkin) {
        LogUtils.logi("Clock_Skin","spineSkin--1: "+ spineSkin);
        String [] mSkinList;
        String JSON_FILE_NAME = "skin_list.json";
        String jsonString = getSkinListJson(JSON_FILE_NAME, mContext);
        mSkinList = gson.fromJson(jsonString, String[].class);
        for (int i = 0; i< mSkinList.length;i++){
            if (mSkinList[i].equals(spineSkin)){
                return true;
            }
        }

//        List<MineClockSkinItem> installedSkinList = PersonalDbOperator.getInstance(mContext).getMineClockSkinList();
//        if (installedSkinList == null ){
//            LogUtils.logi("Clock_Skin","installedSkinList is null !");
//            return false;
//        }
//        if (installedSkinList.size() == 0){
//            LogUtils.logi("Clock_Skin","installedSkinList.size: == 0 ");
//            return false;
//        }
//        for (int j = 0; j< installedSkinList.size();j++){
//            if (installedSkinList.get(j) != null && installedSkinList.get(j).getSkinPath() != null){
//                LogUtils.logi("Clock_Skin","mPathName00--2: installedSkinList.get(j).getSkinPath()"+ installedSkinList.get(j).getSkinPath());
//                if (installedSkinList.get(j).getSkinPath().equals(spineSkin)){
//                    return true;
//                }
//            }
//        }
        return false;
    }
    public boolean isValidCustomSkin(String spineSkin) {
//        LogUtils.logi("Clock_Skin","spineSkin--1: "+ spineSkin);
//        String JSON_FILE_NAME = "skin_list.json";
//        List<MineClockSkinItem> installedSkinList = PersonalDbOperator.getInstance(mContext).getMineClockSkinList();
//        if (installedSkinList == null ){
//            LogUtils.logi("Clock_Skin","installedSkinList is null !");
//            return false;
//        }
//        if (installedSkinList.size() == 0){
//            LogUtils.logi("Clock_Skin","installedSkinList.size: == 0 ");
//            return false;
//        }
//        for (int j = 0; j< installedSkinList.size();j++){
//            if (installedSkinList.get(j) != null && installedSkinList.get(j).getSkinPath() != null){
//                LogUtils.logi("Clock_Skin","mPathName00--2: installedSkinList.get(j).getSkinPath()"+ installedSkinList.get(j).getSkinPath());
//                if (installedSkinList.get(j).getSkinPath().equals(spineSkin)){
//                    return true;
//                }
//            }
//        }
        return false;
    }

    public SpineSkinResPool(Context context) {
        mContext = context;
        mAssetManager = context.getResources().getAssets();
    }

    public void setSkinPath(String pathName) {
        mPathName = pathName;
    }
    public String getSkinPath() {
        return mPathName;
    }


    public void reset() {
        mapBitmap.clear();
    }

    HashMap<String, Bitmap> mapBitmap = new HashMap<String, Bitmap>();

    public Bitmap getBitmap(String fileName) {
        return getBitmap(fileName, 1.0f, 1.0f);
    }

    public Bitmap getBitmap(String fileName, float xRadio, float yRadio) {
        String key = xRadio + "-" + yRadio + "-" + fileName;

        if (mapBitmap.containsKey(key)) {
            return mapBitmap.get(key);
        }
        else {
            Bitmap bitmap = null;
            Bitmap orig_bitmap = null;

            if (mPathName.length() > 0 && mPathName.startsWith("/")) {
                String fullName = mPathName + "/" + fileName;
                orig_bitmap = BitmapFactory.decodeFile(fullName);
            }
            else {
                if (mPathName.length() > 0) {
                    String fullName = mPathName + "/" + fileName;
                    orig_bitmap = loadBitmapFromAssetFilename(fullName);
                }
                else {
                    orig_bitmap = loadBitmapFromAssetFilename(fileName);
                }
            }

            if (orig_bitmap != null) {

                Matrix matrix = new Matrix();
                matrix.postScale(xRadio,yRadio);
                bitmap = Bitmap.createBitmap(orig_bitmap,0,0,orig_bitmap.getWidth(),orig_bitmap.getHeight(),matrix,true);

                mapBitmap.put(key, bitmap);
            }
            return bitmap;
        }
    }


    private Bitmap loadBitmapFromAssetFilename(String filename) {
        InputStream inputStream = null;
        Bitmap bitmap = null;

        try {
            inputStream = mAssetManager.open(filename);
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return bitmap;
    }

    public void fillMapBitmap(String key,Bitmap bitmap){
        String key1 = 1.0f + "-" + 1.0f + "-" + key;
        mapBitmap.put(key1,bitmap);

    }
}
