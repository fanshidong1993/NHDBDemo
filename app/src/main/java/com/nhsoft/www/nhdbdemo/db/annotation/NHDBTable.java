package com.nhsoft.www.nhdbdemo.db.annotation;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public @interface NHDBTable {
    String tableName() default "";
}
