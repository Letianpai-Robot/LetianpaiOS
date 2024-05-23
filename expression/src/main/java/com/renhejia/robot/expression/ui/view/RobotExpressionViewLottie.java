package com.renhejia.robot.expression.ui.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.renhejia.robot.commandlib.log.LogUtils;
import android.view.View;
import android.widget.RelativeLayout;

//import com.airbnb.lottie.LottieAnimationView;
import com.renhejia.robot.commandlib.consts.MCUCommandConsts;
import com.renhejia.robot.commandlib.consts.RobotExpressionConsts;
import com.renhejia.robot.expression.R;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 机器人表情
 * @author liujunbin
 */
public class RobotExpressionViewLottie  {
//public class RobotExpressionViewLottie extends RelativeLayout implements RobotExpressionConsts {
//    private Context mContext;
//    private LottieAnimationView mLottieAnimationView;
//    ArrayList<String> list = new ArrayList<>();
//    private int index =0;
//    String animation = RobotExpressionConsts.L_FACE_CHANGTAI_QUICK;
//    private ExpressionHandler mHandler;
//    private static final int CHANG_EXPRESSION = 1;
//
//    public RobotExpressionViewLottie(Context context) {
//        super(context);
//        init(context);
//    }
//
//    public RobotExpressionViewLottie(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    public RobotExpressionViewLottie(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//    public RobotExpressionViewLottie(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context);
//    }
//
//    private void init(Context context) {
//        this.mContext = context;
//        inflate(mContext, R.layout.robot_expression_view_lottie,this);
//        //initWifi();
//        initDatas();
//        fillData();
//        initView();
//
//    }
//
//    private void initDatas() {
//        mHandler = new ExpressionHandler(mContext);
//    }
//
//    private void fillData1() {
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_SUNNY);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_CLOUD);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_CLOUDY);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_RAIN);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_HAIL);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_SNOW);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_SAND_DUST);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_HAZE);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_THUNDER);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_WIND);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_FOG);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_RAIN_HAIL);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_RAIN_SNOW);
//        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_RAIN_THUNDER);
//
//        list.add(RobotExpressionConsts.LOTTIE_LOGIN_CONNECT);
//        list.add(RobotExpressionConsts.LOTTIE_LOGIN_FAIlED);
//        list.add(RobotExpressionConsts.LOTTIE_LOGIN_SUCCESS);
//        list.add(RobotExpressionConsts.LOTTIE_LOGINING);
//        list.add(RobotExpressionConsts.LOTTIE_WIFI_CONNECT_FAILED);
//        list.add(RobotExpressionConsts.LOTTIE_WIFI_CONNECT_SUCCESS);
//    }
//
//
//    private void fillData() {
////        list.add(RobotExpressionConsts.L_FACE_AIXIN); //需要图片
////        list.add(RobotExpressionConsts.L_FACE_AOMAN);
//////        list.add(RobotExpressionConsts.L_FACE_BABIQ);// 有问题
////        list.add(RobotExpressionConsts.L_FACE_BAOQIAN);
//////        list.add(RobotExpressionConsts.L_FACE_SHUTUP); // 动画不动 没有嘴
////        list.add(RobotExpressionConsts.L_FACE_CHANGTAI_QUICK);
////        list.add(RobotExpressionConsts.L_FACE_CHANGTAI_SLOW);
////        list.add(RobotExpressionConsts.L_FACE_DAHAQIAN);
////        list.add(RobotExpressionConsts.L_FACE_DAXIAOYAN);
////        list.add(RobotExpressionConsts.L_FACE_DAXIAO);
////        list.add(RobotExpressionConsts.L_FACE_DANZHAYAN_1);
////        list.add(RobotExpressionConsts.L_FACE_DANZHAYAN_2);
////        list.add(RobotExpressionConsts.L_FACE_DANZHAYAN_3);
////        list.add(RobotExpressionConsts.L_FACE_WORRY);
////        list.add(RobotExpressionConsts.L_FACE_EMO);
////        list.add(RobotExpressionConsts.L_FACE_HAIPA);
////        list.add(RobotExpressionConsts.L_FACE_HANYAN);
////        list.add(RobotExpressionConsts.L_FACE_HUAIYI1);
////        list.add(RobotExpressionConsts.L_FACE_HUAIYI2);
////        list.add(RobotExpressionConsts.L_FACE_JIDU);
////        list.add(RobotExpressionConsts.L_FACE_JINGYA1);
////        list.add(RobotExpressionConsts.L_FACE_JINGYA2);
//////        list.add(RobotExpressionConsts.L_FACE_KANGJU);//没有眼睛
////        list.add(RobotExpressionConsts.L_FACE_KU1);
////        list.add(RobotExpressionConsts.L_FACE_KU2);
////        list.add(RobotExpressionConsts.L_FACE_LIUHAN);
////        list.add(RobotExpressionConsts.L_FACE_MIYAN);
//////        list.add(RobotExpressionConsts.L_FACE_NANSHOU);// 没有眼睛
////        list.add(RobotExpressionConsts.L_FACE_NAOZHONG);
////        list.add(RobotExpressionConsts.L_FACE_PAIZHAO);
////        list.add(RobotExpressionConsts.L_FACE_QICHUANGQI);
////        list.add(RobotExpressionConsts.L_FACE_QICHUANXUXU);
////        list.add(RobotExpressionConsts.L_FACE_SHANGXIN1);
////        list.add(RobotExpressionConsts.L_FACE_SHANGXIN2);
////        list.add(RobotExpressionConsts.L_FACE_SHENGQI);
////        list.add(RobotExpressionConsts.L_FACE_SHUANYANKAIFA);
////        list.add(RobotExpressionConsts.L_FACE_SLEEP);
////        list.add(RobotExpressionConsts.L_FACE_SHUIJIAOTAIYANPI);
////        list.add(RobotExpressionConsts.L_FACE_SIKAO);
////        list.add(RobotExpressionConsts.L_FACE_TENG);
////        list.add(RobotExpressionConsts.L_FACE_TOUKAN1);
////        list.add(RobotExpressionConsts.L_FACE_TOUKAN2);
////        list.add(RobotExpressionConsts.L_FACE_WUNAI1);
//        list.add(RobotExpressionConsts.L_FACE_WUNAI2);
////        list.add(RobotExpressionConsts.L_FACE_WUNAI3);
////        list.add(RobotExpressionConsts.L_FACE_XIANGSHANGYOUXIEYANKAN);
////        list.add(RobotExpressionConsts.L_FACE_XIANGSHANGZUOXIEYANKAN);
////        list.add(RobotExpressionConsts.L_FACE_XIANGXIAKAN);
////        list.add(RobotExpressionConsts.L_FACE_XIANGXIAYOUXIEYANKAN);
////        list.add(RobotExpressionConsts.L_FACE_XIANGXIAZUOXIEYANKAN);
////        list.add(RobotExpressionConsts.L_FACE_XIANGYOUKANQINGTING);
////        list.add(RobotExpressionConsts.L_FACE_XIANGZUOKANQINGTING);
////        list.add(RobotExpressionConsts.L_FACE_XUANYUN);
////        list.add(RobotExpressionConsts.L_FACE_YAOTOU);
////        list.add(RobotExpressionConsts.L_FACE_YIHUO);
////        list.add(RobotExpressionConsts.L_FACE_ZHOUMEI);
////        list.add(RobotExpressionConsts.LOTTIE_WEATHER_TYPE_SUNNY);
//    }
//
//    public void changeExpression(String expression) {
//        Message message = new Message();
//        message.obj = expression;
//        message.what = CHANG_EXPRESSION;
//        mHandler.sendMessage(message);
//    }
//
//    private void initView() {
//
//        mLottieAnimationView = findViewById(R.id.lav_view);
//        mLottieAnimationView.setImageAssetsFolder("face/");
//        mLottieAnimationView.setAnimation(list.get(index));
//        mLottieAnimationView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                index = index +1;
//                if (index >= list.size()) {
//                    index = 0;
//                }
//                setAnimation(list.get(index));
//            }
//        });
//        mLottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//                LogUtils.logi("letianpai","onAnimationStart ======= 0");
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                LogUtils.logi("letianpai","onAnimationEnd ======= 0");
//                //TODO 判断
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//    }
//
//    private void setAnimation(String expression){
//        animation = "";
//        LogUtils.logi("letianpai","face ===== 9: ");
//        if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_ANGRY)) {
//            animation = RobotExpressionConsts.L_FACE_SHENGQI;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_SAD)) {
//            animation = RobotExpressionConsts.L_FACE_SHANGXIN1;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_ANGER)) {
//            animation = RobotExpressionConsts.L_FACE_SHANGXIN2;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_BORED)) {
//            // TODO 暂无此表情
//            animation = RobotExpressionConsts.L_FACE_HUAIYI1;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_EXCITING)) {
//            animation = RobotExpressionConsts.L_FACE_XIANGXIAKAN;
//            //TODO 这个表情也没有
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_CRY)) {
//            animation = RobotExpressionConsts.L_FACE_KU2;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_LOSE)) {
//            animation = RobotExpressionConsts.L_FACE_WUNAI2;
//            //TODO 失落
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_STAND)) {
//            animation = RobotExpressionConsts.L_FACE_STAND;
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FACE_HAPPY)) {
//            animation = RobotExpressionConsts.L_FACE_DAXIAO;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_BIRTHDAY)) {
//            animation = RobotExpressionConsts.L_FACE_BIRTHDAY;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_BIRTHDAY2)) {
//            animation = RobotExpressionConsts.L_FACE_BIRTHDAY_2;
//        ////////////////////////////
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_COMMONWINK)) {
//            animation = RobotExpressionConsts.L_FACE_CHANGTAI_SLOW;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_BIG_SMALL_EYE)) {
//            animation = RobotExpressionConsts.L_FACE_DAXIAOYAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SINGLE_BLINK)) {
//            animation = RobotExpressionConsts.L_FACE_DANZHAYAN_1;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_PEEP)) {
//            animation = RobotExpressionConsts.L_FACE_TOUKAN1;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SQUINT)) {
//            animation = RobotExpressionConsts.L_FACE_MIYAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SINGLE_EXPECT)) {
//            animation = RobotExpressionConsts.L_FACE_SHUANYANTIAODONG;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_GLANCE_LEFT_RIGHT)) {
//            animation = RobotExpressionConsts.L_FACE_CHANGTAI_SLOW;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SQUINT_RIGHT_UP)) {
//            animation = RobotExpressionConsts.L_FACE_XIANGSHANGYOUXIEYANKAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SQUINT_LEFT_UP)) {
//            animation = RobotExpressionConsts.L_FACE_XIANGSHANGZUOXIEYANKAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SQUINT_RIGHT_DOWN)) {
//            animation = RobotExpressionConsts.L_FACE_XIANGSHANGYOUXIEYANKAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SQUINT_LEFT_DOWN)) {
//            animation = RobotExpressionConsts.L_FACE_XIANGXIAZUOXIEYANKAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SQUINT_LOOK_DOWN)) {
//            animation = RobotExpressionConsts.L_FACE_XIANGXIAKAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_CONFUSE)) {
//            animation = RobotExpressionConsts.L_FACE_YIHUO;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SHAKE_HEAD)) {
//            animation = RobotExpressionConsts.L_FACE_YAOTOU;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_THINK)) {
//            animation = RobotExpressionConsts.L_FACE_SIKAO;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_LISTEN_LEFT)) {
//            animation = RobotExpressionConsts.L_FACE_XIANGZUOKANQINGTING;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_LISTEN_RIGHT)) {
//            animation = RobotExpressionConsts.L_FACE_XIANGYOUKANQINGTING;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_LISTEN_HAPPY)) {
//            animation = RobotExpressionConsts.L_FACE_DAXIAO;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_BIG_LAUGH)) {
//            animation = RobotExpressionConsts.L_FACE_KAIXIN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_EXCITING)) {
//            animation = RobotExpressionConsts.L_FACE_KAIXIN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_LOVE)) {
//            animation = RobotExpressionConsts.L_FACE_AIXIN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_WRONGED)) {
//            animation = RobotExpressionConsts.L_FACE_SHANGXIN1;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FROWN)) {
//            animation = RobotExpressionConsts.L_FACE_ZHOUMEI;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_CRY)) {
//            animation = RobotExpressionConsts.L_FACE_KU1;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_ASHAMED)) {
//            animation = RobotExpressionConsts.L_FACE_HANYAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_UNWEL)) {
//            animation = RobotExpressionConsts.L_FACE_NANSHOU;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_PAIN)) {
//            animation = RobotExpressionConsts.L_FACE_TENG;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SORRY)) {
//            animation = RobotExpressionConsts.L_FACE_BAOQIAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FURIOUS)) {
//            animation = RobotExpressionConsts.L_FACE_KUANGNU;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_RESIST)) {
//            animation = RobotExpressionConsts.L_FACE_KANGJU;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SHUT_UP)) {
//            animation = RobotExpressionConsts.L_FACE_SHUTUP;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_ENVY)) {
//            animation = RobotExpressionConsts.L_FACE_JIDU;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_ARROGNT)) {
//            animation = RobotExpressionConsts.L_FACE_AOMAN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_ANGER)) {
//            animation = RobotExpressionConsts.L_FACE_EMO;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SUPRISE)) {
//            animation = RobotExpressionConsts.L_FACE_JINGYA2;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_BARBIQ)) {
//            animation = RobotExpressionConsts.L_FACE_BABIQ;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SUSPECT)) {
//            animation = RobotExpressionConsts.L_FACE_HUAIYI2;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_FEAR)) {
//            animation = RobotExpressionConsts.L_FACE_HAIPA;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_PANTING)) {
//            animation = RobotExpressionConsts.L_FACE_QICHUANXUXU;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_YAWN)) {
//            animation = RobotExpressionConsts.L_FACE_DAHAQIAN;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_WEAK_UP)) {
//            animation = RobotExpressionConsts.L_FACE_QICHUANGQI;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SLEEP)) {
//            animation = RobotExpressionConsts.L_FACE_SLEEP;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_SLEEP_OPEN_EYE)) {
//            animation = RobotExpressionConsts.L_FACE_SHUIJIAOTAIYANPI;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_DIZZINESS)) {
//            animation = RobotExpressionConsts.L_FACE_XUANYUN;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_TREMBINE)) {
//            animation = RobotExpressionConsts.L_FACE_FADOU;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_CLOCK)) {
//            animation = RobotExpressionConsts.L_FACE_NAOZHONG;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_PHOTO)) {
//            animation = RobotExpressionConsts.L_FACE_PAIZHAO;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_WUNAI)) {
//            animation = RobotExpressionConsts.L_FACE_WUNAI2;
//
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_COMMONWINK)) {
//            animation = RobotExpressionConsts.L_FACE_DANZHAYAN_3;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_LOVE)) {
//            animation = RobotExpressionConsts.L_FACE_AIXIN;
//        } else if (expression.equals(MCUCommandConsts.COMMAND_VALUE_LOVE)) {
//            animation = RobotExpressionConsts.L_FACE_AIXIN;
//
//
//        ///////////////////////////////// 中间可删除  //////////////////////////////////
//
//
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_DANZHAYAN_1)){
//            animation = RobotExpressionConsts.L_FACE_DANZHAYAN_1;
//
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_DANZHAYAN_2)){
//            animation = RobotExpressionConsts.L_FACE_DANZHAYAN_2;
//
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_DANZHAYAN_3)){
//            animation = RobotExpressionConsts.L_FACE_DANZHAYAN_3;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_WORRY)){
//            animation = RobotExpressionConsts.L_FACE_WORRY;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_EMO)){
//            animation = RobotExpressionConsts.L_FACE_EMO;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_HAIPA)){
//            animation = RobotExpressionConsts.L_FACE_HAIPA;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_HANYAN)){
//            animation = RobotExpressionConsts.L_FACE_HANYAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_HUAIYI1)){
//            animation = RobotExpressionConsts.L_FACE_HUAIYI1;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_HUAIYI2)){
//            animation = RobotExpressionConsts.L_FACE_HUAIYI2;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_JIDU)){
//            animation = RobotExpressionConsts.L_FACE_JIDU;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_JINGYA1)){
//            animation = RobotExpressionConsts.L_FACE_JINGYA1;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_JINGYA2)){
//            animation = RobotExpressionConsts.L_FACE_JINGYA2;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_KANGJU)){
//            animation = RobotExpressionConsts.L_FACE_KANGJU;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_KU1)){
//            animation = RobotExpressionConsts.L_FACE_KU1;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_KU2)){
//            animation = RobotExpressionConsts.L_FACE_KU2;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_LIUHAN)){
//            animation = RobotExpressionConsts.L_FACE_LIUHAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_MIYAN)){
//            animation = RobotExpressionConsts.L_FACE_MIYAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_NANSHOU)){
//            animation = RobotExpressionConsts.L_FACE_NANSHOU;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_NAOZHONG)){
//            animation = RobotExpressionConsts.L_FACE_NAOZHONG;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_PAIZHAO)){
//            animation = RobotExpressionConsts.L_FACE_PAIZHAO;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_QICHUANGQI)){
//            animation = RobotExpressionConsts.L_FACE_QICHUANGQI;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_QICHUANXUXU)){
//            animation = RobotExpressionConsts.L_FACE_QICHUANXUXU;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_SHANGXIN1)){
//            animation = RobotExpressionConsts.L_FACE_SHANGXIN1;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_SHANGXIN2)){
//            animation = RobotExpressionConsts.L_FACE_SHANGXIN2;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_SHENGQI)){
//            animation = RobotExpressionConsts.L_FACE_SHENGQI;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_SHUANYANTIAODONG)){
//            animation = RobotExpressionConsts.L_FACE_SHUANYANTIAODONG;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_SLEEP)){
//            animation = RobotExpressionConsts.L_FACE_SLEEP;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_SHUIJIAOTAIYANPI)){
//            animation = RobotExpressionConsts.L_FACE_SHUIJIAOTAIYANPI;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_SIKAO)){
//            animation = RobotExpressionConsts.L_FACE_SIKAO;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_TENG)){
//            animation = RobotExpressionConsts.L_FACE_TENG;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_TOUKAN1)){
//            animation = RobotExpressionConsts.L_FACE_TOUKAN1;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_TOUKAN2)){
//            animation = RobotExpressionConsts.L_FACE_TOUKAN2;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_WUNAI1)){
//            animation = RobotExpressionConsts.L_FACE_WUNAI1;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_WUNAI2)){
//            animation = RobotExpressionConsts.L_FACE_WUNAI2;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_WUNAI3)){
//            animation = RobotExpressionConsts.L_FACE_WUNAI3;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_XIANGSHANGYOUXIEYANKAN)){
//            animation = RobotExpressionConsts.L_FACE_XIANGSHANGYOUXIEYANKAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_XIANGSHANGZUOXIEYANKAN)){
//            animation = RobotExpressionConsts.L_FACE_XIANGSHANGZUOXIEYANKAN;
//
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_XIANGXIAYOUXIEYANKAN)){
//            animation = RobotExpressionConsts.L_FACE_XIANGXIAYOUXIEYANKAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_XIANGXIAZUOXIEYANKAN)){
//            animation = RobotExpressionConsts.L_FACE_XIANGXIAZUOXIEYANKAN;
//
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_XIANGYOUKANQINGTING)){
//            animation = RobotExpressionConsts.L_FACE_XIANGYOUKANQINGTING;
//
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_XIANGZUOKANQINGTING)){
//            animation = RobotExpressionConsts.L_FACE_XIANGZUOKANQINGTING;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_XUANYUN)){
//            animation = RobotExpressionConsts.L_FACE_XUANYUN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_YAOTOU)){
//            animation = RobotExpressionConsts.L_FACE_YAOTOU;
//
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_YIHUO)){
//            animation = RobotExpressionConsts.L_FACE_YIHUO;
//
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_ZHOUMEI)){
//            animation = RobotExpressionConsts.L_FACE_ZHOUMEI;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_YIHUO)){
//            animation = RobotExpressionConsts.L_FACE_YIHUO;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_BIRTHDAY)){
//            animation = RobotExpressionConsts.L_FACE_BIRTHDAY;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_BIRTHDAY_2)){
//            animation = RobotExpressionConsts.L_FACE_BIRTHDAY_2;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_AIXIN)){
//            animation = RobotExpressionConsts.L_FACE_AIXIN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_AOMAN)){
//            animation = RobotExpressionConsts.L_FACE_AOMAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_BABIQ)){
//            animation = RobotExpressionConsts.L_FACE_BABIQ;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_BAOQIAN)){
//            animation = RobotExpressionConsts.L_FACE_BAOQIAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_SHUTUP)){
//            animation = RobotExpressionConsts.L_FACE_SHUTUP;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_CHANGTAI_QUICK)){
//            animation = RobotExpressionConsts.L_FACE_CHANGTAI_QUICK;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_CHANGTAI_SLOW)){
//            animation = RobotExpressionConsts.L_FACE_CHANGTAI_SLOW;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_DAHAQIAN)){
//            animation = RobotExpressionConsts.L_FACE_DAHAQIAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_DAXIAOYAN)){
//            animation = RobotExpressionConsts.L_FACE_DAXIAOYAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_DAXIAO)){
//            animation = RobotExpressionConsts.L_FACE_DAXIAO;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_DAHAQIAN)){
//            animation = RobotExpressionConsts.L_FACE_DAHAQIAN;
//        } else if (expression.equals(RobotExpressionConsts.L_FACE_DAHAQIAN)){
//            animation = RobotExpressionConsts.L_FACE_DAHAQIAN;
//        }
//    /////////////////////////////////  中间可删除  ////////////////////////////////////
//
//        LogUtils.logi("letianpai","face ===== 10: ");
//        if (!TextUtils.isEmpty(animation)){
//            mLottieAnimationView.setAnimation(animation);
//            mLottieAnimationView.playAnimation();
//        }
//
//
//    }
//
//
//    private class ExpressionHandler extends android.os.Handler {
//        private final WeakReference<Context> context;
//
//        public ExpressionHandler(Context context) {
//            this.context = new WeakReference<>(context);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what == CHANG_EXPRESSION){
//                LogUtils.logi("letianpai","face ===== 12: ");
//                if(context != null && context.get()!=null){
//                    LogUtils.logi("letianpai","face ===== 13: ");
//                    setAnimation((String)msg.obj);
//                }
//            }
//        }
//    }
//
//
//    private void initWifi() {
//        mLottieAnimationView = findViewById(R.id.lav_view);
//        mLottieAnimationView.setImageAssetsFolder("face/");
////        mLottieAnimationView.setAnimation("wifiloop.json");
//        mLottieAnimationView.setAnimation("weather/cloudy.json");
//        mLottieAnimationView.loop(true);
//        mLottieAnimationView.playAnimation();
////        mLottieAnimationView.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                index = index +1;
////                if (index >= list.size()) {
////                    index = 0;
////                }
////                mLottieAnimationView.setAnimation(list.get(index));
////                mLottieAnimationView.playAnimation();
////
////            }
////        });
//
//    }
//
//


}
