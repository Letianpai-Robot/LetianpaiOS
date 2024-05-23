package com.renhejia.robot.expression.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renhejia.robot.commandlib.consts.RobotExpressionConsts;
import com.renhejia.robot.expression.R;

/**
 * 机器表情基类
 * @author liujunbin
 */
public abstract class RobotMainImageBaseView extends RelativeLayout implements RobotExpressionConsts {
    protected Context mContext;
    private AnimationDrawable animation;
    private AnimationDrawable playingAnimation;
    private ImageView ivExpression;
    private TextView tvExpression;
    private Animation animationNull;
    private View topView;
    private View bottomView;
    private float topWeight = 1.0f;
    private float bottomWeight = 1.0f;
    private String desContent = "你好 乐天派";


    public RobotMainImageBaseView(Context context) {
        super(context);
        init(context);
    }

    public RobotMainImageBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RobotMainImageBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RobotMainImageBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        inflate(mContext, R.layout.robot_main_image_view,this);

        initView();
        updateViewData();
        topView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, topWeight));
        bottomView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, bottomWeight));
        if(animation != null){
            animation.setOneShot(false);
            ivExpression.setBackground(animation);
            animation.start();
        }
    }

    /**
     * 设置文字，设置 动画，设置文字位置
     */
    protected abstract void updateViewData();

    private void initView() {
        ivExpression = findViewById(R.id.iv_expression);
        tvExpression = findViewById(R.id.tv_expression);
        topView = findViewById(R.id.top_view);
        bottomView = findViewById(R.id.bottom_view);


        tvExpression.setText(desContent);

        //animation = (AnimationDrawable) getResources().getDrawable(R.drawable.blue_hole_animlist);

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

    protected void setAnimation(AnimationDrawable anim) {
        this.animation = anim;
    }

    /**
     * 设置描述文案
     * @param des
     */
    protected void setDesContent(String des) {
        this.desContent = des;
    }

    protected void setViewWeight(float tVWeight, float bVWeight) {
        this.topWeight = tVWeight;
        this.bottomWeight =bVWeight;

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
