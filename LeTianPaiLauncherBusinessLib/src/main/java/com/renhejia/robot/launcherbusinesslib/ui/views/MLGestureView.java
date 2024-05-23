package com.renhejia.robot.launcherbusinesslib.ui.views;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MLGestureView extends View {

    private static final String TAG = "MLGenstureView";

    public static final int UP_TO_DOWN = 1;
    public static final int LEFT_TO_RIGHT = 2;
    public static final int DOWN_TO_UP = 3;
    public static final int RIGHT_TO_LEFT = 4;
    public static final int SCROLL_LAST_POINT_NUM = 2;

    private static final int LONGPRESS_MIN_TIME = 800;
    private static final int FLIT_MAX_TIME = 500;
    private static final int DOUBLECLICK_MAX_TIME = 600;

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
    private int width = 0;
    private int height = 0;

    private Rect mRect1 = new Rect(0, 0, 0, 0);
    private Rect mRect2 = new Rect(0, 0, 0, 0);
    private Rect mRect3 = new Rect(0, 0, 0, 0);

//    private MLMainView mDelegate;

    private AbstractMainView mDelegate;

    private ArrayList<Point> mTouchPoints = new ArrayList<Point>();

    public MLGestureView(Context context) {
        super(context);
        init(context);
    }

    public MLGestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setBackgroundColor(0x00000000);
    }

    public void setDelegate(AbstractMainView delegate) {
        mDelegate = delegate;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                Log.e("view_test", "MotionEvent.ACTION_DOWN: " + "X: " + x + " Y: " + y);
                mIsLongPress = false;
                mIsScrolling = false;
                mDownPoint.set(x, y);
                mIsScrollStartPoint = (mRect1.contains(x, y) && !mRect2.contains(x, y));
                mTouchPoints.clear();

                mTouchPoints.add(new Point(x, y));
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE: {
                Log.e("view_test", "MotionEvent.ACTION_MOVE: " + "X: " + x + " Y: " + y);
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
            break;

            case MotionEvent.ACTION_UP: {
                Log.e("view_test", "MotionEvent.ACTION_UP: " + "X: " + x + " Y: " + y);
                mTouchPoints.add(new Point(x, y));

                int lenX = Math.abs(x - mDownPoint.x);
                int lenY = Math.abs(y - mDownPoint.y);
                int maxLen = Math.max(lenX, lenY);

                if (mIsScrolling) {

                    if (maxLen > mFitMinDist && event.getEventTime() - event.getDownTime() < FLIT_MAX_TIME) {
                        onScrollFinish(mScrollDirect);
                    } else {
                        if (maxLen < mScrollMinDist) {
                            onScrollCancel(mScrollDirect);
                        } else {
                            int nDistX = getLastFivePointXDist(mTouchPoints);
                            int nDistY = getLastFivePointYDist(mTouchPoints);
                            if ((mScrollDirect == LEFT_TO_RIGHT && nDistX > 0) ||
                                    (mScrollDirect == RIGHT_TO_LEFT && nDistX < 0) ||
                                    (mScrollDirect == UP_TO_DOWN && nDistY > 0) ||
                                    (mScrollDirect == DOWN_TO_UP && nDistY < 0)) {
                                onScrollFinish(mScrollDirect);
                            } else {
                                onScrollCancel(mScrollDirect);
                            }
                        }
                    }
                } else {
                    if (maxLen > mFitMinDist && event.getEventTime() - event.getDownTime() < FLIT_MAX_TIME) {
                        if (lenX > lenY) {
                            if (x > mDownPoint.x) {
                                onFlit(LEFT_TO_RIGHT);
                            } else {
                                onFlit(RIGHT_TO_LEFT);
                            }
                        } else {
                            if (y > mDownPoint.y) {
                                onFlit(UP_TO_DOWN);
                            } else {
                                onFlit(DOWN_TO_UP);
                            }

//                            if (y > mDownPoint.y) {
//                                int yy = (width * 2 / 3);
//                                LogUtils.logi("view_test1", "yy : " + yy);
//                                LogUtils.logi("view_test1", "x0 : " + x);
//                                if (x > yy) {
//                                    onFlit(UP_TO_DOWN_RIGHT);
//                                } else {
//                                    onFlit(UP_TO_DOWN);
//                                }
//
//                            } else {
//                                onFlit(DOWN_TO_UP);
//                            }
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
        return true;
    }

    public void onUnknown(Point pt) {
        mDelegate.onUnknown(pt);
    }

    public void onClick(Point pt) {
        mDelegate.onClick(pt);
    }

    public void onDoubleClick(Point pt) {
        mDelegate.onDoubleClick(pt);
    }

    public void onLongPress(Point pt) {
        mDelegate.onLongPress(pt);
    }

    public void onScrollBegin(int direct) {
        Log.e("view_test1", "onScrollBegin ==== 1");
        mDelegate.onScrollBegin(direct);
    }

    public void onScrolling(int direct, Point pt) {
        mDelegate.onScrolling(direct, pt);
    }

    public void onScrollFinish(int direct) {
        mDelegate.onScrollFinish(direct);
    }

    public void onScrollCancel(int direct) {
        mDelegate.onScrollCancel(direct);
    }

    public void onFlit(int direct) {
        mDelegate.onFlit(direct);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        int nSpace = Math.min(w, h) / 10;

        mClickMaxDist = nSpace * 2;
        mFitMinDist = Math.min(w, h) / 4;
        mDoubleClickMaxDist = nSpace * 2;
        mLongPressMaxDist = nSpace * 2;
        mScrollMinDist = Math.min(w, h) / 2;

//        LogUtils.logi("view_test","nSpace: "+ nSpace);
//        LogUtils.logi("view_test","w: "+ w);
//        LogUtils.logi("view_test","h: "+ h);
//        LogUtils.logi("view_test","mClickMaxDist: "+ mClickMaxDist);
//        LogUtils.logi("view_test","mFitMinDist: "+ mFitMinDist);
//        LogUtils.logi("view_test","mDoubleClickMaxDist: "+ mDoubleClickMaxDist);
//        LogUtils.logi("view_test","mLongPressMaxDist: "+ mLongPressMaxDist);
//        LogUtils.logi("view_test","mScrollMinDist: "+ mScrollMinDist);

        mRect1.set(0, 0, w, h);
        mRect2.set(nSpace, nSpace, w - nSpace, h - nSpace);
        mRect3.set(nSpace * 2, nSpace * 2, w - nSpace * 2, h - nSpace * 2);
    }
}
