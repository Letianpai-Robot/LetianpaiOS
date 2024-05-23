package com.renhejia.robot.launcherbusinesslib.ui.views;

import android.content.Context;
import android.util.AttributeSet;

public abstract class RightView extends BaseView {

    public RightView(Context context) {
        super(context);
    }

    public RightView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RightView(AbstractMainView view, Context context) {
        super(context);
    }

    public abstract void updateData();

    @Override
    protected void initView() {
        super.initView();
        setShutDirect(LEFT_TO_RIGHT);
    }
}
