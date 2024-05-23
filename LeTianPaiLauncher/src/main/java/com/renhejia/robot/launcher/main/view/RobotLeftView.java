package com.renhejia.robot.launcher.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.renhejia.robot.commandlib.log.LogUtils;

//import com.renhejia.robot.expression.view.RobotExpressionView;
import com.renhejia.robot.expression.ui.view.RobotExpressionView;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcherbusinesslib.ui.views.LeftView;

/**
 * @author liujunbin
 */
public class RobotLeftView extends LeftView {

    private RobotMainView watchMainView;
    private RobotExpressionView robotExpressionView;

    public RobotLeftView(Context context) {
        super(context);
    }

    public RobotLeftView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotLeftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RobotLeftView(RobotMainView view, Context context) {
        super(view, context);
        this.watchMainView = view;
    }

    @Override
    protected void initView() {
        super.initView();
        inflate(mContext, R.layout.robot_left_view, this);
        robotExpressionView = findViewById(R.id.expression_view);
    }


    public void onHide(){
        Log.d("VoiceServiceView", "=== onHide()");

//        if (game.getScreen() != null) {
//            ((BaseScreen) game.getScreen()).setViewShowing(false);
//            ((BaseScreen) game.getScreen()).onHide();
//        }
    }


    public void hideLeftView(){
        watchMainView.onFlitVoiceService();
    }



    @Override
    public void hideView() {
        super.hideView();
        watchMainView.onFlitVoiceService();
    }
}
