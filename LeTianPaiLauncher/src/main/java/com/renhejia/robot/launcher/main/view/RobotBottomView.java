package com.renhejia.robot.launcher.main.view;

import android.content.Context;
import android.util.AttributeSet;

import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcherbusinesslib.ui.views.AbstractMainView;
import com.renhejia.robot.launcherbusinesslib.ui.views.BottomView;

public class RobotBottomView extends BottomView {
    public RobotBottomView(Context context) {
        super(context);
    }

    public RobotBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RobotBottomView(AbstractMainView view, Context context) {
        super(view, context);
    }

    @Override
    protected void initView() {
        super.initView();
        setShutDirect(UP_TO_DOWN);
        inflate(mContext, R.layout.robot_bottom_view, this);
    }
}
