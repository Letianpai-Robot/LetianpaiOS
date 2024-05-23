package com.renhejia.robot.launcher.main.view;

import android.content.Context;
import android.util.AttributeSet;

import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcherbusinesslib.ui.views.RightView;

/**
 * @author liujunbin
 */
public class RobotRightView extends RightView {

    private RobotMainView watchMainView;

    public RobotRightView(Context context) {
        super(context);
    }

    public RobotRightView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotRightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updateData() {

    }

    public RobotRightView(RobotMainView view, Context context) {
        super(view, context);
        this.watchMainView = view;
    }


    @Override
    protected void initView() {
        super.initView();
        inflate(mContext, R.layout.robot_right_view, this);
    }

    @Override
    protected void downToUp() {

    }

    @Override
    protected void upToDown() {

    }

}
