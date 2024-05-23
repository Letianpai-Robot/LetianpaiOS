package com.renhejia.robot.launcher.remote;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.renhejia.robot.commandlib.log.LogUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renhejia.robot.guidelib.utils.ViewUtils;
import com.renhejia.robot.launcher.R;

/**
 * @author liujunbin
 */
public class RemoteControlView extends LinearLayout {
    private LinearLayout remoteView;
    private ImageView imageView;
    private TextView remoteText;
    private Context mContext;

    public RemoteControlView(Context context) {
        super(context);
        initView(context);
    }

    public RemoteControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RemoteControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected void initView(Context context) {
        this.mContext = context;
        inflate(context, R.layout.robot_remote_control_view, this);
        remoteView = findViewById(R.id.root_remote);
        imageView = findViewById(R.id.remote_image);
        remoteText = findViewById(R.id.remote_text);

    }

    public void showRemoteChangeView() {

//        remoteView.getBackground().setAlpha(256);
//        remoteView.getBackground().setAlpha(256);
        remoteText.setText(R.string.remote_start);
//        LogUtils.logi("letianpai","long: "+ViewUtils.getViewHeightSize())
//        ViewUtils.resizeImageViewSize();
    }

    public void showRemoteImageView() {

        remoteView.getBackground().setAlpha(0);
        imageView.getBackground().setAlpha(0);
        remoteText.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);

    }
    public void showRemoteTextView(String text) {
//        remoteView.getBackground().setAlpha(0);
        imageView.setVisibility(View.GONE);
        remoteText.setText(text);
    }


}
