package com.renhejia.robot.launcher.displaymode.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;



/**
 * 显示模式
 */
public class DisplayView extends ViewPager {

    public DisplayView(@NonNull Context context) {
        super(context);
    }

    public DisplayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


}
