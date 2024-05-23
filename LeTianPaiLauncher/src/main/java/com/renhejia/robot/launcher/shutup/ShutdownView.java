package com.renhejia.robot.launcher.shutup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.renhejia.robot.launcher.R;

/**
 * 关机界面
 *
 */
public class ShutdownView extends RelativeLayout {

    public ShutdownView(Context context) {
        super(context);
        init(context);
    }

    public ShutdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShutdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ShutdownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        inflate(context, R.layout.robot_shutup_view,this);
    }
}
