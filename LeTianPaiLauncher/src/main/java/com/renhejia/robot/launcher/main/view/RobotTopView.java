package com.renhejia.robot.launcher.main.view;

import android.content.Context;
import android.util.AttributeSet;

import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcherbusinesslib.ui.views.AbstractMainView;
import com.renhejia.robot.launcherbusinesslib.ui.views.TopView;

public class RobotTopView extends TopView {

    public RobotTopView(Context context) {
        super(context);
    }

    public RobotTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RobotTopView(AbstractMainView view, Context context) {
        super(view, context);
    }

    @Override
    protected void initView() {
        super.initView();
        setShutDirect(DOWN_TO_UP);
        inflate(mContext, R.layout.robot_top_view, this);
    }
}
