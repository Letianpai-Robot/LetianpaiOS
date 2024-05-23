package com.renhejia.robot.gesturefactory.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import com.renhejia.robot.commandlib.log.LogUtils;

import com.google.gson.Gson;
import com.renhejia.robot.gesturefactory.parser.gesture.Gesture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 姿态解析器
 * @author liujunbin
 */
public class GestureResPool {
    private AssetManager mAssetManager;
    private String mPathName = "";
    private Gson gson = new Gson();
    private Context mContext;
    private Gesture gestureData;

    private String getJson(String fileName, Context context) {

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

    public static String getSkinListJson(String fileName, Context context) {
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


    public Gesture createGesture(String pathName) {
        setSkinPath(pathName);
        String jsonString = getJson("gesture.json", mContext);
        gestureData = gson.fromJson(jsonString, Gesture.class);
        return gestureData;
    }

    public static  String [] getGestureList (Context context) {
        String [] mSkinList;
        String JSON_FILE_NAME = "gesture/gesture_list.json";
        String jsonString = getSkinListJson(JSON_FILE_NAME, context);
        mSkinList = new Gson().fromJson(jsonString, String[].class);
        return mSkinList;
    }

    public boolean isValidSpineSkin(String spineSkin) {
        String[] mSkinList;
        String JSON_FILE_NAME = "skin_list.json";
        String jsonString = getSkinListJson(JSON_FILE_NAME, mContext);
        mSkinList = gson.fromJson(jsonString, String[].class);
        for (int i = 0; i < mSkinList.length; i++) {
            if (mSkinList[i].equals(spineSkin)) {
                return true;
            }
        }
        return false;
    }

    public GestureResPool(Context context) {
        mContext = context;
        mAssetManager = context.getResources().getAssets();
    }

    public void setSkinPath(String pathName) {
        mPathName = pathName;
    }

    public String getSkinPath() {
        return mPathName;
    }




}
