package com.renhejia.robot.expression.face;

/**
 * 表情动画的监听器
 * RobotTestAnimationView
 *
 */
public interface FaceAnimationListener {

	/**
	 * 刷新表情
	 */
	public void invalidateFace();
	
	/**
	 * 局部刷新
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void invalidateFace(int left, int top, int right, int bottom);

}
