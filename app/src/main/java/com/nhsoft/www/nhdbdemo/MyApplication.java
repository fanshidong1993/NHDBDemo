package com.nhsoft.www.nhdbdemo;

import android.app.Application;

import com.nhsoft.www.nhdbdemo.db.NHDBHelper;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NHDBHelper.init(this);
    }
}
