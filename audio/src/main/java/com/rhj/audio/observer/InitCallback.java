package com.rhj.audio.observer;

/**
 * 初始化状态改变的监听
 */
public interface InitCallback {
    void stateChange(boolean initStatus);
}
