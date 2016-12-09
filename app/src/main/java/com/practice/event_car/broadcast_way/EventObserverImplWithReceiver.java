package com.practice.event_car.broadcast_way;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.practice.event_car.reflaction_way.EventObserver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者:XJY
 * @创建日期: 2016/12/7 20:34
 * @描述:用广播+注解完成任务
 */

public class EventObserverImplWithReceiver implements EventObserver {
    private final Context                               mContext;
    private       Map<Class, List<Map<String, Object>>> mObjectMap;
    private static final String TAG = "EventCar";


    public EventObserverImplWithReceiver(Context context) {
        mContext = context;
        mObjectMap = new HashMap<>();
    }

    @Override
    public void eventObserved(Object object) {

    }

    /**
     * 注册对象，将信息保存在Map中
     *
     * @param clazzObj 要注册的对象
     */
    @Override
    public void register(final Object clazzObj) {
        if (clazzObj instanceof Context) {
            Context at = (Context) clazzObj;
            final Map<String, Object> stringObjectMap = ParseAnnotation.getAnnotationInfo(clazzObj.getClass());
            final Class clazz = (Class) stringObjectMap.get(MapField.PARAMS_CLASS);
            BroadcastReceiver receiver = getBroadcastReceiver(at, stringObjectMap);
            stringObjectMap.put(MapField.RECEIVER, receiver);
            stringObjectMap.put(MapField.OBJECT, clazzObj);
            if (mObjectMap.containsKey(clazz)) {
                List<Map<String, Object>> entries = mObjectMap.get(clazz);
                if (entries == null) {
                    entries = new ArrayList<>();
                }
                entries.add(stringObjectMap);
            } else {
                List<Map<String, Object>> entries = new ArrayList<>();
                entries.add(stringObjectMap);
                mObjectMap.put(clazz, entries);
            }

        } else {
            //TODO：
        }
    }

    /**
     * 动态注册广播
     *
     * @param context       要注册的对象，为context的子类情况下
     * @param annotationMap 通过注解的到的类信息Map
     * @return 返回注册的广播
     */
    @NonNull
    private BroadcastReceiver getBroadcastReceiver(Context context, final Map<String, Object> annotationMap) {
        final Class clazz = (Class) annotationMap.get(MapField.PARAMS_CLASS);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Object object;

                if (clazz.equals(String.class)) {
                    object = intent.getStringExtra(MapField.OBJECT);
                } else {
                    Bundle extras = intent.getExtras();
                    object = extras.get(MapField.OBJECT);
                }
                Method method = (Method) annotationMap.get(MapField.METHOD);
                try {
                    method.invoke(context, object);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "==eventObserved: ==" + e.getMessage());
                }
                //EventObserverImplWithReceiver.this.eventObserved(object);
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(clazz.getName());
        context.registerReceiver(receiver, filter);
        return receiver;
    }


    /**
     * 清除注册信息，包括Map信息和广播
     *
     * @param clazzObj 反注册的对象
     */
    @Override
    public void unregister(Object clazzObj) {
        if (clazzObj instanceof Context) {
            Context at = (Context) clazzObj;
            Map<String, Object> method = ParseAnnotation.getAnnotationInfo(clazzObj.getClass());
            Class clazz = (Class) method.get(MapField.PARAMS_CLASS);
            if (mObjectMap.containsKey(clazz)) {
                List<Map<String, Object>> entries = mObjectMap.get(clazz);
                if (entries != null) {
                    Map<String, Object> stringObjectMap = null;
                    BroadcastReceiver receiver = null;
                    for (Map<String, Object> entry : entries) {
                        if (entry.get(MapField.OBJECT).equals(clazzObj)) {
                            receiver = (BroadcastReceiver) entry.get(MapField.RECEIVER);
                            stringObjectMap = entry;
                        }
                    }
                    if (receiver != null) {
                        at.unregisterReceiver(receiver);

                        entries.remove(stringObjectMap);
                        stringObjectMap.clear();
                    }

                }
            }

        } else {
            //TODO：
        }

    }

    /**
     * 发送信息
     *
     * @param object 发送的信息
     * @return 返回默认对象以便于连续发送信息
     */
    @Override
    public EventObserver post(Object object) {
        Intent intent = new Intent(object.getClass().getName());
        if (object instanceof String) {
            intent.putExtra("object", (String) object);
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("object", (Parcelable) object);
            intent.putExtras(bundle);
        }
        mContext.sendBroadcast(intent);
        return this;
    }
}
