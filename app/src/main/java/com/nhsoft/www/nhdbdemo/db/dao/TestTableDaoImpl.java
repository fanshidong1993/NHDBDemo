package com.nhsoft.www.nhdbdemo.db.dao;

import com.nhsoft.www.nhdbdemo.db.NHDBHelper;
import com.nhsoft.www.nhdbdemo.db.entity.TestTable;
import com.nhsoft.www.nhdbdemo.db.exception.ForeignKeyNotFound;

import java.util.List;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public class TestTableDaoImpl implements TestTableDao {

    @Override
    public int insert(TestTable testTable) {
        int res = 0;
        NHDBHelper.getDB().beginTransaction();
        try {
            res = Dao.insert(testTable);
            NHDBHelper.getDB().setTransactionSuccessful();
        } catch (ForeignKeyNotFound foreignKeyNotFound) {
            foreignKeyNotFound.printStackTrace();

        }finally {
            NHDBHelper.getDB().endTransaction();
        }
        return res;
    }

    @Override
    public List<TestTable> findAll() {
        return Dao.findAll(TestTable.class);
    }

    @Override
    public int update(TestTable testTable) {
        return Dao.update(testTable);
    }

    @Override
    public TestTable find(TestTable testTable) {
        return Dao.find(testTable);
    }

    @Override
    public int delete(TestTable testTable) {
        return Dao.delete(testTable);
    }
}
