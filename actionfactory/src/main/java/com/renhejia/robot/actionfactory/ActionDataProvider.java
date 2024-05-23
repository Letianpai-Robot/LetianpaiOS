package com.renhejia.robot.actionfactory;

import android.content.Context;

import com.renhejia.robot.actionfactory.consts.FactoryConst;
import com.renhejia.robot.actionfactory.parser.ActionData;

/**
 * Action 动作响应者
 */
public class ActionDataProvider {

    private static ActionDataProvider provider;
    private Context mContext;

    public ActionDataProvider(Context context) {
        this.mContext = context;
    }

    public static ActionDataProvider getInstance(Context context) {
        if (provider == null) {
            synchronized (ActionDataProvider.class) {
                if (provider == null) {
                    provider = new ActionDataProvider(context);
                }
            }
        }
        return provider;
    }


    public ActionData getActionData(String type) {
        if (type.equals(FactoryConst.ACTION_SMILE)) {
            return null;
        } else {
            return new ActionData();
        }

    }


}
