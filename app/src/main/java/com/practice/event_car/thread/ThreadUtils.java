package com.practice.event_car.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @作者:XJY
 * @创建日期: 2016/12/8 8:47
 * @描述:${TODO}
 * @更新者:${Author}$
 * @更新时间:${Date}$
 * @更新描述:${TODO}
 */

public class ThreadUtils {

    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private  static ExecutorService sExecutorService = Executors.newSingleThreadExecutor();
    public static void getThreadTORun(ThreadMode threadMode, Runnable runnable){

        switch (threadMode) {
            case MAIN:
                mHandler.post(runnable);
                break;
            case SUBTHREAD:
                sExecutorService.execute(runnable);
                break;
            case POST:
                runnable.run();
                break;
        }
    }

    public static void clear(){
        mHandler = null;
        sExecutorService = null;
    }
}
