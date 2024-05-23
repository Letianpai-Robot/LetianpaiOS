package com.renhejia.robot.launcher.displaymode.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


import com.renhejia.robot.launcher.R;

/**
 * 显示模式基类
 *
 * @author liujunbin
 */
public class DisplayModeBaseView extends RelativeLayout {

    public DisplayModeBaseView(Context context) {
        super(context);
        initView(context);
    }

    public DisplayModeBaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DisplayModeBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public DisplayModeBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    protected void initView(Context context) {
        inflate(context, R.layout.robot_test_animation_view,this);

    }



}
