package com.renhejia.robot.expression.face;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * 渐变的Point
 * 
 * @author liujunbin
 *
 */
public class FaceScaleEvaluator implements TypeEvaluator<FaceScale> {
	@Override
	public FaceScale evaluate(float fraction, FaceScale startValue, FaceScale endValue) {

		float x = (int) (startValue.x + fraction * (endValue.x - startValue.x));
		float y = (int) (startValue.y + fraction * (endValue.y - startValue.y));
		return new FaceScale(x, y);
	}

//	@Override
//	public Point evaluate(float fraction, Point startValue, Point endValue) {
//		int x = (int) (startValue.x + fraction * (endValue.x - startValue.x));
//		int y = (int) (startValue.y + fraction * (endValue.y - startValue.y));
//		return new Point(x, y);
//	}

}
