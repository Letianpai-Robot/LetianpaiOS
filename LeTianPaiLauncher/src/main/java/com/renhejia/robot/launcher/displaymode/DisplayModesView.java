package com.renhejia.robot.launcher.displaymode;

import static android.renderscript.Allocation.USAGE_SCRIPT;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;
import com.renhejia.robot.launcher.R;
import com.renhejia.robot.launcher.dispatch.statusbar.StatusBarUpdateCallback;
import com.renhejia.robot.launcher.displaymode.callback.WeatherInfoUpdateCallback;
import com.renhejia.robot.launcher.displaymode.fans.FansView;
import com.renhejia.robot.launcher.displaymode.time.TimeView;
import com.renhejia.robot.launcher.statusbar.TimeUtil;
import com.renhejia.robot.launcher.system.SystemFunctionUtil;

import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DisplayModesView extends RelativeLayout {
    private Context mContext;
    private ImageView background;

    public DisplayModesView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(context, R.layout.robot_display, this);
        background = findViewById(R.id.iv_background);
    }

    public void showViews() {
        Log.i("DisplayModesView", "showViews" );
        Glide.with(mContext)
                .load(com.renhejia.robot.expression.R.drawable.default_start)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(background);
    }

    public void hideViews() {
        Log.i("DisplayModesView", "hideViews" );
        background.setImageDrawable(null);
    }
    public DisplayModesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DisplayModesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

}
