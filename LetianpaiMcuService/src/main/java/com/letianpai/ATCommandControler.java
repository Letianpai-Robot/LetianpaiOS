package com.letianpai;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

import top.keepempty.sph.library.LogUtil;
import top.keepempty.sph.library.SerialPortJNI;

public class ATCommandControler {

    private ArrayList<String> atCommandList = new ArrayList<>();
    private static ATCommandControler instance;
    private Context mContext;
    private boolean isATCommandConsuming = false;


//    private ATCommandControler(Context context) {
//        init(context);
//    }
//
//    public static ATCommandControler getInstance(Context context) {
//        synchronized (ATCommandControler.class) {
//            if (instance == null) {
//                instance = new ATCommandControler(context.getApplicationContext());
//            }
//            return instance;
//        }
//
//    }
    private ATCommandControler() {
        addAtCommandListeners();
    }

    private void addAtCommandListeners() {
        AtCommandCallback.getInstance().setAtCmdResultReturnListener(new AtCommandCallback.AtCmdResultReturnListener() {
            @Override
            public void onAtCmdResultReturn(String atCmdResult) {
                LogUtil.e("AtCommandCallback_onAtCmdResultReturn: "+ atCmdResult);
                consumeATCommands();
            }
        });
    }

    public static ATCommandControler getInstance() {
        synchronized (ATCommandControler.class) {
            if (instance == null) {
                instance = new ATCommandControler();
            }
            return instance;
        }

    }


//    private void init(Context context) {
//        this.mContext = context;
//
//    }

    public void addATCommand(String command) {
        if (!TextUtils.isEmpty(command)) {
            atCommandList.add(command);
            if(!isATCommandConsuming()){
                consumeATCommands();
            }
        }
    }

    public void addATCommands(ArrayList<String> commands) {
        atCommandList.addAll(commands);
    }

    public void consumeATCommands() {
        if (atCommandList.size() > 0) {
            LogUtil.e("atCommandList.size(): "+ atCommandList.size());
            isATCommandConsuming = true;
            String command = atCommandList.remove(0);
            SerialPortJNI.writePort(command.getBytes());
        }else{
            LogUtil.e("atCommandList.size() is 0 ");
            isATCommandConsuming = false;
        }
    }

    public void consumeATCommand(String command) {
        if (!TextUtils.isEmpty(command)) {
            SerialPortJNI.writePort(command.getBytes());
        }

    }

    public boolean isATCommandConsuming() {
        return isATCommandConsuming;
    }
}
