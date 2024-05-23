package com.renhejia.robot.launcherbusinesslib.ui.views;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


public abstract class AbstractMainView extends ViewGroup {

    protected Context mContext;
    public int FLIT_ANIMATION_TIME = 60;

    private MLGestureView mGestureView;

    private View mRightView;
    private View mBottomView;
    private View mTopView;                 //系统信息页
    private View mLeftView;
    protected DesktopView mDesktopView;
    private View currentView;

    private boolean hadUpdateAppList = false;
    private String PREFERENCE_KEY_IN_HOME_SCREEN = "watch_status_in_home_screen";

    private boolean isLeftViewDisable = false;
    private boolean isRightViewDisable = false;
    private boolean isTopViewDisable = false;
    private boolean isBottomViewDisable = false;

    //    private RelativeLayout mLeftView;

    private View mScrollView = null;
    private int mWidth = 0;
    private int mHeight = 0;

    public AbstractMainView(Context context) {
        super(context);
        init(context);
    }

    public AbstractMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AbstractMainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AbstractMainView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context) {
        this.mContext = context;
        initView();
        addListeners();
        addHomeListener();
    }

    private void addHomeListener() {
//        HomeKeyCallback.getInstance().registerHomeKeyListener(new HomeKeyCallback.HomeKeyPressedListener() {
//            @Override
//            public void onHomeKeyPressed(boolean isDisableMode) {
//                if (mHeight != 0 && mWidth != 0) {
//                    if (isDisableMode) {
//                        resetViews();
//                    } else {
//                        if ((FunctionUtils.isLauncherRunningForeground(mContext))) {
//                            resetViews();
//                        }
//                    }
//                }
//            }
//        });
    }

    /**
     * 初始化页面
     */
    protected void resetViews() {
        mTopView.setY(-mHeight);
        mRightView.setX(mWidth);
        mLeftView.setX(-mWidth);
        mBottomView.setY(mHeight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setWatchDesktopStatus(true);
            }
        }, 500);


//                    mTopView.animate().setDuration(FLIT_ANIMATION_TIME);
//                    mTopView.animate().translationY(-mHeight);
//
//                    mRightView.animate().setDuration(FLIT_ANIMATION_TIME);
//                    mRightView.animate().translationX(mWidth);
//
//                    mLeftView.animate().setDuration(FLIT_ANIMATION_TIME);
//                    mLeftView.animate().translationX(-mWidth);
//
//                    mBottomView.animate().setDuration(FLIT_ANIMATION_TIME);
//                    mBottomView.animate().translationY(mHeight);
    }

    /**
     * 初始所有页面
     */
    protected void resetAllViews() {
        mGestureView = new MLGestureView(mContext);
        mGestureView.setDelegate(AbstractMainView.this);

        fillWatchViews();
        addViews();
        resetViews();
    }

    private void addViews() {
        addView(mGestureView);
        addView(mDesktopView);
        addView(mRightView);
        addView(mLeftView);
        addView(mTopView);
        addView(mBottomView);
    }

    private void addListeners() {
//        WatchSpineClockViewUpdateCallback.getInstance().setWatchSpineClockViewUpdateListener(new WatchSpineClockViewUpdateCallback.WatchSpineClockViewUpdateListener() {
//            @Override
//            public void onWatchSpineClockViewUpdate(boolean isSpine, String skinPath) {
//                if (skinPath != null) {
//                    responseChangedSpineSkin(skinPath);
//                }
//            }
//        });
//
//        UnreadMessageClickCallback.getInstance().setUnreadMessageIconClickListener(new UnreadMessageClickCallback.UnreadMessageClickListener() {
//            @Override
//            public void onMessageIconClicked() {
//                responseUnreadMessageIconOnClicked();
//                showMessageView();
//            }
//        });
//
//
//        WatchViewUpdateCallback.getInstance().setWatchViewUpdateListener(new WatchViewUpdateCallback.WatchViewUpdateListener() {
//            @Override
//            public void onWatchViewUpdate() {
//                removeAllViews();
//                resetAllViews();
//            }
//        });

    }

    /**
     * 响应未读消息图标点击事件
     */
    protected void responseUnreadMessageIconOnClicked() {
    }

    /**
     * 响应换肤
     *
     * @param skinPath
     */
    protected abstract void responseChangedSpineSkin(String skinPath);

    protected void initView() {
        mGestureView = new MLGestureView(mContext);
        mGestureView.setDelegate(AbstractMainView.this);

        fillWatchViews();
        addViews();
    }

    /**
     * 填充5个页面
     */
    protected abstract void fillWatchViews();

    protected void setDesktopView(DesktopView desktopView) {
        this.mDesktopView = desktopView;
    }

    protected void setLeftView(View leftView) {
        this.mLeftView = leftView;
    }

    protected void setRightView(View rightView) {
        this.mRightView = rightView;
    }

    protected void setTopView(View topView) {
        this.mTopView = topView;
    }

    protected void setBottomView(View bottomView) {
        this.mBottomView = bottomView;
    }

    protected void setLeftViewDisable(boolean leftViewStatus) {
        this.isLeftViewDisable = leftViewStatus;
    }

    protected void setRightViewDisable(boolean rightViewStatus) {
        this.isRightViewDisable = rightViewStatus;
    }

    protected void setTopViewDisable(boolean topViewStatus) {
        this.isTopViewDisable = topViewStatus;
    }

    protected void setBottomViewDisable(boolean bottomViewStatus) {
        this.isBottomViewDisable = bottomViewStatus;
    }

    protected boolean isAnimationRunning() {
        return mLeftView.hasTransientState() || mTopView.hasTransientState()
                || mBottomView.hasTransientState() || mRightView.hasTransientState()
                || (mRightView != null && mRightView.hasTransientState());

    }

    protected void onUnknown(Point pt) {
        if (isAnimationRunning()) {
            return;
        }

//        if (mTopViewRight == null) {
//            if (mRightView.getX() != 0 && mLeftView.getX() != 0 &&
//                    mTopView.getY() != 0 && mBottomView.getY() != 0) {
////                mTopView.getY() != 0 && mBottomView.getY() != 0) {
//                //Toast.makeText(getContext(), "onUnknown x=" + pt.x + ", y=" + pt.y, Toast.LENGTH_SHORT).show();
//
//            }
//        } else {
//            if (mRightView.getX() != 0 && mLeftView.getX() != 0 &&
//                    mTopViewRight.getY() != 0 && mTopView.getY() != 0 && mBottomView.getY() != 0) {
////                mTopView.getY() != 0 && mBottomView.getY() != 0) {
//                //Toast.makeText(getContext(), "onUnknown x=" + pt.x + ", y=" + pt.y, Toast.LENGTH_SHORT).show();
//
//            }
//        }

    }

    public void onClick(Point pt) {
        if (isAnimationRunning()) {
            return;
        }

        if (mRightView.getX() != 0 && mLeftView.getX() != 0 &&
                mTopView.getY() != 0 && mBottomView.getY() != 0) {
            responseOnClick(pt);
        }

    }

    /**
     * 响应点击
     *
     * @param pt
     */
    protected abstract void responseOnClick(Point pt);

    //    public void onDoubleClick(Point pt) {
//        if (mLeftView.hasTransientState() || mTopView.hasTransientState()
//                || mBottomView.hasTransientState() || mRightView.hasTransientState()) {
//            return;
//        }
//
//        if (mRightView.getX() != 0 && mLeftView.getX() != 0 &&
//                mTopView.getY() != 0 && mBottomView.getY() != 0) {
//            ////mClockView.onDoubleClick(pt);
//        }
//    }
//
//
//    public void onLongPress(Point pt) {
//
//        if (isAnimationRunning()) {
//            return;
//        }
//
//        if (mRightView.getX() != 0 && mLeftView.getX() != 0 &&
//                mTopView.getY() != 0 && mBottomView.getY() != 0) {
//            responseLongPress();
//        }
//    }
//
    public void onDoubleClick(Point pt) {

        if (mLeftView.hasTransientState() || mTopView.hasTransientState()
                || mBottomView.hasTransientState() || mRightView.hasTransientState()) {
            return;
        }

        if (mRightView.getX() != 0 && mLeftView.getX() != 0 &&
                mTopView.getY() != 0 && mBottomView.getY() != 0) {
            ////mClockView.onDoubleClick(pt);
        }

    }


    public void onLongPress(Point pt) {

        if (isAnimationRunning()) {
            return;
        }

        if (mRightView.getX() != 0 && mLeftView.getX() != 0 &&
                mTopView.getY() != 0 && mBottomView.getY() != 0) {
            responseLongPress();
        }

    }

    private void responseLongPress() {
//        DevUtil.vibrate(mContext,300);
        responseLongPressOnWatchView();
    }

    /**
     * 响应长按
     */
    protected abstract void responseLongPressOnWatchView();

    public void onFlit(int direct) {


        if (isAnimationRunning()) {
            return;
        }
        //checkListStatus();

        if (direct == MLGestureView.RIGHT_TO_LEFT) {

            if (mTopView.getY() != 0 && mBottomView.getY() != 0) {
                if (mLeftView.getX() == 0) {
                    mLeftView.animate().setDuration(FLIT_ANIMATION_TIME);
                    mLeftView.animate().translationX(-mWidth);

                    //TODO
                } else if (mRightView.getX() == mWidth) {
                    if (isRightViewDisable) {
                        return;
                    }
                    mRightView.animate().setDuration(FLIT_ANIMATION_TIME);
                    mRightView.animate().translationX(0);
                    setWatchDesktopStatus(false);
                }
            }

        }

        if (direct == MLGestureView.LEFT_TO_RIGHT) {

            if (mTopView.getY() != 0 && mBottomView.getY() != 0) {
                if (mRightView.getX() == 0) {
                    mRightView.animate().setDuration(FLIT_ANIMATION_TIME);
                    mRightView.animate().translationX(mWidth);
                } else if (mLeftView.getX() == -mWidth) {
                    if (isLeftViewDisable) {
                        return;
                    }

                    mLeftView.animate().setDuration(FLIT_ANIMATION_TIME);
                    mLeftView.animate().translationX(0);

                }
            }


        }

        if (direct == MLGestureView.UP_TO_DOWN) {
            if (mLeftView.getX() != 0 && mRightView.getX() != 0) {
                if (mBottomView.getY() == 0) {
                    mBottomView.animate().setDuration(FLIT_ANIMATION_TIME);
                    mBottomView.animate().translationY(mHeight);
                    updateMessageIconStatus();
                } else if (mTopView.getY() == -mHeight) {
                    if (isTopViewDisable) {
                        return;
                    }
                    Log.e("view_test", "onFlit");
                    mTopView.animate().setDuration(FLIT_ANIMATION_TIME);
                    mTopView.animate().translationY(0);
                    setWatchDesktopStatus(false);
                    updateData();

                }
            }

        }

        if (direct == MLGestureView.DOWN_TO_UP) {

            if (mLeftView.getX() != 0 && mRightView.getX() != 0) {
                if (mTopView.getY() == 0) {
                    mTopView.animate().setDuration(FLIT_ANIMATION_TIME);
                    mTopView.animate().translationY(-mHeight);
                    setWatchDesktopStatus(true);

                } else if (mBottomView.getY() == mHeight) {
                    if (isBottomViewDisable) {
                        return;
                    }
                    showMessageListView();
                }
            }


        }
    }

    protected void showMessageListView() {
//        mBottomView.getMessageList();
        Log.d("unread_event", "showMessageListView ===>> mBottomView: " + mBottomView);
        mBottomView.animate().setDuration(FLIT_ANIMATION_TIME);
        mBottomView.animate().translationY(0);
        setWatchDesktopStatus(false);
    }

    public void resetPostion() {
        if (mTopView.getY() != 0 && mTopView.getY() != -mHeight) {
            mTopView.setY(-mHeight);
        }

        if (mRightView.getX() != 0 && mRightView.getX() != mWidth) {
            mRightView.setX(mWidth);
        }
        if (mLeftView.getX() != 0 && mLeftView.getX() != -mWidth) {
            mLeftView.setX(-mWidth);
        }

        if (mBottomView.getY() != 0 && mBottomView.getY() != mHeight) {
            mBottomView.setY(mHeight);
        }
    }

    public void onScrollBegin(int direct) {
        Log.e("view_test1", "onScrollBegin ==== 2+ direct: " + direct);

        if (isAnimationRunning()) {
            return;
        }

        mScrollView = null;

        if (direct == MLGestureView.RIGHT_TO_LEFT) {

            if (mTopView.getY() != 0 && mBottomView.getY() != 0) {
                if (mLeftView.getX() == 0) {
                    mScrollView = mLeftView;
//                } else if (mAppListView.getX() == mWidth) {
//                    mScrollView = mAppListView;
                } else if (mRightView.getX() == mWidth) {
                    if (isRightViewDisable) {
                        return;
                    }
                    mScrollView = mRightView;
                }
            }


        }

        if (direct == MLGestureView.LEFT_TO_RIGHT) {
            if (mTopView.getY() != 0 && mBottomView.getY() != 0) {
                if (mRightView.getX() == 0) {
                    mScrollView = mRightView;
                } else if (mLeftView.getX() == -mWidth) {
                    if (isLeftViewDisable) {
                        return;
                    }
                    mScrollView = mLeftView;
                }
            }

        }

        if (direct == MLGestureView.UP_TO_DOWN) {

            if (mLeftView.getX() != 0 && mRightView.getX() != 0) {
                if (mBottomView.getY() == 0) {
                    mScrollView = mBottomView;
                } else if (mTopView.getY() == -mHeight) {
                    if (isTopViewDisable) {
                        return;
                    }
                    Log.e("view_test", "onScrollBegin");
                    mScrollView = mTopView;
                    updateData();
                }
            }
        }

        if (direct == MLGestureView.DOWN_TO_UP) {

            if (mLeftView.getX() != 0 && mRightView.getX() != 0) {
                if (mTopView.getY() == 0) {
                    mScrollView = mTopView;
                } else if (mBottomView.getY() == mHeight) {
                    if (isBottomViewDisable) {
                        return;
                    }
                    mScrollView = mBottomView;
                }
            }

        }
    }

    public void onScrolling(int direct, Point pt) {

        if (mScrollView == null || isAnimationRunning()) {
            return;
        }

        if (direct == MLGestureView.RIGHT_TO_LEFT) {
            if (mScrollView == mRightView) {
                mScrollView.setX(pt.x);
            }

            if (mScrollView == mLeftView) {
                mScrollView.setX(pt.x - mWidth);
            }
        }

        if (direct == MLGestureView.LEFT_TO_RIGHT) {
            if (mScrollView == mRightView) {
                mScrollView.setX(pt.x);
            }

            if (mScrollView == mLeftView) {
                mScrollView.setX(pt.x - mWidth);
            }
        }

        if (direct == MLGestureView.UP_TO_DOWN) {
            if (mScrollView == mBottomView) {
                mScrollView.setY(pt.y);
            }

            if (mScrollView == mTopView) {
                mScrollView.setY(pt.y - mHeight);
                Log.e("view_test", "onScrolling");
            }
        }


        if (direct == MLGestureView.DOWN_TO_UP) {
            if (mScrollView == mBottomView) {
                mScrollView.setY(pt.y);
            }

            if (mScrollView == mTopView) {
                mScrollView.setY(pt.y - mHeight);
            }
        }
    }

    public void onScrollFinish(int direct) {

        if (mScrollView == null || isAnimationRunning()) {
            return;
        }

        if (direct == MLGestureView.RIGHT_TO_LEFT) {
            if (mScrollView == mRightView) {
                mScrollView.setX(0);
                currentView = mRightView;
                setWatchDesktopStatus(false);
            }

            if (mScrollView == mLeftView) {
                mScrollView.setX(-mWidth);
                currentView = null;
                setWatchDesktopStatus(true);
            }
            //printView(currentView);
        }

        if (direct == MLGestureView.LEFT_TO_RIGHT) {
            if (mScrollView == mRightView) {
                mScrollView.setX(mWidth);
                currentView = null;
                setWatchDesktopStatus(true);
            }

            if (mScrollView == mLeftView) {
                mScrollView.setX(0);
                currentView = mLeftView;
                setWatchDesktopStatus(false);
            }
            //printView(currentView);
        }

        if (direct == MLGestureView.UP_TO_DOWN) {
            if (mScrollView == mBottomView) {
                mScrollView.setY(mHeight);
                currentView = null;
                setWatchDesktopStatus(true);
            }

            if (mScrollView == mTopView) {
                mScrollView.setY(0);
                Log.e("view_test", "onScrollFinish");
                currentView = mTopView;
                setWatchDesktopStatus(false);
            }
            //printView(currentView);
        }

        if (direct == MLGestureView.DOWN_TO_UP) {
            if (mScrollView == mBottomView) {
                mScrollView.setY(0);
                currentView = mBottomView;
                setWatchDesktopStatus(false);
            }

            if (mScrollView == mTopView) {
                mScrollView.setY(-mHeight);
                currentView = null;
                setWatchDesktopStatus(true);

            }

            //printView(currentView);
        }
        mScrollView = null;
    }

    public void onScrollCancel(int direct) {

        if (mScrollView == null || isAnimationRunning()) {
            return;
        }

        if (direct == MLGestureView.RIGHT_TO_LEFT) {
//            if (mScrollView == mAppListView) {
            if (mScrollView == mRightView) {
                mScrollView.setX(mWidth);
            }

            if (mScrollView == mLeftView) {
                mScrollView.setX(0);
            }
        }

        if (direct == MLGestureView.LEFT_TO_RIGHT) {
//            if (mScrollView == mAppListView) {
            if (mScrollView == mRightView) {
                mScrollView.setX(0);
            }

            if (mScrollView == mLeftView) {
                mScrollView.setX(-mWidth);
            }
        }

        if (direct == MLGestureView.UP_TO_DOWN) {
            if (mScrollView == mBottomView) {
                mScrollView.setY(0);
            }

            if (mScrollView == mTopView) {
                mScrollView.setY(-mHeight);
                Log.e("view_test", "onScrollCancel");
            }
        }


        if (direct == MLGestureView.DOWN_TO_UP) {
            if (mScrollView == mBottomView) {
                mScrollView.setY(mHeight);
            }

            if (mScrollView == mTopView) {
                mScrollView.setY(0);
            }
        }
        mScrollView = null;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mTopView.setY(-h);
        mRightView.setX(w);
        mLeftView.setX(-w);
        mBottomView.setY(h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(l, t, r, b);
        }
    }

    public void onFlitAppList() {
        if (mTopView.getY() != 0 && mBottomView.getY() != 0) {
            if (mRightView.getX() == 0) {
                mRightView.animate().setDuration(FLIT_ANIMATION_TIME);
                mRightView.animate().translationX(mWidth);
                currentView = null;
                //printView(currentView);
            }
        }
    }

    /**
     * 通过外部滑动收起VoiceServiceView
     */
    public void onFlitVoiceService() {
        if (mTopView.getY() != 0 && mBottomView.getY() != 0) {
            if (mLeftView.getX() == 0) {
                mLeftView.setX(-mWidth);
                //mLeftView.animate().setDuration(FLIT_ANIMATION_TIME);
                //mLeftView.animate().translationX(-mWidth);

                Settings.Global.putInt(mContext.getContentResolver(), "watch_status_in_home_screen", 1);
            }
        }

    }


    /**
     * 当前Launcher 的View 是否为表盘
     *
     * @return
     */
    public boolean isCurrentWatchView() {
        if (currentView == null) {
            return true;
        } else {
            return false;
        }
    }

    public void unregisterActivationReceiver() {
        if (mDesktopView != null) {
            mDesktopView.unregisterActivationReceiver();
        }
    }

    public void playUnReadMessageAnimator() {
        //TODO ：增加隐藏spine表盘逻辑
//        mDesktopView.playUnReadMessageAnimator();
    }

    public void stopUnReadMessageAnimator() {
//        mDesktopView.stopUnReadMessageAnimator();
    }

    /**
     * 机器人禁用的时候，显示这个
     */
    protected void showClassLimitDialog() {

    }

    protected void setWatchDesktopStatus(boolean status) {

    }


//    }


    /**
     * 更新数据
     */
    protected abstract void updateData();


    private void updateMessageIconStatus() {


    }


}
