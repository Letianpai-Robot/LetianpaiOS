package com.renhejia.robot.guidelib.viewpager;

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
public class GuideViewpagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> guideViews = new ArrayList<>();

    public GuideViewpagerAdapter(Context context, List<View> views) {
        this.mContext = context;
        this.guideViews = views;
    }

    @Override
    public int getCount() {
        return guideViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position < guideViews.size()){
            View view = guideViews.get(position);
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if ((guideViews.size()>0)&&(position < guideViews.size())){
            container.removeView(guideViews.get(position));
        }
    }


    public void refresh(List<View> appList) {
        this.guideViews = appList;
        for (int i = 0; i < guideViews.size(); i++){
            this.guideViews.set(i,appList.get(i));
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}










