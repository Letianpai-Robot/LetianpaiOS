package com.renhejia.robot.display.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 表盘换肤工具类
 * @author liujunbin
 */
public class SpineSkinUtils {
    private static String FOREGROUND = "foreground.png";
    private static String FOREGROUND_ZH = "foreground_zh.png";

    /**
     * 图片合并
     *
     * @param foregroundBitmap
     * @param backgroundBitmap
     * @return
     */
    public static Bitmap mergeBitmap(Bitmap backgroundBitmap, Bitmap foregroundBitmap) {
        return BitmapUtil.mergeBitmap(backgroundBitmap, foregroundBitmap);
    }

    /**
     * 转化Bitmap to File
     * @param bitmap
     * @param fileName
     * @return
     */
    public static File saveBitmapToFile(Bitmap bitmap, String fileName) {
        return BitmapUtil.saveBitmapToFile(bitmap, fileName);
    }



    /**
     * 创建默认缩略图文件
     *
     * @param context
     * @param skinName
     * @return
     */
    public static File createThumbFile(Context context, String skinName) {
        String foreground = skinName + "/" + FOREGROUND;
        return createThumb(context,skinName,foreground,false);
    }

    /**
     * 创建缩略图
     *
     * @param context
     * @param skinName
     * @return
     */
    public static File createThumb(Context context,String skinName,String foreground,boolean isChinese) {
        String background = getFileNameOfChangeSkinBackground(context, skinName);
            Bitmap foregroundBitmap = BitmapUtil.loadBitmapFromAssetFilename(context, foreground);

            Bitmap backgroundBitmap = BitmapFactory.decodeFile(background);

            backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap,
                    320,
                    320,
//                    LauncherConfigManager.getInstance(context).getWatchWidth(),
//                    LauncherConfigManager.getInstance(context).getWatchHeight(),
                    true);

            Bitmap newBackgroundBitmap = mergeBitmap(backgroundBitmap, foregroundBitmap);
            String fileName = "";
            if (isChinese){
                fileName = getFileNameOfChangeSkinChineseThumb(context, skinName);
            }else{
                fileName = getFileNameOfChangeSkinThumb(context, skinName);
            }

            return saveBitmapToFile(newBackgroundBitmap, fileName);
    }

    /**
     * 创建中文缩略图文件
     *
     * @param context
     * @param skinName
     * @return
     */
    public static File createChineseThumbFile(Context context, String skinName) {
        String foreground = skinName + "/" + FOREGROUND_ZH;
        return createThumb(context,skinName,foreground,true);
    }

    /**
     * 备份图片到目标路径
     * @param context
     * @param oldFile
     * @param spineSkinName
     */
    public static void copyBackground(Context context, String oldFile, String spineSkinName) {
        String newFile = getFileNameOfChangeSkinBackground(context,spineSkinName);
        FileUtil.copyFile(oldFile,newFile);
        
    }
    /**
     *
     * @param context
     * @param oldFile
     * @param spineSkinName
     */
    public static void createBackground(Context context, String oldFile, String spineSkinName) {
        String newFile = getFileNameOfChangeSkinBackground(context,spineSkinName);
        File file = new File(newFile);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        Bitmap backgroundBitmap = BitmapFactory.decodeFile(oldFile);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap,
                320,
                320,
//                LauncherConfigManager.getInstance(context).getWatchWidth(),
//                LauncherConfigManager.getInstance(context).getWatchHeight(),
                true);
        saveBitmapToFile(backgroundBitmap, newFile);

    }

    /**
     * 获取图片存储路径
     * @param context
     * @param spineSkinName
     * @return
     */
    public static String getFileNameOfChangeSkinBackground(Context context,String spineSkinName) {
        String spinePath = "/data/data/" + context.getPackageName() + "/spine_skin/";
        String backgroundImage = spinePath + spineSkinName + "_background.png";

        return backgroundImage;
    }
    /**
     * 获取图片存储路径
     * @param context
     * @param spineSkinName
     * @return
     */
    public static String getFileNameOfChangeSkinThumb(Context context,String spineSkinName) {
        String spinePath = "/data/data/" + context.getPackageName() + "/spine_skin/";
        String backgroundImage = spinePath + spineSkinName + "_thumb.png";
        return backgroundImage;
    }

    /**
     * 获取图片存储路径
     * @param context
     * @param spineSkinName
     * @return
     */
    public static String getFileNameOfChangeSkinChineseThumb(Context context,String spineSkinName) {
        String spinePath = "/data/data/" + context.getPackageName() + "/spine_skin/";
        String backgroundImage = spinePath + spineSkinName + "_thumb_zh.png";
        return backgroundImage;
    }

    /**
     * 是否换肤缩略图存在
     * @param context
     * @param skinName
     * @return
     */
    public static boolean isChangeSkinThumbExit(Context context, String skinName) {
        String filePath = getFileNameOfChangeSkinThumb(context,skinName);
        File file = new File(filePath);
        if (file.exists()){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 是否换肤缩略图存在
     * @param context
     * @param skinName
     * @return
     */
    public static boolean isChangeSkinBackgroundExit(Context context, String skinName) {
        String filePath = getFileNameOfChangeSkinBackground(context,skinName);
        File file = new File(filePath);
        if (file.exists()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取文件名
     *
     * @param downloadUrl
     * @return
     */
    public static String getFileName(String downloadUrl) {
        return FileUtil.getFileName(downloadUrl);

    }

    public static String getDefaultSkin(Context context) {
        Gson gson = new Gson();
        String [] mSkinList;
        String JSON_FILE_NAME = "skin_list.json";
        String jsonString = getJson(JSON_FILE_NAME, context);
        mSkinList = gson.fromJson(jsonString, String[].class);
        if (mSkinList.length > 0) {
            return mSkinList[0];
        }

        return null;
    }


    private static String getJson(String fileName, Context context) {
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




}


