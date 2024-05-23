package com.renhejia.robot.letianpaiservice;

import android.os.Parcel;
import android.os.Parcelable;

public class LtpCommand implements Parcelable {
    private String command;
    private String data;

    public LtpCommand() {

    }

    public LtpCommand(String cmd, String data) {
        this.command = cmd;
        this.data = data;
    }
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    protected LtpCommand(Parcel in) {
        this.command = in.readString();
        this.data = in.readString();
    }

    public static final Creator<LtpCommand> CREATOR = new Creator<LtpCommand>() {
        @Override
        public LtpCommand createFromParcel(Parcel in) {
            return new LtpCommand(in);
        }

        @Override
        public LtpCommand[] newArray(int size) {
            return new LtpCommand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(command);
        dest.writeString(data);
    }

    public void readFromParcel(Parcel dest) {
        command = dest.readString();
        data = dest.readString();
    }

}
