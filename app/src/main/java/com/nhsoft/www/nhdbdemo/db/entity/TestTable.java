package com.nhsoft.www.nhdbdemo.db.entity;

import com.nhsoft.www.nhdbdemo.db.annotation.NHDBField;
import com.nhsoft.www.nhdbdemo.db.annotation.NHDBTable;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

@NHDBTable(tableName = "test_table")
public class TestTable {

    @NHDBField(primaryKey = true,columnName = "key")
    private String key;

    @NHDBField(columnName = "column", foreignKey = true, references = "TEST_ENTITY")
    private Double column;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getColumn() {
        return column;
    }

    public void setColumn(Double column) {
        this.column = column;
    }
}
