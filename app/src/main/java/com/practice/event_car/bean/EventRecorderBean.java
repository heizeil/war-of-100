package com.practice.event_car.bean;

import com.practice.event_car.thread.ThreadMode;

import java.lang.reflect.Method;

/**
 * @作者:XJY
 * @创建日期: 2016/12/8 22:55
 * @描述:${TODO}
 * @更新者:${Author}$
 * @更新时间:${Date}$
 * @更新描述:${TODO}
 */

public class EventRecorderBean {
    public Method mMethod;
    public Class mParams;
    public Object mObject;
    public ThreadMode mThreadMode;
}
