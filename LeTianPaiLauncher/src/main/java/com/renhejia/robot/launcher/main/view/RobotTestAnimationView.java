package com.renhejia.robot.launcher.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.renhejia.robot.expression.face.FaceAnimationView;
import com.renhejia.robot.expression.face.FaceConsts;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcherbusinesslib.ui.views.RightView;

/**
 * @author liujunbin
 */
public class RobotTestAnimationView extends RightView {

    private RobotMainView watchMainView;
    private FaceAnimationView faceAnimationView;
    private int animationType;

    public RobotTestAnimationView(Context context) {
        super(context);
    }

    public RobotTestAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotTestAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updateData() {

    }

    public RobotTestAnimationView(RobotMainView view, Context context) {
        super(view, context);
        this.watchMainView = view;
    }


    @Override
    protected void initView() {
        super.initView();
        inflate(mContext, R.layout.robot_test_animation_view, this);
        faceAnimationView = findViewById(R.id.face_view);
//        initFaceListAndPlay(faceAnimationView);
        //faceAnimationView.nextAnimation();
        addListeners();


    }

    private void addListeners() {
        faceAnimationView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                faceAnimationView.nextAnimation();
            }
        });

    }

    @Override
    protected void downToUp() {

    }

    @Override
    protected void upToDown() {

    }

    public void playAnimationView(int playType) {
        if (playType == FaceConsts.FACE_SLEEP){
            playSleepAnimation(faceAnimationView);

        }else if (playType == FaceConsts.FACE_DEEP_SLEEP){
            playDeepSleepAnimation(faceAnimationView);
        }

    }

    public void stopAnimationView() {
        if (faceAnimationView != null){
            faceAnimationView.release();
        }
    }

    private void playSleepAnimation(FaceAnimationView view) {
        view.addFrameAnimation(FaceConsts.FACE_SLEEP, FaceConsts.FACE_FOLDER_SLEEP);
        view.changeAnimation(FaceConsts.FACE_SLEEP);
    }
    private void playDeepSleepAnimation(FaceAnimationView view) {
        view.addFrameAnimation(FaceConsts.FACE_DEEP_SLEEP, FaceConsts.FACE_FOLDER_DEEP_SLEEP);
        view.changeAnimation(FaceConsts.FACE_DEEP_SLEEP);
    }

}
