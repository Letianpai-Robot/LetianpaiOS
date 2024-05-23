package com.renhejia.robot.expression.face;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * 渐变的Point
 * 
 * @author liujunbin
 *
 */
public class FacePointEvaluator implements TypeEvaluator<Point> {
	
	@Override
	public Point evaluate(float fraction, Point startValue, Point endValue) {
		int x = (int) (startValue.x + fraction * (endValue.x - startValue.x));
		int y = (int) (startValue.y + fraction * (endValue.y - startValue.y));
		return new Point(x, y);
	}

}
