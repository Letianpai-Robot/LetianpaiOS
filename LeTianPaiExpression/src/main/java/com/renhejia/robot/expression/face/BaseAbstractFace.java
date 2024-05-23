package com.renhejia.robot.expression.face;

import android.content.Context;
import android.graphics.Canvas;

/**
 * 表情的抽象基类
 * 
 * @author liujunbin
 */
public abstract class BaseAbstractFace {
	
	public final static String ANIMATION_PATH = "annimation/";
	
	protected Context mContext = null;
    
	protected FaceAnimationListener mFaceAnimationListener;

	public abstract void draw(Canvas canvas);

	public abstract void release();
	
	public abstract void start();
	
	public abstract void stop();

	public abstract void init();
	
	public abstract void load();
	
	public BaseAbstractFace(Context mContext) {
		this.mContext = mContext;
	}

	public FaceAnimationListener getFaceAnimationListener() {
		return mFaceAnimationListener;
	}

	public void setFaceAnimationListener(FaceAnimationListener invalidate) {
		this.mFaceAnimationListener = invalidate;
	}
	
	/**
	 * 刷新表情
	 */
	protected void invalidateFace() {
		if (mFaceAnimationListener != null) {
			mFaceAnimationListener.invalidateFace();
		}
	}
}
