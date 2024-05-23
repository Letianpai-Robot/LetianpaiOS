package com.rhj.message;

public class MessageWidgetContentBean extends MessageBean {
    private String title;
    private String subTitle;
    private String imgUrl;

    @Override
    public int getMessage_type() {
        return super.getMessage_type();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getSubTitle() {
        return subTitle;
    }

    @Override
    public void setMessage_type(int message_type) {
        super.setMessage_type(message_type);
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MessageWidgetContentBean{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
