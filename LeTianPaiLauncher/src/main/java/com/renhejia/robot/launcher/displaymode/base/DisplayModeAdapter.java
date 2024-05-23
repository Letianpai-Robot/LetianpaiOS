package com.renhejia.robot.launcher.displaymode.base;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;



import java.util.List;

/**
 * 显示模式adapter
 *
 * @author liujunbin
 */
public class DisplayModeAdapter extends PagerAdapter {
    private List<View> displayModeViews;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }



}
