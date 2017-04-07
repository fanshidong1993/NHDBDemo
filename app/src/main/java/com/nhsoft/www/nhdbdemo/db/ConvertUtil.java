package com.nhsoft.www.nhdbdemo.db;

import com.nhsoft.www.nhdbdemo.db.util.ReflectUtil;

import java.lang.reflect.Field;

/**
 * Created by nhsoft.fanshidong on 2017/4/7.
 */

public class ConvertUtil {
    public static <T,U> U convert(T t, Class<U> clazzU){

        Field[] fieldsU = clazzU.getDeclaredFields();
        try {
            U u = clazzU.newInstance();
            for (Field field : fieldsU){
                ReflectUtil.setValue(
                        u,
                        field.getName(),
                        ReflectUtil.getValue(t,field.getName())
                );
            }
            return u;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
