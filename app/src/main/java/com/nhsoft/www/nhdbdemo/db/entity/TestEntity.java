package com.nhsoft.www.nhdbdemo.db.entity;

import com.nhsoft.www.nhdbdemo.db.annotation.NHDBField;
import com.nhsoft.www.nhdbdemo.db.annotation.NHDBTable;
import com.nhsoft.www.nhdbdemo.db.annotation.NHDBUpdate;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

@NHDBTable(tableName = "TEST_ENTITY")
public class TestEntity {

    @NHDBField(columnName = "PRIMARY_KEY_1", primaryKey = true, canBeNull = false)
    String primaryKey1;

    @NHDBField(columnName = "PRIMARY_KEY_2", primaryKey = true, canBeNull = false)
    Integer primaryKey2;

    @NHDBField(columnName = "COLUMN_NAME_1")
    Long columnName1;

    @NHDBField(columnName = "COLUMN_NAME_2")
    Double columnName2;

    @NHDBField(columnName = "COLUMN_NAME_3")
    Integer columnName3;

    @NHDBField(columnName = "COLUMN_NAME_4")
    Double columnName4;

    @NHDBUpdate(dbVersion = 2)
    @NHDBField(columnName = "COLUMN_NAME_5")
    Integer columnName5;

    @NHDBUpdate(dbVersion = 2)
    @NHDBField(columnName = "COLUMN_NAME_6")
    String columnName6;

    @NHDBUpdate(dbVersion = 3)
    @NHDBField(columnName = "COLUMN_NAME_7")
    String columnName7;

    @NHDBUpdate(dbVersion = 4)
    @NHDBField(columnName = "column")
    private Double column;

    public Double getColumn() {
        return column;
    }

    public void setColumn(Double column) {
        this.column = column;
    }

    public String getPrimaryKey1() {
        return primaryKey1;
    }

    public void setPrimaryKey1(String primaryKey1) {
        this.primaryKey1 = primaryKey1;
    }

    public Integer getPrimaryKey2() {
        return primaryKey2;
    }

    public void setPrimaryKey2(Integer primaryKey2) {
        this.primaryKey2 = primaryKey2;
    }

    public Long getColumnName1() {
        return columnName1;
    }

    public void setColumnName1(Long columnName1) {
        this.columnName1 = columnName1;
    }

    public Double getColumnName2() {
        return columnName2;
    }

    public void setColumnName2(Double columnName2) {
        this.columnName2 = columnName2;
    }

    public Integer getColumnName3() {
        return columnName3;
    }

    public void setColumnName3(Integer columnName3) {
        this.columnName3 = columnName3;
    }

    public Double getColumnName4() {
        return columnName4;
    }

    public void setColumnName4(Double columnName4) {
        this.columnName4 = columnName4;
    }

    public Integer getColumnName5() {
        return columnName5;
    }

    public void setColumnName5(Integer columnName5) {
        this.columnName5 = columnName5;
    }

    public String getColumnName6() {
        return columnName6;
    }

    public void setColumnName6(String columnName6) {
        this.columnName6 = columnName6;
    }

    public String getColumnName7() {
        return columnName7;
    }

    public void setColumnName7(String columnName7) {
        this.columnName7 = columnName7;
    }
}
