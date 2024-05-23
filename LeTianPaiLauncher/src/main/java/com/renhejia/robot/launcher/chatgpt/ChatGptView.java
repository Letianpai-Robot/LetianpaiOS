package com.renhejia.robot.launcher.chatgpt;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.renhejia.robot.launcher.R;

/**
 * GPTView
 *
 * @author liujunbin
 */
public class ChatGptView extends LinearLayout {

    public ChatGptView(Context context) {
        super(context);
        initView(context);
    }

    public ChatGptView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChatGptView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected void initView(Context context) {
        inflate(context, R.layout.robot_chat_gpt_view,this);

    }



}
