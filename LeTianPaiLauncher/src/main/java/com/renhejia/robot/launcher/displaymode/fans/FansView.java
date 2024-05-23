package com.renhejia.robot.launcher.displaymode.fans;

import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.displaymode.callback.FansInfoCallback;
import com.renhejia.robot.launcher.displaymode.time.TimeView;
import com.renhejia.robot.launcher.displaymode.utils.GlideCircleTransform;
import com.renhejia.robot.launcherbaselib.storage.manager.LauncherConfigManager;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

/**
 * @author liujunbin
 */
public class FansView extends RelativeLayout {

    private Context mContext;
    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvFansNum;
    private ImageView ivFans;
    private static final int UPDATE_ICON = 110;
    private static final int UPDATE_STATUS = 111;
    private UpdateViewHandler mHandler;
    private Gson gson;
    private FansInfo mFansInfo;
    private static final String BILIBILI = "bilibili";

    public FansView(Context context) {
        super(context);
        init(context);
        fillData();
        addDataUpdateListeners();
    }

    private void fillData() {
        String fansInfo = LauncherConfigManager.getInstance(mContext).getRobotFansInfo();
        FansInfo fansInfos = gson.fromJson(fansInfo, FansInfo.class);
        updateViews(fansInfos);
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
        mHandler = new UpdateViewHandler(mContext);
        inflate(context, R.layout.robot_display_fans, this);
        ivIcon = findViewById(R.id.iv_icon);
        tvFansNum = findViewById(R.id.tv_fans_num);
        tvName = findViewById(R.id.tv_name);
        ivFans = findViewById(R.id.iv_fans);

    }

    private void addDataUpdateListeners() {
        FansInfoCallback.getInstance().setFansInfoUpdateListener(new FansInfoCallback.FansInfoUpdateListener() {
            @Override
            public void onFansInfoUpdate(FansInfo fansInfo) {
                updateFansInfo(fansInfo);
                update();

                // FansInfo{code=0, data=[FansData{platform='bilibili', avatar='https://i0.hdslb.com/bfs/face/member/noface.jpg', nick_name='圆梦', fans_count='300029'}, FansData{platform='weibo', avatar='https://i0.hdslb.com/bfs/face/member/noface.jpg', nick_name='圆梦', fans_count='2223'}], msg='success'}
                // fansInfo.getData()[0].getFans_count(): 300029
                // fansInfo.getData()[0].getAvatar(): https://i0.hdslb.com/bfs/face/member/noface.jpg
                // fansInfo.getData()[0].getNick_name(): 圆梦
            }
        });
    }

    private void updateViews(FansInfo fansInfo) {
        if (fansInfo == null || fansInfo.getData() == null || fansInfo.getData().length == 0 || fansInfo.getData()[0] == null || fansInfo.getData()[0].getFans_count() == null
                || fansInfo.getData()[0].getNick_name() == null || fansInfo.getData()[0].getAvatar() == null
        ) {
            return;
        }
        Log.e("letianpai_20230427", "fansInfo:" + fansInfo.toString());
        int number = Integer.parseInt(fansInfo.getData()[0].getFans_count());
        if (number > 10000){
            float numbers  = (float) number/10000;
            DecimalFormat df = new DecimalFormat("0.0");//格式化小数
            String s = df.format(numbers);
            tvFansNum.setText(s+"w");
        }else{
            tvFansNum.setText(number);
        }
        if (fansInfo.getData()[0].getPlatform().equals(BILIBILI)){
            ivFans.setImageResource(R.drawable.bili_fans);
        }else{
            ivFans.setImageResource(R.drawable.weibo_fans);
        }

        tvName.setText(fansInfo.getData()[0].getNick_name());
        String url = fansInfo.getData()[0].getAvatar();
        uploadIcon(url);
    }

    private void updateFansInfo(FansInfo fansInfo) {
        this.mFansInfo = fansInfo;
    }

    public FansView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FansView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FansView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void uploadIcon(String url) {
        Message message = new Message();
        message.what = UPDATE_ICON;
        message.obj = url;
        mHandler.sendMessage(message);
    }

    private void update() {
        Message message = new Message();
        message.what = UPDATE_STATUS;
        mHandler.sendMessage(message);
    }

    private class UpdateViewHandler extends android.os.Handler {
        private final WeakReference<Context> context;

        public UpdateViewHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_ICON:
                    Glide.with(mContext)
                            .load((String) msg.obj)
                            .centerCrop()
                            .transform(new GlideCircleTransform(mContext))
                            .into(ivIcon);
                    break;
                case UPDATE_STATUS:
                    updateViews(mFansInfo);
                    break;
            }
        }
    }


}
