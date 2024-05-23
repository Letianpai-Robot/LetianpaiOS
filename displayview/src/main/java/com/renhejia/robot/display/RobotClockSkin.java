package com.renhejia.robot.display;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class RobotClockSkin {

    public final static int ALIGN_LEFT   = 0x0;
    public final static int ALIGN_CENTER = 0x1;
    public final static int ALIGN_RIGHT  = 0x2;

    public final static int ALIGN_TOP     = 0x0;
    public final static int ALIGN_MIDDLE  = 0x4;
    public final static int ALIGN_BOTTOM  = 0x8;

    public final static int ALIGN_LEFT_TO_RIGHT   = 0x0;
    public final static int ALIGN_RIGHT_TO_LEFT   = 0x1;
    public final static int ALIGN_BOTTOM_TO_TOP   = 0x2;
    public final static int ALIGN_TOP_TO_BOTTOM   = 0x3;

    public final static int WEATHER_TYPE_NO_INFO = -1;

    public final static int WEATHER_TYPE_SUNNY = 0;
    public final static int WEATHER_TYPE_CLOUDY = 1;
    public final static int WEATHER_TYPE_RAIN = 2;
    public final static int WEATHER_TYPE_HAIL = 3;
    public final static int WEATHER_TYPE_SNOW = 4;
    public final static int WEATHER_TYPE_SAND_DUST = 5;
    public final static int WEATHER_TYPE_HAZE = 6;
    public final static int WEATHER_TYPE_THUNDER = 7;
    public final static int WEATHER_TYPE_WIND  = 8;

    public final static int WEATHER_TYPE_FOG = 9;
    public final static int WEATHER_TYPE_RAIN_HAIL = 10;
    public final static int WEATHER_TYPE_RAIN_SNOW = 11;
    public final static int WEATHER_TYPE_RAIN_THUNDER = 12;

    public final static int CTRL_NONE_ID = 0;
    public final static int CTRL_BLUETOOTH_ID = 1;
    public final static int CTRL_MEDIA_VOLUME_ID = 2;
    public final static int CTRL_WIFI_ID = 3;
    public final static int CTRL_STEP_ID = 4;
    public final static int CTRL_WEATHER_ID = 5;

    public final static int STYLE_DEFAULT = 0;
    public final static int STYLE_ITALIC = 1;

    private List<RobotSkinAnalogTime> analogTimes = new ArrayList<RobotSkinAnalogTime>();
    private List<RobotSkinNumber> digitTimes = new ArrayList<RobotSkinNumber>();
    private List<RobotSkinLabel> labelTimes = new ArrayList<RobotSkinLabel>();

    private RobotSkinNumber step;
    private RobotSkinNumber airTemp;

    private RobotSkinImage battery;
    private RobotSkinImage wifi;
    private RobotSkinImage bluetooth;
    private RobotSkinImage weather;
    private RobotSkinImage notices;                                  // todo new
    private List<RobotSkinLabel> weatherFeatureTime = new ArrayList<RobotSkinLabel>();
    private List<RobotSkinLabel> weatherFeatureTemp = new ArrayList<RobotSkinLabel>();
    private List<RobotSkinImage> weatherFeatureIcon = new ArrayList<RobotSkinImage>();
    private List<RobotSkinAnchor> backgrounds = new ArrayList<>();    // todo new
    private RobotSkinImage volume;
    private RobotSkinImage charge;
    private RobotSkinImage background;
    private RobotSkinImage foreground;
    private RobotSkinImage middle;
    private RobotSkinImageWithSpace batterySquares;
    private RobotSkinImageWithSpace stepSquares;

    private RobotSkinAnchor batteryAnchor;
    private RobotSkinAnchor weekAnchor;

    private RobotSkinLabel stepNumber;
    private RobotSkinLabel aqiNumber;
    private RobotSkinLabel aqiText;
    private RobotSkinLabel noticeText;               // todo new
    private RobotSkinLabel testDes;
    private RobotSkinLabel batteryNumber;
    private RobotSkinLabel temperature;
    private RobotSkinLabel temperatureRange;
    private RobotSkinLabel windPower;

    private List<RobotSkinLabel> weatherInfo = new ArrayList<RobotSkinLabel>();
    private List<RobotSkinLabel> countdownEvent = new ArrayList<RobotSkinLabel>(); // todo new
    private List<RobotSkinLabel> notice = new ArrayList<RobotSkinLabel>(); // todo new
    private List<RobotSkinLabel> fansInfo = new ArrayList<RobotSkinLabel>(); // todo new
    private RobotSkinImage fansIcon ; // todo new
    private RobotSkinImage fansHead ; // todo new

    private RobotSkinShape batteryProgress;

    private RobotSkinArc stepSkinArc;
    private RobotSkinImageWithAngle batteryAngle;

    private Rect origRect;
    private Rect dispRect;

    private float xRadio = 1.0f;
    private float yRadio = 1.0f;

    private int videoTotal = 0;

    private SpineSkinResPool resPool;

    public float getxRadio() {
        return xRadio;
    }

    public float getyRadio() {
        return yRadio;
    }

    public Rect calcDispRect(Rect origRect, float xRadio, float yRadio) {
        float left = origRect.left * xRadio;
        float top = origRect.top * yRadio;
        float right = origRect.right * xRadio;
        float bottom = origRect.bottom * yRadio;
        return new Rect((int) left, (int) top, (int) right, (int) bottom);
    }

    public Point calcDispPoint(Point origPoint, float xRadio, float yRadio) {
        float x = origPoint.x * xRadio;
        float y = origPoint.y * yRadio;
        return new Point((int) x, (int) y);
    }

    public void resize(Rect rect) {

        if (dispRect != rect && origRect != null) {
            dispRect = rect;

            xRadio = (float) rect.width() / origRect.width();
            yRadio = (float) rect.height() / origRect.height();

            if (background != null) {
                background.setDispRect(calcDispRect(background.getOrigRect(), xRadio, yRadio));
            }

            if(backgrounds != null){
                List<RobotSkinAnchor> bgs = getBackgrounds();

                for (RobotSkinAnchor bg : bgs) {
                    bg.setDispRect(calcDispRect(bg.getOrigRect(), xRadio, yRadio));
                    bg.setDispAnchor(calcDispPoint(bg.getOrigAnchor(), xRadio, yRadio));
                }
            }


            if (foreground != null) {
                foreground.setDispRect(calcDispRect(foreground.getOrigRect(), xRadio, yRadio));
            }

            if (middle != null) {
                middle.setDispRect(calcDispRect(middle.getOrigRect(), xRadio, yRadio));
            }

            if (batteryProgress != null) {
                batteryProgress.setDispRect(calcDispRect(batteryProgress.getOrigRect(), xRadio, yRadio));
            }

            if (stepSkinArc != null) {
                stepSkinArc.setDispRect(stepSkinArc.getOrigRect());
            }

            if (aqiNumber != null) {
                aqiNumber.setDispRect(calcDispRect(aqiNumber.getOrigRect(), xRadio, yRadio));

                if (aqiNumber.getOrigTouchRect() != null) {
                    aqiNumber.setDispTouchRect(calcDispRect(aqiNumber.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    aqiNumber.setDispTouchRect(calcDispRect(aqiNumber.getOrigRect(), xRadio, yRadio));
                }

                float dispSize = aqiNumber.getOrigSize() * (Math.min(xRadio, yRadio));
                aqiNumber.setDispSize((int) dispSize);
            }

            if (stepNumber != null) {
                stepNumber.setDispRect(calcDispRect(stepNumber.getOrigRect(), xRadio, yRadio));

                if (stepNumber.getOrigTouchRect() != null) {
                    stepNumber.setDispTouchRect(calcDispRect(stepNumber.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    stepNumber.setDispTouchRect(calcDispRect(stepNumber.getOrigRect(), xRadio, yRadio));
                }

                float dispSize = stepNumber.getOrigSize() * (Math.min(xRadio, yRadio));
                stepNumber.setDispSize((int) dispSize);
            }
            if (batteryNumber != null) {
                batteryNumber.setDispRect(calcDispRect(batteryNumber.getOrigRect(), xRadio, yRadio));

                if (batteryNumber.getOrigTouchRect() != null) {
                    batteryNumber.setDispTouchRect(calcDispRect(batteryNumber.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    batteryNumber.setDispTouchRect(calcDispRect(batteryNumber.getOrigRect(), xRadio, yRadio));
                }

                float dispSize = batteryNumber.getOrigSize() * (Math.min(xRadio, yRadio));
                batteryNumber.setDispSize((int) dispSize);
            }
            if (temperature != null) {
                temperature.setDispRect(calcDispRect(temperature.getOrigRect(), xRadio, yRadio));

                if (temperature.getOrigTouchRect() != null) {
                    temperature.setDispTouchRect(calcDispRect(temperature.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    temperature.setDispTouchRect(calcDispRect(temperature.getOrigRect(), xRadio, yRadio));
                }

                float dispSize = temperature.getOrigSize() * (Math.min(xRadio, yRadio));
                temperature.setDispSize((int) dispSize);
            }
            if (temperatureRange != null) {
                temperatureRange.setDispRect(calcDispRect(temperatureRange.getOrigRect(), xRadio, yRadio));

                if (temperatureRange.getOrigTouchRect() != null) {
                    temperatureRange.setDispTouchRect(calcDispRect(temperatureRange.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    temperatureRange.setDispTouchRect(calcDispRect(temperatureRange.getOrigRect(), xRadio, yRadio));
                }

                float dispSize = temperatureRange.getOrigSize() * (Math.min(xRadio, yRadio));
                temperatureRange.setDispSize((int) dispSize);
            }

            if (aqiText != null) {
                aqiText.setDispRect(calcDispRect(aqiText.getOrigRect(), xRadio, yRadio));
                if (aqiText.getOrigTouchRect() != null) {
                    aqiText.setDispTouchRect(calcDispRect(aqiText.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    aqiText.setDispTouchRect(calcDispRect(aqiText.getOrigRect(), xRadio, yRadio));
                }

                float dispSize = aqiText.getOrigSize() * (Math.min(xRadio, yRadio));
                aqiText.setDispSize((int) dispSize);
            }

            if (noticeText != null) {
                noticeText.setDispRect(calcDispRect(noticeText.getOrigRect(), xRadio, yRadio));
                if (noticeText.getOrigTouchRect() != null) {
                    noticeText.setDispTouchRect(calcDispRect(noticeText.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    noticeText.setDispTouchRect(calcDispRect(noticeText.getOrigRect(), xRadio, yRadio));
                }

                float dispSize = noticeText.getOrigSize() * (Math.min(xRadio, yRadio));
                noticeText.setDispSize((int) dispSize);
            }

            if (charge != null) {
                charge.setDispRect(calcDispRect(charge.getOrigRect(), xRadio, yRadio));
            }
            if (step != null) {
                step.setDispRect(calcDispRect(step.getOrigRect(), xRadio, yRadio));
                if (step.getOrigTouchRect() != null) {
                    step.setDispTouchRect(calcDispRect(step.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    step.setDispTouchRect(calcDispRect(step.getOrigRect(), xRadio, yRadio));
                }
            }

            if (weather != null) {
                weather.setDispRect(calcDispRect(weather.getOrigRect(), xRadio, yRadio));
                if (weather.getOrigTouchRect() != null) {
                    weather.setDispTouchRect(calcDispRect(weather.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    weather.setDispTouchRect(calcDispRect(weather.getOrigRect(), xRadio, yRadio));
                }
            }
            if (notices != null) {
                notices.setDispRect(calcDispRect(notices.getOrigRect(), xRadio, yRadio));
                if (notices.getOrigTouchRect() != null) {
                    notices.setDispTouchRect(calcDispRect(notices.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    notices.setDispTouchRect(calcDispRect(notices.getOrigRect(), xRadio, yRadio));
                }
            }
            if (fansIcon != null) {
                fansIcon.setDispRect(calcDispRect(fansIcon.getOrigRect(), xRadio, yRadio));
                if (fansIcon.getOrigTouchRect() != null) {
                    fansIcon.setDispTouchRect(calcDispRect(fansIcon.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    fansIcon.setDispTouchRect(calcDispRect(fansIcon.getOrigRect(), xRadio, yRadio));
                }
            }
            if (fansHead != null) {
                fansHead.setDispRect(calcDispRect(fansHead.getOrigRect(), xRadio, yRadio));
                if (fansHead.getOrigTouchRect() != null) {
                    fansHead.setDispTouchRect(calcDispRect(fansHead.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    fansHead.setDispTouchRect(calcDispRect(fansHead.getOrigRect(), xRadio, yRadio));
                }
            }

            if (airTemp != null) {
                airTemp.setDispRect(calcDispRect(airTemp.getOrigRect(), xRadio, yRadio));
                if (airTemp.getOrigTouchRect() != null) {
                    airTemp.setDispTouchRect(calcDispRect(airTemp.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    airTemp.setDispTouchRect(calcDispRect(airTemp.getOrigRect(), xRadio, yRadio));
                }
            }

            if (wifi != null) {
                wifi.setDispRect(calcDispRect(wifi.getOrigRect(), xRadio, yRadio));
                if (wifi.getOrigTouchRect() != null) {
                    wifi.setDispTouchRect(calcDispRect(wifi.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    wifi.setDispTouchRect(calcDispRect(wifi.getOrigRect(), xRadio, yRadio));
                }
            }

            if (bluetooth != null) {
                bluetooth.setDispRect(calcDispRect(bluetooth.getOrigRect(), xRadio, yRadio));
                if (bluetooth.getOrigTouchRect() != null) {
                    bluetooth.setDispTouchRect(calcDispRect(bluetooth.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    bluetooth.setDispTouchRect(calcDispRect(bluetooth.getOrigRect(), xRadio, yRadio));
                }
            }

            if (volume != null) {
                volume.setDispRect(calcDispRect(volume.getOrigRect(), xRadio, yRadio));
                if (volume.getOrigTouchRect() != null) {
                    volume.setDispTouchRect(calcDispRect(volume.getOrigTouchRect(), xRadio, yRadio));
                }
                else {
                    volume.setDispTouchRect(calcDispRect(volume.getOrigRect(), xRadio, yRadio));
                }
            }

            if (battery != null) {
                battery.setDispRect(calcDispRect(battery.getOrigRect(), xRadio, yRadio));
            }


            if (batteryAnchor != null) {
                batteryAnchor.setDispRect(calcDispRect(batteryAnchor.getOrigRect(), xRadio, yRadio));
                batteryAnchor.setDispAnchor(calcDispPoint(batteryAnchor.getOrigAnchor(), xRadio, yRadio));
            }

            if (weekAnchor != null) {
                weekAnchor.setDispRect(calcDispRect(weekAnchor.getOrigRect(), xRadio, yRadio));
                weekAnchor.setDispAnchor(calcDispPoint(weekAnchor.getOrigAnchor(), xRadio, yRadio));
            }

            if (batterySquares != null) {
                batterySquares.setDispRect(calcDispRect(batterySquares.getOrigRect(), xRadio, yRadio));
            }

            if (stepSquares != null) {
                stepSquares.setDispRect(calcDispRect(stepSquares.getOrigRect(), xRadio, yRadio));
            }

            if(weatherFeatureTemp != null){
                List<RobotSkinLabel> wts = getWeatherFeatureTemp();

                for (RobotSkinLabel wt : wts) {
                    wt.setDispRect(calcDispRect(wt.getOrigRect(), xRadio, yRadio));
                }
            }

            if(weatherFeatureTime != null){
                List<RobotSkinLabel> wts = getWeatherFeatureTime();

                for (RobotSkinLabel wt : wts) {
                    wt.setDispRect(calcDispRect(wt.getOrigRect(), xRadio, yRadio));
                }
            }
            
            if(weatherFeatureIcon != null){
                List<RobotSkinImage> wis = getWeatherFeatureIcon();

                for (RobotSkinImage wi : wis) {
                    wi.setDispRect(calcDispRect(wi.getOrigRect(), xRadio, yRadio));
                }
            }

            if(countdownEvent != null){
                List<RobotSkinLabel> ces = getCountdownEvent();
                for (RobotSkinLabel ce : ces) {
                    ce.setDispRect(calcDispRect(ce.getOrigRect(), xRadio, yRadio));
                    float dispSize = ce.getOrigSize() * (Math.min(xRadio, yRadio));
                    ce.setDispSize((int) dispSize);
                }
            }
            if(fansInfo != null){
                List<RobotSkinLabel> infos = getFansInfo();
                for (RobotSkinLabel info : infos) {
                    info.setDispRect(calcDispRect(info.getOrigRect(), xRadio, yRadio));
                    float dispSize = info.getOrigSize() * (Math.min(xRadio, yRadio));
                    info.setDispSize((int) dispSize);
                }
            }
            //notice 和 notice名字需要调整
            if(notice != null){
                List<RobotSkinLabel> notices = getNotice();
                for (RobotSkinLabel notice : notices) {
                    notice.setDispRect(calcDispRect(notice.getOrigRect(), xRadio, yRadio));
                    float dispSize = notice.getOrigSize() * (Math.min(xRadio, yRadio));
                    notice.setDispSize((int) dispSize);
                }
            }

            List<RobotSkinLabel> lts = getLabelTimes();
            for (RobotSkinLabel lt : lts) {
                lt.setDispRect(calcDispRect(lt.getOrigRect(), xRadio, yRadio));
                float dispSize = lt.getOrigSize() * (Math.min(xRadio, yRadio));
                lt.setDispSize((int) dispSize);
            }

            List<RobotSkinNumber> dts = getDigitTimes();

            for (RobotSkinNumber dt : dts) {
                dt.setDispRect(calcDispRect(dt.getOrigRect(), xRadio, yRadio));
            }

            List<RobotSkinAnalogTime> ats = getAnalogTimes();

            for (RobotSkinAnalogTime at : ats) {

                at.setDispRect(calcDispRect(at.getOrigRect(), xRadio, yRadio));
//                at.setDispHourAnchor(calcDispPoint(at.getOrigHourAnchor(), xRadio, yRadio));
//                at.setDispMinuteAnchor(calcDispPoint(at.getOrigMinuteAnchor(), xRadio, yRadio));
                if (at.getOrigSecondAnchor() != null) {
                    at.setDispSecondAnchor(calcDispPoint(at.getOrigSecondAnchor(), xRadio, yRadio));
                }
            }
        }
    }

    public List<RobotSkinLabel> getWeatherFeatureTime() {
        return weatherFeatureTime;
    }

    public List<RobotSkinLabel> getWeatherFeatureTemp() {
        return weatherFeatureTemp;
    }

    public List<RobotSkinImage> getWeatherFeatureIcon() {
        return weatherFeatureIcon;
    }
    public List<RobotSkinAnchor> getBackgrounds() {
        return backgrounds;
    }


    public List<RobotSkinNumber> getDigitTimes() {
        return digitTimes;
    }

    public void setWeatherFeatureIcon(List<RobotSkinImage> weatherFeatureIcon) {
        this.weatherFeatureIcon = weatherFeatureIcon;
    }

    public void setWeatherFeatureTemp(List<RobotSkinLabel> weatherFeatureTemp) {
        this.weatherFeatureTemp = weatherFeatureTemp;
    }

    public void setWeatherFeatureTime(List<RobotSkinLabel> weatherFeatureTime) {
        this.weatherFeatureTime = weatherFeatureTime;
    }

    public void setBackgrounds(List<RobotSkinAnchor> backgrounds) {
        this.backgrounds = backgrounds;
    }

    public void setDigitTimes(List<RobotSkinNumber> digitTimes) {
        this.digitTimes = digitTimes;
    }

    public Rect getOrigRect() {
        return origRect;
    }

    public void setOrigRect(Rect origRect) {
        this.origRect = origRect;
    }

    public SpineSkinResPool getResPool() {
        return resPool;
    }

    public void setResPool(SpineSkinResPool resPool) {
        this.resPool = resPool;
    }

    public RobotSkinImage getBattery() {
        return battery;
    }

    public void setBattery(RobotSkinImage battery) {
        this.battery = battery;
    }

    public RobotSkinImage getBackground() {
        return background;
    }

    public void setBackground(RobotSkinImage background) {
        this.background = background;
    }

    public List<RobotSkinAnalogTime> getAnalogTimes() {
        return analogTimes;
    }

    public void setAnalogTimes(List<RobotSkinAnalogTime> analogTimes) {
        this.analogTimes = analogTimes;
    }

    public RobotSkinImage getWifi() {
        return wifi;
    }

    public void setWifi(RobotSkinImage wifi) {
        this.wifi = wifi;
    }

    public RobotSkinImage getVolume() {
        return volume;
    }

    public void setVolume(RobotSkinImage volume) {
        this.volume = volume;
    }

    public RobotSkinNumber getStep() {
        return step;
    }

    public void setStep(RobotSkinNumber step) {
        this.step = step;
    }

    public RobotSkinAnchor getBatteryAnchor() {
        return batteryAnchor;
    }

    public void setBatteryAnchor(RobotSkinAnchor batteryAnchor) {
        this.batteryAnchor = batteryAnchor;
    }

    public RobotSkinAnchor getWeekAnchor() {
        return weekAnchor;
    }

    public void setWeekAnchor(RobotSkinAnchor weekAnchor) {
        this.weekAnchor = weekAnchor;
    }

    public RobotSkinNumber getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(RobotSkinNumber airTemp) {
        this.airTemp = airTemp;
    }

    public RobotSkinImage getWeather() {
        return weather;
    }

    public void setWeather(RobotSkinImage weather) {
        this.weather = weather;
    }

    public RobotSkinImage getNotices() {
        return notices;
    }

    public void setNotices(RobotSkinImage notices) {
        this.notices = notices;
    }


    public RobotSkinImage getCharge() {
        return charge;
    }

    public void setCharge(RobotSkinImage charge) {
        this.charge = charge;
    }

    public RobotSkinLabel getAqiNumber() {
        return aqiNumber;
    }

    public void setAqiNumber(RobotSkinLabel aqiNumber) {
        this.aqiNumber = aqiNumber;
    }

    public RobotSkinLabel getAqiText() {
        return aqiText;
    }

    public void setAqiText(RobotSkinLabel aqiText) {
        this.aqiText = aqiText;
    }

    public void setNoticeText(RobotSkinLabel noticeText) {
        this.noticeText = noticeText;
    }

    public RobotSkinLabel getNoticeText() {
        return noticeText;
    }

    public RobotSkinImage getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(RobotSkinImage bluetooth) {
        this.bluetooth = bluetooth;
    }

    public List<RobotSkinLabel> getLabelTimes() {
        return labelTimes;
    }

    public void setLabelTimes(List<RobotSkinLabel> labelTimes) {
        this.labelTimes = labelTimes;
    }

    public List<RobotSkinLabel> getCountdownEvent() {
        return countdownEvent;
    }

    public void setCountdownEvent(List<RobotSkinLabel> countdownEvent) {
        this.countdownEvent = countdownEvent;
    }

    public List<RobotSkinLabel> getNotice() {
        return notice;
    }

    public void setNotice(List<RobotSkinLabel> notice) {
        this.notice = notice;
    }

    public void setFansIcon(RobotSkinImage fansIcon) {
        this.fansIcon = fansIcon;
    }

    public RobotSkinImage getFansIcon() {
        return fansIcon;
    }

    public void setFansHead(RobotSkinImage fansHead) {
        this.fansHead = fansHead;
    }

    public RobotSkinImage getFansHead() {
        return fansHead;
    }

    public void setFansInfo(List<RobotSkinLabel> fansInfo) {
        this.fansInfo = fansInfo;
    }

    public List<RobotSkinLabel> getFansInfo() {
        return fansInfo;
    }

    public int getVideoTotal() {
        return videoTotal;
    }

    public void setVideoTotal(int videoTotal) {
        this.videoTotal = videoTotal;
    }

    public RobotSkinLabel getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(RobotSkinLabel stepNumber) {
        this.stepNumber = stepNumber;
    }


    public RobotSkinLabel getBatteryNumber() {
        return batteryNumber;
    }

    public void setBatteryNumber(RobotSkinLabel batteryNumber) {
        this.batteryNumber = batteryNumber;
    }

    public RobotSkinLabel getTemperature() {
        return temperature;
    }

    public void setTemperature(RobotSkinLabel temperature) {
        this.temperature = temperature;
    }

    public RobotSkinLabel getTemperatureRange() {
        return temperatureRange;
    }

    public void setTemperatureRange(RobotSkinLabel temperatureRange) {
        this.temperatureRange = temperatureRange;
    }

    public RobotSkinImageWithSpace getBatterySquares() {
        return batterySquares;
    }

    public void setBatterySquares(RobotSkinImageWithSpace batterySquares) {
        this.batterySquares = batterySquares;
    }

    public RobotSkinImageWithSpace getStepSquares() {
        return stepSquares;
    }

    public void setStepSquares(RobotSkinImageWithSpace stepSquares) {
        this.stepSquares = stepSquares;
    }

    public RobotSkinImage getForeground() {
        return foreground;
    }

    public void setForeground(RobotSkinImage foreground) {
        this.foreground = foreground;
    }

    public RobotSkinImage getMiddle() {
        return middle;
    }

    public void setMiddle(RobotSkinImage middle) {
        this.middle = middle;
    }

    public RobotSkinShape getBatteryProgress() {
        return batteryProgress;
    }

    public void setBatteryProgress(RobotSkinShape batteryProgress) {
        this.batteryProgress = batteryProgress;
    }

    public void setStepSkinArc(RobotSkinArc stepSkinArc) {
        this.stepSkinArc = stepSkinArc;
    }

    public RobotSkinArc getStepSkinArc() {
        return stepSkinArc;
    }

    public RobotSkinImageWithAngle getBatteryAngle() {
        return batteryAngle;
    }

    public void setBatteryAngle(RobotSkinImageWithAngle batteryAngle) {
        this.batteryAngle = batteryAngle;
    }

    public boolean isRefreshPerSecond() {
        List<RobotSkinAnalogTime> analogTimes = getAnalogTimes();
        if (analogTimes != null && analogTimes.size() > 0) {
            for (int i = 0; i < analogTimes.size(); i++) {
                if ((analogTimes.get(i) != null) && (analogTimes.get(i).getOrigSecondAnchor() != null)) {
                    return true;
                }
            }
        }
        return false;

    }
}
