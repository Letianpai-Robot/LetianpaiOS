package com.rhj.message;

public class MessageWidgetWebBean extends MessageBean{
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MessageWidgetWebBean{" +
                "url='" + url + '\'' +
                '}';
    }
}
