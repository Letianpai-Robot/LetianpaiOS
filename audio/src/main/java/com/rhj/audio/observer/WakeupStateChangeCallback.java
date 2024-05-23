package com.rhj.audio.observer;

public interface WakeupStateChangeCallback {
    /**
     * @param stateData avatar.silence 等待唤醒
     *                  avatar.listening 监听中
     *                  avatar.understanding 理解中
     *                  avatar.speaking 播放语音中
     */
    void onState(String stateData);
}
