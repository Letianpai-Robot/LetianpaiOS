package com.renhejia.robot.commandlib.parser.trtc;

public class TRTC {

    private int room_id;
    private String user_id;
    private String user_sig;
    private long expire_ts;

    public int getRoom_id() {
        return room_id;
    }

    public long getExpire_ts() {
        return expire_ts;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_sig() {
        return user_sig;
    }

    public void setExpire_ts(long expire_ts) {
        this.expire_ts = expire_ts;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_sig(String user_sig) {
        this.user_sig = user_sig;
    }

    @Override
    public String toString() {
        return "TRTC{" +
                "room_id=" + room_id +
                ", user_id='" + user_id + '\'' +
                ", user_sig='" + user_sig + '\'' +
                ", expire_ts=" + expire_ts +
                '}';
    }
}
