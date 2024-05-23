package com.renhejia.robot.launcher.audioservice.parser;

import java.util.List;

public class RemindBean {

    private List<Content> content;

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    /**
     * {
     * "operation":"创建",
     * "period":"下午",
     * "time":"17:00:00",
     * "object":"闹钟",
     * "recent_tsp":1675414800,
     * "timestamp":1675414800,
     * "vid":"15149624026738428637",
     * "absuolutely":true,
     * "date":"20230203"
     * }
     */
    public static class Content {
        private String operation;
        private String period;
        private String time;
        private String object;
        private long recent_tsp;
        private long timestamp;
        private String vid;
        private Boolean absuolutely;
        private String date;
        private String repeat;
        private String event;
        private String time_interval;
        private int time_left;

        @Override
        public String toString() {
            return "Content{" +
                    "operation='" + operation + '\'' +
                    ", period='" + period + '\'' +
                    ", time='" + time + '\'' +
                    ", object='" + object + '\'' +
                    ", recent_tsp=" + recent_tsp +
                    ", timestamp=" + timestamp +
                    ", vid='" + vid + '\'' +
                    ", absuolutely=" + absuolutely +
                    ", date='" + date + '\'' +
                    ", repeat='" + repeat + '\'' +
                    ", event='" + event + '\'' +
                    ", time_interval='" + time_interval + '\'' +
                    ", time_left=" + time_left +
                    '}';
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public long getRecent_tsp() {
            return recent_tsp;
        }

        public void setRecent_tsp(long recent_tsp) {
            this.recent_tsp = recent_tsp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public Boolean getAbsuolutely() {
            return absuolutely;
        }

        public void setAbsuolutely(Boolean absuolutely) {
            this.absuolutely = absuolutely;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRepeat() {
            return repeat;
        }

        public void setRepeat(String repeat) {
            this.repeat = repeat;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getTime_interval() {
            return time_interval;
        }

        public void setTime_interval(String time_interval) {
            this.time_interval = time_interval;
        }

        public int getTime_left() {
            return time_left;
        }

        public void setTime_left(int time_left) {
            this.time_left = time_left;
        }
    }
}
