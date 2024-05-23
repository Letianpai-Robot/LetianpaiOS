package com.renhejia.robot.expression.face;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 动画配置解析器
 *
 * @author liujunbin
 */
public class AnimationConfigParser {
	/**
	 * 解析xml
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public List<FaceFrame> parse(InputStream is) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		FrameHandler handler = new FrameHandler();
		parser.parse(is, handler);
		return handler.getFrames();
	}

	private class FrameHandler extends DefaultHandler {  
		private final static int STEP_MODE_SCALE = 0x1;
		private final static int STEP_MODE_ALPHA = 0x2;
		private final static int STEP_MODE_ROTATE = 0x3;
		
        private List<FaceFrame> frames;  
        private FaceFrame frame;
        private FaceFrame.SubFrame subFrame;
        private int duration = 0;
        private int stepMode;
        
        //返回解析后得到的Frame对象集合  
        public List<FaceFrame> getFrames() {  
            return frames;  
        }  
          
        @Override  
        public void startDocument() throws SAXException {  
            super.startDocument();  
            frames = new ArrayList<FaceFrame>();  
        }  
  
        @Override  
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {  
            super.startElement(uri, localName, qName, attributes);
            
            if ("animation".equals(localName)) {
            	duration = Integer.parseInt(attributes.getValue("duration"));
            } else if ("frame".equals(localName)) {
            	// 逐帧动画的一帧
                frame = new FaceFrame();
                frame.setImageName(attributes.getValue("drawable"));
                int frames = Integer.parseInt(attributes.getValue("frames"));
                frame.setDuration(frames * duration);
                frame.setFrames(frames);
            } else if ("sub-frame".equals(localName)) {
            	// 其中一个子帧
            	subFrame = new FaceFrame.SubFrame();
            	subFrame.setDrawableName(attributes.getValue("drawable"));
            } else if ("scale".equals(localName)) {
            	// 添加缩放的参数
            	stepMode = STEP_MODE_SCALE;
            } else if ("alpha".equals(localName)) {
            	// 添加透明度的参数
            	stepMode = STEP_MODE_ALPHA;
            } else if ("rotate".equals(localName)) {
            	stepMode = STEP_MODE_ROTATE;
            } else if ("step".equals(localName)) {
            	float range = Float.parseFloat(attributes.getValue("range"));
            	float ratio = Float.parseFloat(attributes.getValue("ratio"));
            	addStep(stepMode, range, ratio);
            } else if ("point".equals(localName)) {
            	int x = Integer.parseInt(attributes.getValue("x"));
            	int y = Integer.parseInt(attributes.getValue("y"));
            	float ratio = Float.parseFloat(attributes.getValue("ratio"));
            	subFrame.addPivotStep(x, y, ratio);
            }
        }
        
        /**
         * 添加一个渐变的步骤
         * @param mode
         * @param range
         * @param ratio
         */
        private void addStep(int mode, float range, float ratio) {
        	switch (mode) {
        	case STEP_MODE_SCALE:
        		// 缩放
        		subFrame.addScaleStep(range, ratio);
        		break;
        	case STEP_MODE_ALPHA:
        		// 透明度
        		subFrame.addAlphaStep(range, ratio);
        		break;
        	case STEP_MODE_ROTATE:
        		// 旋转
        		subFrame.addRotateStep((int) range, ratio);
        		break;
        	}
        }
        
        @Override  
        public void endElement(String uri, String localName, String qName) throws SAXException {  
            super.endElement(uri, localName, qName);  
            if ("frame".equals(localName)) {  
            	frames.add(frame);
            } else if ("sub-frame".equals(localName)) {
            	frame.addSubFrame(subFrame);
            }
        }  
    }
}
