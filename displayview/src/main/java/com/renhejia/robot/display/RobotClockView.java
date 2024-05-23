package com.renhejia.robot.display;

import static android.renderscript.Allocation.USAGE_SCRIPT;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.provider.Settings;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import com.renhejia.robot.commandlib.log.LogUtils;
import android.view.View;

import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.display.utils.BitmapUtil;
import com.renhejia.robot.display.utils.SpineSkinUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class RobotClockView extends View implements RobotPlatformListener {

    private static final String TAG = RobotClockView.class.getSimpleName();

    public final static int SPINE_CLOCK_HOURS_DEF = 0;
    public final static int SPINE_CLOCK_HOURS_12 = 1;
    public final static int SPINE_CLOCK_HOURS_24 = 2;
    public final static int TEXT_COLOR_GARY = 7566197;
    public final static String YYYYMMDD_FORMAT = "yyyy.MM.dd";
    public final static String MMDD_FORMAT = "MM.dd";
    public final static String THAI = "th";
    public final static String GERMAN = "de";

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private SpineSkinResPool mResPool;
    private RobotClockSkin mSkin;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;
    private RobotPlatformState mPlatformState = new RobotPlatformState();
    private int mWidth = 0;
    private int mHeight = 0;
    private Context mContext;
    private boolean isRefreshNow = false; //
    private Timer mTimer;
    private TimerTask mTimerTask;
    private Thread mThread;

    private int mHourFormat = SPINE_CLOCK_HOURS_24;
    private static String NO_PERCENT_SIGN = "no_percent";

    public RobotClockView(Context context) {
        super(context);
        init(context);
    }

    public RobotClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RobotClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;

        //[niu][20191211]Format 12/24 hour time base on system setting
        mHourFormat = DateFormat.is24HourFormat(context) ? SPINE_CLOCK_HOURS_24 : SPINE_CLOCK_HOURS_12;

//        Timer timer = new Timer();
//        Calendar calendar = Calendar.getInstance();
//        int second = calendar.get(Calendar.SECOND);
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaint.setFakeBoldText(true);
        mPaint.setAntiAlias(true);
//
//        Calendar calendar = Calendar.getInstance();
//        int second = calendar.get(Calendar.SECOND);
//        timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                postInvalidate();
//            }
//        }, (60 - second) * 1000, 1000 * 60);
    }

    ContentObserver mSettingsObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
//            LogUtil.d(TAG, "is24HourFormat onChanged:" + Settings.System.getString(getContext().getContentResolver(), Settings.System.TIME_12_24));
            mHourFormat = DateFormat.is24HourFormat(getContext()) ? SPINE_CLOCK_HOURS_24 : SPINE_CLOCK_HOURS_12;
            setHourFormat(mHourFormat);
            postInvalidate();
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        LogUtil.d(TAG, "registerContentObserver.....");
        getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.TIME_12_24), true, mSettingsObserver);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        LogUtil.d(TAG, "unregisterContentObserver.....");
        getContext().getContentResolver().unregisterContentObserver(mSettingsObserver);
    }


    public void setHourFormat(int hourFormat) {
        mHourFormat = hourFormat;
    }

    public int getHourFormat() {
        return mHourFormat;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = measureWidth;
        mHeight = measureHeight;
        if (mSkin != null) {
            mSkin.resize(new Rect(0, 0, mWidth, mHeight));
        }
    }

    public void setSkin(RobotClockSkin skin) {
        mResPool = skin.getResPool();
        mSkin = skin;
        if (mWidth != 0 && mHeight != 0) {
            mSkin.resize(new Rect(0, 0, mWidth, mHeight));
        }
    }

    private void drawStep(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinNumber step = mSkin.getStep();
        if (step != null) {
            String stepString = String.valueOf(mPlatformState.stepNumber);
            drawNumbers(canvas, step, stepString);
        }
    }

    private void drawAirTemp(Canvas canvas) {
        if (mSkin == null) {
            return;
        }
        RobotSkinNumber airTemp = mSkin.getAirTemp();
        if (airTemp != null) {
            String tempString = "--c";
            if (mPlatformState.weatherState != RobotClockSkin.WEATHER_TYPE_NO_INFO) {
//                tempString = mPlatformState.currentTemp + "c";
                tempString = mPlatformState.currentTemp + "°";
            }

            drawNumbers(canvas, airTemp, tempString);
        }
    }

    private void drawWeekAnchor(Canvas canvas, Date date) {
        if (mSkin == null) {
            return;
        }

        RobotSkinAnchor anchor = mSkin.getWeekAnchor();
        if (anchor != null) {

            Calendar now = Calendar.getInstance();
            now.setTime(date);
            // boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
            int weekDay = now.get(Calendar.DAY_OF_WEEK);
            int weekId = 0;
            switch (weekDay) {
                case Calendar.SUNDAY:
                    weekId = 7;
                    break;
                case Calendar.MONDAY:
                    weekId = 1;
                    break;
                case Calendar.TUESDAY:
                    weekId = 2;
                    break;
                case Calendar.WEDNESDAY:
                    weekId = 3;
                    break;
                case Calendar.THURSDAY:
                    weekId = 4;
                    break;
                case Calendar.FRIDAY:
                    weekId = 5;
                    break;
                case Calendar.SATURDAY:
                    weekId = 6;
                    break;
                default:
                    break;
            }

            Bitmap bitmap = mResPool.getBitmap(anchor.getImgFile(), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                Matrix matrix1 = new Matrix();
                Point imgAnchor = anchor.getDispAnchor();
                Point displayPoint = new Point(anchor.getDispRect().centerX(), anchor.getDispRect().centerY());
                matrix1.postTranslate(displayPoint.x - imgAnchor.x, displayPoint.y - imgAnchor.y);
                matrix1.postRotate(weekId * 360 / 7, displayPoint.x, displayPoint.y);
                canvas.drawBitmap(bitmap, matrix1, mPaint);
            }
        }
    }

    private void drawBatteryAnchor(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinAnchor anchor = mSkin.getBatteryAnchor();
        if (anchor != null) {
            int batteryLevel = mPlatformState.batteryLevel;

            Point imgAnchor;
            Point displayPoint = new Point(anchor.getDispRect().centerX(), anchor.getDispRect().centerY());

            Bitmap bitmap = mResPool.getBitmap(anchor.getImgFile(), mSkin.getxRadio(), mSkin.getyRadio());

            if (bitmap != null) {
                Matrix matrix1 = new Matrix();
                imgAnchor = anchor.getDispAnchor();
                matrix1.postTranslate(displayPoint.x - imgAnchor.x, displayPoint.y - imgAnchor.y);
                matrix1.postRotate(batteryLevel * 33 / 10, displayPoint.x, displayPoint.y);
                canvas.drawBitmap(bitmap, matrix1, mPaint);
            }
        }
    }

    private void drawRotateBackground(Canvas canvas, RobotSkinAnchor rotateBg) {
        if (mSkin == null) {
            return;
        }
        //TODO 背景选装

        if (rotateBg != null && rotateBg.getDispRect() != null) {

            Point imgAnchor;
            Point displayPoint = new Point(rotateBg.getDispRect().centerX(), rotateBg.getDispRect().centerY());

            Bitmap bitmap = mResPool.getBitmap(rotateBg.getImgFile(), mSkin.getxRadio(), mSkin.getyRadio());
            bitmap = rsBlur(mContext, bitmap, 10, 1);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(System.currentTimeMillis()));
            int nSecond = cal.get(Calendar.SECOND);
            int mMillisecond = cal.get(Calendar.MILLISECOND);
            if (bitmap != null) {
                Matrix matrix1 = new Matrix();
                imgAnchor = rotateBg.getDispAnchor();
                if (imgAnchor == null) {
                    LogUtils.logi("testFileName", "imgAnchor is null");
                }
                matrix1.postTranslate(displayPoint.x - imgAnchor.x, displayPoint.y - imgAnchor.y);
//                matrix1.postRotate((nSecond % 8)*45+ (int)(mMillisecond * 45/1000), displayPoint.x, displayPoint.y);
                matrix1.postRotate((nSecond % 15) * 24 + (int) (mMillisecond * 24 / 1000), displayPoint.x, displayPoint.y);
//                matrix1.postRotate(nSecond*6, displayPoint.x, displayPoint.y);
                canvas.drawBitmap(bitmap, matrix1, mPaint);
            }
        }
    }

    // 5分19帧   7秒19帧

    /**
     * 高斯模糊
     *
     * @param context
     * @param source
     * @param radius
     * @param scale
     * @return
     */
    private static Bitmap rsBlur(Context context, Bitmap source, float radius, float scale) {
        if (source == null) {
            return null;
        }
        int scaleWidth = (int) (source.getWidth() * scale);
        int scaleHeight = (int) (source.getHeight() * scale);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(source, scaleWidth,
                scaleHeight, false);

        Bitmap inputBitmap = scaledBitmap;
        //TODO 是否更新刷新机制以节约资源
//        LogUtils.logi("RenderScriptActivity", "size:" + inputBitmap.getWidth() + "," + inputBitmap.getHeight());

        //创建RenderScript
        RenderScript renderScript = RenderScript.create(context);

        //创建Allocation
        Allocation input = Allocation.createFromBitmap(renderScript, inputBitmap, Allocation.MipmapControl.MIPMAP_NONE, USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(renderScript, input.getType());

        //创建ScriptIntrinsic
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

        intrinsicBlur.setInput(input);

        intrinsicBlur.setRadius(radius);

        intrinsicBlur.forEach(output);

        output.copyTo(inputBitmap);

        renderScript.destroy();

        return inputBitmap;
    }


    private void drawNumbers(Canvas canvas, RobotSkinNumber numbers, String text) {
        if (mSkin == null) {
            return;
        }
//        LogUtils.logi("testFileName","drawAirTemp:======= 6 ");
        int align = numbers.getAlign();

//        mPaint.setColor(Color.YELLOW);
//        canvas.drawRect(numbers.getDispRect(), mPaint);

        if (align == (RobotClockSkin.ALIGN_LEFT | RobotClockSkin.ALIGN_TOP)) {
            float top = numbers.getDispRect().top;
            float left = numbers.getDispRect().left;

            Calendar calendar = Calendar.getInstance();
            int millisecond = calendar.get(Calendar.MILLISECOND);

            for (int i = 0; i < text.length(); i++) {
                String subTime = text.substring(i, i + 1);
                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilenameFlash(subTime,millisecond), mSkin.getxRadio(), mSkin.getyRadio());
//                if (bitmap == null) {
//                    bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                }
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, left, top, mPaint);
                    left += numbers.getFileSpace();
                    left += bitmap.getWidth();
                }
            }
        } else {
            int width = 0;
            int height = 0;
            if (numbers == null) {
                LogUtils.logi("robot_exceptipn", "numbers is null");
            } else if (numbers.getDispRect() == null) {
                LogUtils.logi("robot_exceptipn", "numbers.getDispRect() is null");
            } else if (numbers.getDispRect().top == 0) {
                LogUtils.logi("robot_exceptipn", "numbers.getDispRect() is null === 0");
            }
            if (numbers == null || numbers.getDispRect() == null) {
                return;
            }
            int top = numbers.getDispRect().top;
            int left = numbers.getDispRect().left;

            for (int i = 0; i < text.length(); i++) {
                String subTime = text.substring(i, i + 1);
                Calendar calendar = Calendar.getInstance();
                int millisecond = calendar.get(Calendar.MILLISECOND);
                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilenameFlash(subTime,millisecond), mSkin.getxRadio(), mSkin.getyRadio());
//                if (bitmap == null) {
//                    bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                }
                if (bitmap != null) {
                    width += numbers.getFileSpace();
                    width += bitmap.getWidth();

                    if (bitmap.getHeight() > height) {
                        height = bitmap.getHeight();
                    }
                }
            }

            if (align == (RobotClockSkin.ALIGN_CENTER | RobotClockSkin.ALIGN_TOP)) {
                left = numbers.getDispRect().left + (numbers.getDispRect().width() - width) / 2;
                top = numbers.getDispRect().top;
            } else if (align == (RobotClockSkin.ALIGN_RIGHT | RobotClockSkin.ALIGN_TOP)) {
                left = numbers.getDispRect().right - width;
                top = numbers.getDispRect().top;
            } else if (align == (RobotClockSkin.ALIGN_LEFT | RobotClockSkin.ALIGN_MIDDLE)) {
                left = numbers.getDispRect().left;
                top = numbers.getDispRect().top + (numbers.getDispRect().height() - height) / 2;
            } else if (align == (RobotClockSkin.ALIGN_CENTER | RobotClockSkin.ALIGN_MIDDLE)) {
                left = numbers.getDispRect().left + (numbers.getDispRect().width() - width) / 2;
                top = numbers.getDispRect().top + (numbers.getDispRect().height() - height) / 2;
            } else if (align == (RobotClockSkin.ALIGN_RIGHT | RobotClockSkin.ALIGN_MIDDLE)) {
                left = numbers.getDispRect().left + numbers.getDispRect().width() - width;
                top = numbers.getDispRect().top + (numbers.getDispRect().height() - height) / 2;
            } else if (align == (RobotClockSkin.ALIGN_LEFT | RobotClockSkin.ALIGN_BOTTOM)) {
                left = numbers.getDispRect().left;
                top = numbers.getDispRect().top + numbers.getDispRect().height() - height;
            } else if (align == (RobotClockSkin.ALIGN_CENTER | RobotClockSkin.ALIGN_BOTTOM)) {
                left = numbers.getDispRect().left + (numbers.getDispRect().width() - width) / 2;
                top = numbers.getDispRect().top + numbers.getDispRect().height() - height;
            } else if (align == (RobotClockSkin.ALIGN_RIGHT | RobotClockSkin.ALIGN_BOTTOM)) {
                left = numbers.getDispRect().left + numbers.getDispRect().width() - width;
                top = numbers.getDispRect().top + numbers.getDispRect().height() - height;
            }
            Calendar calendar = Calendar.getInstance();
            int millisecond = calendar.get(Calendar.MILLISECOND);
            for (int i = 0; i < text.length(); i++) {
                String subTime = text.substring(i, i + 1);
                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilenameFlash(subTime,millisecond), mSkin.getxRadio(), mSkin.getyRadio());
//                if (bitmap == null) {
//                    bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                }
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, left, top, mPaint);
                    left += numbers.getFileSpace();
                    left += bitmap.getWidth();
                }
            }
        }
    }

    private void drawDigitTime(Canvas canvas, RobotSkinNumber digitTime, Date date) {

        String timeString = digitTime.getTimeString(date, mHourFormat);
        if (timeString != null && timeString.length() > 0) {
            drawNumbers(canvas, digitTime, timeString);
        }
    }

    private void drawAnalogTime(Canvas canvas, RobotSkinAnalogTime analogTime, Date date) {
        if (mSkin == null) {
            return;
        }

        if (analogTime != null) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int nHour = cal.get(Calendar.HOUR);
            int nMinute = cal.get(Calendar.MINUTE);
            int nSecond = cal.get(Calendar.SECOND);
            int nMilliSecond = cal.get(Calendar.MILLISECOND);

            Point imgAnchor;
            Point displayPoint = new Point(analogTime.getDispRect().centerX(), analogTime.getDispRect().centerY());

            Bitmap bitmap = mResPool.getBitmap(analogTime.getHourFilename(), mSkin.getxRadio(), mSkin.getyRadio());

//            if (bitmap != null) {
//                Matrix matrix1 = new Matrix();
//                imgAnchor = analogTime.getDispHourAnchor();
//                matrix1.postTranslate(displayPoint.x - imgAnchor.x, displayPoint.y - imgAnchor.y);
//                matrix1.postRotate((nHour * 3600 + nMinute * 60 + nSecond) * 360 / (12 * 3600), displayPoint.x, displayPoint.y);
//                canvas.drawBitmap(bitmap, matrix1, mPaint);
//            }

            bitmap = mResPool.getBitmap(analogTime.getMinuteFilename(), mSkin.getxRadio(), mSkin.getyRadio());
//
//            if (bitmap != null) {
//                Matrix matrix1 = new Matrix();
//                imgAnchor = analogTime.getDispMinuteAnchor();
//                matrix1.postTranslate(displayPoint.x - imgAnchor.x, displayPoint.y - imgAnchor.y);
//                matrix1.postRotate((nMinute * 60 + nSecond) * 360 / (3600), displayPoint.x, displayPoint.y);
//                canvas.drawBitmap(bitmap, matrix1, mPaint);
//            }

//            if (analogTime.getDispSecondAnchor()!= null) {
//                bitmap = mResPool.getBitmap(analogTime.getSecondFilename(), mSkin.getxRadio(), mSkin.getyRadio());
//
//                if (bitmap != null) {
//                    Matrix matrix1 = new Matrix();
//                    imgAnchor = analogTime.getDispSecondAnchor();
//                    matrix1.postTranslate(displayPoint.x - imgAnchor.x, displayPoint.y - imgAnchor.y);
////                    matrix1.postRotate((nSecond) * 360 / (60), displayPoint.x, displayPoint.y);
//                    matrix1.postRotate((nSecond) * 360 *5 / (60), displayPoint.x, displayPoint.y);
//                    canvas.drawBitmap(bitmap, matrix1, mPaint);
//                }
//            }
//
            if (analogTime.getDispSecondAnchor() != null) {
                bitmap = mResPool.getBitmap(analogTime.getSecondFilename(), mSkin.getxRadio(), mSkin.getyRadio());

                if (bitmap != null) {
                    Matrix matrix1 = new Matrix();
                    imgAnchor = analogTime.getDispSecondAnchor();
                    matrix1.postTranslate(displayPoint.x - imgAnchor.x, displayPoint.y - imgAnchor.y);
//                    matrix1.postRotate((nSecond) * 360 / (60), displayPoint.x, displayPoint.y);
                    matrix1.postRotate((nSecond * 1000 + nMilliSecond) * 360 * 10 / (60 * 1000), displayPoint.x, displayPoint.y);
                    canvas.drawBitmap(bitmap, matrix1, mPaint);
                }
            }
        }
    }

    private void drawBatteryAngle(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImageWithAngle icon = mSkin.getBatteryAngle();
        if (icon != null) {
            int batteryLevel = mPlatformState.batteryLevel;
            int total = icon.getTotal();
            if (total == 0) {
                return;
            }
            int batteryVal = (batteryLevel + (100 / (total * 2))) / (100 / total);

            Bitmap bitmapFull = mResPool.getBitmap(icon.getBatteryFanFull(), mSkin.getxRadio(), mSkin.getyRadio());
            Bitmap bitmapEmpty = mResPool.getBitmap(icon.getBatteryFanEmpty(), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmapEmpty == null || bitmapFull == null) {
                return;
            }

            if (bitmapFull != null) {
                Matrix matrix1 = new Matrix();
                Point imgAnchor = icon.getDisplayAnchor();
                matrix1.postTranslate(icon.getOrigAnchor().x - imgAnchor.x, icon.getOrigAnchor().y - imgAnchor.y);
                for (int i = 0; i < total; i++) {
                    if (i == 0) {
                        matrix1.postRotate(icon.getStartAngle(), icon.getOrigAnchor().x, icon.getOrigAnchor().y);
                    } else {
                        matrix1.postRotate(icon.getImageAngle() + icon.getIntervalAngle(), icon.getOrigAnchor().x, icon.getOrigAnchor().y);
                    }

                    if (i < batteryVal) {
                        canvas.drawBitmap(bitmapFull, matrix1, mPaint);
                    } else {
                        canvas.drawBitmap(bitmapEmpty, matrix1, mPaint);

                    }
                }
            }
        }
    }

    private void drawBatterySquares(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImageWithSpace icon = mSkin.getBatterySquares();
        if (icon != null) {

            int batteryLevel = mPlatformState.batteryLevel;
            int total = icon.getTotal();
            if (total == 0) {
                return;
            }
            int batteryVal = (batteryLevel + (100 / (total * 2))) / (100 / total);

            Bitmap bitmapFull = mResPool.getBitmap(icon.getBatterySquaresFull(), mSkin.getxRadio(), mSkin.getyRadio());
            Bitmap bitmapEmpty = mResPool.getBitmap(icon.getBatterySquaresEmpty(), mSkin.getxRadio(), mSkin.getyRadio());
            drawSquares(canvas, icon, bitmapFull, bitmapEmpty, total, batteryVal);

        }
    }

    private void drawStepSquares(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImageWithSpace stepIcon = mSkin.getStepSquares();
        if (stepIcon != null) {

            int stepNum = mPlatformState.stepNumber;
            int total = stepIcon.getTotal();
            if (total == 0) {
                return;
            }
            int stepLevel = stepNum / 1000;
            if (stepLevel > total) {
                stepLevel = total;
            }
            Bitmap bitmapFull = mResPool.getBitmap(stepIcon.getStepSquaresFull(), mSkin.getxRadio(), mSkin.getyRadio());
            Bitmap bitmapEmpty = mResPool.getBitmap(stepIcon.getStepSquaresEmpty(), mSkin.getxRadio(), mSkin.getyRadio());
            drawSquares(canvas, stepIcon, bitmapFull, bitmapEmpty, total, stepLevel);

        }
    }

    private void drawSquares(Canvas canvas, RobotSkinImageWithSpace icon, Bitmap bitmapFull, Bitmap bitmapEmpty, int total, int currentLevel) {
        int displayX = 0;
        int displayY = 0;
        if (icon != null) {

            if (total == 0) {
                return;
            }

            if ((bitmapEmpty != null) && (bitmapFull != null)) {
                displayX = icon.getDispRect().left;
                displayY = icon.getDispRect().top;

                if (currentLevel >= 0) {

                    for (int j = 0; j < currentLevel; j++) {
                        canvas.drawBitmap(bitmapFull, displayX, displayY, mPaint);
                        if (icon.getAligns() == RobotClockSkin.ALIGN_LEFT_TO_RIGHT) {
                            displayX += bitmapFull.getWidth() + icon.getFileSpace();

                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_RIGHT_TO_LEFT) {
                            displayX -= bitmapFull.getWidth() + icon.getFileSpace();

                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_TOP_TO_BOTTOM) {
                            displayY += bitmapFull.getHeight() + icon.getFileSpace();

                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_BOTTOM_TO_TOP) {
                            displayY -= bitmapFull.getHeight() + +icon.getFileSpace();
                        }
                    }

                    for (int i = 0; i < total - currentLevel; i++) {

                        canvas.drawBitmap(bitmapEmpty, displayX, displayY, mPaint);
                        if (icon.getAligns() == RobotClockSkin.ALIGN_LEFT_TO_RIGHT) {
                            displayX += bitmapEmpty.getWidth() + icon.getFileSpace();

                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_RIGHT_TO_LEFT) {
                            displayX -= bitmapEmpty.getWidth() + icon.getFileSpace();

                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_TOP_TO_BOTTOM) {
                            displayY += bitmapEmpty.getHeight() + icon.getFileSpace();

                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_BOTTOM_TO_TOP) {
                            displayY -= bitmapEmpty.getHeight() + icon.getFileSpace();
                        }
                    }

                }
            }
        }
    }


    private void drawStepSkinArc(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinArc stepSkinArc = mSkin.getStepSkinArc();

        if (stepSkinArc != null) {
            int stepCount = mPlatformState.stepNumber;
            int stepProgress = stepCount / 100;
            if (stepProgress >= 100) {
                stepProgress = 100;
            }
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            if (stepProgress > 0) {
                paint.setColor(stepSkinArc.getColor());
                paint.setStrokeWidth(stepSkinArc.getStrokeWidth());
                canvas.drawArc(new RectF(stepSkinArc.getDispRect()),
                        stepSkinArc.getStartAngle(), ((float) stepProgress / 100) * stepSkinArc.getSweepAngle(), false, paint);
            }

        }
    }


    private void drawBatteryProgressBar(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinShape batteryProgress = mSkin.getBatteryProgress();

        if (batteryProgress != null) {
            int batteryLevel = mPlatformState.batteryLevel;
            int currentProgress = (batteryProgress.getDispRect().right - batteryProgress.getDispRect().left) * batteryLevel / 100;
            Paint backgroundPaint = new Paint();
            backgroundPaint.setAntiAlias(true);
            backgroundPaint.setColor(batteryProgress.getBgColor());
            canvas.drawRect(batteryProgress.getDispRect(), backgroundPaint);

            Paint foregroundPaint = new Paint();
            foregroundPaint.setAntiAlias(true);
            foregroundPaint.setColor(batteryProgress.getFgColor());
            canvas.drawRect(new Rect(batteryProgress.getDispRect().left,
                    batteryProgress.getDispRect().top,
                    batteryProgress.getDispRect().left + currentProgress,
                    batteryProgress.getDispRect().bottom
            ), foregroundPaint);
        }
    }

    private void drawBattery(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage icon = mSkin.getBattery();
        if (icon != null) {

            int batteryLevel = mPlatformState.batteryLevel;
            Bitmap bitmap = mResPool.getBitmap(icon.getBatteryFilename(batteryLevel), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
            }
        }
    }

    private void drawWifi(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage icon = mSkin.getWifi();
        if (icon != null) {
            boolean wifiEnable = mPlatformState.wifiEnabled;
            Bitmap bitmap = mResPool.getBitmap(icon.getOnOffFilename(wifiEnable), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
            }
        }
    }

    private void drawBluetooth(Canvas canvas) {
        if (mSkin == null) {
            return;
        }
        RobotSkinImage icon = mSkin.getBluetooth();
        if (icon != null) {
            boolean bluetoothEnable = mPlatformState.bluetoothEnabled;
            Bitmap bitmap = mResPool.getBitmap(icon.getOnOffFilename(bluetoothEnable), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
            }
        }
    }

    private void drawCharge(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage icon = mSkin.getCharge();
        if (icon != null) {
            boolean isCharging = mPlatformState.batteryCharging;

            Bitmap bitmap = mResPool.getBitmap(icon.getOnOffFilename(isCharging), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
            }
        }
    }

    private void drawWeather(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage icon = mSkin.getWeather();
        if (icon != null) {
            int weatherId = mPlatformState.weatherState;
            weatherId = 0;
            if (weatherId != RobotPlatformState.NO_WEATHER) {
                Bitmap bitmap = mResPool.getBitmap(icon.getWeatherFilename(weatherId), mSkin.getxRadio(), mSkin.getyRadio());
                if (bitmap != null && icon.getDispRect() != null) {
                    canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
                }
            }
        }
    }

    private void drawNotices(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage icon = mSkin.getNotices();
        if (icon != null) {
            Bitmap bitmap = mResPool.getBitmap(icon.getNoticeFileName(), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null && icon.getDispRect() != null) {
                canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
            }
        }
    }

    private void drawFansIcon(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage icon = mSkin.getFansIcon();
        if (icon != null && icon.getDispRect() != null) {
            Bitmap bitmap = mResPool.getBitmap(icon.getFansIconFileName(), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
            }
        }
    }

    private void drawFansHead(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage icon = mSkin.getFansHead();
        if (icon != null && icon.getDispRect() != null) {
            Bitmap bitmap = mResPool.getBitmap(icon.getFansHeadFileName(), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
            }
        }
    }

    private void drawAqiNumber(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinLabel label = mSkin.getAqiNumber();
        if (label != null) {
            String aqiNumber = "";

            if (mPlatformState.weatherState != RobotClockSkin.WEATHER_TYPE_NO_INFO) {
                aqiNumber = String.valueOf(mPlatformState.airQuality);
            }

            drawLabel(canvas, label, aqiNumber);
        }
    }

    private void drawStepNumber(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinLabel label = mSkin.getStepNumber();
        if (label != null) {
            String stepNumber = "";
            stepNumber = String.valueOf(mPlatformState.stepNumber);

            drawLabel(canvas, label, stepNumber);
        }
    }

    private void drawBatteryNumber(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        if (mPlatformState.batteryLevel == 0) {
            return;
        }
        RobotSkinLabel label = mSkin.getBatteryNumber();
        if (label != null) {
            String batteryLevel = "";
            if ((label.getDataFormat() != null) && (label.getDataFormat().equals(NO_PERCENT_SIGN))) {
                batteryLevel = String.valueOf(mPlatformState.batteryLevel);
            } else {
                batteryLevel = String.valueOf(mPlatformState.batteryLevel) + "%";
            }

            drawLabel(canvas, label, batteryLevel);
        }
    }

    private void drawTemperature(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinLabel label = mSkin.getTemperature();
        if (label != null) {
            String temperature = "";
            if (mPlatformState.currentTemp == RobotPlatformState.NO_TEMP) {
                temperature = "--" + "℃";
                label.setColor(TEXT_COLOR_GARY);
            } else {
                temperature = String.valueOf(mPlatformState.currentTemp) + "℃";
            }

            drawLabel(canvas, label, temperature);
        }
    }

    private void drawTemperatureRange(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinLabel label = mSkin.getTemperatureRange();
        if (label != null) {
            String temperature = mPlatformState.tempRange;
            drawLabel(canvas, label, temperature);
        }
    }

    private void drawLabel(Canvas canvas, RobotSkinLabel label, String text) {
        mPaint.setTextSize(label.getDispSize());
        mPaint.setColor(label.getColor());
        if (label.getStyle() == RobotClockSkin.STYLE_ITALIC) {
            mPaint.setTextSkewX((float) -0.25);
        }

        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = fontMetrics.top + fontMetrics.descent;

        int align = label.getAlign();

        if (align == (RobotClockSkin.ALIGN_LEFT | RobotClockSkin.ALIGN_TOP)) {
//            mPaint.setTextSkewX((float) -0.25);
            if (label != null && (label.getDispRect() != null)) {
                canvas.drawText(text, label.getDispRect().left, label.getDispRect().top - baseline, mPaint);
            }

        } else {
            Rect minRect = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), minRect);
            if (label == null || (label.getDispRect() == null)) {
                return;
            }
            int topY = label.getDispRect().top - baseline;
            int middleY = label.getDispRect().top + label.getDispRect().height() / 2 - minRect.height() / 2 - baseline;
            int bottomY = label.getDispRect().bottom - baseline - minRect.height();

            int centerX = label.getDispRect().left + (label.getDispRect().width() - minRect.width()) / 2;
            int leftX = label.getDispRect().left;
            int rightX = label.getDispRect().right - minRect.width();

            if (align == (RobotClockSkin.ALIGN_CENTER | RobotClockSkin.ALIGN_TOP)) {
                canvas.drawText(text, centerX, topY, mPaint);
            } else if (align == (RobotClockSkin.ALIGN_RIGHT | RobotClockSkin.ALIGN_TOP)) {
                canvas.drawText(text, rightX, topY, mPaint);
            } else if (align == (RobotClockSkin.ALIGN_LEFT | RobotClockSkin.ALIGN_MIDDLE)) {
                canvas.drawText(text, leftX, middleY, mPaint);
            } else if (align == (RobotClockSkin.ALIGN_CENTER | RobotClockSkin.ALIGN_MIDDLE)) {
                canvas.drawText(text, centerX, middleY, mPaint);
            } else if (align == (RobotClockSkin.ALIGN_RIGHT | RobotClockSkin.ALIGN_MIDDLE)) {
                canvas.drawText(text, rightX, middleY, mPaint);
            } else if (align == (RobotClockSkin.ALIGN_LEFT | RobotClockSkin.ALIGN_BOTTOM)) {
                canvas.drawText(text, leftX, bottomY, mPaint);
            } else if (align == (RobotClockSkin.ALIGN_CENTER | RobotClockSkin.ALIGN_BOTTOM)) {
                canvas.drawText(text, centerX, bottomY, mPaint);
            } else if (align == (RobotClockSkin.ALIGN_RIGHT | RobotClockSkin.ALIGN_BOTTOM)) {
                canvas.drawText(text, rightX, bottomY, mPaint);
            } else {
                canvas.drawText(text, leftX, topY, mPaint);
            }
        }
        mPaint.setTextSkewX(0);
    }

    private String getAqiText(int aqiNumber) {
        if (aqiNumber < 51) {
            return "优";
        } else if (aqiNumber < 101) {
            return "良";
        } else if (aqiNumber < 151) {
            return "轻度污染";
        } else if (aqiNumber < 201) {
            return "中度污染";
        } else if (aqiNumber < 301) {
            return "重度污染";
        } else {
            return "严重污染";
        }
    }

    private void drawAqiText(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinLabel label = mSkin.getAqiText();
        if (label != null) {
            String aqiText = "";
            if (mPlatformState.weatherState != RobotClockSkin.WEATHER_TYPE_NO_INFO) {
                aqiText = getAqiText(mPlatformState.airQuality);
////                aqiText = "晴 26°  3个日历提醒";
//                aqiText = "晴 26°";
            }
            drawLabel(canvas, label, aqiText);
        }
    }

    private void drawNoticeText(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinLabel label = mSkin.getNoticeText();
        if (label != null) {
            String noticeText = "";
            //TODO
            noticeText = " 1个日历提醒";

            drawLabel(canvas, label, noticeText);
        }
    }

    private void drawVolume(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage icon = mSkin.getVolume();
        if (icon != null) {
            int volume = mPlatformState.mediaVolume;
            Bitmap bitmap = mResPool.getBitmap(icon.getVolumeFilename(volume), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, icon.getDispRect().left, icon.getDispRect().top, mPaint);
            }
        }
    }

//    private void drawBackgroup(Canvas canvas) {
//
//        SpineSkinImage background = mSkin.getBackground();
//        if (background != null) {
//            Bitmap bitmap = mResPool.getBitmap(background.getBackgroundFilename());
//            if (bitmap != null) {
//                canvas.drawBitmap(bitmap, background.getOrigRect(), background.getDispRect(), mPaint);
//            }
//        }
//    }

    private void drawBackgroup(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage background = mSkin.getBackground();
        Bitmap bitmap;
        if (background != null) {
            if (SpineSkinUtils.isChangeSkinBackgroundExit(mContext, mResPool.getSkinPath())) {
                if (mResPool.getBitmap(background.getCustomizedBackgroundFilename()) == null) {


                    bitmap = BitmapUtil.convertImageToBitmap(SpineSkinUtils.getFileNameOfChangeSkinBackground(mContext, mResPool.getSkinPath()));
                    mResPool.fillMapBitmap(background.getCustomizedBackgroundFilename(), bitmap);
                }
                bitmap = mResPool.getBitmap(background.getCustomizedBackgroundFilename());

            } else {
                bitmap = mResPool.getBitmap(background.getBackgroundFilename());
            }
            if (bitmap != null && background != null && background.getOrigRect() != null && background.getDispRect() != null) {
                canvas.drawBitmap(bitmap, background.getOrigRect(), background.getDispRect(), mPaint);
            }
        }
    }

    private void drawForeground(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage foreground = mSkin.getForeground();
        if (foreground != null) {

            Bitmap bitmap = mResPool.getBitmap(foreground.getForeground(), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, foreground.getDispRect().left, foreground.getDispRect().top, mPaint);
            }
        }
    }

    private void drawMiddle(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        RobotSkinImage middle = mSkin.getMiddle();
        if (middle != null) {
            Bitmap bitmap = mResPool.getBitmap(middle.getMiddle(), mSkin.getxRadio(), mSkin.getyRadio());
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, middle.getDispRect().left, middle.getDispRect().top, mPaint);
            }
        }

    }

    private void drawLabelTime(Canvas canvas, RobotSkinLabel label, Date date, int hourFormat) {

        String strFormat = label.getDataFormat();

        if (hourFormat == SPINE_CLOCK_HOURS_24) {
            strFormat = strFormat.replace("a", "");
            strFormat = strFormat.replace("hh", "HH");
        }

        if (strFormat.length() > 0) {
            if (strFormat.length() == 1) {
//                SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.getDefault());
//                String strDate = SpineSkinFileMap.getFilePostfix(format.format(date), label.getLanguageFormat());
////                mPaint.setTextSkewX((float) -0.25);
//                drawLabel(canvas, label, strDate);

                SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.getDefault());

                String strDate = RobotSkinFileMap.getFilePostfix(format.format(date), label.getLanguageFormat());
                if ((Locale.getDefault().getLanguage()).equals(THAI) || (Locale.getDefault().getLanguage()).equals(GERMAN)) {
                    if ((label.getLanguageFormat() != null) && (label.getLanguageFormat().equals(THAI))) {
                        drawLabel(canvas, label, strDate);
                    } else if (label.getLanguageFormat() == null) {
                        label.setDispSize(14);
                        drawLabel(canvas, label, strDate);
                    }
                } else if (!((label.getLanguageFormat() != null) && (label.getLanguageFormat().equals(THAI)))) {
                    drawLabel(canvas, label, strDate);
                }

            } else {
                if (strFormat.equals(YYYYMMDD_FORMAT) || strFormat.equals(MMDD_FORMAT)) {
                    SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.getDefault());
                    String strDate = RobotSkinFileMap.getFilePostfix(format.format(date));
//                    mPaint.setTextSkewX((float) -0.25);
                    drawLabel(canvas, label, strDate);
                } else {
                    String strBestFormat = DateFormat.getBestDateTimePattern(Locale.getDefault(), strFormat);
                    String strDate = DateFormat.format(strBestFormat, date).toString();
//                    mPaint.setTextSkewX((float) -0.25);
                    drawLabel(canvas, label, strDate);
                }
            }
        }
    }

    private void drawTimes(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        Date date = new Date(System.currentTimeMillis());

        List<RobotSkinNumber> digitTimes = mSkin.getDigitTimes();
        if (digitTimes != null) {
            for (RobotSkinNumber dt : digitTimes) {
                drawDigitTime(canvas, dt, date);
            }
        }

        drawWeekAnchor(canvas, date);

        drawMiddle(canvas);

        List<RobotSkinLabel> labelTimes = mSkin.getLabelTimes();
        if (labelTimes != null) {
            for (RobotSkinLabel lt : labelTimes) {
                drawLabelTime(canvas, lt, date, mHourFormat);
            }
        }

        List<RobotSkinAnalogTime> analogTimes = mSkin.getAnalogTimes();
        if (analogTimes != null) {
            for (RobotSkinAnalogTime at : analogTimes) {
                drawAnalogTime(canvas, at, date);
            }
        }

    }

    private void drawCountdownEvent(Canvas canvas) {
        if (mSkin == null) {
            return;
        }
        //TODO 需要更新数据维护逻辑

        List<RobotSkinLabel> events = mSkin.getCountdownEvent();
        String eventText = "";

        if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                if (i == 0) {
                    eventText = "1天后";
                } else {
                    eventText = "结婚纪念日";
                }
                drawLabel(canvas, events.get(i), eventText);
            }

        }

    }

    private void drawBackgrounds(Canvas canvas) {
        if (mSkin == null) {
            return;
        }

        List<RobotSkinAnchor> backgrounds = mSkin.getBackgrounds();

        if (backgrounds != null) {
            for (RobotSkinAnchor bg : backgrounds) {
//                LogUtils.logi("testFileName","bg.getImgFile(): "+ bg.getImgFile());
                drawRotateBackground(canvas, bg);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mPaintFlagsDrawFilter);
        drawBackgroup(canvas);
        drawBackgrounds(canvas);
        drawBatteryProgressBar(canvas);
        drawBluetooth(canvas);
        drawWifi(canvas);
        drawVolume(canvas);
        drawBattery(canvas);
        drawStep(canvas);
        drawBatteryAnchor(canvas);
        drawAirTemp(canvas);
        drawWeather(canvas);
        drawCharge(canvas);
        drawAqiNumber(canvas);
        drawAqiText(canvas);
        drawStepNumber(canvas);
        drawBatteryNumber(canvas);
        drawTemperature(canvas);
        drawStepSkinArc(canvas);
        drawBatterySquares(canvas);
        drawBatteryAngle(canvas);
        drawStepSquares(canvas);
        drawTimes(canvas);
        drawForeground(canvas);
        drawNotices(canvas);
        drawNotice(canvas);
        drawNoticeText(canvas);
        drawCountDownTimer(canvas);
        drawCountdownEvent(canvas);
        drawFansInfo(canvas);
        drawFansIcon(canvas);
        drawFansHead(canvas);
        drawTemperatureRange(canvas);
    }

    private void drawCountDownTimer(Canvas canvas) {


    }

    /**
     * 绘制提醒功能
     *
     * @param canvas
     */
    private void drawNotice(Canvas canvas) {
        if (mSkin == null) {
            return;
        }
        //TODO 需要更新数据维护逻辑

        List<RobotSkinLabel> notices = mSkin.getNotice();
        String noticesText = "";

        if (notices != null) {
            for (int i = 0; i < notices.size(); i++) {
                if (i == 0) {
                    noticesText = "今天 15:09";
                } else if (i == 1) {
                    noticesText = "去超市买菜，要买白萝卜";
                } else if (i == 2) {
                    noticesText = "今天 18:09";
                } else if (i == 3) {
                    noticesText = "去户外遛狗30分钟";
                } else if (i == 4) {
                    noticesText = "明天 8:30";
                } else if (i == 5) {
                    noticesText = "起床卧推20个";
                }
                drawLabel(canvas, notices.get(i), noticesText);
            }

        }

    }

    /**
     * 绘制提醒功能
     *
     * @param canvas
     */
    private void drawFansInfo(Canvas canvas) {
        if (mSkin == null) {
            return;
        }
        //TODO 需要更新数据维护逻辑

        List<RobotSkinLabel> fansInfo = mSkin.getFansInfo();
        String fansText = "";

        if (fansInfo != null) {
            for (int i = 0; i < fansInfo.size(); i++) {
                if (i == 0) {
                    fansText = "圆梦是个PM";
                } else if (i == 1) {
                    fansText = "1423.1w";
                } else if (i == 2) {
                    fansText = "粉丝";
                }
                drawLabel(canvas, fansInfo.get(i), fansText);
            }

        }

    }

    public int getCtrlId(int x, int y) {

        if (mSkin.getBluetooth() != null && mSkin.getBluetooth().getDispTouchRect() != null) {
            if (mSkin.getBluetooth().getDispTouchRect().contains(x, y)) {
                return RobotClockSkin.CTRL_BLUETOOTH_ID;
            }
        }

        if (mSkin.getVolume() != null && mSkin.getVolume().getDispTouchRect() != null) {
            if (mSkin.getVolume().getDispTouchRect().contains(x, y)) {
                return RobotClockSkin.CTRL_MEDIA_VOLUME_ID;
            }
        }

        if (mSkin.getWifi() != null && mSkin.getWifi().getDispTouchRect() != null) {
            if (mSkin.getWifi().getDispTouchRect().contains(x, y)) {
                return RobotClockSkin.CTRL_WIFI_ID;
            }
        }

        if (mSkin.getStep() != null && mSkin.getStep().getDispTouchRect() != null) {
            if (mSkin.getStep().getDispTouchRect().contains(x, y)) {
                return RobotClockSkin.CTRL_STEP_ID;
            }
        }

        if (mSkin.getAirTemp() != null && mSkin.getAirTemp().getDispTouchRect() != null) {
            if (mSkin.getAirTemp().getDispTouchRect().contains(x, y)) {
                return RobotClockSkin.CTRL_WEATHER_ID;
            }
        }

        if (mSkin.getWeather() != null && mSkin.getWeather().getDispTouchRect() != null) {
            if (mSkin.getWeather().getDispTouchRect().contains(x, y)) {
                return RobotClockSkin.CTRL_WEATHER_ID;
            }
        }

        if (mSkin.getAqiNumber() != null && mSkin.getAqiNumber().getDispTouchRect() != null) {
            if (mSkin.getAqiNumber().getDispTouchRect().contains(x, y)) {
                return RobotClockSkin.CTRL_WEATHER_ID;
            }
        }

        if (mSkin.getAqiText() != null && mSkin.getAqiText().getDispTouchRect() != null) {
            if (mSkin.getAqiText().getDispTouchRect().contains(x, y)) {
                return RobotClockSkin.CTRL_WEATHER_ID;
            }
        }

        return RobotClockSkin.CTRL_NONE_ID;
    }

    @Override
    public void updateBatteryLevel(int batteryLevel) {
        if (mPlatformState.batteryLevel != batteryLevel) {
            mPlatformState.batteryLevel = batteryLevel;
            invalidate();
        }
    }

    @Override
    public void updateWifiEnabled(boolean wifiEnabled) {
        if (mPlatformState.wifiEnabled != wifiEnabled) {
            mPlatformState.wifiEnabled = wifiEnabled;
            invalidate();
        }
    }

    @Override
    public void updateStepNumber(int stepNumber) {
        if (mPlatformState.stepNumber != stepNumber) {
            mPlatformState.stepNumber = stepNumber;
            invalidate();
        }
    }

    @Override
    public void updateMediaVolume(int mediaVolume) {
        if (mPlatformState.mediaVolume != mediaVolume) {
            mPlatformState.mediaVolume = mediaVolume;
            invalidate();
        }
    }

    @Override
    public void updateBluetoothEnabled(boolean bluetoothEnabled) {
        if (mPlatformState.bluetoothEnabled != bluetoothEnabled) {
            mPlatformState.bluetoothEnabled = bluetoothEnabled;
            invalidate();
        }
    }

    @Override
    public void updateBatteryCharging(boolean batteryCharging) {
        if (mPlatformState.batteryCharging != batteryCharging) {
            mPlatformState.batteryCharging = batteryCharging;
            invalidate();
        }
    }

    @Override
    public void updateWeather(int weatherState, int currentTemp, int airQuality) {
        if (mPlatformState.weatherState != weatherState ||
                mPlatformState.currentTemp != currentTemp ||
                mPlatformState.airQuality != airQuality) {

            mPlatformState.weatherState = weatherState;
            mPlatformState.currentTemp = currentTemp;
            mPlatformState.airQuality = airQuality;

            invalidate();
        }
    }

    @Override
    public void updateAll(RobotPlatformState state) {
        mPlatformState = state;
        invalidate();
    }

    public boolean isRefreshPerSecond() {
        if (mSkin == null) {
            return false;
        }
        return (isRefreshNow && mSkin.isRefreshPerSecond());
    }

    public void setRefreshNow(boolean refreshNow) {
        isRefreshNow = refreshNow;
        if (isRefreshNow) {
            startTimerTask();
        } else {
            stopTimerTask();
        }
    }

    private void initTimerTask() {
        mTimer = null;
        mTimerTask = null;
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
//                    LogUtils.logi("test", "refrash~~~~~");
                    postInvalidate();
                }
            };
        }
    }

    private void startTimerTask() {
        initTimerTask();
        mTimer.schedule(mTimerTask, 0, 50);

    }
//    private void startTimerTask() {
//        initTimerTask();
//        if (isRefreshPerSecond()) {
//            mTimer.schedule(mTimerTask, 0, 1000);
//        } else {
//            Calendar calendar = Calendar.getInstance();
//            int second = calendar.get(Calendar.SECOND);
//            mTimer.schedule(mTimerTask, (60 - second) * 1000, 1000 * 60);
//        }
//    }

    private void stopTimerTask() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;
        }
    }

}

