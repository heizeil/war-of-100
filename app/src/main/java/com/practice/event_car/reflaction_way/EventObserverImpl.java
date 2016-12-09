package com.practice.event_car.reflaction_way;

import com.practice.event_car.annotation.AnnotationParser;
import com.practice.event_car.bean.EventRecorderBean;
import com.practice.event_car.thread.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @作者:XJY
 * @创建日期: 2016/12/7 18:56
 * @描述:采用反射+注解模拟EventBus
 */

public class EventObserverImpl implements EventObserver {


    private Map<Class, List<EventRecorderBean>> mObjectMap;
    private static final String TAG = "EventCar";



    public EventObserverImpl() {
        mObjectMap = new HashMap<>();
    }

    @Override
    public void eventObserved(final Object object) {
        if (mObjectMap.containsKey(object.getClass())) {
            List<EventRecorderBean> eventRecorderBeens = mObjectMap.get(object.getClass());
            if (eventRecorderBeens!=null&&eventRecorderBeens.size()>0) {
                for (final EventRecorderBean eventRecorderBeen : eventRecorderBeens) {
                    ThreadUtils.getThreadTORun(eventRecorderBeen.mThreadMode, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                eventRecorderBeen.mMethod.invoke(eventRecorderBeen.mObject,object);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        }
    }



    @Override
    public void register(Object toRegister) {
        List<EventRecorderBean> annotationInfo = AnnotationParser.getAnnotationInfo(toRegister);
        if (annotationInfo.size() == 0) {
            return;
        }
        for (EventRecorderBean bean : annotationInfo) {
            Class clazz = bean.mParams;
            if (mObjectMap.containsKey(clazz)) {
                List<EventRecorderBean> maps = mObjectMap.get(clazz);
                if (maps == null) {
                    maps = new ArrayList<>();
                }
                maps.add(bean);
            }else{
                List<EventRecorderBean> list = new ArrayList<>();
                list.add(bean);
                mObjectMap.put(clazz,list);
            }
        }

    }

    @Override
    public void unregister(Object toRegister) {
        List<EventRecorderBean> annotationInfo = AnnotationParser.getAnnotationInfo(toRegister);
        if (annotationInfo.size() == 0) {
            annotationInfo = null;
            return;
        }

        for (EventRecorderBean bean : annotationInfo) {
            Class clazz = bean.mParams;
            if (mObjectMap.containsKey(clazz)) {
                List<EventRecorderBean> list = mObjectMap.get(clazz);
                if (list == null) {
                    mObjectMap.remove(clazz);
                    continue;
                }
                ListIterator<EventRecorderBean> iterator = list.listIterator();
                while (iterator.hasNext()){
                    if(iterator.next().mObject.equals(bean.mObject)){//对比的条件错误，new的对象和原对象不等价
                        iterator.remove();
                    }
                }

            }
//            bean = null;
        }
        annotationInfo = null;


    }

    @Override
    public EventObserver post(Object object) {
        eventObserved(object);
        return this;
    }
}
