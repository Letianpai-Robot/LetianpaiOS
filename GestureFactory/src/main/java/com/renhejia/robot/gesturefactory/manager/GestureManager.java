package com.renhejia.robot.gesturefactory.manager;

import static com.renhejia.robot.gesturefactory.manager.GestureCenter.resetStandGestureData;

import android.content.Context;
import android.text.TextUtils;

import com.renhejia.robot.commandlib.log.LogUtils;

import com.renhejia.robot.gesturefactory.parser.GestureData;
import com.renhejia.robot.gesturefactory.util.GestureConsts;

import java.util.ArrayList;
import java.util.Random;

/**
 * 姿态管理
 *
 * @author liujunbin
 */
public class GestureManager {

    private static GestureManager gestureManager;
    private Context mContext;
    private ArrayList<ArrayList<GestureData>> gestureList;
    private ArrayList<ArrayList<GestureData>> blockList;
    private ArrayList<ArrayList<GestureData>> mixList;
    private int index = 0;

    public GestureManager(Context context) {
        this.mContext = context;
        initGestureList();
        initBlockList();
        fillMixList();
    }

    private void fillMixList() {
        mixList = new ArrayList<>();
        for (int i = 0; i < gestureList.size(); i++) {
            mixList.add(gestureList.get(i));
            mixList.add(blockList.get(0));
        }
    }

    private void initBlockList() {
        blockList = new ArrayList<>();
        blockList.add(GestureCenter.test_GestureData0());
    }

    private void initGestureList() {
        gestureList = new ArrayList<>();
        gestureList.add(GestureCenter.test_GestureData1());
        gestureList.add(GestureCenter.test_GestureData2());
        gestureList.add(GestureCenter.test_GestureData3());
        gestureList.add(GestureCenter.test_GestureData4());
        gestureList.add(GestureCenter.test_GestureData5());
        gestureList.add(GestureCenter.test_GestureData6());
        gestureList.add(GestureCenter.test_GestureData7());
        gestureList.add(GestureCenter.test_GestureData8());
    }


    public static GestureManager getInstance(Context context) {
        if (gestureManager == null) {
            synchronized (ParserManager.class) {
                if (gestureManager == null) {
                    gestureManager = new GestureManager(context);
                }
            }
        }
        return gestureManager;
    }

    /**
     * 获取姿态
     *
     * @param gesture
     * @return
     */
    public ArrayList<GestureData> getRobotGesture(String gesture) {

        if (TextUtils.isEmpty(gesture)) {
            return null;

        } else if (gesture.equals(GestureConsts.GESTURE_STAND_RESET)) {
            return resetStandGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_RANDOM)) {
            return getRandomGesture();
//
        } else if (gesture.equals(GestureConsts.GESTURE_DEFAULT)) {
            return GestureCenter.commonStandGestureData();
//
        } else if (gesture.equals(GestureConsts.GESTURE_ORDER)) {
//            if (index >= 0 && index < gestureList.size() - 1) {
            if (index >= 0 && index < mixList.size() - 1) {
                index += 1;
            } else {
                index = 1;
            }
            if (index >= mixList.size()) {
                index = 0;
            }
            return mixList.get(index);

        } else if (gesture.equals(GestureConsts.GESTURE_COMMON_00)) {
            return GestureCenter.commonGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_ROBOT_RANDOM)) {
            return GestureCenter.getRandomGesture();

        } else if (gesture.equals(GestureConsts.GESTURE_ROBOT_FOUND_PEOPLE)) {
            return GestureCenter.robotFoundPeoGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_ROBOT_STAND)) {
            return GestureCenter.robotStandGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_COMMON_01)) {
            return GestureCenter.common01GestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_PAIR)) {
            return GestureCenter.pairGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_STARTUP)) {
            return GestureCenter.startupGestureData2();

        } else if (gesture.equals(GestureConsts.GESTURE_SHUTDOWN)) {
            return GestureCenter.shutdownGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_BATLOWER)) {
            return GestureCenter.batteryLowerGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_START_CHARGE)) {
            return GestureCenter.startChargingGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_SCAN)) {
            return GestureCenter.scanGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_FOUND_OWNER)) {
            return GestureCenter.foundOwnerGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_FOUND_PEO)) {
            return GestureCenter.foundPeoGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_FOUND_NO)) {
            return GestureCenter.foundNoPeoGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_STANDBY)) {
            return GestureCenter.standByGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_ASSISTANT)) {
            return GestureCenter.assistantGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_DANGLING)) {
            return GestureCenter.danglingGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_FALL_PREVENTION)) {
            return GestureCenter.fallPreventionGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_WHERE_ABOUT)) {
            return GestureCenter.whereaboutsGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_BIRTHDAY)) {
            return GestureCenter.birthdayGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_RANDOM)) {
            return GestureCenter.birthdayGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_CLOCK)) {
            return GestureCenter.clockGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_COUNT_DOWN)) {
            return GestureCenter.countdownGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_DEMO)) {
            return GestureCenter.autoDisplayGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST1)) {
            return GestureCenter.test_GestureData1();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST2)) {
            return GestureCenter.test_GestureData2();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST3)) {
            return GestureCenter.test_GestureData3();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST4)) {
            return GestureCenter.test_GestureData4();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST5)) {
            return GestureCenter.test_GestureData5();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST6)) {
            return GestureCenter.test_GestureData6();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST7)) {
            return GestureCenter.test_GestureData7();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST8)) {
            return GestureCenter.test_GestureData8();

        } else if (gesture.equals(GestureConsts.GESTURE_TEST0)) {
            return GestureCenter.test_GestureData0();

        } else if (gesture.equals(GestureConsts.GESTURE_PRECIPICE_START)) {
            return GestureCenter.danglingGestureData();

        } else if (gesture.equals(GestureConsts.GESTURE_PRECIPICE_STOP)) {
            return GestureCenter.danglingStopGestureData();

        } else {
            return null;
        }
    }

    private ArrayList<GestureData> getRandomGesture() {
        ArrayList<GestureData> gestureData;
        Random rand = new Random();
        int randNum = rand.nextInt(gestureList.size());
        gestureData = gestureList.get(randNum);
        if (gestureData == null) {
            return GestureCenter.foundOwnerGestureData();
        } else {
            return gestureData;
        }
    }


}
