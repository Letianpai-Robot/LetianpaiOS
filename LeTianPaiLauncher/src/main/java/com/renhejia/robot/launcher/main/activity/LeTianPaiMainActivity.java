package com.renhejia.robot.launcher.main.activity;

import static com.renhejia.robot.commandlib.consts.MCUCommandConsts.COMMAND_SET_SHOW_TIME;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.letianpai.robot.notice.alarm.receiver.AlarmCallback;
import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.commandlib.consts.RobotExpressionConsts;
import com.renhejia.robot.commandlib.consts.RobotRemoteConsts;
import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.commandlib.parser.displaymodes.logo.LogoInfo;
import com.renhejia.robot.expression.face.FaceConsts;
import com.renhejia.robot.expression.ui.view.RobotExpressionViewGif;
import com.renhejia.robot.gesturefactory.manager.GestureResPool;
import com.renhejia.robot.guidelib.ble.callback.GuideFunctionCallback;
import com.renhejia.robot.guidelib.manager.LTPGuideConfigManager;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;
import com.renhejia.robot.launcher.LTPConfigConsts;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.callback.CommandResponseCallback;
import com.renhejia.robot.launcher.dispatch.mode.ModeChangeCallback;
import com.renhejia.robot.launcher.dispatch.mode.ViewModeConsts;
import com.renhejia.robot.launcher.dispatch.service.StartServiceCallback;
import com.renhejia.robot.launcher.displaymode.DisplayModesView;
import com.renhejia.robot.launcher.displaymode.callback.DeviceChannelLogoCallBack;
import com.renhejia.robot.launcher.main.view.RobotTestAnimationView;
import com.renhejia.robot.launcher.nets.GeeUINetResponseManager;
import com.renhejia.robot.launcher.nets.GeeUiNetManager;
import com.renhejia.robot.launcher.statusbar.service.DispatchService;
import com.renhejia.robot.launcher.system.LetianpaiFunctionUtil;
import com.renhejia.robot.launcher.system.SystemFunctionUtil;
import com.renhejia.robot.launcherbaselib.storage.manager.LauncherConfigManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;


public class LeTianPaiMainActivity extends Activity {

    //开机的hello world GIF
    private RobotExpressionViewGif robotExpressionViewGif;

    // private static final int CHANGE_EXPRESSION = 99;
    private static final int SHOW_MAIN_IMAGE = 102;
    private static final int SHOW_DISPLAY_VIEW = 103;
    private static final int SHOW_REMOTE_CONTROL_VIEW = 105;
    // private static final int SHOW_REMOTE_TEXT = 107;
    private static final int SHOW_REMOTE_BLACK = 108;
    private static final int UPDATE_ALARM_TIME = 109;
    private static final int SHUTDOWN = 111;
    // private static final int SHOW_SLEEP = 114;
    // private static final int SHOW_DEEP_SLEEP = 115;
    private static final int SHOW_CHARGING = 116;

    private ChangeExpressionHandler mHandler;
    private ImageView sleep;
    private TextView alertTime;
    private DisplayModesView displayModesView;
    private View shutdownView;
    private RobotTestAnimationView robotSleepAnimationView;
    private boolean hadSetMainCountdownTimer;
    private CountDownTimer engineCountDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        SystemUtil.setAppLanguage(LeTianPaiMainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        LogUtils.logd("LeTianPaiMainActivity", "onStart  LTPGuideConfigManager.getInstance(this).isActivated()::" + LTPGuideConfigManager.getInstance(this).isActivated());
    }

    private void init() {
        //思必驰语音独立出来了，启动放在RobotService即可
        if (!LetianpaiFunctionUtil.isFactoryRom()) {
            String pro = SystemUtil.get(SystemUtil.REGION_LANGUAGE, "zh");
//            if (LTPGuideConfigManager.getInstance(LeTianPaiMainActivity.this).isActivated()){
            if ("zh".equals(pro)) {
                startSpeechAudioService();
            } else if ("en".equals(pro)) {
                startAmazonAudioService();
            } else {
                startSpeechAudioService();
            }
//            }

            startAppStoreService();
            startMenuService();
        }

        SystemFunctionUtil.setTimeZone(LeTianPaiMainActivity.this);
        startMainCountDownTimer();
        initEngineCountDownTimer();
    }

    private void startMenuService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                ComponentName cn = new ComponentName("com.letianpai.robot.desktop", "com.letianpai.robot.desktop.service.GeeUIDesktopService");
                intent.setComponent(cn);
                startService(intent);
            }
        }).start();
    }

    private void startAppStoreService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                ComponentName cn = new ComponentName("com.letianpai.robot.appstore", "com.letianpai.robot.appstore.service.AppStoreService");
                intent.setComponent(cn);
                startService(intent);
            }
        }).start();
    }

    private void startMainCountDownTimer() {
        Log.e("letianpai_test111", "hadSetMainCountdownTimer: " + hadSetMainCountdownTimer);
        if (!hadSetMainCountdownTimer) {
            Log.e("letianpai_test111", "hadSetMainCountdownTimer1: " + hadSetMainCountdownTimer);
            hadSetMainCountdownTimer = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StartServiceCallback.getInstance().startService();
                }
            }, 5000);
        }
    }

    protected void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSteeringEngine();
        LogUtils.logd("LeTianPaiMainActivity", "LTPGuideConfigManager.getInstance(this).isActivated()::" + LTPGuideConfigManager.getInstance(this).isActivated());

        //激活设备
        if (!LTPGuideConfigManager.getInstance(this).isActivated() && SystemUtil.getRobotActivateStatus()) {
            SystemUtil.setRobotActivated();
            LTPGuideConfigManager.getInstance(this).setActivated(true);
            LTPGuideConfigManager.getInstance(this).commit();
        }
        showChargingView();
        if (robotExpressionViewGif.getVisibility() == View.GONE) {
            displayModesView.showViews();
        }
    }

    public void showCharging() {
        CommandResponseCallback.getInstance().setAppCommand(RobotRemoteConsts.COMMAND_SET_APP_MODE, RobotRemoteConsts.COMMAND_SHOW_CHARGING);
    }

    private void checkSteeringEngine() {
        engineCountDownTimer.start();
    }


    private void initEngineCountDownTimer() {

        engineCountDownTimer = new CountDownTimer(3000, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (LetianpaiFunctionUtil.isLauncherOnTheTop(LeTianPaiMainActivity.this)) {
                    GuideFunctionCallback.getInstance().shutDownSteeringEngine();
                }
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            if ("stop".equals(action)) {
                finish();
                return;
            }
        }

        keepScreenOn();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_main_letianpai);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        inits();

        if (intent != null) {
            String from = intent.getStringExtra(LTPConfigConsts.START_FROM);
            showViews(from);
        }
        getLogo();
        getDeviceLogo();
    }
    private void getLogo(){
        GeeUINetResponseManager.getInstance(this).getLogoInfo();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        LogUtils.logd("LeTianPaiMainActivity", "onNewIntent action::" + action);
        if ("stop".equals(action)) {
            finish();
            return;
        }

        String from = intent.getStringExtra(LTPConfigConsts.START_FROM);
        showViews(from);
        getDeviceLogo();
    }

    private void showViews(String from) {
        LogUtils.logd("LeTianPaiMainActivity", "onNewIntent from::" + from);
        if (!TextUtils.isEmpty(from) && from.equals(LTPConfigConsts.START_FROM_MINI_APP)) {
            showDisplayViews(null);
        } else if (!TextUtils.isEmpty(from) && from.equals(LTPConfigConsts.START_FROM_START_APP)) {
            showBlack(false);
        } else if (!TextUtils.isEmpty(from) && from.equals(LTPConfigConsts.START_FROM_SLEEP)) {
            //这里只是显示view布局，打呼噜声音不在这里
            showRobotSleepMode();
        } else if (!TextUtils.isEmpty(from) && from.equals(LTPConfigConsts.START_FROM_DEEP_SLEEP)) {
            showRobotDeepSleepMode();
        } else if (!TextUtils.isEmpty(from) && from.equals(LTPConfigConsts.START_FROM_WIFI_CONNECTOR)) {
            showTime();
        }
    }

    private void showTime() {

        com.renhejia.robot.launcher.dispatch.command.CommandResponseCallback.getInstance().setLTPCommand(COMMAND_SET_SHOW_TIME, COMMAND_SET_SHOW_TIME);

    }

    private void getDeviceLogo() {
         DeviceChannelLogoCallBack.getInstance().setDeviceChannelLogoUpdateListener(new DeviceChannelLogoCallBack.DeviceChannelLogoUpdateListener() {
                @Override
                public void onLogoInfoUpdate(LogoInfo logoInfo) {
                    LogUtils.logd("LeTianPaiMainActivity", "onNewIntent action::AA " + logoInfo.toString());
                    updateViewData(logoInfo);
                }
            });

    }

    /***
     * 更新 背景
     * @param logoInfo
     */
    private void updateViewData(LogoInfo logoInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                robotExpressionViewGif.updateView(logoInfo.getData().getHello_logo());
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        displayModesView.hideViews();
        showBlackView();
    }

    private void startSpeechAudioService() {
        LogUtils.logd("LeTianPaiMainActivity", "startSpeechAudioService: ");
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.rhj.speech", "com.rhj.audio.service.LTPAudioService");
        intent.setComponent(cn);
        startService(intent);
    }

    private void startDownloadService() {
        try {
            LogUtils.logd("LeTianPaiMainActivity", "startDownloadService: ");
            Intent intent = new Intent();
            ComponentName cn = new ComponentName("com.letianpai.robot.downloader", "com.letianpai.robot.downloader.service.DownloadService");
            intent.setComponent(cn);
            startService(intent);
        }catch (Exception e){

        }
    }

    private void startAmazonAudioService() {
        LogUtils.logd("LeTianPaiMainActivity", "startAmazonAudioService: ");
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.geeui.lex", "com.geeui.lex.services.BotService");
        intent.setComponent(cn);
        startService(intent);
    }

    private void inits() {
        initView();
    }

    private void showDisplayViews(String viewName) {
        Message message = new Message();
        message.what = SHOW_DISPLAY_VIEW;
        message.obj = viewName;
        mHandler.sendMessage(message);
    }

    private void showBlack(boolean isShow) {
        Message message = new Message();
        message.what = SHOW_REMOTE_BLACK;
        message.obj = isShow;
        mHandler.sendMessage(message);
    }

    private void showChargingView() {
        Message message = new Message();
        message.what = SHOW_CHARGING;
        mHandler.sendMessageDelayed(message, 1500);
    }

    private void showBlackView() {
        robotExpressionViewGif.setVisibility(View.GONE);
        alertTime.setVisibility(View.GONE);
        sleep.setVisibility(View.GONE);
        hideRobotSleepMode();
    }

    private void hideBlackView() {
        robotExpressionViewGif.setVisibility(View.GONE);
        alertTime.setVisibility(View.GONE);
        sleep.setVisibility(View.GONE);
    }

    private void showDisplayView(Message msg) {
        robotExpressionViewGif.setVisibility(View.GONE);
        alertTime.setVisibility(View.GONE);
        sleep.setVisibility(View.GONE);
        hideRobotSleepMode();
        ModeChangeCallback.getInstance().setModeChange(ViewModeConsts.VM_DISPLAY_MODE);
        showChargingView();
    }


    private void initView() {
        mHandler = new ChangeExpressionHandler(LeTianPaiMainActivity.this);
        robotExpressionViewGif = findViewById(R.id.rview);
        alertTime = findViewById(R.id.tv_alarm_time);
        displayModesView = findViewById(R.id.display_modes_view);

        shutdownView = findViewById(R.id.shutdown);
        sleep = findViewById(R.id.sleep);
        robotSleepAnimationView = findViewById(R.id.robot_sleep_view);
    }

    private class ChangeExpressionHandler extends android.os.Handler {
        private final WeakReference<Context> context;

        public ChangeExpressionHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_MAIN_IMAGE:
                    break;
                case SHOW_DISPLAY_VIEW:
                    Log.e("onDisplayViewShow", "onDisplayViewShow ======= 7 =====================");
                    showDisplayView(msg);
                    break;
                case SHOW_REMOTE_CONTROL_VIEW:
                    showRemoteControlView();
                    break;
                case SHOW_REMOTE_BLACK:
                    if ((boolean) msg.obj == true) {
                        showBlackView();
                    } else {
                        hideBlackView();
                    }
                    break;

                case UPDATE_ALARM_TIME:
                    updateAlarmText((String) msg.obj);
                    break;
                case SHUTDOWN:
                    showShutdown();
                    break;
                case SHOW_CHARGING:
                    if ((robotSleepAnimationView.getVisibility() != View.VISIBLE) && (robotExpressionViewGif.getVisibility() != View.VISIBLE)) {
                        //TODO setChargingShow
                        showCharging();
                    }
                    break;
            }
        }
    }


    private void updateAlarmText(String obj) {
        alertTime.setText(obj);
    }

    private void showRemoteControlView() {

        robotExpressionViewGif.setVisibility(View.GONE);
        alertTime.setVisibility(View.GONE);
        sleep.setVisibility(View.GONE);
        hideRobotSleepMode();

    }

    private void showRobotSleepMode() {
        //隐藏默认的表情GIF
        displayModesView.hideViews();
        //播放睡眠动画表情
        robotSleepAnimationView.setVisibility(View.VISIBLE);
        robotSleepAnimationView.playAnimationView(FaceConsts.FACE_SLEEP);
    }

    private void showRobotDeepSleepMode() {
        //隐藏默认的表情GIF
        displayModesView.hideViews();
        //播放睡眠动画表情
        robotSleepAnimationView.setVisibility(View.VISIBLE);
        robotSleepAnimationView.playAnimationView(FaceConsts.FACE_DEEP_SLEEP);
    }

    private void hideRobotSleepMode() {
        robotSleepAnimationView.setVisibility(View.GONE);
        robotSleepAnimationView.stopAnimationView();
    }


    private void showShutdown() {
        shutdownView.setVisibility(View.VISIBLE);
        hideRobotSleepMode();
    }

}
