package com.renhejia.robot.launcher.main.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.renhejia.robot.guidelib.qrcode.QRCodeView;
import com.renhejia.robot.launcherbusinesslib.ui.views.AbstractMainView;


public class RobotMainView extends AbstractMainView {

//    private RobotTopView robotTopView;
    private QRCodeView qrCodeView;
    private RobotBottomView robotBottomView;
    private RobotLeftView robotLeftView;
    private RobotRightView robotRightView;
//    private RobotTestAnimationView robotTestAnimationView;
    private RobotDesktopView robotDesktopView;

    public RobotMainView(Context context) {
        super(context);
    }

    public RobotMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotMainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RobotMainView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void responseChangedSpineSkin(String skinPath) {

    }

    @Override
    protected void fillWatchViews() {

        robotDesktopView = new RobotDesktopView(mContext);
//        robotTopView = new RobotTopView(mContext);
        qrCodeView = new QRCodeView(mContext);
        robotBottomView = new RobotBottomView(mContext);
        robotLeftView = new RobotLeftView(mContext);
//        robotRightView = new RobotRightView(mContext);
//        robotTestAnimationView = new RobotTestAnimationView(mContext);

        setDesktopView(robotDesktopView);
//        setTopView(robotTopView);
        setTopView(qrCodeView);
        setBottomView(robotBottomView);
        setLeftView(robotLeftView);
        setRightView(robotRightView);
//        setRightView(robotTestAnimationView);



    }

    @Override
    protected void responseOnClick(Point pt) {
        robotDesktopView.onClick(pt);
    }

    @Override
    protected void responseLongPressOnWatchView() {
        //TODO 预留切换页面入口

    }

    @Override
    protected void updateData() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (QSettings.isDisableMode(mContext)) {
          if (false){
                showClassLimitDialog();
//                return true;
                return false;
            }
        }

        return super.dispatchTouchEvent(ev);
    }


}
