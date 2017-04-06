package com.nhsoft.www.nhdbdemo.db.dao;

import com.nhsoft.www.nhdbdemo.db.entity.TestTable;

import java.util.List;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public interface TestTableDao {
    public int insert(TestTable testTable);
    public List<TestTable> findAll();
    public int update(TestTable testTable);
    public TestTable find(TestTable testTable);
    public int delete(TestTable testTable);
}
