package com.renhejia.robot.launcherbusinesslib.ui.views;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public abstract class BaseView extends RelativeLayout {

    protected Context mContext;

    public static final int UP_TO_DOWN = 1;
    public static final int LEFT_TO_RIGHT = 2;
    public static final int DOWN_TO_UP = 3;
    public static final int RIGHT_TO_LEFT = 4;
    public static final int SCROLL_LAST_POINT_NUM = 2;

    private static final int LONGPRESS_MIN_TIME = 800;
    private static final int FLIT_MAX_TIME = 500;
    private static final int DOUBLECLICK_MAX_TIME = 600;

    private int FLIT_ANIMATION_TIME = 60;

    private int mShutDirect = UP_TO_DOWN;

    private int mWidth = 0;
    private int mHeight = 0;
    private int mLongPressMaxDist;
    private int mClickMaxDist;
    private int mFitMinDist;
    private int mDoubleClickMaxDist;
    private int mScrollMinDist;

    private Point mDownPoint = new Point(-1, -1);
    private long mLastClickTime = -1;
    private Point mLastDownPoint = new Point(-1, -1);
    private boolean mIsLongPress = false;

    private boolean mIsScrollStartPoint = false;
    private boolean mIsScrolling = false;
    private int mScrollDirect = -1;

    private Rect mRect1 = new Rect(0, 0, 0, 0);
    private Rect mRect2 = new Rect(0, 0, 0, 0);
    private Rect mRect3 = new Rect(0, 0, 0, 0);

    private ArrayList<Point> mTouchPoints = new ArrayList<Point>();

    public BaseView(Context context) {
        super(context);
        init(context);
        initView();
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initView();
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initView();
    }

    protected void initView() {

    }

    protected void init(Context context) {
        this.mContext = context;
    }

    private int getLastFivePointXDist(ArrayList<Point> points) {

        int nDist = 0;
        if (points.size() > SCROLL_LAST_POINT_NUM) {
            for (int i = points.size() - SCROLL_LAST_POINT_NUM; i < points.size(); i++) {
                nDist += points.get(i).x - points.get(i - 1).x;
            }
        } else {
            nDist = points.get(points.size() - 1).x - points.get(0).x;
        }

        return nDist;
    }

    private int getLastFivePointYDist(ArrayList<Point> points) {

        int nDist = 0;
        if (points.size() > SCROLL_LAST_POINT_NUM) {
            for (int i = points.size() - SCROLL_LAST_POINT_NUM; i < points.size(); i++) {
                nDist += points.get(i).y - points.get(i - 1).y;
            }
        } else {
            nDist = points.get(points.size() - 1).y - points.get(0).y;
        }

        return nDist;
    }

    private int getMaxDist(ArrayList<Point> points) {

        int nMaxDist = 0;
        if (points.size() > 1) {
            Point begin = points.get(0);
            for (int i = 1; i < points.size(); i++) {
                nMaxDist = Math.max(nMaxDist, Math.max(Math.abs(begin.x - points.get(i).x), Math.abs(begin.y - points.get(i).y)));
            }
        }

        return nMaxDist;
    }

    public void setShutDirect(int shutDirect) {
        mShutDirect = shutDirect;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        boolean interceptEvent = false;

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mIsLongPress = false;
                mIsScrolling = false;
                mDownPoint.set(x, y);
                mIsScrollStartPoint = (mRect1.contains(x, y) && !mRect2.contains(x, y));
                mTouchPoints.clear();

                mTouchPoints.add(new Point(x, y));
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE: {
                mTouchPoints.add(new Point(x, y));

                int lenX = Math.abs(x - mDownPoint.x);
                int lenY = Math.abs(y - mDownPoint.y);
                int maxLen = Math.max(lenX, lenY);

                if (event.getEventTime() - event.getDownTime() > LONGPRESS_MIN_TIME && maxLen < mLongPressMaxDist) {
                    int nMaxDist = getMaxDist(mTouchPoints);
                    if (nMaxDist < mLongPressMaxDist) {
                        if (!mIsLongPress) {
                            mIsLongPress = true;
                            onLongPress(mDownPoint);
                        }
                    }
                }

                if (!mIsScrolling && mIsScrollStartPoint) {
                    if (mRect3.contains(x, y)) {

                        mScrollDirect = UP_TO_DOWN;

                        if (lenX > lenY) {
                            if (x > mDownPoint.x) {
                                mScrollDirect = LEFT_TO_RIGHT;
                            } else {
                                mScrollDirect = RIGHT_TO_LEFT;
                            }
                        } else {
                            if (y > mDownPoint.y) {
                                mScrollDirect = UP_TO_DOWN;
                            } else {
                                mScrollDirect = DOWN_TO_UP;
                            }
                        }

                        onScrollBegin(mScrollDirect);
                        mIsScrolling = true;
                    }
                }

                if (mIsScrolling) {
                    onScrolling(mScrollDirect, new Point(x, y));
                }
            }
            interceptEvent = false;
            break;

            case MotionEvent.ACTION_UP: {
                updateMessageIconStatus();
                mTouchPoints.add(new Point(x, y));

                int lenX = Math.abs(x - mDownPoint.x);
                int lenY = Math.abs(y - mDownPoint.y);
                int maxLen = Math.max(lenX, lenY);

                if (mIsScrolling) {

                    if (maxLen > mFitMinDist && event.getEventTime() - event.getDownTime() < FLIT_MAX_TIME) {
                        onScrollFinish(mScrollDirect);
                        interceptEvent = true;
                    } else {
                        if (maxLen < mScrollMinDist) {
                            onScrollCancel(mScrollDirect);
                            interceptEvent = true;
                        } else {
                            int nDistX = getLastFivePointXDist(mTouchPoints);
                            int nDistY = getLastFivePointYDist(mTouchPoints);
                            if ((mScrollDirect == LEFT_TO_RIGHT && nDistX > 0) ||
                                    (mScrollDirect == RIGHT_TO_LEFT && nDistX < 0) ||
                                    (mScrollDirect == UP_TO_DOWN && nDistY > 0) ||
                                    (mScrollDirect == DOWN_TO_UP && nDistY < 0)) {
                                onScrollFinish(mScrollDirect);
                                interceptEvent = true;
                            } else {
                                onScrollCancel(mScrollDirect);
                                interceptEvent = true;
                            }
                        }
                    }
                } else {
                    if (maxLen > mFitMinDist && event.getEventTime() - event.getDownTime() < FLIT_MAX_TIME) {
                        if (lenX > lenY) {
                            if (x > mDownPoint.x) {
                                onFlit(LEFT_TO_RIGHT);
                                interceptEvent = true;
                            } else {
                                onFlit(RIGHT_TO_LEFT);
                                interceptEvent = true;
                            }
                        } else {
                            if (y > mDownPoint.y) {
                                onFlit(UP_TO_DOWN);
                                interceptEvent = true;
                            } else {
                                onFlit(DOWN_TO_UP);
                                interceptEvent = true;
                            }
                        }
                    } else {
                        if (!mIsLongPress) {
                            int nMaxDist = getMaxDist(mTouchPoints);

                            if (nMaxDist < mClickMaxDist) {
                                if (event.getEventTime() - mLastClickTime < DOUBLECLICK_MAX_TIME) {
                                    if (Math.max(Math.abs(x - mLastDownPoint.x), Math.abs(y - mLastDownPoint.y)) < mDoubleClickMaxDist) {
                                        mLastClickTime = -1;
                                        onDoubleClick(new Point(x, y));
                                    } else {
                                        mLastClickTime = event.getEventTime();
                                        mLastDownPoint.set(mDownPoint.x, mDownPoint.y);
                                        onClick(new Point(x, y));
                                    }
                                } else {
                                    mLastClickTime = event.getEventTime();
                                    mLastDownPoint.set(mDownPoint.x, mDownPoint.y);
                                    onClick(new Point(x, y));
                                }
                            } else {
                                onUnknown(new Point(x, y));
                            }
                        }
                    }
                }
            }
            break;

            case MotionEvent.ACTION_CANCEL:
                if (mIsScrolling) {
                    mIsScrolling = false;
                    onScrollCancel(mScrollDirect);
                }
                break;

        }

        return interceptEvent;
    }

    public void onUnknown(Point pt) {

    }

    public void onClick(Point pt) {

    }

    public void onDoubleClick(Point pt) {

    }

    public void onLongPress(Point pt) {

    }

    public void onScrollBegin(int direct) {
        updateMessageIconStatus();

    }

    public void onScrolling(int direct, Point pt) {
        if (mShutDirect == direct) {
            if (direct == UP_TO_DOWN) {
                this.setY(pt.y);
            } else if (direct == DOWN_TO_UP) {
//                this.animate().translationY(mHeight - pt.y);
                this.animate().translationY(pt.y - mHeight);
//            } else if (direct == LEFT_TO_RIGHT) {
//                this.animate().translationX(mWidth);
            }
        }
    }


    public void onScrollFinish(int direct) {

        if (mShutDirect == direct) {
            this.animate().setDuration(FLIT_ANIMATION_TIME);

            if (direct == UP_TO_DOWN) {
                updateViewWhenDownToUp();
                this.animate().translationY(mHeight);
                upToDown();
            } else if (direct == DOWN_TO_UP) {
                this.animate().translationY(-mHeight);
                downToUp();
            } else if (direct == LEFT_TO_RIGHT) {
                Log.e("Mars11113333","MLGestureView.RIGHT_TO_LEFT: === 8");
                this.animate().translationX(mWidth);
            }
        }
    }

    protected abstract void downToUp();

    protected abstract void upToDown();

    public void onScrollCancel(int direct) {
        this.animate().setDuration(60);
        this.animate().translationY(0);
    }

    public void onFlit(int direct) {
        if (mShutDirect == direct) {
            this.animate().setDuration(FLIT_ANIMATION_TIME);
            if (direct == UP_TO_DOWN) {
                updateViewWhenDownToUp();
                this.animate().translationY(mHeight);
                upToDown();
            } else if (direct == DOWN_TO_UP) {
                updateViewWhenDownToUp();
                this.animate().translationY(-mHeight);
                downToUp();
            } else if (direct == LEFT_TO_RIGHT) {
                Log.e("Mars11113333","MLGestureView.RIGHT_TO_LEFT: === 7");
                this.animate().setDuration(60);
                this.animate().translationX(mWidth);
            }
        }
    }

    private void updateViewWhenDownToUp() {
//        DownToUpUpdateCallback.getInstance().setDownToUpUpdate();
    }

    protected void updateMessageIconStatus() {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

        int nSpace = Math.min(w, h) / 10;

        mWidth = w;
        mHeight = h;

        mClickMaxDist = nSpace * 2;
        mFitMinDist = Math.min(w, h) / 4;
        mDoubleClickMaxDist = nSpace * 2;
        mLongPressMaxDist = nSpace * 2;
        mScrollMinDist = Math.min(w, h) / 2;

        mRect1.set(0, 0, w, h);
        mRect2.set(nSpace, nSpace, w - nSpace, h - nSpace);
        mRect3.set(nSpace * 2, nSpace * 2, w - nSpace * 2, h - nSpace * 2);
    }

    protected void setWatchDesktopStatus(boolean status) {
        String PREFERENCE_KEY_IN_HOME_SCREEN = "watch_status_in_home_screen";
        if (status) {
            Settings.Global.putInt(mContext.getContentResolver(), PREFERENCE_KEY_IN_HOME_SCREEN, 1);

        } else {
            Settings.Global.putInt(mContext.getContentResolver(), PREFERENCE_KEY_IN_HOME_SCREEN, 0);
        }
    }


}
