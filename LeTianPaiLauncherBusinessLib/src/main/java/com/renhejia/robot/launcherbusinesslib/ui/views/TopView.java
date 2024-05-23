package com.renhejia.robot.launcherbusinesslib.ui.views;

import android.content.Context;
import android.util.AttributeSet;

public class TopView extends BaseView {

    public TopView(Context context) {
        super(context);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TopView(AbstractMainView view, Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        setShutDirect(DOWN_TO_UP);
    }

    @Override
    protected void downToUp() {
        setWatchDesktopStatus(true);
    }

    @Override
    protected void upToDown() {
        setWatchDesktopStatus(false);

    }
}
