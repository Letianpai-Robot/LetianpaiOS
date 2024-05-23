package com.rhj.message;

import java.util.ArrayList;
import java.util.List;

public class MessageWidgetListBean extends MessageBean {
    private int currentPage;
    private int itemsPerPage;
    private List<MessageWidgetBean> messageWidgetBeanList=new ArrayList<>();

    public List<MessageWidgetBean> getMessageWidgetBean() {
        return messageWidgetBeanList;
    }

    public void setMessageWidgetBean(List<MessageWidgetBean> list) {
        messageWidgetBeanList=list;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public String toString() {
        return "MessageWidgetListBean{" +
                "currentPage=" + currentPage +
                ", itemsPerPage=" + itemsPerPage +
                ", messageWidgetBean=" + messageWidgetBeanList +
                '}';
    }
}
