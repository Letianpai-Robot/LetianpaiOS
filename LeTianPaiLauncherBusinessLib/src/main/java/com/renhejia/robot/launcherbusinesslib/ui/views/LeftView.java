package com.renhejia.robot.launcherbusinesslib.ui.views;

import android.content.Context;
import android.util.AttributeSet;

public class LeftView extends BaseView {

    public LeftView(Context context) {
        super(context);
    }

    public LeftView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void downToUp() {

    }

    @Override
    protected void upToDown() {

    }

    public LeftView(AbstractMainView view, Context context) {
        super(context);
    }

    public void hideView(){

    }

    @Override
    protected void initView() {
        super.initView();
        setShutDirect(RIGHT_TO_LEFT);
    }
}
