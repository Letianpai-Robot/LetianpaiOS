package com.renhejia.robot.launcher.countdown;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.gesturefactory.util.GestureConsts;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.dispatch.gesture.GestureCallback;

import java.lang.ref.WeakReference;

/**
 * @author liujunbin
 */
public class CountdownView extends RelativeLayout {

    private Context mContext;
    private TextView countDownTime;
    private TextView countdownFinish;
    private ImageView clock;
    private TextView title;
    private CountDownTimer countDownTimer;
    private CountDownTimer clockCountDownTimer;
    private final int UPDATE_COUNT_DOWN = 1;
    private final int FINISH_COUNT_DOWN = 2;
    private UpdateCountDownHandler updateCountDownHandler;
    private long mHour,mMinute,mSecond;

    public CountdownView(Context context) {
        super(context);
        init(context);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        updateCountDownHandler = new UpdateCountDownHandler(context);
        inflate(context, R.layout.robot_countdown_view, this);
        countDownTime = findViewById(R.id.time_count_down);
        countdownFinish = findViewById(R.id.time_count_down_finish);
        title = findViewById(R.id.tv_countdown_title);
        clock = findViewById(R.id.clock);
//        setCountDownTime("00:05:00");
    }

    private void startCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    public void setCountDownTime(String time) {
        title.setVisibility(View.VISIBLE);
        countDownTime.setVisibility(View.VISIBLE);
        clock.setVisibility(View.GONE);
        countdownFinish.setVisibility(View.GONE);

        long countTime = convertTime(time);
        initCountDownTimer(countTime, 1000);
        countDownTime.setText(time);
        startCountdownTimer();

    }

    private long convertTime(String time) {
        Log.e("letianpai_1356789", "time: " + time);
        String[] times = time.split(":");
        if (times == null && times.length < 3) {
            return 0;
        } else {
            long hour, minute, second;
            if (times[0].equals("00")) {
                hour = 0;
            } else {
                hour = Long.parseLong(times[0]) * 1000 * 60 * 60;
            }
            mHour = hour;

            if (times[1].equals("00")) {
                minute = 0;
            } else {
                minute = Long.parseLong(times[1]) * 1000 * 60;
            }
            mMinute = minute;

            if (times[2].equals("00")) {
                second = 0;
            } else {
                second = Long.parseLong(times[2]) * 1000;
            }
            mSecond = second;
            Log.e("letianpai_1356789","hour + minute + second: "+ hour + minute + second);
            return hour + minute + second;
        }
    }

    public void initCountDownTimer(long millisInFuture, long countDownInterval) {

        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("letianpai_1356789", "millisUntilFinished: " + millisUntilFinished);
                updateCountDown(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                finishCountDown();

            }
        };

        clockCountDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                GestureCallback.getInstance().setGesture(GestureConsts.GESTURE_STAND_RESET);

            }
        };


    }

    private void updateCountDown(long millisUntilFinished) {
        Message message = new Message();
        message.what = UPDATE_COUNT_DOWN;
        message.obj = millisUntilFinished;
        updateCountDownHandler.sendMessage(message);

    }

    private void finishCountDown() {
        Message message = new Message();
        message.what = FINISH_COUNT_DOWN;
        updateCountDownHandler.sendMessage(message);

    }

    private void updateCountDownView(Message message) {
        if (message.obj == null) {
            return;
        }
        long time = (long) message.obj;
        Log.e("letianpai_1356789", "time: " + time);
        long hour = time / 1000 / 60 / 60;
        long minute = time / 1000 / 60;
        long second = time / 1000 % 60;

        Log.e("letianpai_1356789", "hour: " + hour);
        Log.e("letianpai_1356789", "minute: " + minute);
        Log.e("letianpai_1356789", "second: " + second);
        countDownTime.setText(getCorrectString(hour) + ":" + getCorrectString(minute) + ":" + getCorrectString(second));

    }

    private String getCorrectString(long number) {
        if (number < 10){
            return "0" + number;
        }else{
            return "" + number;
        }
    }

    private void finishCountDownView(Message message) {
        Log.e("letianpai", "time_finished");
        GestureCallback.getInstance().setGesture(GestureConsts.GESTURE_COUNT_DOWN);
        title.setVisibility(View.GONE);
        countDownTime.setVisibility(View.GONE);
        clock.setVisibility(View.VISIBLE);
        countdownFinish.setVisibility(View.VISIBLE);
        setFinishText(mHour,mMinute,mSecond);
        clockCountDownTimer.start();


    }

    private class UpdateCountDownHandler extends android.os.Handler {
        private final WeakReference<Context> context;

        public UpdateCountDownHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_COUNT_DOWN:
                    updateCountDownView(msg);
                    break;

                case FINISH_COUNT_DOWN:
                    finishCountDownView(msg);
                    break;

            }
        }
    }

    public void setFinishText(long hour, long minute, long second) {
        String hourStr = null;
        String minuteStr = null;
        String secondStr = null;
        if (hour == 0){
            hourStr ="";
        }else{
            hourStr = hour + mContext.getString(R.string.hour) ;
        }
        if (minute == 0){
            minuteStr ="";
        }else{
            minuteStr = minute + mContext.getString(R.string.minute) ;
        }
        if (second == 0){
            secondStr ="";
        }else{
            secondStr = second + mContext.getString(R.string.second) ;
        }
//        String result = String.format(mContext.getString(R.string.countdown_finish), hourStr, minuteStr,secondStr);
        String result = mContext.getString(R.string.countdown_finish1);
        countdownFinish.setText(result);

    }
}
