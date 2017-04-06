package com.nhsoft.www.nhdbdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nhsoft.www.nhdbdemo.db.entity.TestEntity;
import com.nhsoft.www.nhdbdemo.db.entity.TestTable;
import com.nhsoft.www.nhdbdemo.db.util.TableUtil;

import java.util.List;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public class NHDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "NHDBDemo.db";
    private static final int DB_VERSION = 4;

    private static NHDBHelper instance;
    public static void init(Context context){
        Log.i("NHDBHelper", "==onInit==");
        if (instance == null)
            instance = new NHDBHelper(context);
    }

    /**
     * 必须在 init 之后调用
     * @return NHDBHelper 单例
     */
    public static NHDBHelper getInstance() {
        return instance;
    }


    private NHDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("NHDBHelper", "==onCreate==");
        db.execSQL(TableUtil.createTable(TestEntity.class));
        db.execSQL(TableUtil.createTable(TestTable.class));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("NHDBHelper", "==onUpgrade==");
        updateTable(db,TableUtil.updateTable(TestEntity.class,oldVersion));
        updateTable(db,TableUtil.updateTable(TestTable.class,oldVersion));
    }

    private void updateTable(SQLiteDatabase db, List<String> execSqlList) {
        for (String execSql : execSqlList){
            db.execSQL(execSql);
        }
    }

    public static SQLiteDatabase getDB(){
        return getInstance().getWritableDatabase();
    }
}
