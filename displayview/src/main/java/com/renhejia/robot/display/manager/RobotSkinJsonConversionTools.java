package com.renhejia.robot.display.manager;


import android.graphics.Point;
import android.graphics.Rect;
import com.renhejia.robot.commandlib.log.LogUtils;


import com.renhejia.robot.display.RobotClockSkin;
import com.renhejia.robot.display.RobotSkinAnalogTime;
import com.renhejia.robot.display.RobotSkinAnchor;
import com.renhejia.robot.display.RobotSkinArc;
import com.renhejia.robot.display.RobotSkinImage;
import com.renhejia.robot.display.RobotSkinImageWithAngle;
import com.renhejia.robot.display.RobotSkinImageWithSpace;
import com.renhejia.robot.display.RobotSkinLabel;
import com.renhejia.robot.display.RobotSkinNumber;
import com.renhejia.robot.display.RobotSkinShape;
import com.renhejia.robot.display.utils.ClockViewConsts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RobotSkinJsonConversionTools implements ClockViewConsts {

    public static RobotClockSkin getSpineClockSkin(String json) {
        LogUtils.logi("Mars1069","getSpineClockSkin ==== 10086");
        RobotClockSkin spineClockSkin = new RobotClockSkin();
        try {
            JSONObject object = new JSONObject(json);

            JSONObject origRect = getTargetJsonObject(object,ORIG_RECT);
            JSONObject background = getTargetJsonObject(object,BACKGROUND);
            JSONObject foreground = getTargetJsonObject(object,FOREGROUND);
            JSONObject middle = getTargetJsonObject(object,MIDDLE);
            JSONObject battery = getTargetJsonObject(object,BATTERY);
            JSONObject wifi = getTargetJsonObject(object,WIFI);
            JSONObject bluetooth = getTargetJsonObject(object,BLUETOOTH);
            JSONObject weather = getTargetJsonObject(object,WEATHER);
            JSONObject notice = getTargetJsonObject(object,NOTICE);
            JSONObject volume = getTargetJsonObject(object,VOLUME);
            JSONObject charge = getTargetJsonObject(object,CHARGE);

            JSONObject step = getTargetJsonObject(object,STEP);
            JSONObject airTemp = getTargetJsonObject(object,AIR_TEMP);

            JSONObject batterySquares =  getTargetJsonObject(object,BATTERY_SQUARES);
            JSONObject stepSquares =  getTargetJsonObject(object,STEP_SQUARES);

            JSONObject batteryAnchor = getTargetJsonObject(object,BATTERY_ANCHOR);
            JSONObject weekAnchor =  getTargetJsonObject(object,WEEK_ANCHOR);

            JSONObject stepNumber =  getTargetJsonObject(object,STEP_NUMBER);
            JSONObject aqiNumber =  getTargetJsonObject(object,AQI_NUMBER);
            JSONObject aqiText =  getTargetJsonObject(object,AQI_TEXT);
            JSONObject noticeText =  getTargetJsonObject(object,NOTICE_TEXT);
            JSONObject batteryNumber =  getTargetJsonObject(object,BATTERY_NUMBER);
            JSONObject temperature =  getTargetJsonObject(object,TEMPERATURE);

            JSONObject batteryProgress =  getTargetJsonObject(object,BATTERY_PROGRESS);

            JSONObject stepSkinArc =  getTargetJsonObject(object,STEP_SKIN_ARC);
            JSONObject batteryAngle =  getTargetJsonObject(object,BATTERY_ANGLE);

            JSONArray digitTimes = getTargetJSONArray(object,DIGIT_TIMES);
            JSONArray labelTimes = getTargetJSONArray(object,LABEL_TIMES);
            JSONArray analogTimes = getTargetJSONArray(object,ANALOG_TIMES);
            JSONArray backgrounds = getTargetJSONArray(object,BACKGROUNDS);
            JSONArray countdownEvent = getTargetJSONArray(object,COUNTDOWN_EVENT);
            JSONArray notices = getTargetJSONArray(object,NOTICES);
            JSONArray fansInfo = getTargetJSONArray(object,FANS_INFO);
            JSONObject fansIcon = getTargetJsonObject(object,FANS_ICON);
            JSONObject fansHead = getTargetJsonObject(object,FANS_HEAD);
            JSONObject temperatureRange =  getTargetJsonObject(object,TEMPERATURE_RANGE);

            if (object == null) {
                return null;
            }
            //设置 origRect
            if (origRect != null) {
                Rect rect = getRect(origRect);
                if (rect != null){
                    spineClockSkin.setOrigRect(rect);
                }
            }

            //设置 background
            if (background != null) {
                RobotSkinImage spineSkinImageBg = getSpineSkinImage(background);
                spineClockSkin.setBackground(spineSkinImageBg);
            }
//
            //设置 foreground
            if (foreground != null) {
                RobotSkinImage spineSkinImageFg = getSpineSkinImage(foreground);
                spineClockSkin.setForeground(spineSkinImageFg);
            }

            //设置 middle
            if (middle != null) {
                RobotSkinImage spineSkinImageMd = getSpineSkinImage(middle);
                spineClockSkin.setMiddle(spineSkinImageMd);
            }

            //设置 battery
            if (battery != null) {
                RobotSkinImage spineSkinImageBattery = getSpineSkinImage(battery);
                spineClockSkin.setBattery(spineSkinImageBattery);
            }

            //设置 wifi
            if (wifi != null) {
                RobotSkinImage spineSkinImageWifi = getSpineSkinImage(wifi);
                spineClockSkin.setWifi(spineSkinImageWifi);
            }

            //设置 bluetooth
            if (bluetooth != null) {
                RobotSkinImage spineSkinImageBT = getSpineSkinImage(bluetooth);
                spineClockSkin.setBluetooth(spineSkinImageBT);
            }

            //设置 weather
            if (weather != null) {
                RobotSkinImage spineSkinImageWeather = getSpineSkinImage(weather);
                spineClockSkin.setWeather(spineSkinImageWeather);
            }

            //设置 notice
            if (notice != null) {
                RobotSkinImage spineSkinImageNotice = getSpineSkinImage(notice);
                spineClockSkin.setNotices(spineSkinImageNotice);
            }

            //设置 头像
            if (fansIcon != null) {
                RobotSkinImage spineSkinImageFansIcon = getSpineSkinImage(fansIcon);
                spineClockSkin.setFansIcon(spineSkinImageFansIcon);
            }

            //设置 头像
            if (fansHead != null) {
                RobotSkinImage spineSkinImageFansHead= getSpineSkinImage(fansHead);
                spineClockSkin.setFansHead(spineSkinImageFansHead);
            }

            //设置 volume
            if (volume != null) {
                RobotSkinImage spineSkinImageVolume = getSpineSkinImage(volume);
                spineClockSkin.setVolume(spineSkinImageVolume);
            }

            //设置 charge
            if (charge != null) {
                RobotSkinImage spineSkinImageCharge = getSpineSkinImage(charge);
                spineClockSkin.setCharge(spineSkinImageCharge);
            }

            //设置 step
            if (step != null) {
                RobotSkinNumber spineSkinNumberStep = getSpineSkinNumber(step);
                spineClockSkin.setStep(spineSkinNumberStep);
            }

            //设置 airTemp
            if (airTemp != null) {
//                LogUtils.logi("testFileName0","drawAirTemp:======= 000");
                RobotSkinNumber spineSkinNumberAirTemp = getSpineSkinNumber(airTemp);
                spineClockSkin.setAirTemp(spineSkinNumberAirTemp);
            }else{
//                LogUtils.logi("testFileName0","drawAirTemp:======= 111");

            }

            //设置 batterySquares
            if (batterySquares != null) {
                RobotSkinImageWithSpace spineSkinImageWithSpaceBatterySquares = getSpineSkinImageWithSpace(batterySquares);
                spineClockSkin.setBatterySquares(spineSkinImageWithSpaceBatterySquares);
            }

            //设置 stepSquares
            if (stepSquares != null) {
                RobotSkinImageWithSpace spineSkinImageWithSpaceStepSquares = getSpineSkinImageWithSpace(stepSquares);
                spineClockSkin.setStepSquares(spineSkinImageWithSpaceStepSquares);
            }

            //设置 batteryAnchor
            if (batteryAnchor != null) {
                RobotSkinAnchor spineSkinAnchorBatteryAnchor = getSpineSkinAnchor(batteryAnchor);
                spineClockSkin.setBatteryAnchor(spineSkinAnchorBatteryAnchor);
            }

            //设置 weekAnchor
            if (weekAnchor != null) {
                RobotSkinAnchor spineSkinAnchorWeekAnchor = getSpineSkinAnchor(weekAnchor);
                spineClockSkin.setWeekAnchor(spineSkinAnchorWeekAnchor);
            }

            //设置 stepNumber
            if (stepNumber != null) {
                RobotSkinLabel spineSkinLabelStepNumber = getSpineSkinLabel(stepNumber);
                spineClockSkin.setStepNumber(spineSkinLabelStepNumber);
            }

            //设置 aqiNumber
            if (aqiNumber != null) {
                RobotSkinLabel spineSkinLabelAqiNumber = getSpineSkinLabel(aqiNumber);
                spineClockSkin.setAqiNumber(spineSkinLabelAqiNumber);
            }

            //设置 aqiText
            if (aqiText != null) {
                RobotSkinLabel spineSkinLabelAqiText = getSpineSkinLabel(aqiText);
                spineClockSkin.setAqiText(spineSkinLabelAqiText);
            }

            //设置 noticeText
            if (noticeText!= null) {
                RobotSkinLabel spineSkinLabelNoticeText = getSpineSkinLabel(noticeText);
                spineClockSkin.setNoticeText(spineSkinLabelNoticeText);
            }

            //设置 batteryNumber
            if (batteryNumber != null) {
                RobotSkinLabel spineSkinLabelBatteryNumber = getSpineSkinLabel(batteryNumber);
                spineClockSkin.setBatteryNumber(spineSkinLabelBatteryNumber);
            }

            //设置 temperature
            if (temperature != null) {
                RobotSkinLabel spineSkinLabelTemperature = getSpineSkinLabel(temperature);
                spineClockSkin.setTemperature(spineSkinLabelTemperature);
            }

            //设置 temperatureRange
            if (temperatureRange != null) {
                RobotSkinLabel spineSkinLabelTemperature = getSpineSkinLabel(temperatureRange);
                spineClockSkin.setTemperatureRange(spineSkinLabelTemperature);
            }

            //设置 batteryProgress
            if (batteryProgress != null) {
                RobotSkinShape spineSkinShapeBatteryProgress = getSpineSkinShape(batteryProgress);
                spineClockSkin.setBatteryProgress(spineSkinShapeBatteryProgress);
            }

            //设置 batteryProgress
            if (stepSkinArc != null) {
                RobotSkinArc spineSkinArcStep = getSpineSkinArc(stepSkinArc);
                spineClockSkin.setStepSkinArc(spineSkinArcStep);
            }

            //设置 batteryAngle
            if (batteryAngle != null) {
                RobotSkinImageWithAngle SpineSkinImageWithAngleBattery = getSpineSkinImageWithAngle(batteryAngle);
                spineClockSkin.setBatteryAngle(SpineSkinImageWithAngleBattery);
            }

            if (digitTimes != null) {
                List<RobotSkinNumber> digitList = getDigitTime(digitTimes);
                spineClockSkin.setDigitTimes(digitList);
            }

            if (backgrounds != null) {
                List<RobotSkinAnchor> backgroundList = getBackgrounds(backgrounds);
                spineClockSkin.setBackgrounds(backgroundList);
            }

            if (countdownEvent != null) {
                List<RobotSkinLabel> labelList = getLabelTimes(countdownEvent);
                spineClockSkin.setCountdownEvent(labelList);
            }

            if (fansInfo != null) {
                List<RobotSkinLabel> labelList = getLabels(fansInfo);
                spineClockSkin.setFansInfo(labelList);
            }

            if (notices != null) {
                List<RobotSkinLabel> labelList = getLabelTimes(notices);
                spineClockSkin.setNotice(labelList);
            }

            if (labelTimes != null) {
                List<RobotSkinLabel> labelList = getLabelTimes(labelTimes);
                spineClockSkin.setLabelTimes(labelList);
            }

            if (analogTimes != null) {
                List<RobotSkinAnalogTime> analoglList = getAnalogTimes(analogTimes);
                spineClockSkin.setAnalogTimes(analoglList);
            }

        } catch (Exception e) {

        }

        return spineClockSkin;
    }

    private static JSONObject getTargetJsonObject(JSONObject object, String origRect) {
        JSONObject jsonObject = null;
        try {
            jsonObject = object.getJSONObject(origRect);
        } catch (JSONException e) {
            return null;
        }
        return jsonObject;
    }

    private static JSONArray getTargetJSONArray(JSONObject object, String origRect) {
        JSONArray jsonArray = null;
        try {
            jsonArray = object.getJSONArray(origRect);
        } catch (JSONException e) {
            return null;
        }
        return jsonArray;
    }

    private static int getTargetIntValue(JSONObject object, String origRect) {
        int value = 0;
        try {
            value = object.getInt(origRect);
        } catch (JSONException e) {
            return value;
        }
        return value;
    }

    private static String getTargetStringValue(JSONObject object, String origRect) {
        String value = null;
        try {
            value = object.getString(origRect);
        } catch (JSONException e) {
            return null;
        }
        return value;
    }

    /**
     *
     *
     * @param digitTimes
     * @return
     */
    private static List<RobotSkinNumber> getDigitTime(JSONArray digitTimes) {
        List<RobotSkinNumber> digitList = new ArrayList<>();

        for (int i = 0;i<digitTimes.length();i++){
            RobotSkinNumber spineSkinNumber = new RobotSkinNumber();

            try {
                JSONObject jsonObject = digitTimes.getJSONObject(i);
                if (jsonObject != null){
                    spineSkinNumber = getSpineSkinNumber(jsonObject);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            digitList.add(spineSkinNumber);
        }

        return digitList;
    }

    /**
     *List<RobotSkinAnchor> backgrounds
     *
     * @param backgrounds
     * @return
     */
    private static List<RobotSkinAnchor> getBackgrounds(JSONArray backgrounds) {
        List<RobotSkinAnchor> backgroundList = new ArrayList<>();

        for (int i = 0;i<backgrounds.length();i++){
            RobotSkinAnchor robotSkinAnchor = new RobotSkinAnchor();

            try {
                JSONObject jsonObject = backgrounds.getJSONObject(i);
                if (jsonObject != null){
                    robotSkinAnchor = getSpineSkinAnchor(jsonObject);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            backgroundList.add(robotSkinAnchor);
        }

        return backgroundList;
    }

//
//    /**
//     * @param labelTimes
//     * @return
//     */
//    private static List<RobotSkinLabel> getLabelTimes(JSONArray labelTimes) {
//        List<RobotSkinLabel> labelList = new ArrayList<>();
//
//        for (int i = 0; i < labelTimes.length(); i++) {
//            try {
//                RobotSkinLabel spineSkinLabel = getSpineSkinLabel(labelTimes.getJSONObject(i));
//                if (spineSkinLabel != null) {
//                    labelList.add(spineSkinLabel);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return labelList;
//    }

    /**
     * @param labelTimes
     * @return
     */
    private static List<RobotSkinLabel> getLabelTimes(JSONArray labelTimes) {

        return getLabels(labelTimes);
    }
    /**
     * @param labelTimes
     * @return
     */
    private static List<RobotSkinLabel> getLabels(JSONArray labelTimes) {
        List<RobotSkinLabel> labelList = new ArrayList<>();

        for (int i = 0; i < labelTimes.length(); i++) {
            try {
                RobotSkinLabel spineSkinLabel = getSpineSkinLabel(labelTimes.getJSONObject(i));
                if (spineSkinLabel != null) {
                    labelList.add(spineSkinLabel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return labelList;
    }

    /**
     *
     * // TODO 需要更新代码
     * @param analogTimes
     * @return
     */
    private static List<RobotSkinAnalogTime> getAnalogTimes(JSONArray analogTimes) {
        List<RobotSkinAnalogTime> analogList = new ArrayList<>();

        for (int i = 0;i<analogTimes.length();i++){
            try {
                RobotSkinAnalogTime spineSkinAnalogTime = getSpineSkinAnalogTime(analogTimes.getJSONObject(i));
                if (spineSkinAnalogTime != null) {
                    analogList.add(spineSkinAnalogTime);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return analogList;
    }

    private static RobotSkinAnalogTime getSpineSkinAnalogTime(JSONObject spineSkinAnalogTimeJsonObject) {

        RobotSkinAnalogTime spineSkinAnalogTime = new RobotSkinAnalogTime();
        try {

            JSONObject analogTimeOrigRect = getTargetJsonObject(spineSkinAnalogTimeJsonObject,ORIG_RECT);
            if (analogTimeOrigRect != null){
                Rect rect = getRect(analogTimeOrigRect);
                if (rect != null){
                    spineSkinAnalogTime.setOrigRect(rect);
                }
            }

            String filePrefix = getTargetStringValue(spineSkinAnalogTimeJsonObject,FILE_PREFIX);
            if (filePrefix != null){
                spineSkinAnalogTime.setFilePrefix(filePrefix);
            }

            JSONObject origHourAnchor = getTargetJsonObject(spineSkinAnalogTimeJsonObject, ORIG_HOUR_ANCHOR);
            if (origHourAnchor != null) {
                Point hourPoint = getPoint(origHourAnchor);
                if (hourPoint != null){
                    spineSkinAnalogTime.setOrigHourAnchor(hourPoint);
                }
            }

            JSONObject origMinuteAnchor = getTargetJsonObject(spineSkinAnalogTimeJsonObject, ORIG_MINUTE_ANCHOR);
            if (origMinuteAnchor != null) {
                Point minutePoint = getPoint(origMinuteAnchor);
                if(minutePoint != null){
                    spineSkinAnalogTime.setOrigMinuteAnchor(minutePoint);
                }
            }

            JSONObject origSecondAnchor = getTargetJsonObject(spineSkinAnalogTimeJsonObject, ORIG_SECOND_ANCHOR);
            if (origSecondAnchor != null) {
                Point secondPoint = getPoint(origSecondAnchor);
                if (secondPoint != null){
                    spineSkinAnalogTime.setOrigSecondAnchor(secondPoint);
                }
            }

        }catch (Exception e){

        }

        return spineSkinAnalogTime;

    }


    /**
     * 获取对应的 SpineSkinLabel 对象
     *
     * @param spineSkinLabelJSONObject
     * @return
     */
    private static RobotSkinLabel getSpineSkinLabel(JSONObject spineSkinLabelJSONObject) {

        RobotSkinLabel spineSkinLabel = new RobotSkinLabel();

        try {

            JSONObject digitTimesOrigRect = getTargetJsonObject(spineSkinLabelJSONObject,ORIG_RECT);
            if (digitTimesOrigRect != null){
                Rect rect = getRect(digitTimesOrigRect);
                if (rect != null){
                    spineSkinLabel.setOrigRect(rect);
                }
            }

            int align = getTargetIntValue(spineSkinLabelJSONObject,ALIGN);
            spineSkinLabel.setAlign(align);

            String dataFormat = getTargetStringValue(spineSkinLabelJSONObject,DATA_FORMAT);
            if (dataFormat != null) {
                spineSkinLabel.setDataFormat(dataFormat);
            }

            int origSize = getTargetIntValue(spineSkinLabelJSONObject,ORIG_SIZE);
            spineSkinLabel.setOrigSize(origSize);

            String languageFormat = getTargetStringValue(spineSkinLabelJSONObject,LANGUAGE_FORMAT);
            if (languageFormat != null) {
                spineSkinLabel.setLaunguageFormat(languageFormat);
            }
            int color = getTargetIntValue(spineSkinLabelJSONObject,COLOR);
            spineSkinLabel.setColor(color);

            int style = getTargetIntValue(spineSkinLabelJSONObject,STYLE);
            spineSkinLabel.setStyle(style);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return spineSkinLabel;
    }

    /**
     * 获取对应的 SpineSkinShape 对象
     *
     * @param spineSkinShapeJSONObject
     * @return
     */
    private static RobotSkinShape getSpineSkinShape(JSONObject spineSkinShapeJSONObject) {

        RobotSkinShape spineSkinShape = new RobotSkinShape();

        try {
            int align = getTargetIntValue(spineSkinShapeJSONObject,ALIGN);
            spineSkinShape.setAlign(align);

            int bgColor = getTargetIntValue(spineSkinShapeJSONObject,BG_COLOR);
            spineSkinShape.setBgColor(bgColor);

            int fgColor =  getTargetIntValue(spineSkinShapeJSONObject,FG_COLOR);
            spineSkinShape.setFgColor(fgColor);

            JSONObject digitTimesOrigRect = getTargetJsonObject(spineSkinShapeJSONObject,ORIG_RECT);
            if (digitTimesOrigRect != null){
                Rect rect = getRect(digitTimesOrigRect);
                if (rect != null){
                    spineSkinShape.setOrigRect(rect);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return spineSkinShape;
    }

    /**
     * 获取对应的 SpineSkinArc 对象
     *
     * @param spineSkinArcJSONObject
     * @return
     */
    private static RobotSkinArc getSpineSkinArc(JSONObject spineSkinArcJSONObject) {

        RobotSkinArc spineSkinArc = new RobotSkinArc();

        try {

            int align = getTargetIntValue(spineSkinArcJSONObject,ALIGN);
            int color = getTargetIntValue(spineSkinArcJSONObject,COLOR);
            int strokeWidth = getTargetIntValue(spineSkinArcJSONObject,STROKE_WIDTH);
            int startAngle = getTargetIntValue(spineSkinArcJSONObject,START_ANGLE);
            int sweepAngle = getTargetIntValue(spineSkinArcJSONObject,SWEEP_ANGLE);

            JSONObject spineSkinArcOrigRect = getTargetJsonObject(spineSkinArcJSONObject,ORIG_RECT);
            if (spineSkinArcOrigRect != null){
                Rect rect = getRect(spineSkinArcOrigRect);
                if (rect != null){
                    spineSkinArc.setOrigRect(rect);
                }
            }

            spineSkinArc.setAlign(align);
            spineSkinArc.setColor(color);
            spineSkinArc.setStrokeWidth(strokeWidth);
            spineSkinArc.setStartAngle(startAngle);
            spineSkinArc.setSweepAngle(sweepAngle);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return spineSkinArc;
    }

    /**
     * 获取对应的 SpineSkinAnchor 对象
     *
     * @param spineSkinAnchorJSONObject
     */
    private static RobotSkinAnchor getSpineSkinAnchor(JSONObject spineSkinAnchorJSONObject) {

        RobotSkinAnchor spineSkinAnchor = new RobotSkinAnchor();

        try {
            JSONObject origRect = getTargetJsonObject(spineSkinAnchorJSONObject, ORIG_RECT);
            if (origRect != null) {
                Rect bgRect = getRect(origRect);
                if (bgRect != null){
                    spineSkinAnchor.setOrigRect(bgRect);
                }
            }

            JSONObject origAnchor = getTargetJsonObject(spineSkinAnchorJSONObject, ORIG_ANCHOR);
            if (origAnchor != null) {
                Point oaPoint = getPoint(origAnchor);
                if (oaPoint != null){
                    spineSkinAnchor.setOrigAnchor(oaPoint);
                }
            }

            String imgFile = getTargetStringValue(spineSkinAnchorJSONObject, IMG_FILE);
            if (imgFile != null) {
                spineSkinAnchor.setImgFile(imgFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return spineSkinAnchor;

    }

    /**
     * 获取对应的 SpineSkinImage 对象
     * @param spineSkinImageJSONObject
     */
    private static RobotSkinImage getSpineSkinImage(JSONObject spineSkinImageJSONObject) {

        RobotSkinImage spineSkinImage = new RobotSkinImage();

        try {
            JSONObject origRect = getTargetJsonObject(spineSkinImageJSONObject,ORIG_RECT);
            if (origRect != null){
                Rect bgRect = getRect(origRect);
                if (bgRect != null){
                    spineSkinImage.setOrigRect(bgRect);
                }
            }

            int align = getTargetIntValue(spineSkinImageJSONObject,ALIGN);
            spineSkinImage.setAlign(align);

            String filePrefix = getTargetStringValue(spineSkinImageJSONObject,FILE_PREFIX);
            if (filePrefix != null){
                spineSkinImage.setFilePrefix(filePrefix);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return spineSkinImage;

    }

    /**
     * 获取对应的 SpineSkinImageWithSpace 对象
     * @param spineSkinImageWithSpaceJSONObject
     */
    private static RobotSkinImageWithSpace getSpineSkinImageWithSpace(JSONObject spineSkinImageWithSpaceJSONObject) {

        RobotSkinImageWithSpace spineSkinImageWithSpace = new RobotSkinImageWithSpace();

        try {
            JSONObject origRect = getTargetJsonObject(spineSkinImageWithSpaceJSONObject,ORIG_RECT);
            if (origRect != null){
                Rect bgRect = getRect(origRect);
                if (bgRect != null){
                    spineSkinImageWithSpace.setOrigRect(bgRect);
                }
            }

            int align = getTargetIntValue(spineSkinImageWithSpaceJSONObject,ALIGN);
            spineSkinImageWithSpace.setAlign(align);

            int aligns = getTargetIntValue(spineSkinImageWithSpaceJSONObject,ALIGNS);
            spineSkinImageWithSpace.setAligns(aligns);

            int total = getTargetIntValue(spineSkinImageWithSpaceJSONObject,TOTAL);
            spineSkinImageWithSpace.setTotal(total);

            int fileSpace = getTargetIntValue(spineSkinImageWithSpaceJSONObject,FILE_SPACE);
            spineSkinImageWithSpace.setFileSpace(fileSpace);

            String filePrefix = getTargetStringValue(spineSkinImageWithSpaceJSONObject,FILE_PREFIX);
            if (filePrefix != null){
                spineSkinImageWithSpace.setFilePrefix(filePrefix);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return spineSkinImageWithSpace;

    }
    /**
     * 获取对应的 SpineSkinImageWithSpace 对象
     * @param spineSkinImageWithAngleJSONObject
     */
    private static RobotSkinImageWithAngle getSpineSkinImageWithAngle(JSONObject spineSkinImageWithAngleJSONObject) {

        RobotSkinImageWithAngle spineSkinImageWithAngle = new RobotSkinImageWithAngle();

        try {
            JSONObject origAnchor = getTargetJsonObject(spineSkinImageWithAngleJSONObject,ORIG_ANCHOR);
            if (origAnchor != null){
                Point oaPoint = getPoint(origAnchor);
                if (oaPoint != null){
                    spineSkinImageWithAngle.setOrigAnchor(oaPoint);
                }
            }

            JSONObject displayAnchor = getTargetJsonObject(spineSkinImageWithAngleJSONObject,DISPLAY_ANCHOR);
            if (displayAnchor != null){
                Point daPoint = getPoint(displayAnchor);
                if (daPoint != null){
                    spineSkinImageWithAngle.setDisplayAnchor(daPoint);
                }
            }

            int align = getTargetIntValue(spineSkinImageWithAngleJSONObject,ALIGN);
            spineSkinImageWithAngle.setAlign(align);

            int total = getTargetIntValue(spineSkinImageWithAngleJSONObject,TOTAL);
            spineSkinImageWithAngle.setTotal(total);

            int intervalAngle = getTargetIntValue(spineSkinImageWithAngleJSONObject,INTERVAL_ANGLE);
            spineSkinImageWithAngle.setIntervalAngle(intervalAngle);

            int imageAngle = getTargetIntValue(spineSkinImageWithAngleJSONObject,IMAGE_ANGLE);
            spineSkinImageWithAngle.setImageAngle(imageAngle);

            int startAngle = getTargetIntValue(spineSkinImageWithAngleJSONObject,START_ANGLE);
            spineSkinImageWithAngle.setStartAngle(startAngle);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return spineSkinImageWithAngle;

    }


    /**
     * 获取对应的 SpineSkinNumber 对象
     * @param spineSkinNumberSONObject
     */
    private static RobotSkinNumber getSpineSkinNumber(JSONObject spineSkinNumberSONObject) {

        RobotSkinNumber spineSkinNumber = new RobotSkinNumber();

        try {
            JSONObject origRect = getTargetJsonObject(spineSkinNumberSONObject,ORIG_RECT);
            if (origRect != null){
                Rect bgRect = getRect(origRect);
                if (bgRect != null){
                    spineSkinNumber.setOrigRect(bgRect);
                }
            }

            int align = getTargetIntValue(spineSkinNumberSONObject,ALIGN);
            spineSkinNumber.setAlign(align);

            String filePrefix = getTargetStringValue(spineSkinNumberSONObject,FILE_PREFIX);
            if (filePrefix != null){
                spineSkinNumber.setFilePrefix(filePrefix);
            }
            String dataFormat = getTargetStringValue(spineSkinNumberSONObject,DATA_FORMAT);
            if (filePrefix != null){
                spineSkinNumber.setDataFormat(dataFormat);
            }

            int fileSpace = getTargetIntValue(spineSkinNumberSONObject,FILE_SPACE);
            spineSkinNumber.setFileSpace(fileSpace);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return spineSkinNumber;

    }

    /**
     * 获取对应的rect
     * @param origRect
     * @return
     */
    private static Rect getRect(JSONObject origRect) {

        Rect rect = new Rect();
        try {
            int left = origRect.getInt(LEFT);
            int top = origRect.getInt(TOP);
            int right = origRect.getInt(RIGHT);
            int bottom = origRect.getInt(BOTTOM);
            rect.set(left,top,right,bottom);

        } catch (JSONException e) {
            return null;
        }
        return rect;

    }

    /**
     * 获取对应的 Point
     * @param origAnchor
     * @return
     */
    private static Point getPoint(JSONObject origAnchor) {

        Point point = new Point();
        try {
            int x = origAnchor.getInt(X);
            int y = origAnchor.getInt(Y);
            point.set(x,y);

        } catch (JSONException e) {
            return null;
        }
        return point;

    }


}
