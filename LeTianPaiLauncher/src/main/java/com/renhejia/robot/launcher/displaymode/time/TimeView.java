package com.renhejia.robot.launcher.displaymode.time;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.letianpai.robot.notice.general.parser.GeneralInfo;
import com.renhejia.robot.guidelib.utils.SystemUtil;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.displaymode.callback.GeneralInfoCallback;
import com.renhejia.robot.launcher.net.GeeUINetConsts;
import com.renhejia.robot.launcher.nets.GeeUINetResponseManager;
import com.renhejia.robot.launcher.system.LetianpaiFunctionUtil;
import com.renhejia.robot.launcherbaselib.timer.TimerKeeperCallback;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeView extends LinearLayout {
    private Context mContext;
    private TextView title;
    private TextView date;
    private TextView tvHour;
    private long time;
    private TextView weather;
    private ImageView ivWeather;
    private TextView notice;
    private Gson gson;
    private TextView tvMin;
//    private static String DU = "℃";
    private static String DU = "°";
    private TextView location;
    private ImageView ivLocation;


    public TimeView(Context context) {
        super(context);
        init(context);

    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
        mHandler = new UpdateViewHandler(context);
        inflate(context, R.layout.robot_display_time,this);
        initView();
        getGeneralInfo();
        fillData();
        addListeners();
        addTimerUpdateCallback();

    }

    private void getGeneralInfo() {
        GeeUINetResponseManager.getInstance(mContext).getGeneralInfo();
//        GeeUINetResponseManager.getInstance(mContext).getWeatherInfo();
    }

//    private void fillData() {
//        String weather = LauncherConfigManager.getInstance(mContext).getRobotGeneralInfo();
//        GeneralInfo generalInfo = gson.fromJson(weather,GeneralInfo.class);
//        updateViews(generalInfo);
//
//    }

    private void fillData() {
//        String weather = LauncherConfigManager.getInstance(mContext).getRobotGeneralInfo();
        GeneralInfo generalInfo = GeeUINetResponseManager.getInstance(mContext).getGeneralInfo();
        if (generalInfo != null){
            updateViews(generalInfo);
        }

    }

    private void updateViews(GeneralInfo generalInfo) {
        if (generalInfo == null || generalInfo.getData() == null ||generalInfo.getData().getTem() == null || generalInfo.getData().getWea() == null || generalInfo.getData().getTem() == null){
            return;
        }
        Log.e("letianpai_1234","generalInfo.toString(): "+ generalInfo.toString());
        //GeneralData{wea='阴', wea_img='', tem='18', calender_total=3}
        String weatherInfo = generalInfo.getData().getWea() + " " + generalInfo.getData().getTem();
        int notices = generalInfo.getData().getCalender_total();
        tvHour.setText(getHourTime());

        tvMin.setText(getMinTime());
        if (!TextUtils.isEmpty(generalInfo.getData().getWea())){
            weather.setText(weatherInfo + DU);
            ivWeather.setVisibility(View.VISIBLE);
            fillWeatherIcon(generalInfo);
        }else{
            weather.setText("");
            ivWeather.setVisibility(View.INVISIBLE);
        }

        String city = generalInfo.getData().getCity();
//        if (!TextUtils.isEmpty(city)){
//            location.setText(city);
//            ivLocation.setVisibility(View.VISIBLE);
//        }else{
//            location.setText("");
//            ivLocation.setVisibility(View.INVISIBLE);
//        }

            location.setText("");
            ivLocation.setVisibility(View.GONE);

//        notice.setText(notices +"个日历提醒");
    }

    private void fillWeatherIcon(GeneralInfo generalInfo) {
        if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_1_FOG)) {
            ivWeather.setImageResource(R.drawable.wea1);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_2_CLOUDY)) {
            ivWeather.setImageResource(R.drawable.wea2);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_3_WIND)) {
            ivWeather.setImageResource(R.drawable.wea3);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_4_SUNNY)) {
            ivWeather.setImageResource(R.drawable.wea4);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_5_DUST)) {
            ivWeather.setImageResource(R.drawable.wea5);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_6_SMOG)) {
            ivWeather.setImageResource(R.drawable.wea6);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_7_SNOW)) {
            ivWeather.setImageResource(R.drawable.wea7);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_8_RAIN)) {
            ivWeather.setImageResource(R.drawable.wea8);
        } else {
            ivWeather.setImageResource(R.drawable.wea4);
        }
    }

        private GeneralInfo mGeneralInfo;
    private void updateViewData(GeneralInfo generalInfo) {
        this.mGeneralInfo = generalInfo;
    }

    private void addTimerUpdateCallback() {
        TimerKeeperCallback.getInstance().registerTimerKeeperUpdateListener(new TimerKeeperCallback.TimerKeeperUpdateListener() {
            @Override
            public void onTimerKeeperUpdateReceived(int hour, int minute) {
                updateTime();
            }
        });
    }

    private void initView() {
        date = findViewById(R.id.tv_date);
        tvHour = findViewById(R.id.tv_time_hour);
        tvMin = findViewById(R.id.tv_time_min);
        weather = findViewById(R.id.tv_weather);
        ivWeather = findViewById(R.id.iv_weather);
        location = findViewById(R.id.tv_location);
//        notice = findViewById(R.id.tv_notice);
        ivLocation = findViewById(R.id.iv_location);
        date.setText(getFullTime());
        tvHour.setText(getHourTime());
        tvMin.setText(getMinTime());
        String hour = getHourTime();
        String minute = getMinTime();
        Log.e("letianpai","getHourTime: "+ hour);
        Log.e("letianpai","getMinTime: "+ minute);
    }


    private String getFullTime() {
//        if (RobotDifferenceUtil.isChinese()){
//            return convertTimeFormat("yyyy年MM月dd日   E");
//        }else{
            return convertTimeFormat("yyyy/MM/dd   E");
//        }

    }

    private String getClockTime() {
        return convertTimeFormat("HH:mm");
    }
    private String get12HourTime() {
        return convertTimeFormat("hh");
    }

    private String getHourTime() {
        if (LetianpaiFunctionUtil.is24HourFormat(mContext)){
            return get24HourTime();
        }else {
            return get12HourTime();
        }

    }

    private String get24HourTime() {
        return convertTimeFormat("HH");
    }
    private String getMinTime() {
        return convertTimeFormat("mm");
    }


    private String convertTimeFormat(String strFormat) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format;
        if (SystemUtil.isRobotInChinese()){
            format = new SimpleDateFormat(strFormat, Locale.SIMPLIFIED_CHINESE);
        }else{
            format = new SimpleDateFormat(strFormat, (Locale.ENGLISH));
        }
        return format.format(date);
    }
//
//    private String convertTimeFormat(String strFormat) {
//        Date date = new Date(System.currentTimeMillis());
////        SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.getDefault());
//        SimpleDateFormat format = new SimpleDateFormat(strFormat, (Locale.ENGLISH));
//        return format.format(date);
//    }

    private void addListeners() {
        GeneralInfoCallback.getInstance().setGeneralInfoUpdateListener(new GeneralInfoCallback.GeneralInfoUpdateListener() {
            @Override
            public void onAtCmdResultReturn(GeneralInfo generalInfo) {
                updateViewData(generalInfo);
                update();
            }
        });
    }

    private static final int UPDATE_STATUS = 110;
    private static final int UPDATE_TIME = 111;

    private void update() {
        Message message = new Message();
        message.what = UPDATE_STATUS;
        mHandler.sendMessage(message);
    }
    private void updateTime() {
        Message message = new Message();
        message.what = UPDATE_TIME;
        mHandler.sendMessage(message);
        addTimeFormatChangeListeners();
    }

    private void addTimeFormatChangeListeners() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mContext.getContentResolver().registerContentObserver(
                    android.provider.Settings.System.CONTENT_URI,
                    true,
                    new ContentObserver(new Handler()) {
                        @Override
                        public void onChange(boolean selfChange) {
                            super.onChange(selfChange);
                            if (android.provider.Settings.System.getString(mContext.getContentResolver(), android.provider.Settings.System.TIME_12_24).equals("24")) {
                                // 时间格式为24小时制
                                tvHour.setText(getHourTime());

                            } else {
                                // 时间格式为12小时制
                                tvHour.setText(getHourTime());
                            }
                        }
                    }
            );
        }
    }

    private UpdateViewHandler mHandler;

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
                    updateViews(mGeneralInfo);
                    break;
                case UPDATE_TIME:
                    tvHour.setText(getHourTime());
                    tvMin.setText(getMinTime());
                    date.setText(getFullTime());
                    break;
            }
        }
    }


}
