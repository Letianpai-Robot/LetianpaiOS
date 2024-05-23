package com.rhj.player;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.rhj.message.MessageMediaListBean;
import com.rhj.message.MessageMusicBean;

import org.json.JSONObject;

import java.util.List;

/**
 * 播放音乐技能
 */
public class PlayerService extends Service {
    private static final String TAG = "PlayerService";
    public static final String INTENT_EXTRA_DATA = "data";
    private Player player;
    private Gson mGson;
    private List<MessageMusicBean> musicList;
    private AudioManager am;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "PlayerService onCreate: ");
        mGson = new Gson();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onNewDataLoad(intent.getStringExtra(INTENT_EXTRA_DATA));
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        Log.i(TAG, "PlayerService init: ");
        player = Player.getInstance();
        player.setOnPreparedListener(mp -> {
            Log.i("PlayerService", "init: ");
            if (mp != null) {
                mp.start();
            }
        });
        registerFocus();
    }

    private void registerFocus() {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        onAudioFocusChangeListener = focusChange -> {
            Log.i(TAG, "PlayerService registerFocus: " + focusChange);
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    player.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    player.start();
                    break;
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            am.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    public void unRegisterFocus() {
        am.abandonAudioFocus(onAudioFocusChangeListener);
    }

    private void onNewDataLoad(String json) {
        musicList = loadMusic(json);
        player.init(musicList);
    }

    public void setNewMusic(String json) {
        onNewDataLoad(json);
    }

    private List<MessageMusicBean> loadMusic(String data) {
        try {
            Log.d(TAG, "loadMusic: " + data);
            JSONObject object = new JSONObject(data);
            MessageMediaListBean musics = mGson.fromJson(data, MessageMediaListBean.class);
            return musics.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void play() {
        if (player != null) {
            registerFocus();
            player.start();
        }
    }

    public void prev() {
        if (player != null) {
            player.prev();
        }
    }

    public void next() {
        if (player != null) {
            player.next();
        }
    }

    public void pause() {
        if (player != null) {
            unRegisterFocus();
            player.pause();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "PlayerService onBind: ");
        onNewDataLoad(intent.getStringExtra(INTENT_EXTRA_DATA));
        if (myBinder == null) {
            myBinder = new MyBinder();
        }
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterFocus();
    }

    MyBinder myBinder;


    public class MyBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }
}