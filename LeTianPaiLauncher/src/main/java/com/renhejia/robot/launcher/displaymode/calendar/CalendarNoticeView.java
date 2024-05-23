package com.renhejia.robot.launcher.displaymode.calendar;

import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.letianpai.robot.notice.general.parser.GeneralInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.displaymode.callback.CalendarNoticeInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.utils.GlideCircleTransform;
import com.renhejia.robot.launcherbaselib.storage.manager.LauncherConfigManager;

import java.lang.ref.WeakReference;

/**
 * 日历提醒
 */
public class CalendarNoticeView extends RelativeLayout {
    private Context mContext;
    private TextView noticeTime1;
    private TextView noticeName1;
    private TextView noticeTime2;
    private TextView noticeName2;
    private TextView noticeTime3;
    private TextView noticeName3;
    private Gson gson;
    private UpdateViewHandler mHandler;
    private static final int UPDATE_STATUS = 111;
    private CalenderInfo mCalenderInfo;


    public CalendarNoticeView(Context context) {
        super(context);
        init(context);
        fillData();
        addListeners();

    }

    private void fillData() {
        String calendar = LauncherConfigManager.getInstance(mContext).getRobotCalendar();
        CalenderInfo calenderInfo = gson.fromJson(calendar,CalenderInfo.class);
        updateViews(calenderInfo);
    }

    private void updateViews(CalenderInfo calenderInfo) {
        if (calenderInfo != null
                && calenderInfo.getData().getMemo_list() != null
                && calenderInfo.getData().getMemo_list().length > 0){
            if (calenderInfo.getData().getMemo_list()[0] != null && calenderInfo.getData().getMemo_list()[0].getMemo_title() != null && calenderInfo.getData().getMemo_list()[0].getMemo_time_label() != null ){
                Log.e("letianpai_123456"," ====== canlendar 0=========");
                noticeName1.setText(calenderInfo.getData().getMemo_list()[0].getMemo_title() );
                noticeTime1.setText(calenderInfo.getData().getMemo_list()[0].getMemo_time_label());
            }
            if (calenderInfo.getData().getMemo_list()[1] != null && calenderInfo.getData().getMemo_list()[1].getMemo_title() != null && calenderInfo.getData().getMemo_list()[1].getMemo_time_label() != null ){
                Log.e("letianpai_123456"," ====== canlendar 1=========");
                noticeName2.setText(calenderInfo.getData().getMemo_list()[1].getMemo_title() );
                noticeTime2.setText(calenderInfo.getData().getMemo_list()[1].getMemo_time_label());
            }
            if (calenderInfo.getData().getMemo_list()[2] != null && calenderInfo.getData().getMemo_list()[2].getMemo_title() != null && calenderInfo.getData().getMemo_list()[2].getMemo_time_label() != null ){
                Log.e("letianpai_123456"," ====== canlendar 2=========");
                noticeName3.setText(calenderInfo.getData().getMemo_list()[2].getMemo_title() );
                noticeTime3.setText(calenderInfo.getData().getMemo_list()[2].getMemo_time_label());
            }
        }
    }

    private void updateCalenderInfo(CalenderInfo calenderInfo) {
        this.mCalenderInfo = calenderInfo;
    }

    private void addListeners() {
        CalendarNoticeInfoUpdateCallback.getInstance().setCalendarInfoListener(new CalendarNoticeInfoUpdateCallback.RobotInfoListener() {
            @Override
            public void updateNotice(CalenderInfo calenderInfo) {
                //CalendarData{event_total=3, has_more=false, memo_list=[CalenderItem{memo_title='去超市买菜，要买萝卜和西红柿。', memo_time=1682582038, memo_time_label='今天 15:00'}, CalenderItem{memo_title='去户外遛狗30分钟', memo_time=1682585638, memo_time_label='今天 18:00'}, CalenderItem{memo_title='起床俯卧撑20个', memo_time=1682589238, memo_time_label='明天 8:00'}]}
//                updateViews(calenderInfo);
                updateCalenderInfo(calenderInfo);
                update();
//

            }
        });
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
        mHandler = new UpdateViewHandler(context);
        inflate(context, R.layout.robot_display_calendar,this);
        noticeName1 = findViewById(R.id.tv_notice_name1);
        noticeTime1 = findViewById(R.id.tv_notice_time1);
        noticeName2 = findViewById(R.id.tv_notice_name2);
        noticeTime2 = findViewById(R.id.tv_notice_time2);
        noticeName3 = findViewById(R.id.tv_notice_name3);
        noticeTime3 = findViewById(R.id.tv_notice_time3);
    }

    public CalendarNoticeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarNoticeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


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
                    updateViews(mCalenderInfo);
                    break;
            }
        }
    }

    //WeatherData{province='北京', city='北京城区', town='', wea='多云', wea_img='', tem='16', tem_max='22', tem_min='13', win='北', win_speed='≤3', hourWeather=null}
  
}
