package com.renhejia.robot.gesturefactory.manager;

import android.app.ActivityManager;
import android.content.Context;

/**
 * 解析管理器
 * @author liujunbin
 */
public class ParserManager {

    private static ParserManager parserManager;

    private Context mContext;

    public ParserManager(Context context) {
        this.mContext = context;
    }

    public static ParserManager getInstance(Context context) {
        if (parserManager == null) {
            synchronized (ParserManager.class) {
                if (parserManager == null) {
                    parserManager = new ParserManager(context);
                }
            }
        }
        return parserManager;
    }

    private static void getGestureById(int gestureId) {


    }










}
