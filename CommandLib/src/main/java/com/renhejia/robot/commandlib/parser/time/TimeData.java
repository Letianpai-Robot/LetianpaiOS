package com.renhejia.robot.commandlib.parser.time;

public class TimeData {
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TimeData{" +
                "timestamp=" + timestamp +
                '}';
    }
}
