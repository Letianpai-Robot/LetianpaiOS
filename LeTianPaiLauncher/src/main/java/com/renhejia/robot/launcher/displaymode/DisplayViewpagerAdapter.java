package com.renhejia.robot.launcher.displaymode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujunbin
 */
public class DisplayViewpagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> displayViews = new ArrayList<>();

    public DisplayViewpagerAdapter(Context context, List<View> views) {
        this.mContext = context;
        this.displayViews = views;
    }

    @Override
    public int getCount() {
        return displayViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = displayViews.get(position % displayViews.size());
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null){
                parent.removeView(view);
            }
            container.addView(view);
            return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if ((displayViews.size()>0)&&(position < displayViews.size())){
            container.removeView(displayViews.get(position));
        }
    }

    public void refresh(List<View> appList) {
        this.displayViews = appList;
        for (int i = 0; i < displayViews.size(); i++){
            this.displayViews.set(i,appList.get(i));
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}










