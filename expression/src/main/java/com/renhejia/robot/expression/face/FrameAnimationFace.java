package com.renhejia.robot.expression.face;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;


import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 逐帧动画，从asset中加载动画资源，逐帧播放
 * 
 * @author liujunbin
 */
public class FrameAnimationFace extends BaseAbstractFace {
	private AnimationDrawable animation = null;
	private ImageView mView = null;
	private String animationName = null;
	private AnimationConfigParser mConfigParser = null;
	private AnimationLruCache mCache = null;
	private boolean hasSubFrame = false;
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;

	private Rect mDirtyRect = new Rect();
	private volatile boolean needInvalidate = true;
	
	public FrameAnimationFace(Context mContext) {
		super(mContext);
		mConfigParser = new AnimationConfigParser();
		mCache = AnimationLruCache.getInstance(mContext);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
	}

	@Override
	public void init() {
		
	}
	
	@SuppressLint("NewApi")
	public void init(ImageView view, String animationName) {
		this.mView = view;
		this.animationName = animationName;
	}
	
	public void load() {
		animation = new AnimationDrawable();
		List<FaceFrame> frameList = parserFrameList();
		
		if (frameList == null) {
			return;
		}
		// 只是一帧的时候，逐帧动画只播放一次，子帧动画不停的重复播放
		boolean onShot = frameList.size() == 1;
		hasSubFrame = false;
		
		for (FaceFrame f : frameList) {
			String path = ANIMATION_PATH + animationName + "/" + f.getDrawableName();
			// 逐帧动画中的一帧
			PropertyFrameDrawable frameDrawable = mCache.getDrawable(path);
			if (frameDrawable != null) {
				frameDrawable.setDuration(f.getDuration());
				frameDrawable.setFrames(f.getFrames());
				frameDrawable.setOneShot(onShot);
				frameDrawable.clearSubFrame();
				// 自帧中，有多种元素
				for (FaceFrame.SubFrame subFrame : f.getSubFrameList()) {
					String subPath = ANIMATION_PATH + animationName + "/" + subFrame.getDrawableName();
					PropertyFrameDrawable subDrawable = mCache.getDrawable(subPath);
					if (subDrawable != null) {
						frameDrawable.addSubFrame(subDrawable, subFrame);
						hasSubFrame = true;
					}
				}
				frameDrawable.setFace(this);
				animation.addFrame(frameDrawable, f.getDuration());
			}
		}
		animation.setOneShot(onShot);
		mView.setImageDrawable(animation);
	}

	/**
	 * 解析xml
	 * 
	 * @return
	 */
	private List<FaceFrame> parserFrameList() {
		final Context context = mView.getContext();
		InputStream is;
		try {
			is = context.getAssets().open(ANIMATION_PATH + animationName + "/config.xml");
			return mConfigParser.parse(is);
		} catch (Exception e) {
//			KTExceptionPrinter.printStackTrace(e);
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void start() {
		// 判断是否是纯逐帧动画
		if (hasSubFrame) {
			startTimer();
		} else { // 没有子帧，则不需要间隔刷新
			stopTimer();
		}
		animation.start();
	}

	private void startTimer() {
		boolean needSchedule = false;
		if (mTimer == null) {
			mTimer = new Timer();
			needSchedule = true;
		}
		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					if (needInvalidate) {
						needInvalidate = false;
						invalidateFace();
					}
				}
			};
		}
		if (needSchedule && mTimer != null && mTimerTask != null) {
			mTimer.schedule(mTimerTask, 0, 100);
		}
	}
	
	private void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
	}
	
	/**
	 * 更新刷新脏区
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	void updateDirtyRect(int left, int top, int right, int bottom) {
		mDirtyRect.set(left, top, right, bottom);
	}
	
	/**
	 * 是否需要刷新
	 * 
	 * @param need
	 */
	void needInvalidate(boolean need) {
		needInvalidate = need;
	}
	
	@Override
	public void stop() {
		if (animation != null){
			animation.stop();
		}

	}
	
	@SuppressLint("NewApi")
	@Override
	public void release() {
		animation = null;
//		mView.setImageDrawable(null);
		stopTimer();
	}
}
