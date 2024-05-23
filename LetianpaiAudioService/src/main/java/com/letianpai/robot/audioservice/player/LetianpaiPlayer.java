package com.letianpai.robot.audioservice.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;

import com.renhejia.robot.commandlib.log.LogUtils;

import java.io.File;
import java.lang.ref.WeakReference;


public class LetianpaiPlayer {
    private Context context;
    private int audioStreamType = -1;
    private MediaPlayer mediaPlayer;

    private String path;
    private int fileRes = 0;

    private int loop = 0;

    private boolean hadGotAudioFocus;

    private VoicePlayerCallback voicePlayerCallback;

    private VoicePlayFocusChangeListener audioFocusChangeListener;

    public LetianpaiPlayer(Context context) {
        this(context, -1);
    }

    public LetianpaiPlayer(Context context, int audioStreamType) {
        this.context = context;
        this.audioStreamType = audioStreamType;
        this.audioFocusChangeListener = new VoicePlayFocusChangeListener(this);
    }

    public void play() {
        if (mediaPlayer != null) {
            stop();
        }

        try {
            if (path == null) {
                mediaPlayer = MediaPlayer.create(context, fileRes);
                if (audioStreamType != -1) {
                    mediaPlayer.setAudioStreamType(audioStreamType);
                }
            } else {
                LogUtils.logd("LetianpaiPlayer", "play: 播放音效=：" + path);
                mediaPlayer = MediaPlayer.create(context, Uri.fromFile(new File(path)));
                if (audioStreamType != -1) {
                    mediaPlayer.setAudioStreamType(audioStreamType);
                }
            }

//            mediaPlayer.setOnErrorListener(errorListener);
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.i("TAG", "LetianpaiPlayer onError: VoicePlayer onError-- what->" + what + ",extra->" + extra);
                    mediaPlayer = null;
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(completionListener);

            requestAudioFocus();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.start();
        } catch (Exception e) {
            cancelAudioFocus();

            if (voicePlayerCallback != null) {
                voicePlayerCallback.onPlayError();
            }
        }
    }

    public void play(int fileRaw) {
        if (mediaPlayer != null) {
            stop();
        }
        Log.i("TAG", "LetianpaiPlayer play:fileRaw: " + fileRaw);
        try {
            if (fileRaw == 0) {
                return;
            }
            mediaPlayer = MediaPlayer.create(context, fileRaw);
            if (audioStreamType != -1) {
                mediaPlayer.setAudioStreamType(audioStreamType);
            }
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.i("TAG", " VoicePlayer onErro===r-- what->" + what + ",extra->" + extra);
                    mediaPlayer = null;
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(completionListener);

            requestAudioFocus();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.start();
        } catch (Exception e) {
            cancelAudioFocus();

            if (voicePlayerCallback != null) {
                voicePlayerCallback.onPlayError();
            }
        }
    }

    public void pause() {
        cancelAudioFocus();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        cancelAudioFocus();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer = null;
        }
    }

    private void innerStop() {
        stop();
        if (null != voicePlayerCallback) {
            voicePlayerCallback.onPlayError();
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileRes(int fileRes) {
        this.fileRes = fileRes;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    private OnCompletionListener completionListener = new OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stop();

            if (loop > 1) {
                loop--;
                play();
            }

            if (voicePlayerCallback != null) {
                voicePlayerCallback.onPlayCompletion(loop);
            }
        }
    };

    private MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
            LogUtils.logi("", "VoicePlayer onError-- what->" + what + ",extra->" + extra);
            cancelAudioFocus();
            return true;
        }
    };

    private void requestAudioFocus() {
        if (!hadGotAudioFocus) {
//			hadGotAudioFocus = AudioFocusHelper.getInstance().requestAudioFocus(context,
//					AudioManager.STREAM_MUSIC, audioFocusChangeListener);
        }
    }

    private void cancelAudioFocus() {
        if (hadGotAudioFocus) {
//			AudioFocusHelper.getInstance().abandonAudioFocus(context, audioFocusChangeListener);
            hadGotAudioFocus = false;
        }
    }

    public int getDuration() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void setVoicePlayerCallback(VoicePlayerCallback voicePlayerCallback) {
        this.voicePlayerCallback = voicePlayerCallback;
    }


    public interface VoicePlayerCallback {
        void onPlayCompletion(int laveLoop);

        void onPlayError();
    }

    private static class VoicePlayFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {

        private final WeakReference<LetianpaiPlayer> voicePlayRefs;

        public VoicePlayFocusChangeListener(LetianpaiPlayer player) {
            this.voicePlayRefs = new WeakReference<>(player);
        }

        private LetianpaiPlayer getPlayer() {
            return null != voicePlayRefs ? voicePlayRefs.get() : null;
        }

        @Override
        public void onAudioFocusChange(int focusChange) {
            LetianpaiPlayer player = getPlayer();
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                //pause
                LogUtils.logi("", "VoicePlayFocusChangeListener AUDIOFOCUS_LOSS_TRANSIENT");
                if (player != null) {
                    player.innerStop();
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //stop
                LogUtils.logi("", "VoicePlayFocusChangeListener AUDIOFOCUS_LOSS");
                if (player != null) {
                    player.innerStop();
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //play
                LogUtils.logi("", "VoicePlayFocusChangeListener AUDIOFOCUS_GAIN");
            }
        }
    }

}
