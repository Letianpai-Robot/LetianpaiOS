package com.renhejia.robot.expression.ui.view;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.renhejia.robot.commandlib.log.LogUtils;
import android.view.SurfaceHolder;
import android.widget.VideoView;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 *
 */
public class RobotVideoView extends VideoView {
    public String mAssetsPathName;
    public int mVideoTotal = 0;
    private int mPlayIndex = 0;
    private boolean isSurfaceCreated = false;
    private Context mContext;
    MediaPlayer player = new MediaPlayer();
    private static final int PLAY_VIDEO = 9;

    public RobotVideoView(Context context) {
        super(context);
        this.mContext =context;
    }

    public RobotVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext =context;
    }

    public RobotVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext =context;
    }

    public void setAssetsPath(String pathName) {
        mAssetsPathName = pathName;


    }

    public void stopVideo() {
        stopPlayback();
    }

    public boolean isPlayVideo() {
        return isPlaying();
    }

//    public void playNext() {
//        if (mVideoTotal > 0) {
//            mPlayIndex++;
//
//            if (mPlayIndex > mVideoTotal) {
//                mPlayIndex = 1;
//            }
//            playVideo(mPlayIndex);
//        }
//    }

//    public void playRand() {
//        if (mVideoTotal > 0) {
//            Random random = new Random();
//            Integer index = random.nextInt(mVideoTotal) + 1;
//            if (index == mPlayIndex) {
//                playNext();
//            }
//            playVideo(index);
//
//        }
//    }

    public void playVideo(String name) {



            SurfaceHolder holder = this.getHolder();

            if (isSurfaceCreated) {
//                playAssetVideoByHandler1(name);
                playAssetVideo(name);
            } else {
                holder.addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        isSurfaceCreated = true;
                        player.setDisplay(holder);
                        if (mAssetsPathName != null && !TextUtils.isEmpty(name)) {
                            playAssetVideo(name);
//                            playAssetVideoByHandler1(name);
                        }
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {

                    }
                });
            }


    }


    private void playAssetVideoByHandler1(String name) {
        Message message = new Message();
        message.obj = name;
        message.what = PLAY_VIDEO;
        handler.sendMessage(message);

    }


    public void playAssetVideo(String name) {
        try {
            player.reset();
//            String filename = mAssetsPathName + "/" + "video_" + name + ".mp4";
//            String filename = "video" + "/" + "bigLaugh.mp4";
            String filename = "video" + "/" +  name + ".mp4";;
            AssetFileDescriptor afd = RobotVideoView.this.getContext().getAssets().openFd(filename);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
//                                RobotVideoView.this.setBackgroundColor(Color.TRANSPARENT);
                                return true;
                            }
                            return false;
                        }
                    });

                    mp.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // mp.start();
                }
            });
            try {
//                player.prepareAsync();
                player.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
//            playAssetVideo(1);
//            playAssetVideo();
            LogUtils.logi("letiabpai","文件没找到");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public void playAssetVideo(int index) {
//        try {
//            player.reset();
//            String filename = mAssetsPathName + "/" + "video_" + String.valueOf(index) + ".mp4";
//            AssetFileDescriptor afd = RobotVideoView.this.getContext().getAssets().openFd(filename);
//            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//
//            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                        @Override
//                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
//                                RobotVideoView.this.setBackgroundColor(Color.TRANSPARENT);
//                                return true;
//                            }
//                            return false;
//                        }
//                    });
//
//                    mp.start();
//                }
//            });
//            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    // mp.start();
//                }
//            });
//            try {
////                player.prepareAsync();
//                player.prepare();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
////            playAssetVideo(1);
//            playAssetVideo(1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case PLAY_VIDEO:
                    playAssetVideo((String) msg.obj);
                    break;

            }


        }
    };

}
