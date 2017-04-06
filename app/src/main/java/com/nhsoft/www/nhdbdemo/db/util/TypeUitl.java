package com.nhsoft.www.nhdbdemo.db.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public class TypeUitl {
    /**
     * 获取属性的类型
     * @param type 未知类型
     * @return 只支持 INTEGER DOUBLE TEXT
     */
    public static String getTypeTxt(Class<?> type){
        if (type.equals(Integer.class) || type.equals(Long.class) || type.equals(int.class) || type.equals(long.class) ){
            return " INTEGER";
        }else if (type.equals(Double.class) || type.equals(double.class)){
            return " DOUBLE";
        }else{
            return " TEXT";
        }
    }

}
