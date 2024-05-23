package com.renhejia.robot.display.personal;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;


import com.renhejia.robot.display.parser.Middle;
import com.renhejia.robot.display.utils.Consts;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujunbin
 */
public class XMLParsingUtils {

    public static InputStream getSkinCustomInputStreamFromAsset(Context context, String filename) {

        AssetManager mAssetManager = context.getResources().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = mAssetManager.open(filename);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    public static InputStream getSkinCustomInputStreamFromSD(Context context, String filename) {

        InputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filename);
            ;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileInputStream;
    }

    public static List<Middle> getMiddleNameList(InputStream inputStream) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();

            Middle currentMiddle = null;
            List<Middle> middleList = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        middleList = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if (name.equals(Consts.MIDDLE)) {
                            currentMiddle = new Middle();

                        } else if (currentMiddle != null) {
                            if (name.equals(Consts.NAME)) {
                                String name3 = parser.nextText();
                                currentMiddle.setName(name3);
                            } else if (name.equals(Consts.ID)) {
                                String id = parser.nextText();
                                currentMiddle.setId(new Integer(id));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equalsIgnoreCase(Consts.MIDDLE) && currentMiddle != null) {
                            middleList.add(currentMiddle);
                            currentMiddle = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            inputStream.close();

            return middleList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 Middle 名字描述列表
     *
     * @param context
     * @param filename
     * @return
     */
    public static List<Middle> getMiddleDesNameList(Context context, String filename, boolean isFromSdCard) {
        InputStream middleStream = null;
        if (isFromSdCard) {
            middleStream = getSkinCustomInputStreamFromSD(context, filename);
        } else {
            middleStream = getSkinCustomInputStreamFromAsset(context, filename);
        }

        if (middleStream == null) {
            return null;
        }
        return getMiddleNameList(middleStream);

    }


}
