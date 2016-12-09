package com.practice.event_car.annotation;

import com.practice.event_car.bean.EventRecorderBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者:XJY
 * @创建日期: 2016/12/8 22:57
 * @描述:${TODO}
 * @更新者:${Author}$
 * @更新时间:${Date}$
 * @更新描述:${TODO}
 */

public class AnnotationParser {

    public static List<EventRecorderBean> getAnnotationInfo(Object obj){
        List<EventRecorderBean> list = new ArrayList<>();
        Method[] declaredMethods = obj.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Function.class)) {
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                Class<?> parameterClazz = parameterTypes[0];

                EventRecorderBean bean= new EventRecorderBean();
                Annotation[] annotations = declaredMethod.getAnnotations();
                if (annotations == null||annotations.length ==0) {
                    throw new RuntimeException("请标注ThreadMode");
                }
                Function annotation = (Function) annotations[0];
                bean.mThreadMode = annotation.threadMod();
                bean.mMethod = declaredMethod;
                bean.mParams = parameterClazz;
                bean.mObject = obj;
               list.add(bean);
            }
        }
        return list;
    }
}
