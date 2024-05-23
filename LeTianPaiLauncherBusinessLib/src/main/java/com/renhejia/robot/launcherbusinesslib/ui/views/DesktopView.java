package com.renhejia.robot.launcherbusinesslib.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class DesktopView extends RelativeLayout {

    public DesktopView(Context context) {
        super(context);
    }

    public DesktopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DesktopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public abstract void playUnReadMessageAnimator();
//
//    public abstract void stopUnReadMessageAnimator();
    public abstract void unregisterActivationReceiver();

}
