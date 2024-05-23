package com.renhejia.robot.expression.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.LruCache;

import java.io.InputStream;

/**
 * 动画中所使用的图片的内存缓存
 * @author liujunbin
 */
public class AnimationLruCache {
	public static final int MAX_SIZE = 60 * 1024 * 1024;
	
	private static AnimationLruCache lruCache = null;
	private Context mContext = null;
	private LruCache<String, PropertyFrameDrawable> mCache = null;
	
	private AnimationLruCache(Context context) {
		mContext = context;
		mCache = new LruCache<String, PropertyFrameDrawable>(MAX_SIZE) {

			@Override
			protected void entryRemoved(boolean evicted, String key,
					PropertyFrameDrawable oldValue, PropertyFrameDrawable newValue) {
				if (oldValue instanceof BitmapDrawable) {
					BitmapDrawable bw = (BitmapDrawable) oldValue;
					bw.getBitmap().recycle();
				}
			}

			@Override
			protected int sizeOf(String key, PropertyFrameDrawable value) {
				return getDrawableByteCount(value);
			}
			
		};
	}

	public static AnimationLruCache getInstance(Context context) {
		if (lruCache == null) {
			lruCache = new AnimationLruCache(context);
		}
		return lruCache;
	}

	/**
	 * 从缓存中得到图片，如果内存中不存在则从硬件加载
	 * 
	 * @param path asset中全路径
	 * @return
	 */
	public PropertyFrameDrawable getDrawable(String path) {
		PropertyFrameDrawable d = mCache.get(path);
		if (d != null) {
			return d;
		}
		InputStream is;
		try {
			is = mContext.getAssets().open(path);
			PropertyFrameDrawable drawable = new PropertyFrameDrawable(mContext.getResources(), is);
			mCache.put(path, drawable);
			return drawable;
		} catch (Throwable e) {
			return null;
		}
	}
	
	/**
	 * 清空所有
	 */
	public void clear() {
		mCache.evictAll();
	}
	
	private int getDrawableByteCount(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bw = (BitmapDrawable) drawable;
			Bitmap bm = bw.getBitmap();
			return bm.getHeight() * bm.getWidth() * 4;
		}
		return 0;
	}
}
