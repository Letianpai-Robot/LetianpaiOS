package com.letianpai;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Utils {
    private static final ExecutorService UTIL_POOL = Executors.newFixedThreadPool(3);
    static final Handler UTIL_HANDLER = new Handler(Looper.getMainLooper());
    @SuppressLint({"StaticFieldLeak"})
    private static Application sApplication;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Application getApp() {
        return sApplication != null ? sApplication : getApplicationByReflect();
    }

    static boolean isAppForeground() {
        ActivityManager am = (ActivityManager)getApp().getSystemService("activity");
        if (am == null) {
            return false;
        } else {
            List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
            if (info != null && info.size() != 0) {
                Iterator var2 = info.iterator();

                ActivityManager.RunningAppProcessInfo aInfo;
                do {
                    if (!var2.hasNext()) {
                        return false;
                    }

                    aInfo = (ActivityManager.RunningAppProcessInfo)var2.next();
                } while(aInfo.importance != 100 || !aInfo.processName.equals(getApp().getPackageName()));

                return true;
            } else {
                return false;
            }
        }
    }

    static <T> Task<T> doAsync(Task<T> task) {
        UTIL_POOL.execute(task);
        return task;
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            UTIL_HANDLER.post(runnable);
        }

    }

    public static void runOnUiThreadDelayed(Runnable runnable, long delayMillis) {
        UTIL_HANDLER.postDelayed(runnable, delayMillis);
    }

    static String getCurrentProcessName() {
        String name = getCurrentProcessNameByFile();
        if (!TextUtils.isEmpty(name)) {
            return name;
        } else {
            name = getCurrentProcessNameByAms();
            if (!TextUtils.isEmpty(name)) {
                return name;
            } else {
                name = getCurrentProcessNameByReflect();
                return name;
            }
        }
    }

    private static String getCurrentProcessNameByFile() {
        try {
            File file = new File("/proc/" + Process.myPid() + "/cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception var3) {
            var3.printStackTrace();
            return "";
        }
    }

    private static String getCurrentProcessNameByAms() {
        ActivityManager am = (ActivityManager)getApp().getSystemService("activity");
        if (am == null) {
            return "";
        } else {
            List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
            if (info != null && info.size() != 0) {
                int pid = Process.myPid();
                Iterator var3 = info.iterator();

                ActivityManager.RunningAppProcessInfo aInfo;
                do {
                    if (!var3.hasNext()) {
                        return "";
                    }

                    aInfo = (ActivityManager.RunningAppProcessInfo)var3.next();
                } while(aInfo.pid != pid || aInfo.processName == null);

                return aInfo.processName;
            } else {
                return "";
            }
        }
    }

    private static String getCurrentProcessNameByReflect() {
        String processName = "";

        try {
            Application app = getApp();
            Field loadedApkField = app.getClass().getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(app);
            Field activityThreadField = loadedApk.getClass().getDeclaredField("mActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(loadedApk);
            Method getProcessName = activityThread.getClass().getDeclaredMethod("getProcessName");
            processName = (String)getProcessName.invoke(activityThread);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return processName;
    }

    private static Application getApplicationByReflect() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke((Object)null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }

            return (Application)app;
        } catch (NoSuchMethodException var3) {
            var3.printStackTrace();
        } catch (IllegalAccessException var4) {
            var4.printStackTrace();
        } catch (InvocationTargetException var5) {
            var5.printStackTrace();
        } catch (ClassNotFoundException var6) {
            var6.printStackTrace();
        }

        throw new NullPointerException("u should init first");
    }

    public interface Callback<T> {
        void onCall(T var1);
    }

    public abstract static class Task<Result> implements Runnable {
        private static final int NEW = 0;
        private static final int COMPLETING = 1;
        private static final int CANCELLED = 2;
        private static final int EXCEPTIONAL = 3;
        private volatile int state = 0;
        private Callback<Result> mCallback;

        abstract Result doInBackground();

        public Task(Callback<Result> callback) {
            this.mCallback = callback;
        }

        public void run() {
            try {
                final Result t = this.doInBackground();
                if (this.state != 0) {
                    return;
                }

                this.state = 1;
                Utils.UTIL_HANDLER.post(new Runnable() {
                    public void run() {
                        Task.this.mCallback.onCall(t);
                    }
                });
            } catch (Throwable var2) {
                if (this.state != 0) {
                    return;
                }

                this.state = 3;
            }

        }

        public void cancel() {
            this.state = 2;
        }

        public boolean isDone() {
            return this.state != 0;
        }

        public boolean isCanceled() {
            return this.state == 2;
        }
    }
}

