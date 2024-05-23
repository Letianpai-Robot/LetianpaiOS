package com.letianpai.robot.audioservice.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.SoundPool;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.letianpai.robot.letianpaiservice.LtpAudioEffectCallback;
import com.renhejia.robot.commandlib.log.LogUtils;

import com.google.gson.Gson;
import com.letianpai.robot.audioservice.player.LetianpaiPlayer;
import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.commandlib.consts.SoundEffect;
import com.renhejia.robot.commandlib.parser.face.Face;
import com.renhejia.robot.commandlib.parser.sound.Sound;
import com.renhejia.robot.letianpaiservice.ILetianpaiService;

/**
 * @author liujunbin
 */
public class LTPAudioService extends Service {

    private LetianpaiPlayer mPlayer;
    private static final String TAG = "letianpai";
    private boolean isConnectService = false;
    private ILetianpaiService iLetianpaiService;
    private Gson mGson;
    public final static String COMMAND_TYPE_SOUND = "controlSound";
    public final static String COMMAND_OPEN_AE = "ltp123";
    public final static String COMMAND_CLOSE_AE = "ltp456";
    public boolean isCommandWork = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mGson = new Gson();
        initPlayer();
        addListeners();
        connectService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        createNotificationChannel();
        return super.onStartCommand(intent, flags, startId);
    }

    private void addListeners() {
        mPlayer.setVoicePlayerCallback(new LetianpaiPlayer.VoicePlayerCallback() {
            @Override
            public void onPlayCompletion(int laveLoop) {
                LogUtils.logi("letianpai", "Audio_onPlayCompletion ========== ");
            }

            @Override
            public void onPlayError() {

            }
        });
    }

    private void initPlayer() {
        mPlayer = new LetianpaiPlayer(LTPAudioService.this);
    }

    public void pause() {
        mPlayer.pause();
    }

    public void play(int res, String name) {
        mPlayer.setLoop(1);
        mPlayer.play(res);
        if (name.equals(SoundEffect.COMMAND_VALUE_SOUND_CLOCK)) {
            mPlayer.setLoop(26);
        }
        mPlayer.setVoicePlayerCallback(new LetianpaiPlayer.VoicePlayerCallback() {
            @Override
            public void onPlayCompletion(int laveLoop) {
                if (name.equals(MCUCommandConsts.COMMAND_VALUE_SOUND_BIRTHDAY)) {
                    try {
//                        iLetianpaiService.setCommand(new LtpCommand(MCUCommandConsts.COMMAND_TYPE_FACE, (new Face(MCUCommandConsts.COMMAND_VALUE_FACE_HAPPY)).toString()));
                        iLetianpaiService.setExpression(MCUCommandConsts.COMMAND_TYPE_FACE, new Face(MCUCommandConsts.COMMAND_VALUE_FACE_HAPPY).toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onPlayError() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "乐天派 音频服务 完成AIDLService服务");
            iLetianpaiService = ILetianpaiService.Stub.asInterface(service);
            try {
                iLetianpaiService.registerAudioEffectCallback(ltpAudioEffectCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isConnectService = true;
        }


        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "乐天派 音频服务 无法绑定aidlserver的AIDLService服务");
            isConnectService = false;
        }
    };

    //链接服务端
    private void connectService() {
        Intent intent = new Intent();
        intent.setPackage("com.renhejia.robot.letianpaiservice");
        intent.setAction("android.intent.action.LETIANPAI");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private final LtpAudioEffectCallback.Stub ltpAudioEffectCallback = new LtpAudioEffectCallback.Stub() {
        @Override
        public void onAudioEffectCommand(String command, String data) throws RemoteException {

            if (command.equals(COMMAND_OPEN_AE)) {
                isCommandWork = true;

            } else if (command.equals(COMMAND_CLOSE_AE)) {
                isCommandWork = false;
            } else {
                if (isCommandWork) {
                    playSoundEffect(data);
                }
            }
        }
    };

    private void playSoundEffect(String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        name = name.toLowerCase();
        LogUtils.logd("LTPAudioService", "playSoundEffect: 播放音效文件：" + name);
        if (name.equals(SoundEffect.COMMAND_FUNCTION_STOP)) {
            mPlayer.stop();
        } else {
//            play(R.raw.mood_circumgyration, MCUCommandConsts.COMMAND_VALUE_SOUND_LOSE);
            Log.i(TAG, "LTPAudioService playSoundEffect: 直接播放名字对应的文件");
            int resId = getResources().getIdentifier(name, "raw", "com.letianpai.robot.audioservice");
            play(resId, name);
        }
    }


}
