package com.renhejia.robot.expression.face;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 表情绘制的View
 * 
 * @author liujunbin
 */
public class FaceAnimationView extends android.support.v7.widget.AppCompatImageView {
	
	public interface OnFaceAnimationChangedListener {
        /**
         * Called when FaceAnimationView change face animation.
         *
         * @param v The FaceAnimationView that face animation was change.
         * @param faceNew new face.
         * @param faceOld old face.
         */
        void onFaceAnimationChanged(FaceAnimationView v, int faceNew, int faceOld);
    }
	
	private BaseAbstractFace mCurrentAnimation = null;
	private ArrayMap<Integer, BaseAbstractFace> animationMap = new ArrayMap<Integer, BaseAbstractFace>();
	private int mCurrentIndex = 0;
	private final static int DEFAULT_FACE = FaceConsts.FACE_CHANGTAI;
	private int mCurrentFaceType = DEFAULT_FACE;
	
	private OnFaceAnimationChangedListener mOnFaceAnimationChangedListener = null;
	
	public FaceAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FaceAnimationView(Context context) {
		super(context);
	}
	
	public void clearAnimationList() {
		animationMap.clear();
	}
	
	/**
	 * 添加逐帧动画
	 */
	public void addFrameAnimation(int faceType, String dirName) {
		animationMap.put(faceType, createFrameAnimation(dirName));
	}
	
	private FrameAnimationFace createFrameAnimation(String name) {
		FrameAnimationFace a = new FrameAnimationFace(getContext());
		a.init(this, name);
		a.setFaceAnimationListener(faceAnimationListener);
		return a;
	}
	
	public void changeAnimation(int faceType) {
		if (!animationMap.containsKey(faceType)) {
			faceType = FaceConsts.FACE_CHANGTAI;
		}

		int faceOld = mCurrentFaceType;

		mCurrentFaceType = faceType;
		changeAnimation(animationMap.get(faceType));
		
		if(null != mOnFaceAnimationChangedListener) {
			mOnFaceAnimationChangedListener.onFaceAnimationChanged(this, faceType, faceOld);
		}
	}
	
	/**
	 * 是否是默认表情
	 * 
	 * @return
	 */
	public boolean isCurrentDefaultFace() {
		return mCurrentFaceType == DEFAULT_FACE;
	}
	
	/**
	 * 返回到默认表情
	 */
	public void changeToDefaultFace() {
		changeAnimation(DEFAULT_FACE);
	}
	
	/**
	 * 获取当前正在播放的表情
	 * 
	 * @return
	 */
	public int getCurrentFace() {
		return mCurrentFaceType;
	}
	
	/**
	 * 下一个动画
	 */
	public void nextAnimation() {
		mCurrentIndex++;
		if (mCurrentIndex == animationMap.size()) {
			mCurrentIndex = 0;
		}
		int key = animationMap.keyAt(mCurrentIndex);
		changeAnimation(key);
	}
	
	private void changeAnimation(BaseAbstractFace face) {
		if (mCurrentAnimation == face) {
			return;
		}
		BaseAbstractFace before = null;
		if (mCurrentAnimation != null) {
			mCurrentAnimation.stop();
			before = mCurrentAnimation;
//			mCurrentAnimation.release();
		}
		mCurrentAnimation = face;
		mCurrentAnimation.load();
		mCurrentAnimation.start();
		if (before != null) {
			before.release();
		}
		invalidate();
	}
	
	public BaseAbstractFace getCurrentAnimation() {
		return mCurrentAnimation;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		super.onDraw(canvas);
		if (mCurrentAnimation != null) {
			mCurrentAnimation.draw(canvas);
		}
	}
	
	private FaceAnimationListener faceAnimationListener = new FaceAnimationListener() {

		@Override
		public void invalidateFace() {
			postInvalidate();
		}

		@Override
		public void invalidateFace(int left, int top, int right, int bottom) {
			postInvalidate(left, top, right, bottom);
		}
		
	};

	public void release() {
		mCurrentAnimation.release();
	}
	
	public void start() {
		mCurrentAnimation.start();
	}
	
	/**
	 * 设置表情变化监听器
	 * @param listener
	 */
	public void setOnFaceAnimationChangedListener(OnFaceAnimationChangedListener listener) {
		this.mOnFaceAnimationChangedListener = listener;
	}
}
