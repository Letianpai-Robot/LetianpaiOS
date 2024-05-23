package com.renhejia.robot.launcher.audioservice;


import android.support.annotation.NonNull;

import com.renhejia.robot.commandlib.log.LogUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GestureDataThreadExecutor implements Executor {
    Future<?> currentTask = null;
    private static GestureDataThreadExecutor gestureDataThreadExecutor;

    public static GestureDataThreadExecutor getInstance() {
        if (gestureDataThreadExecutor == null) {
            gestureDataThreadExecutor = new GestureDataThreadExecutor();
        }
        return gestureDataThreadExecutor;
    }

    private final ExecutorService mExecutorService;

    private GestureDataThreadExecutor() {
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable task) {
        if (currentTask != null && !currentTask.isDone()) {
            LogUtils.logd("GestureDataThreadExecutor", "execute: cancel thread");
            currentTask.cancel(true);
        } else {
            LogUtils.logd("GestureDataThreadExecutor", "execute: thread empty");
        }
        currentTask = mExecutorService.submit(task);
    }
}
