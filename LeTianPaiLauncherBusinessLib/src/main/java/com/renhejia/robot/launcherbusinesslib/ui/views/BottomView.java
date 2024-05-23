package com.renhejia.robot.launcherbusinesslib.ui.views;

import android.content.Context;
import android.util.AttributeSet;

public class BottomView extends BaseView {


    public BottomView(Context context) {
        super(context);
    }

    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void downToUp() {

    }

    @Override
    protected void upToDown() {
        setWatchDesktopStatus(true);
    }

    public BottomView(AbstractMainView view, Context context) {
        super(context);
    }

    @Override
    protected void updateMessageIconStatus() {
        super.updateMessageIconStatus();
    }
}
