package com.renhejia.robot.expression.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.renhejia.robot.commandlib.consts.RobotExpressionConsts;
import com.renhejia.robot.expression.R;
import com.renhejia.robot.expression.locale.LocaleUtils;

/**
 * 机器人表情
 * @author liujunbin
 */
public class RobotExpressionViewGif extends RelativeLayout implements RobotExpressionConsts {
    private Context mContext;
    private ImageView ivExpression; //
    private TextView tvExpression;  //
    private Animation animationNull;

    public RobotExpressionViewGif(Context context) {
        super(context);
        init(context);
    }

    public RobotExpressionViewGif(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RobotExpressionViewGif(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RobotExpressionViewGif(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(mContext, R.layout.robot_expression_view,this);
        initView();
    }

    /***
     * 更新
     * @param url
     */
    public void updateView(String url){
        if(url.lastIndexOf(".gif") > 0){
            Glide.with(mContext)
                    .load(url)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivExpression);
        }else {
            Glide.with(mContext)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivExpression);
        }
    }


    private void initView() {
        ivExpression = findViewById(R.id.iv_expression);
        tvExpression = findViewById(R.id.tv_expression);

        Glide.with(mContext)
                .load(R.drawable.launche_bg)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivExpression);

//        if (LocaleUtils.isChinese()){
//            Glide.with(mContext)
//                    .load(R.drawable.bluehole0415)
//                    .asGif()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(ivExpression);
//        }else{
//            Glide.with(mContext)
//                    .load(R.drawable.bluehole_en)
//                    .asGif()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(ivExpression);
//        }




//        Glide.with(mContext)
////                .load(R.drawable.blue_hole)
//                .load(R.drawable.wifi2)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .listener(new RequestListener<Integer, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception arg0, Integer arg1,
//                                               Target<GlideDrawable> arg2, boolean arg3) {
//                        return false;
//                    }
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource,
//                                                   Integer model, Target<GlideDrawable> target,
//                                                   boolean isFromMemoryCache, boolean isFirstResource) {
//                        // 计算动画时长
////                        GifDrawable drawable = (GifDrawable) resource;
////                        GifDecoder decoder = drawable.getDecoder();
////                        for (int i = 0; i < drawable.getFrameCount(); i++) {
////                            duration += decoder.getDelay(i);
////                        }
////                        //发送延时消息，通知动画结束
////                        handler.sendEmptyMessageDelayed(MESSAGE_SUCCESS,
////                                duration);
//                        return false;
//                    }
//                }) //仅仅加载一次gif动画
//                .into(new GlideDrawableImageViewTarget(ivExpression, -1));



    }



}
