package com.renhejia.robot.launcherbaselib.utils;

import android.content.Context;

import java.io.InputStream;

public class AssetsUtil {
    public static byte[] getFile(Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int length = in.available();
            // 创建byte数组
            byte[] buffer = new byte[length];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            in.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
