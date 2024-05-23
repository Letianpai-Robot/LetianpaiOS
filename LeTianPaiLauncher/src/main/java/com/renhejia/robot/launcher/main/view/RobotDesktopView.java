package com.renhejia.robot.launcher.main.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;

import com.renhejia.robot.display.SpineSkinView;
import com.renhejia.robot.expression.ui.view.RobotExpressionView;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcherbusinesslib.ui.views.DesktopView;

public class RobotDesktopView extends DesktopView {
    private Context mContext;
    private SpineSkinView mSpineSkinView;
    private RobotExpressionView expressionView;
//    private static String SKIN_PATH = "skin/skin_101";

    public RobotDesktopView(Context context) {
        super(context);
        init(context);
    }

    public RobotDesktopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RobotDesktopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void unregisterActivationReceiver() {

    }

    private void init(Context context) {
        this.mContext = context;
        inflate(mContext, R.layout.robot_desktop_view, this);
        initView();
        resizeView();
        loadClockView();

    }

    private void resizeView() {
    }

    private void initView() {
        mSpineSkinView = findViewById(R.id.ssv_view);
//        expressionView = findViewById(R.id.expression_view);
    }

    private void loadClockView() {
//        mSpineSkinView.loadSkin(SKIN_PATH);
    }


    public void onClick(Point pt) {

    }

}
