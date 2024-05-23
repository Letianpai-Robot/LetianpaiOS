package com.renhejia.robot.expression.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.renhejia.robot.expression.R;
import com.renhejia.robot.expression.utils.RobotExpressionConsts;

/**
 * 机器人表情
 * @author liujunbin
 */
public class RobotExpressionView extends RelativeLayout implements RobotExpressionConsts {
    private Context mContext;
    private AnimationDrawable animation;
    private AnimationDrawable playingAnimation;
    private ImageView ivExpression;

    public RobotExpressionView(Context context) {
        super(context);
        init(context);
    }

    public RobotExpressionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RobotExpressionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RobotExpressionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(mContext, R.layout.robot_expression_view,this);
        initView();
        if(animation != null){
            animation.setOneShot(false);
            ivExpression.setBackground(animation);
            animation.start();
        }
    }

    private void initView() {
        ivExpression = findViewById(R.id.iv_expression);
        animation = (AnimationDrawable) getResources().getDrawable(R.drawable.excited_expression_animlist);
        AnimationSet set = new AnimationSet(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void playingAnimation(int animationType,boolean isPlayOnce){
        if (animationType == RobotExpressionConsts.ROBOT_EXPRESSION_1){
            playingAnimation = animation;
        }

        //TODO 添加对应的表情的初始化
        if(playingAnimation != null){
            if (!isPlayOnce){
                animation.setOneShot(false);
            }
            playingAnimation.start();
        }
    }

    public void stopAnimation(){
        if (playingAnimation != null){
            playingAnimation.stop();
        }
    }



}
