package com.renhejia.robot.expression.face;

import android.animation.TypeEvaluator;

/**
 * 表情部分，分段值域的分布计算
 * 
 * @author liujunbin
 *
 */
public class FaceFloatEvaluator implements TypeEvaluator<Number> {

	public FaceFloatEvaluator() {
		super();
	}

	@Override
	public Float evaluate(float fraction, Number startValue, Number endValue) {
		float startFloat = startValue.floatValue();
		float endFloat = endValue.floatValue();
		return startFloat + fraction * (endFloat - startFloat);
	}

}
