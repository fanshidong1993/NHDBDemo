package com.nhsoft.www.nhdbdemo.db.dao;

import com.nhsoft.www.nhdbdemo.db.NHDBHelper;
import com.nhsoft.www.nhdbdemo.db.entity.TestEntity;

import java.util.List;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public class TestEntityDaoImpl implements TestEntityDao{

    @Override
    public void insert(TestEntity testEntity) {
        //开启一个事务
        NHDBHelper.getDB().beginTransaction();
        try{
            Dao.insert(testEntity);
            //事务成功
            NHDBHelper.getDB().setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            //终止事务 回滚
            NHDBHelper.getDB().endTransaction();
        }



    }

    @Override
    public List<TestEntity> findAll() {
        return Dao.findAll(TestEntity.class);
    }

    @Override
    public int update(TestEntity testEntity) {
        return Dao.update(testEntity);
    }

    @Override
    public TestEntity find(TestEntity testEntity) {
        return Dao.find(testEntity);
    }

    @Override
    public int delete(TestEntity testEntity) {
        int res = 0;
        NHDBHelper.getDB().beginTransaction();
        try{
            res = Dao.delete(testEntity);
           /*
           //测试 回滚
           if (true)
                throw new NullPointerException();*/
            NHDBHelper.getDB().setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            NHDBHelper.getDB().endTransaction();
        }
        return res;
    }


}
