package com.renhejia.robot.display.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmap 工具类
 *
 * @author liujunbin
 */
public class BitmapUtil {

    public byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


    public static Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {

        int width = drawable.getIntrinsicWidth();

        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height,

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, width, height);

        drawable.draw(canvas);

        return bitmap;

    }

    /**
     * 照片转byte二进制
     *
     * @param imagepath 需要转byte的照片路径
     * @return 已经转成的byte
     * @throws Exception
     */
    public static byte[] convertImageToByte(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    /**
     * 合并图片
     *
     * @param firstBitmap
     * @param secondBitmap
     * @return
     */
    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
//        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),firstBitmap.getConfig());
        Bitmap bitmap = Bitmap.createBitmap(secondBitmap.getWidth(), secondBitmap.getHeight(),secondBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        return bitmap;
    }

    /**
     * 合并图片
     *
     * @param firstBitmap
     * @param secondBitmap
     * @return
     */
    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap,int left,int top) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, left, top, null);
        return bitmap;
    }

    /**
     * 将bitmap 转为 File
     *
     * @param bitmap
     * @return
     */
    public static File saveBitmapToFile(Bitmap bitmap, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
//        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        File file = new File(fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 加载asset文件夹中的bitmap
     * @param context
     * @param filename
     * @return
     */
    public static Bitmap loadBitmapFromAssetFilename(Context context, String filename) {
        InputStream inputStream = null;
        Bitmap bitmap = null;

        try {
            inputStream = context.getResources().getAssets().open(filename);
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return bitmap;
    }

    /**
     * 加载asset文件夹中的bitmap
     * @param filename
     * @return
     */
    public static Bitmap convertImageToBitmap(String filename) {
        Bitmap bitmap = null;

        try {
            FileInputStream fis = new FileInputStream(filename);
            bitmap = BitmapFactory.decodeStream(fis);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return bitmap;
    }





}
