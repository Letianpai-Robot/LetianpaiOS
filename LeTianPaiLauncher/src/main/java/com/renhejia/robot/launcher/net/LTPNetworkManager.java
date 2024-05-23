package com.renhejia.robot.launcher.net;

import android.content.Context;

import com.google.gson.Gson;

public class LTPNetworkManager {

    private static LTPNetworkManager instance;
    private Context mContext;
    private Gson gson;

    private LTPNetworkManager(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
    }

    public static LTPNetworkManager getInstance(Context context) {
        synchronized (LTPNetworkManager.class) {
            if (instance == null) {
                instance = new LTPNetworkManager(context.getApplicationContext());
            }
            return instance;
        }

    }









}
