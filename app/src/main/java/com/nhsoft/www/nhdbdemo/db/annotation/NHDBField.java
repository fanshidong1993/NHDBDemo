package com.nhsoft.www.nhdbdemo.db.annotation;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public @interface NHDBField {
    String columnName() default "";
    boolean primaryKey() default false;
    boolean foreignKey() default false;
    boolean canBeNull() default true;
    String references() default "";
}
