package com.renhejia.robot.launcher.displaymode.eventcountdown;

import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.displaymode.callback.CountDownInfoUpdateCallback;

import java.lang.ref.WeakReference;

public class EventCountdownView extends RelativeLayout {
    private Context mContext;
    private TextView countdownTime;
    private TextView countdownTitle;
    private Gson gson;
    private UpdateViewHandler mHandler;
    private static final int UPDATE_STATUS = 111;
    private CountDownListInfo mCountDownListInfo;

    public EventCountdownView(Context context) {
        super(context);
        init(context);
    }

    public EventCountdownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EventCountdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public EventCountdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
        mHandler = new UpdateViewHandler(context);
        inflate(context, R.layout.robot_display_countdown,this);
        countdownTime = findViewById(R.id.countdown_time);
        countdownTitle = findViewById(R.id.countdown_title);
        fillData();
        addEventCountListeners();
    }
    private void fillData() {
//        String countDown = LauncherConfigManager.getInstance(mContext).getRobotCountDown();
//        CountDownListInfo countDownListInfo = gson.fromJson(countDown,CountDownListInfo.class);
//        updateViews(countDownListInfo);

    }

    private void updateCountDownList(CountDownListInfo countDownListInfo) {
        this.mCountDownListInfo =countDownListInfo;
    }

    private void updateViews(CountDownListInfo countDownListInfo) {
        if (countDownListInfo != null && countDownListInfo.getData() != null && countDownListInfo.getData().getEvent_list() != null && countDownListInfo.getData().getEvent_list().length >0 ){
            if (countDownListInfo.getData().getEvent_list()[0].getEvent_title() != null){
//                if (countDownListInfo.getData().getEvent_list()[0].getEvent_title().length() >10){
//                    countdownTitle.setText((countDownListInfo.getData().getEvent_list()[0].getEvent_title()).substring(10));
//                }
                countdownTitle.setText(countDownListInfo.getData().getEvent_list()[0].getEvent_title() );
                if (countDownListInfo.getData().getEvent_list()[0].getRemain_days() == 0){
                    countdownTime.setText("今天");
                }else if(countDownListInfo.getData().getEvent_list()[0].getRemain_days() == 1){
                    countdownTime.setText("明天");
                }else if(countDownListInfo.getData().getEvent_list()[0].getRemain_days() == 2){
                    countdownTime.setText("后天");
                }else{
                    countdownTime.setText(countDownListInfo.getData().getEvent_list()[0].getRemain_days() + "天后");
                }

            }
        }
    }

    private void addEventCountListeners() {
        CountDownInfoUpdateCallback.getInstance().seCountDownInfoUpdateListener(new CountDownInfoUpdateCallback.CountDownInfoListener() {
            @Override
            public void updateCountDown(CountDownListInfo countDownListInfo) {
                updateCountDownList(countDownListInfo);
                update();
            }
        });
    }

    //event_list=[CountDownEventItem{event_title='明天是个好日子', event_time=-62135596800, remain_days=0}]}
    private void update() {
        Message message = new Message();
        message.what = UPDATE_STATUS;
        mHandler.sendMessage(message);
    }

    private class UpdateViewHandler extends android.os.Handler {
        private final WeakReference<Context> context;

        public UpdateViewHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case UPDATE_STATUS:
                    updateViews(mCountDownListInfo);
                    break;
            }
        }
    }


}
