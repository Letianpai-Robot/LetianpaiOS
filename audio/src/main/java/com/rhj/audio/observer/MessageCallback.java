package com.rhj.audio.observer;

import com.rhj.message.MessageBean;

public interface MessageCallback {
    void onMessage(MessageBean messageBean);
}
