package com.nhsoft.www.nhdbdemo.db.util;

import java.lang.reflect.Method;

public class ReflectUtil {
      
    /** 
     * 获取实体类一个成员变量的值 
     */  
    public static Object getValue(Object entity, String fieldName) {  
        Object value = null;  
        try {  
            Class<?> clazz = entity.getClass();  
            String methodName = "get" + StrUtil.getUpperCharAt(fieldName, 0);  
            Method method = clazz.getMethod(methodName);
            value = method.invoke(entity);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return value;  
    }  
      
    public static void setValue(Object entity, String fieldName, Object value) {  
        try {  
            Class<?> clazz = entity.getClass();  
            Class<?> type = clazz.getDeclaredField(fieldName).getType();  
            String methodName = "set" + StrUtil.getUpperCharAt(fieldName, 0);  
            Method method = clazz.getMethod(methodName, type);  
            if(value != null) {  
                if(type.equals(Integer.class)) {  
                    value = Integer.parseInt(value.toString());  
                } else if(type.equals(Double.class)) {  
                    value = Double.parseDouble(value.toString());  
                } else if(type.equals(Long.class)) {  
                    value = Long.parseLong(value.toString());  
                }  
            }  
            method.invoke(entity, new Object[] {value});  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}