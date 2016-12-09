package com.practice.event_car.broadcast_way;

import com.practice.event_car.annotation.Function;
import com.practice.event_car.thread.ThreadMode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @作者:XJY
 * @创建日期: 2016/12/7 22:28
 * @描述:${TODO}
 * @更新者:不完全版
 */

public class ParseAnnotation {

    public static Map<String,Object> getAnnotationInfo(Class clazz){
        Map<String,Object> result = new HashMap<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Function.class)) {
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                Class<?> parameterClazz = parameterTypes[0];
                result.put(MapField.METHOD,declaredMethod);
                result.put(MapField.PARAMS_CLASS,parameterClazz);
            }
        }
        if(!result.containsKey(MapField.METHOD))
        {
            return  null;
        }
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {

            if (Function.class.equals(annotation.annotationType())) {
                Function anno = (Function) annotation;
                ThreadMode mode = anno.threadMod();
                result.put(MapField.THREAD_MODE,mode);
            }
        }
        return result;

    }

}
