package com.nhsoft.www.nhdbdemo.db.util;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhsoft.www.nhdbdemo.db.NHDBHelper;
import com.nhsoft.www.nhdbdemo.db.annotation.NHDBField;
import com.nhsoft.www.nhdbdemo.db.annotation.NHDBTable;
import com.nhsoft.www.nhdbdemo.db.annotation.NHDBUpdate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 * 数据库 建表 和 加表字段 的工具
 */

public class TableUtil {

    /**
     * 根据类 创建表 支持多主键 和 外键
     * @param table ENTITY类(表) XXX.class
     * @param <T> 范型 XXX
     */
    public static <T> String createTable(Class<T> table){
        //获取表名
        String tableName = table.getAnnotation(NHDBTable.class).tableName();
        String execSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ( ";
        List<String> primaryKeys = new ArrayList<>();
        Map<String,String> foreignKeys = new HashMap<>();

        NHDBField column;
        Field[] fields = table.getDeclaredFields();
        for(Field field : fields){
            //获取属性的注解
            column = field.getAnnotation(NHDBField.class);
            if (column == null)
                continue;

            //获取属性的类型
            execSql += column.columnName() + TypeUitl.getTypeTxt(field.getType());

            //判断是否可以为空
            if (!column.canBeNull()){
                execSql += " NOT NULL";
            }
            execSql += ", ";

            //判断是否是主键
            if (column.primaryKey()){
                primaryKeys.add(column.columnName());
            }
            //判断是否是外键
            if (column.foreignKey()){
                foreignKeys.put(column.columnName(),column.references());
            }
        }
        //添加外键
        if (foreignKeys.size() > 0){
            //execSql += ", ";
            for (Object o : foreignKeys.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                execSql += " FOREIGN KEY ( " + entry.getKey() + " ) REFERENCES " + entry.getValue() + " ( " + entry.getKey() + " ), ";
            }
        }

        //添加主键
        execSql += "PRIMARY KEY ( ";
        for(int i = 0 ; i < primaryKeys.size() ; ++ i ){
            if (i != 0)
                execSql += ", ";
            execSql += primaryKeys.get(i);
        }
        execSql += " )";

        //结束
        execSql += " );";

        Log.d("execSql",execSql);

        return execSql;
    }
    /*
    * 示例
    * CREATE TABLE IF NOT EXISTS TEST_ENTITY (
    * COLUMN_NAME_1 INTEGER,
    * COLUMN_NAME_2 DOUBLE,
    * COLUMN_NAME_3 INTEGER NOT NULL,
    * COLUMN_NAME_4 DOUBLE NOT NULL,
    * COLUMN_NAME_5 INTEGER,
    * COLUMN_NAME_6 TEXT,
    * PRIMARY_KEY_1 TEXT NOT NULL,
    * PRIMARY_KEY_2 INTEGER NOT NULL,
    * FOREIGN KEY ( COLUMN_NAME_4 ) REFERENCES TABLE_Y ( COLUMN_NAME_4 ),
    * FOREIGN KEY ( COLUMN_NAME_3 ) REFERENCES TABLE_X ( COLUMN_NAME_3 ),
    * PRIMARY KEY ( PRIMARY_KEY_1, PRIMARY_KEY_2 ) );
    * */

    /**
     * 根据类 更新字段 仅支持添加字段
     * @param table ENTITY类(表) XXX.class
     * @param <T> 范型 XXX
     */
    public static <T> List<String> updateTable(Class<T> table, int oldVersion){

        List<String> execSqlList = new ArrayList<>();

        //获取表名
        String tableName = table.getAnnotation(NHDBTable.class).tableName();

        NHDBUpdate update;
        NHDBField column;
        Class<?> type;
        Field[] fields = table.getDeclaredFields();
        for(Field field : fields){
            //获取更新注解
            update = field.getAnnotation(NHDBUpdate.class);
            //获取字段注解
            column = field.getAnnotation(NHDBField.class);
            if (column == null)
                continue;
            if (update == null)
                continue;
            //判断指定更新版本 是否大于当前版本
            if (update.dbVersion() <= oldVersion)
                continue;

            String execSql = "ALTER TABLE " + tableName + " ADD ";

            execSql += column.columnName() + TypeUitl.getTypeTxt(field.getType());

            Log.d("update "+oldVersion,execSql);

            execSqlList.add(execSql);

        }
        return execSqlList;
    }

    /*
    * 示例
    * ALTER TABLE TEST_ENTITY ADD COLUMN_NAME_5 INTEGER
    * */



}
