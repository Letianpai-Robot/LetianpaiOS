package com.renhejia.robot.launcher.audioservice;

//public class LTPAudioService extends Service {
public class LTPAudioService {
//
//    private boolean isConnectService = false;
//    private ILetianpaiService iLetianpaiService;
//    private Context mContext;
//    private String TAG = "LTPAudioService";
//    private Gson gson;
//
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what==101){
//                LogUtils.logd("LTPAudioService", "handleMessage: 切换回去上一个显示状态");
//                RobotModeManager.getInstance(mContext).switchToPreviousRobotMode();
//            }
//        }
//    };
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // createNotificationChannel();
//        return super.onStartCommand(intent, flags, startId);
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//
//        super.onCreate();
//
//        init();
//        //connectService();
//
//    }
//
//    private void init() {
//        gson = new Gson();
//        initAudio();
//    }
//
//
//    private void initAudio() {
//
//        RhjAudioManager.getInstance().setInitCallback(new InitCallback() {
//            @Override
//            public void stateChange(boolean initStatus) {
////                RhjAudioManager.getInstance().setTestWakeup();
//            }
//        });
//
//        RhjAudioManager.getInstance().init(this, "05cae0d6e03f05cae0d6e03f642fb8c5");
//        RhjAudioManager.getInstance().setWakeupStateChangeCallback(stateData -> {
//            updateWakeupState(stateData);
//        });
//
//        RhjAudioManager.getInstance().setWakeupDoaCallback(doaData -> {
//            dealDoaData(doaData);
//        });
//
//        RhjAudioManager.getInstance().setMessageCallback(messageBean -> {
//            dealMessage(messageBean);
//        });
//
//        RhjAudioManager.getInstance().setCommandCallback(new CommandCallback() {
//            @Override
//            public void onCommand(String command, String data) {
//                LogUtils.logi(TAG, "command: " + command);
//                LogUtils.logi(TAG, "data: " + data);
//
//                switch (command) {
//                    case Const.RhjController.enterChatGpt:
//                        enterChatgpt();
//                        Log.i(TAG, "MainActivity dealCommand: enter chatgpt");
//                        break;
//
//                    case Const.RhjController.quitChatGpt:
//                        quitChatgpt();
//                        Log.i(TAG, "MainActivity dealCommand: quit chatgpt");
//                        break;
//
//                    default: {
//                        AudioCommand audioCommand = gson.fromJson(data, AudioCommand.class);
//                        if (audioCommand != null) {
//                            Log.i(TAG, "birthday~~~1");
//                            CommandResponseCallback.getInstance().setCommand("voice", command, audioCommand);
//                            dealCommand(command, data);
//                        }
//                        break;
//                    }
//                }
//            }
//        });
//
////        RhjAudioManager.getInstance().speak("暂未处理此命令");
//        RhjAudioManager.getInstance().setTtsStateChangeCallback(new TtsStateChangeCallback() {
//            @Override
//            public void onSpeakBegin() {
//                LogUtils.logi("", " ======= onSpeakBegin");
//            }
//
//            @Override
//            public void onSpeakEnd(String ttsId, int errorCode) {
//                LogUtils.logi("", " ======= onSpeakEnd");
//            }
//
//            @Override
//            public void onSpeakProgress(int current, int total) {
//
//            }
//
//            @Override
//            public void error(String s) {
//
//            }
//        });
//    }
//
//    /**
//     * 唤醒的角度回掉回来
//     *
//     * @param doaData {"doa":355,"wakeupWord":"嗨，小乐","wakeupType":"major"}
//     */
//    private void dealDoaData(String doaData) {
//        try {
//            JSONObject jsonObject = new JSONObject(doaData);
//            int degree = jsonObject.getInt("doa");
//            Log.i(TAG, "LTPAudioService dealDoaData: 唤醒的角度：" + degree);
//            //旋转一定的角度
//            //TODO 增加状态设置
////            CommandResponseCallback.getInstance().setCommand("voice", MCUCommandConsts.COMMAND_AUDIO_TURN_AROUND, 9);
//            //再拉起来某个应用
////            openFaceIdent();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 打开人脸识别
//     */
//    private void openFaceIdent() {
//        new Thread(() -> {
//            try {
//                //TODO 延迟应该等待sensormanager
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Log.i(TAG, "LTPAudioService openFaceIdent: ");
//            Intent intent = new Intent();
//            intent.setComponent(new ComponentName("com.ltp.ident", "com.ltp.ident.services.IdentFaceService"));
//            startService(intent);
//        }).start();
//
//    }
//
//    private void dealCommand(String command, String data) {
//        Log.i(TAG, "收到命令指令: " + command + "  data " + data);
//        switch (command) {
//            case Const.Remind.Insert:
//                Log.e("letianpai_12346: ", "addClock_times.length:0000-1 ");
//                try {
//                    JSONObject jsonObject = new JSONObject(data);
//                    String extra = jsonObject.optString("extra");
//                    RemindBean remind = gson.fromJson(extra, RemindBean.class);
//                    Log.i(TAG, "MainActivity dealCommand: remind:" + remind.getContent().get(0));
//                    Log.e("letianpai_12346: ", "addClock_times.length:0000-2 ");
//                    setRemindAction(remind);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case Const.Remind.Remove:
//                break;
//            case Const.RhjController.GoBack:
//                break;
//            case Const.RhjController.GoHome:
//                break;
//            case Const.MediaController.Play:
//                playerService.play();
//                break;
//            case Const.MediaController.Pause:
//                playerService.pause();
//                break;
//            case Const.MediaController.Stop:
//                playerService.unbindService(serviceConnection);
//                break;
//
//            case Const.MediaController.Prev:
//                playerService.prev();
//                break;
//            case Const.MediaController.Next:
//                playerService.next();
//                break;
//            case Const.RhjController.enterChatGpt:
//                enterChatgpt();
//                Log.i(TAG, "MainActivity dealCommand: enter chatgpt");
//                break;
//            case Const.RhjController.quitChatGpt:
//                quitChatgpt();
//                Log.i(TAG, "MainActivity dealCommand: quit chatgpt");
//                break;
//
//            default:
//                Log.i(TAG, "dealCommand: 未处理此命令");
////                RhjAudioManager.getInstance().speak("暂未处理此命令");
//        }
//    }
//
//
//    private void dealMessage(MessageBean messageBean) {
//        String str = "";
//        switch (messageBean.getMessage_type()) {
//            case MessageBean.TYPE_OUTPUT: {
//                str = ((MessageOutputTextBean) messageBean).toString();
//                break;
//            }
//            case MessageBean.TYPE_INPUT: {
//                str = ((MessageInputTextBean) messageBean).toString();
//                break;
//            }
//            case MessageBean.TYPE_WIDGET_CONTENT: {
//                str = ((MessageWidgetContentBean) messageBean).toString();
//                break;
//            }
//            case MessageBean.TYPE_WIDGET_LIST: {
//                str = ((MessageWidgetListBean) messageBean).toString();
//                break;
//            }
//            case MessageBean.TYPE_WIDGET_WEB: {
//                str = ((MessageWidgetWebBean) messageBean).toString();
//                break;
//            }
//            case MessageBean.TYPE_WIDGET_MEDIA: {
//                str = ((MessageMediaListBean) messageBean).toString();
//                startMediaPlayer((MessageMediaListBean) messageBean);
//                break;
//            }
//            default: {
//                ModeChangeCallback.getInstance().setModeChange(ViewModeConsts.VM_CHAT_GPT_MODE);
//            }
//        }
////        showMessageText(str);
//    }
//
//    private void enterChatgpt() {
////获取返回的结果的所有内容
//        RhjAudioManager.getInstance().setRhjDMTaskCallback(new RhjDMTaskCallback() {
//            @Override
//            public String dealResult(DmTaskResultBean dmTaskResultBean) {
//                ModeChangeCallback.getInstance().setModeChange(ViewModeConsts.VM_FUNCTION_MODE);
//                ExpressionChangeCallback.getInstance().showChatGpt();
//                Log.i(TAG, "MainActivity dealResult: " + dmTaskResultBean);
//                String videoInput = dmTaskResultBean.getDmInput();
//                Log.i(TAG, "语音输入的内容：" + videoInput);
//                return dealVideoInputText(videoInput);
//            }
//
//            @Override
//            public void dealErrorResult() {
//                Log.i(TAG, "MainActivity dealErrorResult: ");
//            }
//        });
//    }
//
//    private void quitChatgpt() {
//        Log.i(TAG, "MainActivity quitChatgpt: ======");
//        ExpressionChangeCallback.getInstance().showDisplayView(null);
//        //获取返回的结果的所有内容
//        RhjAudioManager.getInstance().setRhjDMTaskCallback(null);
//        ModeChangeCallback.getInstance().setModeChange(ViewModeConsts.VM_STANDBY_MODE);
//    }
//
//    /**
//     * 接管语音输入的内容，不在处理之后的逻辑
//     * 拿到内容之后进行处理
//     *
//     * @param videoInput
//     */
//    private String dealVideoInputText(String videoInput) {
//        RhjAudioManager.getInstance().stopDialog();
////        RhjAudioManager.getInstance().speakAndStartDialog("现在用：" + videoInput + "为关键字去访问 Chat Gpt");
//        return getChatGptInfo(videoInput);
//    }
//
//    private ArrayList<Object> list = new ArrayList<>();
//    ;
//
//    private String getChatGptInfo(String videoInput) {
//        OkHttpClient client = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            client = new OkHttpClient.Builder().connectTimeout(Duration.ofMinutes(10l)).readTimeout(Duration.ofMinutes(10l)).callTimeout(Duration.ofMinutes(10l)).build();
//        }
//        String url = "http://43.153.60.110:8080/content";
//
//        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
//
//        HashMap hashMap = new HashMap();
//        hashMap.put("role", "user");
//        hashMap.put("content", videoInput);
//
//        if (list.size() > 6) {
//            list.remove(0);
//        }
//        list.add(hashMap);
//        RequestBody body = RequestBody.create(gson.toJson(list), mediaType);
//
//        Request request = new Request.Builder().url(url).method("POST", body).addHeader("Content-Type", "application/json").build();
//
//        Log.i(TAG, "MainActivity ChatGptInfo 问题: " + videoInput);
//
//        try (Response response = client.newCall(request).execute()) {
//            String resData = response.body().string();
//            JSONObject jsonObject = new JSONObject(resData);
//            Log.i(TAG, "MainActivity ChatGptInfo 结果: " + jsonObject);
//            String result = jsonObject.getString("msg");
//            HashMap hashMap1 = new HashMap();
//            hashMap1.put("role", "assistant");
//            hashMap1.put("content", result);
//            list.add(hashMap1);
////            RhjAudioManager.getInstance().speak(result);
//            return result;
//        } catch (IOException | JSONException e) {
//            Log.i(TAG, "MainActivity GptInfo 结果: 服务器繁忙");
////            RhjAudioManager.getInstance().speak("服务器繁忙");
//            e.printStackTrace();
//            return "服务器繁忙";
////            throw new RuntimeException(e);
//        }
//
//    }
//
//
//    /**
//     * 设置闹钟
//     *
//     * @param remind object:闹钟，日程，备忘录，倒计时
//     *               {
//     *               "time": "17:00:00", //时间，三段式
//     *               "object": "闹钟", //创建对象
//     *               "period": "下午", //时间段
//     *               "absuolutely": true, //是否为绝对时间，五点为绝对时间，五分钟后为相对时间
//     *               "date": "20190222", //日期
//     *               "time_interval":"00:00:30",
//     *               "time_left":30,
//     *               "vid": "17146409489307697948", //闹钟、提醒的id，相同时间的闹钟，相同时间相同事件的提醒会被覆盖
//     *               "recent_tsp": 1550826000,
//     *               "timestamp": 1550826000
//     *               }
//     */
//    private void setRemindAction(RemindBean remind) {
//        Log.i(TAG, "MainActivity setRemindAction: " + remind.getContent().get(0));
//        if (remind != null && remind.getContent().size() > 0) {
//            for (RemindBean.Content content : remind.getContent()) {
//                switch (content.getObject()) {
//                    case "闹钟":
////                        setClock(remind);
//                        //addClock(remind.getContent().get(0).getTime());
//                        break;
//                    case "倒计时":
//                        //addCountdownTimer(remind.getContent().get(0).getTime_interval());
//                        break;
//                    case "日程":
//                        break;
//                    case "备忘录":
//                        break;
//                    default:
//                }
//            }
//        }
//    }
//
//    private void addClock(String time) {
//        Log.e("letianpai_12346: ", "addClock_times.length:1111 ");
//        String [] times = time.split(":");
//        if (times != null && times.length >2){
//            Log.e("letianpai_12346: ", "addClock_times.length: "+ times.length);
//            GeeUIAlarmManager.getInstance(LTPAudioService.this).createAlarm(Integer.valueOf(times[0]),Integer.valueOf(times[1]));
//        }
//
//    }
//    private void addCountdownTimer(String time) {
//        Log.e("letianpai_12346: ", "addClock_times.length:1111 ");
//        String [] times = time.split(":");
//        if (times != null && times.length >2){
//            Log.e("letianpai_12346: ", "addClock_times.length: "+ times.length);
////            GeeUIAlarmManager.getInstance(LTPAudioService.this).createAlarm(Integer.valueOf(times[0]),Integer.valueOf(times[1]));
//            ExpressionChangeCallback.getInstance().showCountDown(time);
//        }
//
//    }
//
//    private void setClock(RemindBean remind) {
//        /*AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
//        am.set(AlarmManager.RTC_WAKEUP, content.getTimestamp() * 1000, pendingIntent);*/
//        for (RemindBean.Content content : remind.getContent()) {
//            Log.i(TAG, "MainActivity setClock: " + content.getTimestamp());
//            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(content.getTimestamp() * 1000L);
//            //闹钟的小时
//            intent.putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY))
//                    //闹钟的分钟
//                    .putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE))
//                    //响铃时提示的信息
//                    .putExtra(AlarmClock.EXTRA_MESSAGE, "larry")
//                    //用于指定该闹铃触发时是否振动
//                    .putExtra(AlarmClock.EXTRA_VIBRATE, true)
//                    //一个 content: URI，用于指定闹铃使用的铃声，也可指定 VALUE_RINGTONE_SILENT 以不使用铃声。
//                    //如需使用默认铃声，则无需指定此 extra。
////                .putExtra(AlarmClock.EXTRA_RINGTONE, ringtoneUri)
//                    //一个 ArrayList，其中包括应重复触发该闹铃的每个周日。
//                    // 每一天都必须使用 Calendar 类中的某个整型值（如 MONDAY）进行声明。
//                    //对于一次性闹铃，无需指定此 extra
//                    .putExtra(AlarmClock.EXTRA_DAYS, new int[]{calendar.get(Calendar.DAY_OF_WEEK)})
//                    //如果为true，则调用startActivity()不会进入手机的闹钟设置界面
//                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//            startActivity(intent);
//        }
//    }
//
//
//    private void startMediaPlayer(MessageMediaListBean messageMediaListBean) {
//        Log.i(TAG, "MainActivity startMediaPlayer: ");
//        Intent intent = new Intent(LTPAudioService.this, PlayerService.class);
//        Gson gson = new Gson();
//        String json = gson.toJson(messageMediaListBean);
//        intent.putExtra("data", json);
//        if (playerService == null) {
//            bindService(intent, connectionPlayerService, Service.BIND_AUTO_CREATE);
////            startService(intent);
//        } else {
//            playerService.setNewMusic(json);
////            startService(intent);
//        }
//    }
//
//    private PlayerService playerService;
//    private ServiceConnection connectionPlayerService = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.i(TAG, "LTPAudioService onServiceConnected: " + service.getClass());
//
//            PlayerService.MyBinder myBinder = (PlayerService.MyBinder) service;
//            PlayerService p = myBinder.getService();
//            Log.i(TAG, "LTPAudioService onServiceConnected: " + p);
//            playerService = p;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            playerService = null;
//        }
//    };
//
//    private void updateWakeupState(String data) {
//        LogUtils.logd("LTPAudioService", "updateWakeupState: "+data);
//        switch (data) {
//            case AudioServiceConst.ROBOT_STATUS_SILENCE:
////                ModeChangeCallback.getInstance().setModeChange(ViewModeConsts.VM_STANDBY_MODE);
//                Log.i(TAG, "updateWakeupState:  等待唤醒...");
//                handler.sendEmptyMessageDelayed(101,3000);
//                break;
//            case AudioServiceConst.ROBOT_STATUS_LISTENING:
//                Log.i(TAG, "updateWakeupState: " + "监听中...");
//                RobotModeManager.getInstance(mContext).switchRobotMode(ViewModeConsts.VM_AUDIO_WAKEUP_MODE,1);
//                handler.removeMessages(101);
//                GestureCallback.getInstance().setGestures(GestureCenter.wakeupListenGesture(),0);
//                break;
//            case AudioServiceConst.ROBOT_STATUS_UNDERSTANDING:
//                Log.i(TAG, "updateWakeupState: " + "理解中...");
//                //showWakeupState("理解中");
//                GestureCallback.getInstance().setGestures(GestureCenter.wakeupUnderstandGesture(),0);
////                GestureCallback.getInstance().setGesture(GestureConsts.GESTURE_ASSISTANT);
//                break;
//            case AudioServiceConst.ROBOT_STATUS_SPEAKING:
//                Log.i(TAG, "updateWakeupState: " + "播放语音中...");
//                GestureCallback.getInstance().setGestures(GestureCenter.wakeupSpeakGesture(),0);
//                //showWakeupState("播放语音中");
//                break;
//            default: {
//                Log.i(TAG, "updateWakeupState: without identify state");
//                //showWakeupState("updateWakeupState: without identify state");
//            }
//        }
//    }
//
//    /**
//     * 获取系统闹钟
//     */
//    private void checkClock() {
//        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//        //获取的是系统的闹钟
//        AlarmManager.AlarmClockInfo info = am.getNextAlarmClock();
//        Log.i(TAG, "MainActivity checkClock: nextinfo " + info.getTriggerTime());
//        //或者
////        String str = Settings.System.getString(getContentResolver(), Settings.System.NEXT_ALARM_FORMATTED);
////        Log.i(TAG, "MainActivity checkClock: next" + str);
//    }
//
//
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.d("LTPAudioService", "LTPAudioService 乐天派 完成AIDLService服务");
//            iLetianpaiService = ILetianpaiService.Stub.asInterface(service);
//            try {
//                iLetianpaiService.registerCallback(bookCallBack);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//            isConnectService = true;
//        }
//
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.d("LTPAudioService", "LTPAudioService 乐天派 无法绑定aidlserver的AIDLService服务");
//            isConnectService = false;
//        }
//    };
//
//    //链接服务端
//    private void connectService() {
//        Intent intent = new Intent();
//        intent.setPackage("com.renhejia.robot.letianpaiservice");
//        intent.setAction("android.intent.action.LETIANPAI");
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//    }
//
//    //实现回调接口获取相关数据
//    private final LtpCommandCallback.Stub bookCallBack = new LtpCommandCallback.Stub() {
//
//        @Override
//        public void onRobotStatusChanged(int status) throws RemoteException {
//            LogUtils.logi("letianpai", "mcu_onRobotStatusChanged1: " + status);
//            //restorePosition();
////            walk(2,50);
////            walkForward(2);
////            consumeATCommand("AT+MOVEW,1,2,2\\r\\n ");
////            consumeATCommand("AT+EARW,1,2,2\\r\\n");
//////            consumeATCommand("AT+EARW,2,2,2\\r\\n");
//        }
//
//        @Override
//        public void onCommandReceived(LtpCommand ltpCommand) throws RemoteException {
//            if (ltpCommand != null) {
//                LogUtils.logi("letianpai", "launcher_onCommandReceived: ltpCommand.getCommand: " + ltpCommand.getCommand());
//                LogUtils.logi("letianpai", "launcher_onCommandReceived: ltpCommand.getData(): " + ltpCommand.getData());
//            } else {
//                LogUtils.logi("letianpai", "onCommandReceived: ltpCommand is null ");
//            }
//        }
//    };
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        RhjAudioManager.getInstance().unInit(this);
//    }
}
