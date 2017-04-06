package com.nhsoft.www.nhdbdemo.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.nhsoft.www.nhdbdemo.db.NHDBHelper;
import com.nhsoft.www.nhdbdemo.db.annotation.NHDBField;
import com.nhsoft.www.nhdbdemo.db.annotation.NHDBTable;
import com.nhsoft.www.nhdbdemo.db.exception.ForeignKeyNotFound;
import com.nhsoft.www.nhdbdemo.db.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 * dao 数据库 操作
 */

class Dao {

    /** 插入一个实体 **/

    static <T> Integer insert(T entity) throws ForeignKeyNotFound {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getAnnotation(NHDBTable.class).tableName();
        Field[] fieldArray = clazz.getDeclaredFields();

        ContentValues content = new ContentValues();

        String fieldName;
        Field field;
        Object value;
        for (Field aFieldArray : fieldArray) {
            field = aFieldArray;
            fieldName = field.getName();
            NHDBField column = field.getAnnotation(NHDBField.class);
            if (column == null)
                continue;
            value = ReflectUtil.getValue(entity, fieldName);
            if (column.foreignKey()){
                try {
                    String querySql = "SELECT * FROM " + column.references() + " WHERE " + column.columnName() + " = " + value.toString();
                    Cursor cursor = NHDBHelper.getDB().rawQuery(querySql,null);
                    if (!cursor.moveToNext())
                        throw new ForeignKeyNotFound();
                }catch (RuntimeException e){
                    throw e;
                }

            }
            if (value instanceof Integer) {
                content.put(column.columnName(), (Integer) value);
            } else if (value instanceof Long) {
                content.put(column.columnName(), (Long) value);
            } else if (value instanceof Double) {
                content.put(column.columnName(), (Double) value);
            }else if (value == null) {
                content.putNull(column.columnName());
            }else {
                content.put(column.columnName(), (String) value);
            }

        }
        long res = NHDBHelper.getDB().insert(tableName, null, content);
        return (int)res;
    }

    /** 查询一个表（实体类）的所有数据 **/
    static <T> List<T> findAll(Class<?> clazz) {
        String tableName = clazz.getAnnotation(NHDBTable.class).tableName();
        Field[] fieldArray = clazz.getDeclaredFields();

        String sql = "select * from " + tableName;
        Cursor cursor =  NHDBHelper.getInstance().getWritableDatabase().rawQuery(sql, null);

        NHDBField column;
        Field field;
        Class<?> type;
        int index;
        T entity = null;
        List<T> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            try {
                entity = (T) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            for (Field aFieldArray : fieldArray) {
                field = aFieldArray;
                column = field.getAnnotation(NHDBField.class);
                if (column == null)
                    continue;
                type = field.getType();
                index = cursor.getColumnIndex(column.columnName());
                Object value;
                if (type.equals(Integer.class)) {
                    value = cursor.getInt(index);
                } else if (type.equals(Long.class)) {
                    value = cursor.getLong(index);
                } else if (type.equals(Double.class)) {
                    value = cursor.getDouble(index);
                } else
                    value = cursor.getString(index);

                ReflectUtil.setValue(entity, field.getName(), value);
            }
            list.add(entity);
        }
        cursor.close();
        return list;
    }

    /** 更新一个表（实体类）的数据  **/
    public static <T> Integer update(T entity) {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getAnnotation(NHDBTable.class).tableName();

        NHDBField column ;
        Object value;
        Field[] fieldArray = clazz.getDeclaredFields();
        Map<String ,Object> primaryKeyValues = new HashMap<>();
        for (Field field : fieldArray){
            column = field.getAnnotation(NHDBField.class);
            if (column == null)
                continue;
            if (column.primaryKey()) {
                value = ReflectUtil.getValue(entity, field.getName());
                primaryKeyValues.put(column.columnName(),value);
            }
        }

        ContentValues content = new ContentValues();

        Field field;

        for (Field aFieldArray : fieldArray) {
            field = aFieldArray;
            column = field.getAnnotation(NHDBField.class);
            if (column == null)
                continue;
            boolean isPrimaryKey = false;
            for (Map.Entry<String,Object> entry: primaryKeyValues.entrySet()){
                if (entry.getKey().endsWith(column.columnName())){
                    isPrimaryKey = true;
                    break;
                }
            }
            if (isPrimaryKey)
                continue;
            value = ReflectUtil.getValue(entity, field.getName());
            if (value instanceof Integer) {
                content.put(column.columnName(), (Integer) value);
            } else if (value instanceof Long) {
                content.put(column.columnName(), (Long) value);
            } else if (value instanceof Double) {
                content.put(column.columnName(), (Double) value);
            }else if (value == null) {
                content.putNull(column.columnName());
            }else {
                content.put(column.columnName(), (String) value);
            }
        }

        String execSql = "";
        String[] lists = new String[primaryKeyValues.size()];
        int i = 0;
        for (Map.Entry<String,Object> entry: primaryKeyValues.entrySet()){
            if (!execSql.equals(""))
                execSql += " AND ";
            execSql += entry.getKey() + "=?";
            lists[i++] = entry.getValue().toString();
        }
        Integer result = NHDBHelper.getDB().update(tableName, content, execSql, lists);
        return result;
    }

    /**
     * 根据 id 查找
     * @param entity 实体的id 必须完备 默认 大于等于 1个 id
     * @return 1个结果
     */
    public static <T> T find(T entity){
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getAnnotation(NHDBTable.class).tableName();

        NHDBField column ;
        Object value;
        Field[] fieldArray = clazz.getDeclaredFields();
        Map<String ,Object> primaryKeyValues = new HashMap<>();
        for (Field field : fieldArray){
            column = field.getAnnotation(NHDBField.class);
            if (column == null)
                continue;
            if (column.primaryKey()) {
                value = ReflectUtil.getValue(entity, field.getName());
                primaryKeyValues.put(column.columnName(),value);
            }
        }
        String querySql = "";
        for (Map.Entry<String,Object> entry : primaryKeyValues.entrySet()){
            if (querySql.equals("")){
                querySql += "SELECT * FROM " + tableName + " WHERE ";
            }else {
                querySql += " AND ";
            }
            value = entry.getValue();
            if (value instanceof String)
                querySql += entry.getKey() + " = '" + value.toString() + "'";
            else
                querySql += entry.getKey() + " = " + value.toString();
        }
        Cursor cursor = NHDBHelper.getDB().rawQuery(querySql,null);

        T resEntity = null;
        Field field;
        Class<?> type;
        int index;
        if(cursor.moveToNext()) {
            try {
                resEntity = (T) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            for (Field aFieldArray : fieldArray) {
                field = aFieldArray;
                column = field.getAnnotation(NHDBField.class);
                if (column == null)
                    continue;
                type = field.getType();
                index = cursor.getColumnIndex(column.columnName());
                if (type.equals(Integer.class)) {
                    value = cursor.getInt(index);
                } else if (type.equals(Long.class)) {
                    value = cursor.getLong(index);
                } else if (type.equals(Double.class)) {
                    value = cursor.getDouble(index);
                }else
                    value = cursor.getString(index);
                ReflectUtil.setValue(resEntity, field.getName(), value);
            }
        }
        cursor.close();
        return resEntity;
    }

    /**
     * 删除一个 实例
     * @param entity 实例 主键必须完善
     * @return 1 成功
     */
    public static <T> Integer delete(T entity){
        if (entity == null)
            return 0;
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getAnnotation(NHDBTable.class).tableName();
        NHDBField column ;
        Object value;
        Field[] fieldArray = clazz.getDeclaredFields();
        Map<String ,Object> primaryKeyValues = new HashMap<>();
        for (Field field : fieldArray){
            column = field.getAnnotation(NHDBField.class);
            if (column == null)
                continue;
            if (column.primaryKey()) {
                value = ReflectUtil.getValue(entity, field.getName());
                primaryKeyValues.put(column.columnName(),value);
            }
        }
        String execSql = "";
        String[] lists = new String[primaryKeyValues.size()];
        int i = 0;
        for (Map.Entry<String,Object> entry: primaryKeyValues.entrySet()){
            if (!execSql.equals(""))
                execSql += " AND ";
            execSql += entry.getKey() + "=?";
            lists[i++] = entry.getValue().toString();
        }
        Integer result = NHDBHelper.getDB().delete(tableName, execSql, lists);
        return result;
    }
}
