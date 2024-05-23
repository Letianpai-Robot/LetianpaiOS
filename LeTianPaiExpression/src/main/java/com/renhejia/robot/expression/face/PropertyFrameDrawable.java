package com.renhejia.robot.expression.face;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import com.renhejia.robot.commandlib.log.LogUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PropertyFrameDrawable extends BitmapDrawable {
	private boolean hasSubFrame = false;
	private BaseAbstractFace face = null;
	private int duration = 0;
	/**
	 * 是否只是播放一次，不循环
	 */
	private boolean isOneShot = true;

	/**
	 * 一共这个动画需要播放的帧数
	 */
	private int frames = 1;
	
	private List<SubFrameDrawable> subFrameList = new ArrayList<SubFrameDrawable>();
	
	public PropertyFrameDrawable(Resources res, InputStream is) {
		super(res, is);
//		setFilterBitmap(true);// 暂时关闭缩放的锯齿
	}

	@Override
	public void draw(Canvas canvas) {
		try {
			super.draw(canvas);
			if (!hasSubFrame) {
				return;
			}
			if (subFrameList.size() > 0 && !subFrameList.get(0).isRunning()) {
				startSubFrameDraw();
			}
			for (SubFrameDrawable sub : subFrameList) {
				sub.draw(canvas);
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void invalidateSelf() {
		super.invalidateSelf();
	}
	
	private void startSubFrameDraw() {
		for (SubFrameDrawable sub : subFrameList) {
			sub.start();
		}
	}
	
	/**
	 * 单一选项的时候没有动画
	 * 
	 * @param range
	 * @param ratio
	 * @return
	 */
	private boolean hasNoAnimator(Object[] range, float[] ratio) {
		return range.length == 1 && ratio.length == 1 && ratio[0] == 1;
	}
	
	public boolean isOneShot() {
		return isOneShot;
	}

	public void setOneShot(boolean isOneShot) {
		this.isOneShot = isOneShot;
	}

	/**
	 * 添加一个子帧
	 * 
	 * @param drawable 子帧的图片
	 * @param subFrame 子帧的信息
	 */
	public void addSubFrame(PropertyFrameDrawable drawable, FaceFrame.SubFrame subFrame) {
		SubFrameDrawable subDrawable = new SubFrameDrawable();
		subDrawable.setDrawable(drawable);
		// 移动
		if (subFrame.hasPivotAnimation()) {
			Object[] range = subFrame.getPivotRangeArray();
			float[] ratio = subFrame.getPivotRatioArray();
			if (hasNoAnimator(range, ratio)) {
				// 如果没有动画
				subDrawable.setPiovt((Point) range[0]);
			} else {
				subDrawable.initPivotAnimation(range, ratio);
			}
		}
		// 透明度变化
		if (subFrame.hasAplhaAnimation()) {
			Object[] range = subFrame.getAlphaRangeArray();
			float[] ratio = subFrame.getAlphaRatioArray();
			if (hasNoAnimator(range, ratio)) {
				subDrawable.setAlpha((Float) range[0]);
			} else {
				subDrawable.initAlphaAnimation(range, ratio);
			}
		}
		// 缩放
		if (subFrame.hasScaleAnimation()) {
			Object[] range = subFrame.getScaleRangeArray();
			float[] ratio = subFrame.getScaleRatioArray();
			subDrawable.initScaleAnimation(range, ratio);
		}
		// 旋转
		if (subFrame.hasRotateAnimation()) {
			Object[] range = subFrame.getRotateRangeArray();
			float[] ratio = subFrame.getRotateRatioArray();
			subDrawable.initRotateAnimation(range, ratio);
		}
		// 名字
		subDrawable.setName(subFrame.getDrawableName());
		subFrameList.add(subDrawable);
		hasSubFrame = true;
	}
	
	public void clearSubFrame() {
		subFrameList.clear();
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public void setFace(BaseAbstractFace face) {
		this.face = face;
	}
	
	public Bitmap getFrameBitmap() {
		return super.getBitmap();
	}
	
	/**
	 * 需要刷新
	 */
	private void needInvalidate() {
		((FrameAnimationFace)face).needInvalidate(true);
	}
	
	/**
	 * 子帧Drawable，里面包括属性动画
	 * 
	 * @author liujunbin
	 *
	 */
	@SuppressWarnings("unused")
	private class SubFrameDrawable {
		private String name = null;
		private PropertyFrameDrawable drawable = null;
		private float alpha = 1;
//		private float scale = 1;
		private Scale scale = new Scale(1,1);
		private int degrees = 0; // 旋转角度
		private int height = 0; // 图片高度
		private int width = 0; // 图片宽度
		private Point piovt = new Point();
		private Matrix matrix = new Matrix();

		private ValueAnimator alphaAnimation;
		private ValueAnimator scaleAnimation;
		private ValueAnimator piovtAnimation;
		private ValueAnimator degreesAnimation;
		// 动画集合
		private List<Animator> items = new ArrayList<Animator>(4);
		private AnimatorSet bouncer = new AnimatorSet();
		private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		public SubFrameDrawable() {
			bouncer.setDuration(duration);
		}

		public void initAlphaAnimation(Object[] range, float[] ratio) {
			PropertyValuesHolder holder = createValuesHolder("alpha", range, ratio);
			alphaAnimation = ValueAnimator.ofPropertyValuesHolder(holder);//.ofObject(new FaceFloatEvaluator(ratio), range);
			alphaAnimation.setDuration(duration);
			alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
			if (isOneShot) {
				alphaAnimation.setRepeatCount(ValueAnimator.INFINITE);
			}
			alphaAnimation.setInterpolator(new LinearInterpolator());
			alphaAnimation.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float a = (Float) animation.getAnimatedValue();
					setAlpha(a);
					needInvalidate();
				}
			});
			items.add(alphaAnimation);
		}
		
//		public void initScaleAnimation(Object[] range, float[] ratio) {
//			PropertyValuesHolder holder = createValuesHolder("scale", range, ratio);
//			scaleAnimation = ValueAnimator.ofPropertyValuesHolder(holder);//.ofObject(new FaceFloatEvaluator(ratio), range);
//			scaleAnimation.setDuration(duration);
//			if (isOneShot) {
//				scaleAnimation.setRepeatCount(ValueAnimator.INFINITE);
//			}
//			scaleAnimation.setInterpolator(new LinearInterpolator());
//			scaleAnimation.addUpdateListener(new AnimatorUpdateListener() {
//
//				@Override
//				public void onAnimationUpdate(ValueAnimator animation) {
//					scale = (Float) animation.getAnimatedValue();
//					updateMatrix();
//					needInvalidate();
//				}
//			});
//			items.add(scaleAnimation);
//		}
		
		public void initScaleAnimation(Object[] range, float[] ratio) {
			LogUtils.logi("scale","initScaleAnimation ====== 1  ");
			PropertyValuesHolder holder = createValuesHolder("scale", range, ratio);
			LogUtils.logi("scale","initScaleAnimation ====== 2  ");
			scaleAnimation = ValueAnimator.ofPropertyValuesHolder(holder);//.ofObject(new FaceFloatEvaluator(ratio), range);
			LogUtils.logi("scale","initScaleAnimation ====== 3  ");
			scaleAnimation.setDuration(duration);
			LogUtils.logi("scale","initScaleAnimation ====== 4  ");
			if (isOneShot) {
				LogUtils.logi("scale","initScaleAnimation ====== 5  ");
				scaleAnimation.setRepeatCount(ValueAnimator.INFINITE);
			}
			scaleAnimation.setInterpolator(new LinearInterpolator());
			LogUtils.logi("scale","initScaleAnimation ====== 6  ");
			scaleAnimation.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					scale = (Scale) animation.getAnimatedValue();
					LogUtils.logi("scale","initScaleAnimation ====== 7  ");
					LogUtils.logi("scale","scale.x: "+ scale.getX());
					LogUtils.logi("scale","scale.y: "+ scale.getY());
					updateMatrix();
					needInvalidate();
				}
			});
			items.add(scaleAnimation);
			LogUtils.logi("scale","initScaleAnimation ====== 8  ");
		}

		public void initPivotAnimation(Object[] range, float[] ratio) {
			PropertyValuesHolder holder = createValuesHolder("pivot", range, ratio);
			piovtAnimation = ValueAnimator.ofPropertyValuesHolder(holder);  //.ofObject(new FacePointEvaluator(ratio), range);
			piovtAnimation.setEvaluator(new FacePointEvaluator());
			piovtAnimation.setDuration(duration);
			if (isOneShot) {
				piovtAnimation.setRepeatCount(ValueAnimator.INFINITE);
			}
			piovtAnimation.setInterpolator(new LinearInterpolator());
			piovtAnimation.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					piovt = (Point) animation.getAnimatedValue();
					updateMatrix();
					needInvalidate();
				}
			});
			items.add(piovtAnimation);
		}
		
		public void initRotateAnimation(Object[] range, float[] ratio) {
			final PropertyValuesHolder holder = createValuesHolder("degrees", range, ratio);
			degreesAnimation = ValueAnimator.ofPropertyValuesHolder(holder);
			degreesAnimation.setDuration(duration);
			if (isOneShot) {
				degreesAnimation.setRepeatCount(ValueAnimator.INFINITE);
			}
			degreesAnimation.setInterpolator(new LinearInterpolator());
			degreesAnimation.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					degrees = (Integer)animation.getAnimatedValue();
					updateMatrix();
					needInvalidate();
				}
			});
			items.add(degreesAnimation);
		}
		
		private PropertyValuesHolder createValuesHolder(String propertyName, Object[] range, float[] ratio) {
			Keyframe[] keyFrames = new Keyframe[ratio.length];
			for (int i = 0; i < ratio.length; i++) {
				if (range[i] instanceof Float) {
					keyFrames[i] = Keyframe.ofFloat(ratio[i], (Float) range[i]);
				} else if (range[i] instanceof Integer) {
					keyFrames[i] = Keyframe.ofInt(ratio[i], (Integer) range[i]);
				} else {
					keyFrames[i] = Keyframe.ofObject(ratio[i], range[i]);
				}
			}
			return PropertyValuesHolder.ofKeyframe(propertyName, keyFrames);
		}
		
		/**
		 * 根据中心点，计算出来偏移量
		 * @param
		 */
		private void updateMatrix() {
			final int px = width / 2;
			final int py = height / 2;
//			matrix.setScale(scale, scale, px, py);
			matrix.setScale(scale.getX(), scale.getY(), px, py);
			final int dx = piovt.x - px;
			final int dy = piovt.y - py;
			matrix.postTranslate(dx, dy);
			matrix.postRotate(degrees, piovt.x, piovt.y);
		}
		
		public void draw(Canvas canvas) {
			Bitmap bitmap = drawable.getFrameBitmap();
			canvas.drawBitmap(bitmap, matrix, mPaint);
		}
		
		public void start() {
			if (isRunning()) {
				return;
			}
			for (Animator anim : items) {
				anim.start();
			}
//			bouncer.playTogether(items);
//			bouncer.start();
		}
		
		public boolean isRunning() {
			for (Animator anim : items) {
				if (anim.isRunning()) {
					return true;
				}
			}
			return false;
		}
		
		public PropertyFrameDrawable getDrawable() {
			return drawable;
		}

		public void setDrawable(PropertyFrameDrawable drawable) {
			this.drawable = drawable;
			Bitmap bitmap = drawable.getBitmap();
			width = bitmap.getWidth();
			height = bitmap.getHeight();
//			width = height;
//			rate = height/width;
//			LogUtils.logi("robot","rate: "+ rate);
		}

		public float getAlpha() {
			return alpha;
		}

		public void setAlpha(float alpha) {
			this.alpha = alpha;
			mPaint.setAlpha((int) (alpha * 255));
		}
//
//		public float getScale() {
//			return scale;
//		}
//
//		public void setScale(float scale) {
//			this.scale = scale;
//		}

		public Scale getScale() {
			return scale;
		}

		public void setScale(Scale scale) {
			this.scale = scale;
		}

		public Point getPiovt() {
			return piovt;
		}

		public void setPiovt(Point piovt) {
			this.piovt = piovt;
			updateMatrix();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
}
