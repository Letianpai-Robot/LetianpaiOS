package com.rhj.message;

import java.util.ArrayList;
import java.util.List;

public class MessageMediaListBean extends MessageBean{
    private int  count;
    private String widgetName;

    private List<MessageMusicBean> list=new ArrayList<>();

    public void setCount(int count) {
        this.count = count;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public void setList(List<MessageMusicBean> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public List<MessageMusicBean> getList() {
        return list;
    }

    public String getWidgetName() {
        return widgetName;
    }

    @Override
    public String toString() {
        return "MessageMediaListBean{" +
                "count=" + count +
                ", widgetName='" + widgetName + '\'' +
                ", list=" + list +
                '}';
    }
}
