package com.practice.event_car;

import android.content.Context;

import com.practice.event_car.reflaction_way.EventObserver;
import com.practice.event_car.reflaction_way.EventObserverImpl;


/**
 * @作者:XJY
 * @创建日期: 2016/12/7 18:55
 * @描述:
 */

public class EventCar {
    private static Context       mContext;
    private static EventObserver eoi;
    public static EventObserver getDefaultInstance(){
        if (eoi == null) {
            synchronized (EventCar.class){
                if (eoi == null) {
                    eoi = new EventObserverImpl();
                }
            }
        }
        return eoi;
    }
    public static void initEventCar(Context context){
        mContext = context;
    }
    public static void clearCar(){
        eoi = null;
    }

}
