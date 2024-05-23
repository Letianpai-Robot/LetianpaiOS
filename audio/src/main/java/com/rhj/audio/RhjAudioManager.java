package com.rhj.audio;

import static android.content.Context.TELEPHONY_SERVICE;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.aispeech.dui.dds.DDS;
import com.aispeech.dui.dds.DDSAuthListener;
import com.aispeech.dui.dds.DDSConfig;
import com.aispeech.dui.dds.DDSInitListener;
import com.aispeech.dui.dds.agent.ASREngine;
import com.aispeech.dui.dds.agent.DMTaskCallback;
import com.aispeech.dui.dds.agent.tts.TTSEngine;
import com.aispeech.dui.dds.agent.wakeup.WakeupCallback;
import com.aispeech.dui.dds.agent.wakeup.word.WakeupWord;
import com.aispeech.dui.dds.exceptions.DDSNotInitCompleteException;
import com.demon.fmodsound.FmodSound;
import com.google.gson.Gson;
import com.rhj.audio.observer.AuthStatusCallback;
import com.rhj.audio.observer.CommandCallback;
import com.rhj.audio.observer.InitCallback;
import com.rhj.audio.observer.MessageCallback;
import com.rhj.audio.observer.RhjCommandObserver;
import com.rhj.audio.observer.RhjDMTaskCallback;
import com.rhj.audio.observer.RhjMessageObserver;
import com.rhj.audio.observer.TtsStateChangeCallback;
import com.rhj.audio.observer.WakeupDoaCallback;
import com.rhj.audio.observer.WakeupStateChangeCallback;
import com.rhj.audio.utils.LogUtils;
import com.rhj.audio.utils.SPUtils;

import org.fmod.FMOD;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RhjAudioManager {
    private final String TAG = "RhjAudioManager";
    public static final String ENABLE_WORDUP_XIAOLE = "enable_xiao_le";
    public static final String ENABLE_WORDUP_XIAOPAI = "enable_xiao_pai";
    public static final String SPEAKER_NAME = "speakerName";

    private static volatile RhjAudioManager rhjAudioManager;
    private RhjMessageObserver mMessageObserver = new RhjMessageObserver();// 消息监听器
    private RhjCommandObserver mCommandObserver = new RhjCommandObserver();// 命令监听器
    private boolean initComplete = false;
    private boolean authSuccess = false;
    private AuthStatusCallback authStatusCallback;
    private InitCallback initCallback;

    // dds初始状态监听器,监听init是否成功
    private DDSInitListener mInitListener = new DDSInitListener() {
        @Override
        public void onInitComplete(boolean isFull) {
            LogUtils.logi("LTPAudioService", "RhjAudioManager_onInitComplete() ========== 5.4 =========");
            LogUtils.logd("RhjAudioManager", "onInitComplete " + isFull);
            if (isFull) {
                initComplete = true;
                if (initCallback != null) {
                    initCallback.stateChange(true);
                }
                LogUtils.logi("LTPAudioService", "RhjAudioManager_onInitComplete() ========== 5.5 =========");
                initComplete();
            }
        }

        @Override
        public void onError(int what, final String msg) {
            initComplete = false;
            if (initCallback != null) {
                initCallback.stateChange(false);
            }
            LogUtils.logi("LTPAudioService", "RhjAudioManager_onError() ========== 5.6 =========");
            LogUtils.logd("RhjAudioManager", "Init onError: " + what + ", error: " + msg);
        }
    };

    // dds认证状态监听器,监听auth是否成功
    private DDSAuthListener mAuthListener = new DDSAuthListener() {
        @Override
        public void onAuthSuccess() {
            authSuccess = true;
            if (authStatusCallback != null) {
                authStatusCallback.onAuthStausStateChange(true);
            }
            LogUtils.logd("RhjAudioManager", "onAuthSuccess");
        }

        @Override
        public void onAuthFailed(final String errId, final String error) {
            authSuccess = false;
            if (authStatusCallback != null) {
                authStatusCallback.onAuthStausStateChange(false);
            }
            LogUtils.logd("RhjAudioManager", "onAuthFailed: " + errId + ", error:" + error);
        }
    };

    private List<MessageCallback> messageCallbackList = new ArrayList<>();
    private List<WakeupStateChangeCallback> wakeupStateChangeCallbackList = new ArrayList<>();
    private List<WakeupDoaCallback> wakeupDoaCallbackList = new ArrayList<>();
    private List<TtsStateChangeCallback> ttsStateChangeCallbackList = new ArrayList<>();
    private List<CommandCallback> commandCallbackList = new ArrayList<>();
    private RhjDMTaskCallback rhjDMTaskCallback;
    private List<DmTaskResultBean> resultHistoryList = new ArrayList();
    public static final int RESULT_HISTORY_MAX_NUMBER = 10;
    private Gson mGson = new Gson();
    /**
     * 是否使用FMod 变声
     */
    private boolean useFmod;
    private boolean enableWakeupWordXiaole = true;
    private boolean enableWakeupWordXiaoPai = false;
    private Context context;

    public static RhjAudioManager getInstance() {
        if (rhjAudioManager == null) {
            rhjAudioManager = new RhjAudioManager();
        }
        return rhjAudioManager;
    }

    public void init(Context context, String apiKey) {
        LogUtils.logi("LTPAudioService", "RhjAudioManager_init() ========== 5.3 =========");
//        registerVolumeChange(context);
        this.context = context;
        DDS.getInstance().init(context.getApplicationContext(), createConfig(context, apiKey), mInitListener, mAuthListener);
    }

    public void test() {
        try {
            DDS.getInstance().getAgent().getASREngine().startListening(new ASREngine.Callback() {
                @Override
                public void beginningOfSpeech() {

                }

                @Override
                public void endOfSpeech() {

                }

                @Override
                public void bufferReceived(byte[] bytes) {

                }

                @Override
                public void partialResults(String s) {

                }

                @Override
                public void finalResults(String s) {
                    Log.i(TAG, "RhjAudioManager finalResults: " + s);
                }

                @Override
                public void error(String s) {

                }

                @Override
                public void rmsChanged(float v) {

                }
            });
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    public void unInit(Context context) {
//        unRegisterVolumeChange(context);
        messageCallbackList.clear();
        wakeupStateChangeCallbackList.clear();
        wakeupDoaCallbackList.clear();
        ttsStateChangeCallbackList.clear();
        commandCallbackList.clear();
        mMessageObserver.unregister();
        mCommandObserver.unregister();
        DDS.getInstance().release();
    }

    private void initComplete() {
        setTtsListener();
        setMessageObserver();
        setCommandObserver();
        setResultWithoutSpeak();
        enableWakeup();
//        disableWakeup();
        try {
            DDS.getInstance().getAgent().getWakeupEngine().setWakeupCallback(new WakeupCallback() {
                @Override
                public JSONObject onWakeup(JSONObject jsonObject) {
                    LogUtils.logd("RhjAudioManager", "onWakeup: " + jsonObject);
                    JSONObject result = new JSONObject();
                    try {
                        result.put("greeting", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
            //https://www.duiopen.com/docs/ct_cloud_TTS_Voice
//            DDS.getInstance().getAgent().getTTSEngine().setSpeaker("xijunmv4");//臻品男声小军
//            DDS.getInstance().getAgent().getTTSEngine().setSpeaker("yyqiaf");//臻品女声悦悦
//            DDS.getInstance().getAgent().getTTSEngine().setSpeaker("lzliafp");//精品可爱男童连连
//            DDS.getInstance().getAgent().getTTSEngine().setSpeaker("xbekef");//女孩-精品女童贝壳
//            DDS.getInstance().getAgent().getTTSEngine().setSpeaker("robot");//变声
            DDS.getInstance().getAgent().getTTSEngine().setStreamType(AudioAttributes.CONTENT_TYPE_MUSIC); //可以设置TTS播报通道，默认使用ALARM通道。
            DDS.getInstance().getAgent().getTTSEngine().setVolume(300);//可以调整TTS音量，建议可以初始化授权成功后调用该接口设置TTS音量。
            DDS.getInstance().getAgent().getTTSEngine().setSpeed(1.0f);
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开唤醒
     */
    public void enableWakeup() {
        try {

            DDS.getInstance().getAgent().getWakeupEngine().enableWakeupWhenAsr(true);
            DDS.getInstance().getAgent().getASREngine().enableVolume(true);
            DDS.getInstance().getAgent().getWakeupEngine().enableWakeup();

            //设置音色
            String speakerName = SPUtils.getInstance(context).getString(SPEAKER_NAME);
            if (speakerName != null) {
                setSpeaker(context, speakerName);
            }
            //设置唤醒词
            enableWakeupWordXiaole = SPUtils.getInstance(context).getBoolean(ENABLE_WORDUP_XIAOLE);
            enableWakeupWordXiaoPai = SPUtils.getInstance(context).getBoolean(ENABLE_WORDUP_XIAOPAI);
            setWakeupWord();

            String[] result = DDS.getInstance().getAgent().getWakeupEngine().getWakeupWords();
            Log.i(TAG, "RhjAudioManager 初始唤醒词 " + Arrays.toString(result));
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭唤醒
     */
    public void disableWakeup() {
        try {
            DDS.getInstance().getAgent().getWakeupEngine().disableWakeup();
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    public void setWakeupWord(Context context, boolean enableXiaoLe, boolean enableXiaoPai) {
        SPUtils.getInstance(context).putBoolean(ENABLE_WORDUP_XIAOLE, enableXiaoLe);
        SPUtils.getInstance(context).putBoolean(ENABLE_WORDUP_XIAOPAI, enableXiaoPai);
        enableWakeupWordXiaole = enableXiaoLe;
        enableWakeupWordXiaoPai = enableXiaoPai;
        setWakeupWord();
    }

    public void setSpeaker(Context context, String speakerName) {
        SPUtils.getInstance(context).putString(SPEAKER_NAME, speakerName);
        if (isInitComplete()) {
            if (speakerName.equals("robot")) {
                //https://www.duiopen.com/docs/ct_cloud_TTS_Voice
                try {
                    //默认机器人的底色是小军
                    DDS.getInstance().getAgent().getTTSEngine().setSpeaker("xijunmv4");
                    useFmod = true;
                } catch (DDSNotInitCompleteException e) {
                    throw new RuntimeException(e);
                }
            } else {
                useFmod = false;
                //https://www.duiopen.com/docs/ct_cloud_TTS_Voice
                try {
                    LogUtils.logd("RhjAudioManager", "设置音色setSpeaker: " + speakerName);
                    DDS.getInstance().getAgent().getTTSEngine().setSpeaker(speakerName);
                    LogUtils.logd("RhjAudioManager", "setSpeaker: " + DDS.getInstance().getAgent().getTTSEngine().getSpeaker());
                } catch (DDSNotInitCompleteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void setWakeupWord() {
        try {
            ArrayList<WakeupWord> list = new ArrayList<>();
            if (enableWakeupWordXiaole) {
                WakeupWord mainWord = new WakeupWord().setPinyin("hai xiao le").setWord("嗨，小乐").setThreshold("0.32").addGreeting("");
                list.add(mainWord);
            }
            if (enableWakeupWordXiaoPai) {
                WakeupWord mainWord = new WakeupWord().setPinyin("hai xiao pai").setWord("嗨，小派").setThreshold("0.32").addGreeting("");
                list.add(mainWord);
            }
            if (list.isEmpty()) {
                return;
            }
            DDS.getInstance().getAgent().getWakeupEngine().clearMainWakeupWord();
            DDS.getInstance().getAgent().getWakeupEngine().addMainWakeupWords(list);


            String[] result = DDS.getInstance().getAgent().getWakeupEngine().getWakeupWords();
            Log.i(TAG, "RhjAudioManager 唤醒词为 " + Arrays.toString(result));
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 让当前唤醒状态进入下一个状态
     * 点击唤醒/停止识别/打断播报 操作接口
     */
    public void avatarClick() {
        try {
            DDS.getInstance().getAgent().avatarClick();
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止所有播报
     */
    public void shutupTts() {
        try {
            DDS.getInstance().getAgent().getTTSEngine().shutup(null);
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    private void setTtsListener() {
        try {
            DDS.getInstance().getAgent().getTTSEngine().setListener(new TTSEngine.CallbackOptimize() {
                @Override
                public void beginning(String s) {
                    LogUtils.logd("RhjAudioManager", "beginning: ");
                    for (TtsStateChangeCallback ttsStateChangeCallback : ttsStateChangeCallbackList) {
                        ttsStateChangeCallback.onSpeakBegin();
                    }
                }

                @Override
                public void end(String ttsId, int i) {
                    LogUtils.logd("RhjAudioManager", "end: ");
                    for (TtsStateChangeCallback ttsStateChangeCallback : ttsStateChangeCallbackList) {
                        ttsStateChangeCallback.onSpeakEnd(ttsId, i);
                    }
                }

                @Override
                public void error(String s) {
                    LogUtils.logd("RhjAudioManager", "error: ");
                    for (TtsStateChangeCallback ttsStateChangeCallback : ttsStateChangeCallbackList) {
                        ttsStateChangeCallback.error(s);
                    }
                }

                @Override
                public void onSpeechProgress(String ttsId, int currentFrame, int totalFrame, boolean isDataReady) {
                    super.onSpeechProgress(ttsId, currentFrame, totalFrame, isDataReady);
                    LogUtils.logd("RhjAudioManager", "onSpeechProgress: ");
                    for (TtsStateChangeCallback ttsStateChangeCallback : ttsStateChangeCallbackList) {
                        ttsStateChangeCallback.onSpeakProgress(currentFrame, totalFrame);
                    }
                }

                @Override
                public void phoneReturnReceived(String s) {
                    LogUtils.logd("RhjAudioManager", "phoneReturnReceived: " + s);
                }
            });
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    private void setMessageObserver() {
        mMessageObserver.register(messageBean -> {
            for (MessageCallback messageCallback : messageCallbackList) {
                messageCallback.onMessage(messageBean);
            }
        }, stateData -> {
            for (WakeupStateChangeCallback wakeupStateChangeCallback : wakeupStateChangeCallbackList) {
                wakeupStateChangeCallback.onState(stateData);
            }
        }, doaData -> {
            for (WakeupDoaCallback wakeupDoaCallback : wakeupDoaCallbackList) {
                wakeupDoaCallback.onDoa(doaData);
            }
        });
    }

    private void setCommandObserver() {
        mCommandObserver.register((command, data) -> {
            for (CommandCallback commandCallback : commandCallbackList) {
                commandCallback.onCommand(command, data);
            }
        });
    }

    /**
     * 添加监听的命令项
     *
     * @param strings
     */
    public void addCommandSubscribeArray(String[] strings) {
        mCommandObserver.addSubscribe(strings);
    }

    /**
     * 添加消息监听项
     *
     * @param strings
     */
    public void addMessageSubscribeArray(String[] strings) {
        mMessageObserver.addSubscribe(strings);
    }

    /**
     * 获取语音处理结果所有内容
     * 如果需要白名单，可以在此处处理
     * {
     * "from":"dm.output",
     * "sessionId":"8489c5640ebc42e680c4e7dc951f38a2",
     * "recordId":"ecc7bca5ed6d4a94a6e5cfe641f47d70:f6070dd46d44498e88e2d39c57a6a967",
     * "skillId":"2019042500000544",
     * "skillName":false,
     * "taskId":"",
     * "shouldEndSession":true,
     * "intentName":"查询天气",
     * "task":"天气",
     * "nlg":"北京市今天全天多云，气温-8~2℃，和昨天差不多，有西南风转南风1级",
     * "ssml":"",
     * "speakUrl":"https:\/\/dds-ack.dui.ai\/runtime\/v1\/longtext\/ecc7bca5ed6d4a94a6e5cfe641f47d70:f6070dd46d44498e88e2d39c57a6a967?productId=279614681&aispeech-da-env=hd-ack",
     * "widget":Object{...},
     * "dmInput":"天气",
     * "endSessionReason":Object{...},
     * "display":"北京市今天全天多云，气温-8~2℃，和昨天差不多，有西南风转南风1级",
     * "watchId":"03e5f2552579412f8b3616accb510c8a"
     * }
     */
    private void setResultWithoutSpeak() {
        DDS.getInstance().getAgent().setDMTaskCallback(new DMTaskCallback() {
            @Override
            public JSONObject onDMTaskResult(JSONObject dmTaskResult, Type type) {
                Log.i(TAG, "RhjAudioManager onDMTaskResult: " + (rhjDMTaskCallback != null) + dmTaskResult);
                DmTaskResultBean dmTaskResultBean = mGson.fromJson(dmTaskResult.toString(), DmTaskResultBean.class);
                if (resultHistoryList.size() > RESULT_HISTORY_MAX_NUMBER) {
                    resultHistoryList.remove(0);
                }
//                try {
//                    DDS.getInstance().getAgent().stopDialog();
//                } catch (DDSNotInitCompleteException e) {
//                    throw new RuntimeException(e);
//                }
                /*new Thread(() -> {
                    FmodSound.INSTANCE.playTts(dmTaskResultBean.getSpeakUrl());
                    LogUtils.logd("<<<","进入launcher-----"+dmTaskResultBean.getSpeakUrl());
                }).start();

                try {
                    //语音不播报，唤醒的回复会在。
                    dmTaskResult.put("nlg", "");
                    dmTaskResult.put("speakUrl", null); // 不设置为null的话，播放的还是原来的音频
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return dmTaskResult;*/

                if (rhjDMTaskCallback != null) {
                    try {
                        String input = dmTaskResult.getString("dmInput");
                        if (input.contains("退出智能模式")) {
                            return dmTaskResult;
                        }
                        try {
                            DDS.getInstance().getAgent().stopDialog();//结束对话，不会走后续的处理命令的流程
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String result = rhjDMTaskCallback.dealResult(dmTaskResultBean);
                                    Log.i(TAG, "RhjAudioManager onDMTaskResult: result= " + result);
                                    speak(result);
                                }
                            }).start();
                        } catch (DDSNotInitCompleteException e) {
                            throw new RuntimeException(e);
                        }
                        return dmTaskResult;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, "RhjAudioManager onDMTaskResult: null " + dmTaskResultBean.toString());
                    resultHistoryList.add(dmTaskResultBean);

                    //TODO 如果有白名单，可以在此处理
                    if (type == DMTaskCallback.Type.DM_OUTPUT) {
                        if (rhjDMTaskCallback != null) {
                            rhjDMTaskCallback.dealResult(dmTaskResultBean);
                            try {
                                //语音不播报，唤醒的回复会在。
                                dmTaskResult.put("nlg", "");
                                dmTaskResult.put("speakUrl", null); // 不设置为null的话，播放的还是原来的音频
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (useFmod) {
                            LogUtils.logd("RhjAudioManager", "onDMTaskResult: useFmod:" + useFmod);
                            new Thread(() -> {
                                LogUtils.logd("<<<", "进入fmod变声-----" + dmTaskResultBean.getSpeakUrl());
                                FmodSound.INSTANCE.playTts(dmTaskResultBean.getSpeakUrl());
                            }).start();

                            try {
                                //语音不播报，唤醒的回复会在。
                                dmTaskResult.put("nlg", "");
                                dmTaskResult.put("speakUrl", null); // 不设置为null的话，播放的还是原来的音频
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            LogUtils.logd("RhjAudioManager", "onDMTaskResult: useFmod:" + useFmod);
                        }

                    } else if (type == DMTaskCallback.Type.CDM_ERROR) {
                        if (rhjDMTaskCallback != null) {
                            rhjDMTaskCallback.dealErrorResult();
                        }
                    }
                }
                return dmTaskResult;
            }
        });
    }

    // 创建dds配置信息
    private DDSConfig createConfig(Context context, String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
//            apiKey = "fe6fd8c58e3c1b1b25e3cdaf63bd69a1";
//            apiKey = "e012c990cb64e012c990cb64641827a8";
            apiKey = "2ade05a72d062ade05a72d06641864b8";
//            0bafb81e5f8e0bafb81e5f8e641924ce

        }
        // 用户设置自己实现的单个功能，目前支持 wakeup 和 vad。WakeupII.class 是一个使用示例
//         DDS.getInstance().setOutsideEngine(IEngine.Name.WAKEUP_SINGLE_MIC, WakeupII.class);
        DDSConfig config = new DDSConfig();
        // 基础配置项
//        config.addConfig("TTS_HEAD_NULL_WAV", "30"); // 产品ID -- 必填

//        config.addConfig("TTS_TAIL_NULL_WAV", "30"); // 产品ID -- 必填
        config.addConfig(DDSConfig.K_PRODUCT_ID, "279614681"); // 产品ID -- 必填
        config.addConfig(DDSConfig.K_USER_ID, "aispeech");  // 用户ID -- 必填
        config.addConfig(DDSConfig.K_ALIAS_KEY, "test");   // 产品的发布分支 -- 必填
        config.addConfig(DDSConfig.K_PRODUCT_KEY, "12e4c9cdffa62009662bff42d6858372");// Product Key -- 必填
        config.addConfig(DDSConfig.K_PRODUCT_SECRET, "54f665b47df0b43b357cd8e09243bdb6");// Product Secre -- 必填
        config.addConfig(DDSConfig.K_DEVICE_ID, getDeviceId(context));//填入唯一的deviceId -- 选填

        config.addConfig(DDSConfig.K_API_KEY, apiKey);  // 产品授权秘钥，服务端生成，用于产品授权 -- 必填


        // 更多高级配置项,请参考文档: https://www.dui.ai/docs/ct_common_Andriod_SDK 中的 --> 四.高级配置项

        config.addConfig(DDSConfig.K_DUICORE_ZIP, "duicore.zip"); // 预置在指定目录下的DUI内核资源包名, 避免在线下载内核消耗流量, 推荐使用
        config.addConfig(DDSConfig.K_USE_UPDATE_DUICORE, "false"); //设置为false可以关闭dui内核的热更新功能，可以配合内置dui内核资源使用

        // 资源更新配置项
        config.addConfig(DDSConfig.K_CUSTOM_ZIP, "product.zip"); // 预置在指定目录下的DUI产品配置资源包名, 避免在线下载产品配置消耗流量, 推荐使用
        // config.addConfig(DDSConfig.K_USE_UPDATE_NOTIFICATION, "false"); // 是否使用内置的资源更新通知栏

        // 录音配置项
        // config.addConfig(DDSConfig.K_IS_REVERSE_AUDIO_CHANNEL, "false"); // 录音机通道是否反转，默认不反转
        // config.addConfig(DDSConfig.K_AUDIO_SOURCE, AudioSource.DEFAULT); // 内置录音机数据源类型
        // config.addConfig(DDSConfig.K_AUDIO_BUFFER_SIZE, (16000 * 1 * 16 * 100 / 1000)); // 内置录音机读buffer的大小

        // TTS配置项
        config.addConfig(DDSConfig.K_STREAM_TYPE, AudioManager.STREAM_MUSIC); // 内置播放器的STREAM类型
        config.addConfig(DDSConfig.K_AUDIO_USAGE, AudioAttributes.USAGE_MEDIA);
        config.addConfig(DDSConfig.K_CONTENT_TYPE, AudioAttributes.CONTENT_TYPE_MUSIC);


        config.addConfig(DDSConfig.K_TTS_MODE, "internal"); // TTS模式：external（使用外置TTS引擎，需主动注册TTS请求监听器）、internal（使用内置DUI TTS引擎）
        // config.addConfig(DDSConfig.K_CUSTOM_TIPS, "{\"71304\":\"请讲话\",\"71305\":\"不知道你在说什么\",\"71308\":\"咱俩还是聊聊天吧\"}"); // 指定对话错误码的TTS播报。若未指定，则使用产品配置。

        //唤醒配置项
        config.addConfig(DDSConfig.K_WAKEUP_ROUTER, "dialog"); //唤醒路由：partner（将唤醒结果传递给partner，不会主动进入对话）、dialog（将唤醒结果传递给dui，会主动进入对话）
//        config.addConfig(DDSConfig.K_CUSTOM_AUDIO,"");
        config.addConfig(DDSConfig.K_WAKEUP_DISABLE_CUSTOM_GREETING, "false");
        // config.addConfig(DDSConfig.K_WAKEUP_BIN, "/sdcard/wakeup.bin"); //商务定制版唤醒资源的路径。如果开发者对唤醒率有更高的要求，请联系商务申请定制唤醒资源。
        // config.addConfig(DDSConfig.K_ONESHOT_MIDTIME, "500");// OneShot配置：
        // config.addConfig(DDSConfig.K_ONESHOT_ENDTIME, "2000");// OneShot配置：

        //识别配置项识别配置项
        // config.addConfig(DDSConfig.K_ASR_ENABLE_PUNCTUATION, "false"); //识别是否开启标点
        // config.addConfig(DDSConfig.K_ASR_ROUTER, "dialog"); //识别路由：partner（将识别结果传递给partner，不会主动进入语义）、dialog（将识别结果传递给dui，会主动进入语义）
        // config.addConfig(DDSConfig.K_VAD_TIMEOUT, 5000); // VAD静音检测超时时间，默认8000毫秒
        // config.addConfig(DDSConfig.K_ASR_ENABLE_TONE, "true"); // 识别结果的拼音是否带音调
        // config.addConfig(DDSConfig.K_ASR_TIPS, "true"); // 识别完成是否播报提示音
        // config.addConfig(DDSConfig.K_VAD_BIN, "/sdcard/vad.bin"); // 商务定制版VAD资源的路径。如果开发者对VAD有更高的要求，请联系商务申请定制VAD资源。

        // 麦克风阵列配置项
        config.addConfig("PING_TIMEOUT", "20000");//单位ms,SDK初始化时，修改PING_TIMEOUT字段的值即可。默认10s


        // config.addConfig(DDSConfig.K_MIC_ARRAY_AEC_CFG, "/data/aec.bin"); // 麦克风阵列aec资源的磁盘绝对路径,需要开发者确保在这个路径下这个资源存在
        // config.addConfig(DDSConfig.K_MIC_ARRAY_BEAMFORMING_CFG, "/data/beamforming.bin"); // 麦克风阵列beamforming资源的磁盘绝对路径，需要开发者确保在这个路径下这个资源存在

        // 全双工/半双工配置项
        config.addConfig(DDSConfig.K_DUPLEX_MODE, "HALF_DUPLEX");// 半双工模式


//TODO 自己的板子，设配hal 之后使用

        config.addConfig(DDSConfig.K_MIC_TYPE, "6"); // （根据麦克风实际类型进行配置）设置硬件采集模组的类型 0：无。默认值。 1：单麦回消 2：线性四麦 3：环形六麦 4：车载双麦 5：家具双麦 6: 环形四麦  7: 新车载双麦 8: 线性6麦
        config.addConfig(DDSConfig.K_AEC_MODE, "external");//AEC模式，HAL层未集成AEC算法时，选择"internal"。HAL已经集成AEC算法时，选择"external"
        config.addConfig(DDSConfig.K_USE_SSPE, "true");//如果资源是SSPE资源，则需要将此配置置为true

        config.addConfig(DDSConfig.K_RECORDER_MODE, "internal");  //（适配了hal之后选内部，或者不写这一条，SDK默认是内部---录音机模式：external（使用外置录音机，需主动调用拾音接口）、internal（使用内置录音机，DDS自动录音）
        config.addConfig(DDSConfig.K_MIC_ARRAY_SSPE_BIN, "sspe_aec-uca-wkp_46mm_ch6_4mic_1ref_release-v2.0.0.130.bin");//SSPE资源（放在test/src/main/assert文件夹下，或放到机器上指定绝对路径）(已包含aec算法)绝对路径，请务必保证绝对路径有可读写权限
        //config.addConfig(DDSConfig.K_WAKEUP_BIN, "wakeup_s20_zhihuijingling_20230103.bin"); //商务定制版唤醒资源的路径。如果开发者对唤醒率有更高的要求，请联系商务申请定制唤醒资源。

        String wakeAudioJson = "[{"+
                "\"name\":\"我在，有什么可以帮你\","+
                "\"type\":\"mp3\","+
                "\"path\":\"/storage/emulated/0/error.mp3\""+
                "}]";
        config.addConfig(DDSConfig.K_CUSTOM_AUDIO, wakeAudioJson);

        if (BuildConfig.DEBUG) {
            config.addConfig(DDSConfig.K_CACHE_PATH, "/sdcard/cache"); // 调试信息保存路径,如果不设置则保存在默认路径"/sdcard/Android/data/包名/cache"
            config.addConfig(DDSConfig.K_CACHE_SIZE, "1024"); // 调试信息保存路径,如果不设置则保存在默认路径"/sdcard/Android/data/包名/cache"
            config.addConfig(DDSConfig.K_WAKEUP_DEBUG, "true"); // 用于唤醒音频调试, 开启后在 "/sdcard/Android/data/包名/cache" 目录下会生成唤醒音频
            config.addConfig(DDSConfig.K_VAD_DEBUG, "true"); // 用于过vad的音频调试, 开启后在 "/sdcard/Android/data/包名/cache" 目录下会生成过vad的音频
            config.addConfig(DDSConfig.K_ASR_DEBUG, "true"); // 用于识别音频调试, 开启后在 "/sdcard/Android/data/包名/cache" 目录下会生成识别音频
            config.addConfig(DDSConfig.K_TTS_DEBUG, "true");  // 用于tts音频调试, 开启后在 "/sdcard/Android/data/包名/cache/tts/" 目录下会自动生成tts音频
        }
//        https://iot-sz.aispeech.com/doc/cicada-doc/#/ProjectDocking/?id=%e5%8d%8a%e5%8f%8c%e5%b7%a5%e6%a8%a1%e5%bc%8f%e4%b8%8b%ef%bc%8c%e6%92%ad%e6%8a%a5%e5%94%a4%e9%86%92%e5%9b%9e%e5%a4%8d%e8%af%ad%e7%9a%84%e5%90%8c%e6%97%b6%ef%bc%8c%e8%bf%9b%e8%a1%8c%e8%af%ad%e9%9f%b3%e8%af%86%e5%88%ab%e7%9a%84%e6%96%b9%e6%a1%88
//        config.addConfig(DDSConfig.K_ONESHOT_MIDTIME, -1);
//        config.addConfig(DDSConfig.K_ONESHOT_ENDTIME, -1);

        LogUtils.logd("RhjAudioManager", "config->" + config.toString());
        return config;
    }

    // 获取手机的唯一标识符: deviceId
    @SuppressLint("MissingPermission")
    private String getDeviceId(Context context) {
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String imei = null;
        try {
//            imei = telephonyMgr.getDeviceId();
            imei = SystemUtil.getLtpSn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String serial = Build.SERIAL;
        String uuid;
        if (TextUtils.isEmpty(imei)) {
            imei = "unkown";
        } else if (TextUtils.isEmpty(serial)) {
            serial = "unkown";
        }
        uuid = UUID.nameUUIDFromBytes((imei + serial).getBytes()).toString();
        return uuid;
    }

    public void setMessageCallback(MessageCallback messageCallback) {
        this.messageCallbackList.add(messageCallback);
    }

    public void setWakeupStateChangeCallback(WakeupStateChangeCallback wakeupStateChangeCallback) {
        this.wakeupStateChangeCallbackList.add(wakeupStateChangeCallback);
    }

    public void setWakeupDoaCallback(WakeupDoaCallback wakeupDoaCallback) {
        this.wakeupDoaCallbackList.add(wakeupDoaCallback);
    }

    public void setTtsStateChangeCallback(TtsStateChangeCallback ttsStateChangeCallback) {
        this.ttsStateChangeCallbackList.add(ttsStateChangeCallback);
    }

    public void setCommandCallback(CommandCallback commandCallback) {
        this.commandCallbackList.add(commandCallback);
    }

    public void setRhjDMTaskCallback(RhjDMTaskCallback rhjDMTaskCallback) {
        this.rhjDMTaskCallback = rhjDMTaskCallback;
    }

    public void setAuthStatusCallback(AuthStatusCallback authStatusCallback) {
        this.authStatusCallback = authStatusCallback;
    }

    public void setInitCallback(InitCallback initCallback) {
        this.initCallback = initCallback;
    }

    /**
     * String text, int priority, String ttsId, int audioFocus, String type
     *
     * @param text
     */
    public void speak(String text) {
        speak(text, 0);
    }

    /**
     * @param text
     * @param priority 建议是用 0 ，否则影响播放的同时，语音唤醒
     */
    public void speak(String text, int priority) {
        speak(text, priority, UUID.randomUUID().toString(), android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE, "0");
    }

    public void speak(String text, String ttsId) {
        speak(text, 0, ttsId, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE, "0");
    }

    /**
     * @param text       播报文本，支持SSML。
     * @param priority   优先级
     *                   ①优先级0：保留，与DDS语音交互同级，仅限内部使用；
     *                   ②优先级1：正常，默认选项，同级按序播放；
     *                   ③优先级2：重要，可以插话<优先级1>，同级按序播放，播报完毕后继续播报刚才被插话的<优先级1>；
     *                   ④优先级3：紧急，可以打断当前正在播放的<优先级1|优先级2>，同级按序播放，播报完毕后不再继续播报刚才被打断的<优先级1｜优先级2>。
     * @param ttsId      用于追踪该次播报的id，建议使用UUID。
     * @param audioFocus 该次播报的音频焦点，默认值:
     *                   ①优先级0：android.media.AudioManager#AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE
     *                   ②优先级非0：android.media.AudioManager#AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
     * @param type       语音合成格式 云端合成，建议是mp3，本地合成，可以用wav
     */
    public void speak(String text, int priority, String ttsId, int audioFocus, String type) {
        try {
            DDS.getInstance().getAgent().getTTSEngine().speak(text, priority, ttsId, audioFocus, type);
        } catch (DDSNotInitCompleteException e) {
            e.printStackTrace();
        }
    }

    public void speakAndStartDialog(String text) {
        JSONObject jsonObject = new JSONObject();
        try {
            DDS.getInstance().getAgent().stopDialog();
            jsonObject.put("speakText", text);
            DDS.getInstance().getAgent().startDialog(jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (DDSNotInitCompleteException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopDialogWithText(String text) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("speakText", text);
            DDS.getInstance().getAgent().stopDialog(jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (DDSNotInitCompleteException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopDialog() {
        try {
            DDS.getInstance().getAgent().stopDialog();
        } catch (DDSNotInitCompleteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取最近的十条处理结果
     *
     * @return
     */
    public List<DmTaskResultBean> getResultHistoryList() {
        return resultHistoryList;
    }

    /**
     * 获取是否初始化成功
     *
     * @return
     */
    public boolean isInitComplete() {
        return initComplete;
    }

    /**
     * 获取是否授权成功
     *
     * @return
     */
    public boolean isAuthSuccess() {
        return authSuccess;
    }


}
