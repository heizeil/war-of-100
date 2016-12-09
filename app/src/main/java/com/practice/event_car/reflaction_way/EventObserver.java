package com.practice.event_car.reflaction_way;

/**
 * @作者:XJY
 * @创建日期: 2016/12/7 18:42
 * @描述:${TODO}
 * @更新者:${Author}$
 * @更新时间:${Date}$
 * @更新描述:${TODO}
 */

public interface EventObserver {

    public void eventObserved(Object object);
    public void register(Object clazzObj);
    public void unregister(Object clazzObj);
    public EventObserver post(Object object);
}
