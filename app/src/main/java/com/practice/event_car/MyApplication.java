package com.practice.event_car;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @作者:XJY
 * @创建日期: 2016/12/7 20:16
 * @描述:${TODO}
 * @更新者:${Author}$
 * @更新时间:${Date}$
 * @更新描述:${TODO}
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

    }
}
