package com.renhejia.robot.launcher.displaymode;

import android.content.Context;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.renhejia.robot.launcher.displaymode.calendar.CalendarNoticeView;
import com.renhejia.robot.launcher.displaymode.callback.DisplayInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.display.DisplayMode;
import com.renhejia.robot.launcher.displaymode.eventcountdown.EventCountdownView;
import com.renhejia.robot.launcher.displaymode.fans.FansView;
import com.renhejia.robot.launcher.displaymode.time.TimeView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujunbin
 */

public class DisplayModeView extends ViewPager {

    private Context mContext;
    private DisplayViewpagerAdapter adapter;
    private List<View> displayViews = new ArrayList<>();
    private FansView fansView;
    private TimeView timeView;
    private CalendarNoticeView noticeView;
    private EventCountdownView eventCountdownView;
    private int currentIndex =1;
    private int showPosition;
    private DisplayModeView displayModeView;
    private int realCount = 5;
    private UpdateViewHandler mHandler;
    private static final int UPDATE_STATUS = 110;
    private DisplayMode displayModes;

    public DisplayModeView(Context context) {

        super(context);
        init(context);
    }

    public DisplayModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public int getViewSize() {
        return displayViews.size();
    }

    public View getView(int i) {
        if (i< displayViews.size()){
            return displayViews.get(i);
        }else{
            return null;
        }
    }

    private void init(Context context) {
        displayModeView = DisplayModeView.this;
        this.mContext = context;
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        mHandler = new UpdateViewHandler(context);
        requestData();
        initView();
        fillData();
        addListeners();
        //addPageChangeListener();
    }

    private void addListeners() {
        DisplayInfoUpdateCallback.getInstance().seDisplayInfoUpdateListener(new DisplayInfoUpdateCallback.DisplayInfoUpdateListener() {
            @Override
            public void updateDisplayList(DisplayMode displayMode) {
                updateViewData(displayMode);
                uploadStatus();
            }
        });
    }

    private void updateViewData(DisplayMode displayMode) {
        this.displayModes = displayMode;
    }


    private void updateViews(DisplayMode displayMode) {
        if (displayMode == null){
            return;
        }
        displayViews.clear();

        if (displayMode.getGeneral_display_switch() == 1){
            displayViews.add(timeView);
        }
   
//        if (displayMode.getCalendar_display_switch() == 1){
//            displayViews.add(noticeView);
//        }
        if (displayMode.getCountdown_display_switch() == 1){
            displayViews.add(eventCountdownView);
        }
        if (displayMode.getFans_display_switch() == 1){
            displayViews.add(fansView);
        }

        fillData();
        adapter.notifyDataSetChanged();
    }



    private void addPageChangeListener() {
        displayModeView.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                int showPosition;
                if (position == 0) {
                    showPosition = realCount;
                } else if (position == realCount + 1) {
                    showPosition = 1;
                } else {
                    showPosition = position;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.e("letianpai_1111","currentIndex: "+ currentIndex);
                        if (currentIndex == 0) {
                            displayModeView.setCurrentItem(displayViews.size() -2, false);
                        }else if (currentIndex == displayViews.size() -1){
                            displayModeView.setCurrentItem(1, false);
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        if (currentIndex == realCount + 1) {
                            displayModeView.setCurrentItem(1, false);
                        } else if (currentIndex == 0) {
                            displayModeView.setCurrentItem(displayViews.size() -1, false);
                        }
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                    default:
                        break;
                }


            }
        });
    }

    private void requestData() {
        // 请求数据
    }

    private void fillData() {
//        currentIndex = 1;
//        displayViews.add(0,displayViews.get(displayViews.size()-1));
//        displayViews.add(displayViews.get(1));
//        //

        adapter = new DisplayViewpagerAdapter(mContext,displayViews);
        displayModeView.setAdapter(adapter);
        displayModeView.refreshDrawableState();
//        displayModeView.setCurrentItem(1);

    }

    private void initView() {
//        displayViews.add(new )

//        fansView = new FansView(mContext);
        timeView = new TimeView(mContext);
//        noticeView = new CalendarNoticeView(mContext);
//        weatherView = new WeatherView(mContext);
////        weatherView.setDisplayModeView(DisplayModeView.this);
//        eventCountdownView = new EventCountdownView(mContext);

//        displayViews.add(fansView);
//        displayViews.add(timeView);
//        displayViews.add(weatherView);
////        displayViews.add(noticeView);
//        displayViews.add(eventCountdownView);
//        displayViews.add(fansView);
        displayViews.add(timeView);

//        displayViews.add(0,displayViews.get(displayViews.size()-1));
//        displayViews.add(displayViews.get(1));

    }

    private void uploadStatus() {
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
                    //updateViews(displayModes);
                    break;
            }
        }
    }





}
