package com.letianpai.robot.mcuservice.manager;

/**
 *
 */
public class McuCommandControlManager {
//    private Gson mGson;


//    private static McuCommandControlManager instance;
//    private Context mContext;
//
//
//    private McuCommandControlManager(Context context){
//        this.mContext = context;
//        init(context);
//    }
//
//    public static McuCommandControlManager getInstance(Context context){
//        synchronized (McuCommandControlManager.class){
//            if (instance == null){
//                instance = new McuCommandControlManager(context.getApplicationContext());
//            }
//            return instance;
//        }
//
//    };
//
//    private void init(Context context) {
//        mGson = new Gson();
//    }

//    public void commandDistribute(String command, String data) {
//        LogUtils.logi("letianpai","DispatchService_commandFrom:  ======= 8" );
//        LogUtils.logi("letianpai_Distribute","data222: "+ data);
//        if(command.equals(MCUCommandConsts.COMMAND_TYPE_MOTION)){
//            LogUtils.logi("letianpai_MOTION","data: "+ data);
//            LogUtils.logi("letianpai","DispatchService_commandFrom:  ======= 9" );
//            responseMotion(data);
//
//        }else if(command.equals(MCUCommandConsts.COMMAND_TYPE_FACE)){
//            responseFace(data);
//
//        }else if(command.equals(MCUCommandConsts.COMMAND_TYPE_ANTENNA_LIGHT)){
//            //responseAntennaLight(data);
//
//        }else if(command.equals(MCUCommandConsts.COMMAND_TYPE_SOUND)){
//            responseSound(data);
//
//        }else if(command.equals(MCUCommandConsts.COMMAND_TYPE_ANTENNA_MOTION)){
//            responseAntennaMotion(data);
//
//        }else if(command.equals(MCUCommandConsts.COMMAND_TYPE_TRTC)){
//            //responseTRTC(data);
//        }
//
//    }

//    private void responseAntennaMotion(String data) {
//
//
//    }
//    private void responseTRTC(String data) {
//        LogUtils.logi("letianpai_Distribute","COMMAND_TYPE_TRTC ==== 2: ");
//        TRTC trtc = mGson.fromJson(data, TRTC.class);
//        if (trtc != null){
//            LogUtils.logi("letianpai_Distribute","trtc is not null: "+ trtc.toString());
//            openVideos(trtc.getRoom_id(),trtc.getUser_id(),trtc.getUser_sig());
//        }else{
//            LogUtils.logi("letianpai_Distribute","trtc is null: ");
//        }
//
//
//    }
//    private void openVideos(int room_id, String user_id, String user_sig) {
//        LogUtils.logi("letianpai_Distribute","room_id: "+room_id );
//        LogUtils.logi("letianpai_Distribute","user_id: "+user_id );
//        LogUtils.logi("letianpai_Distribute","user_sig: "+user_sig );
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName( "com.rhj.aduioandvideo","com.tencent.trtc.videocall.VideoCallingActivity"));
//        intent.putExtra("roomId", room_id);
//        intent.putExtra("userId", user_id);
//        intent.putExtra("userSig", user_sig);
//        intent.addFlags(FLAG_ACTIVITY_NEW_TASK );
//        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP );
//        mContext.startActivity(intent);
//    }


//    private void responseSound(String data) {
//
//    }
//
//    private void responseAntennaLight(String data) {
//    }
//
//    private void responseFace(String data) {
//    }


//    private void responseMotion(String data) {
//        LogUtils.logi("letianpai","DispatchService_commandFrom:  ======= 10" );
//        LogUtils.logi("commandDistribute","responseMotion_data111: "+ data);
//        Motion motion = mGson.fromJson(data,Motion.class);
//        String motionType;
//        int number = 1;
//        if (motion != null){
//            motionType = motion.getMotion();
//            number = motion.getNumber();
//            if(TextUtils.isEmpty(motionType)){
//                return;
//            }
//            if(motionType.equals(MCUCommandConsts.COMMAND_VALUE_MOTION_FORWARD)){
//                LogUtils.logi("letianpai","DispatchService_commandFrom:  ======= 11" );
//                LogUtils.logi("letianpai_MOTION","COMMAND_VALUE_MOTION_FORWARD: ");
//                walkForward(number);
//
//            }else if (motionType.equals(MCUCommandConsts.COMMAND_VALUE_MOTION_BACKEND)){
//                LogUtils.logi("letianpai","DispatchService_commandFrom:  ======= 12" );
//                LogUtils.logi("letianpai_MOTION","COMMAND_VALUE_MOTION_BACKEND: ");
//                walkBackend(number);
//
//            }else if (motionType.equals(MCUCommandConsts.COMMAND_VALUE_MOTION_LEFT)){
//                LogUtils.logi("letianpai_MOTION","COMMAND_VALUE_MOTION_LEFT: ");
//                walkLeft(number);
//
//            }else if (motionType.equals(MCUCommandConsts.COMMAND_VALUE_MOTION_RIGHT)){
//                LogUtils.logi("letianpai_MOTION","COMMAND_VALUE_MOTION_RIGHT: ");
//                walkRight(number);
//
//            }else if (motionType.equals(MCUCommandConsts.COMMAND_VALUE_MOTION_LEFT_ROUND)){
//
//
//            }else if (motionType.equals(MCUCommandConsts.COMMAND_VALUE_MOTION_RIGHT_ROUND)){
//
//
//            }else if (motionType.equals(MCUCommandConsts.COMMAND_VALUE_MOTION_SET_STRAIGHT)){
//                //TODO 增加回正逻辑
//            }
//
//        }
//        //walkBackend(2);
//
//    }


//    public void consumeATCommand(String command) {
//        if (!TextUtils.isEmpty(command)) {
//            SerialPortJNI.writePort(command.getBytes());
//        }
//
//    }

//    private void walkForward(int stepNum) {
//        walks(1,stepNum);
//    }
//
//    private void walkBackend(int stepNum) {
//        walks(2,stepNum);
//    }
//
//    private void walkLeft(int stepNum) {
//        walks(3,stepNum);
//    }
//
//    private void walkRight(int stepNum) {
//        walks(4,stepNum);
//    }

//    private void walks(int moveCommand,int stepNum) {
//        String command = String.format("AT+MOVEW,%d,%d,2\\r\\n",moveCommand,stepNum);
//        LogUtils.logi("letianpai_walks","command: "+ command);
//        consumeATCommand(command);
//    }
//
//    private void turnEarLeftRound() {
//        consumeATCommand("AT+EARW,1,2,2\\r\\n");
//    }
//    private void turnEarRightRound() {
//        consumeATCommand("AT+EARW,2,2,2\\r\\n");
//    }
//
//    public void light(int lightColor) {
//
//        String command = String.format("AT+LEDon,%d\\r\\n",lightColor);
//        LogUtils.logi("letianpai_walks","command: "+ command);
//        consumeATCommand(command);
//    }
//    public void lightOff() {
//
//        String command = String.format("AT+LEDOff\\r\\n");
//        LogUtils.logi("letianpai_walks","command: "+ command);
//        consumeATCommand(command);
//    }
//
//    private void move(int moveCommand,int stepNum,int speed) {
//        String command = String.format("AT+MOVEW,%d,%d,%d\\r\\n",moveCommand,stepNum,speed);
//        consumeATCommand(command);
//    }
//    private void turnLeft(int moveCommand,int stepNum,int speed) {
//    }


}
