package com.renhejia.robot.display;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class RobotSkinFileMap {
    private static String CHINESE_LANGUAGE = "c";
    public final static Map mAssnMap = new HashMap() {{
        put(":", "colon");
        put("°", "degree");
        put("#", "month");
        put("&", "day");
        put("-", "link");
        put("!", "space");
        put("上午", "AM");
        put("下午", "PM");

    }};
    public final static Map mAssnMap0 = new HashMap() {{
        put(":", "colon_2");
        put("°", "degree");
        put("#", "month");
        put("&", "day");
        put("-", "link");
        put("!", "space");
        put("上午", "AM");
        put("下午", "PM");

    }};
    public final static Map mAssnMap1 = new HashMap() {{
        put(":", "colon_4");
        put("°", "degree");
        put("#", "month");
        put("&", "day");
        put("-", "link");
        put("!", "space");
        put("上午", "AM");
        put("下午", "PM");

    }};
    public final static Map mAssnMap2 = new HashMap() {{
        put(":", "colon_6");
        put("°", "degree");
        put("#", "month");
        put("&", "day");
        put("-", "link");
        put("!", "space");
        put("上午", "AM");
        put("下午", "PM");

    }};
    public final static Map mAssnMap3 = new HashMap() {{
        put(":", "colon_8");
        put("°", "degree");
        put("#", "month");
        put("&", "day");
        put("-", "link");
        put("!", "space");
        put("上午", "AM");
        put("下午", "PM");

    }};
    public final static Map mAssnMap4 = new HashMap() {{
        put(":", "colon_10");
        put("°", "degree");
        put("#", "month");
        put("&", "day");
        put("-", "link");
        put("!", "space");
        put("上午", "AM");
        put("下午", "PM");

    }};

//    public static String getFilePostfix(String key) {
//
//        if (mAssnMap.containsKey(key)) {
//            return (String) mAssnMap.get(key);
//        }
//
//        return key;
//    }

    public static String getFilePostfix(String key) {

        if (mAssnMap.containsKey(key)) {
            return (String) mAssnMap.get(key);
        }

        return key;
    }
    public static String getFilePostfixWith(String key,int time) {
        if (time > 0 && time < 200){
            if (mAssnMap0.containsKey(key)) {
                return (String) mAssnMap0.get(key);
            }

        }else if (time >=200 && time <400){
            if (mAssnMap1.containsKey(key)) {
                return (String) mAssnMap1.get(key);
            }

        }else if (time >=400 && time <600){
            if (mAssnMap2.containsKey(key)) {
                return (String) mAssnMap2.get(key);
            }

        }else if (time >= 600 && time <800){
            if (mAssnMap3.containsKey(key)) {
                return (String) mAssnMap3.get(key);
            }
        }else if (time >= 800 && time <1000){
            if (mAssnMap4.containsKey(key)) {
                return (String) mAssnMap4.get(key);
            }
        }

        return key;
    }

    public static String getFilePostfix(String key, String language) {
        if (mAssnMap.containsKey(key)) {
            if ((!TextUtils.isEmpty(language)) && (language.equals(CHINESE_LANGUAGE))) {
                return key;
            } else {
                return (String) mAssnMap.get(key);
            }

        }

        return key;
    }
}

