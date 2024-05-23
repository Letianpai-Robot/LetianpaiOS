package com.renhejia.robot.expression.face;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * 表情动画的帧
 * 
 * @author liujunbin
 *
 */
public class FaceFrame {
	private String drawableName = null;
	private int duration = 0;
	private int frames = 0;
	private List<SubFrame> subFrameList = new ArrayList<SubFrame>();
	
	public String getDrawableName() {
		return drawableName;
	}

	public void setImageName(String imageName) {
		this.drawableName = imageName;
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

	public void addSubFrame(SubFrame subFrame) {
		subFrameList.add(subFrame);
	}
	
	public List<SubFrame> getSubFrameList() {
		return subFrameList;
	}
	
	/**
	 * 是否有子帧
	 * 
	 * @return
	 */
	public boolean hasSubFrame() {
		return !subFrameList.isEmpty();
	}
	
	public static class SubFrame {
		private String drawableName = null;
//		private List<Float> scaleRangeList = new ArrayList<Float>();
		private List<Scale> scaleRangeList = new ArrayList<Scale>();
		private List<Float> scaleRatioList = new ArrayList<Float>();
		
		private List<Float> alphaRangeList = new ArrayList<Float>();
		private List<Float> alphaRatioList = new ArrayList<Float>();
		
		private List<Point> pivotRangeList = new ArrayList<Point>();
		private List<Float> pivotRatioList = new ArrayList<Float>();
		
		private List<Integer> degreesRangeList = new ArrayList<Integer>();
		private List<Float> degreesRatioList = new ArrayList<Float>();
		
		public String getDrawableName() {
			return drawableName;
		}

		public void setDrawableName(String drawableName) {
			this.drawableName = drawableName;
		}

		/**
		 * 添加缩放参数
		 * 
		 * @param range 缩放大小
		 * @param ratio 百分比时间
		 */
//		public void addScaleStep(float range, float ratio) {
//			scaleRangeList.add(range);
//			scaleRatioList.add(ratio);
//		}

		public void addScalesStep(Scale range, float ratio) {
			scaleRangeList.add(range);
			scaleRatioList.add(ratio);
		}

		public void addAlphaStep(float range, float ratio) {
			alphaRangeList.add(range);
			alphaRatioList.add(ratio);
		}
		
		public void addPivotStep(int x, int y, float ratio) {
			pivotRangeList.add(new Point(x, y));
			pivotRatioList.add(ratio);
		}
		
		public void addRotateStep(int range, float ratio) {
			degreesRangeList.add(range);
			degreesRatioList.add(ratio);
		}

		public boolean hasScaleAnimation() {
			return !scaleRangeList.isEmpty();
		}
		
		public Object[] getScaleRangeArray() {
			return scaleRangeList.toArray();
		}

		public float[] getScaleRatioArray() {
			return toArray(scaleRatioList);
		}

		public boolean hasAplhaAnimation() {
			return !alphaRangeList.isEmpty();
		}
		
		public Object[] getAlphaRangeArray() {
			return alphaRangeList.toArray();
		}

		public float[] getAlphaRatioArray() {
			return toArray(alphaRatioList);
		}
		
		public Object[] getPivotRangeArray() {
			return pivotRangeList.toArray();
		}

		public float[] getPivotRatioArray() {
			return toArray(pivotRatioList);
		}

		public boolean hasPivotAnimation() {
			return !pivotRangeList.isEmpty();
		}

		public Object[] getRotateRangeArray() {
			return degreesRangeList.toArray();
		}

		public float[] getRotateRatioArray() {
			return toArray(degreesRatioList);
		}

		public boolean hasRotateAnimation() {
			return !degreesRangeList.isEmpty();
		}

		private float[] toArray(List<Float> list) {
			float[] result = new float[list.size()];
			for (int i = 0; i < list.size(); i++) {
				result[i] = list.get(i);
			}
			return result;
		}
	}
}
