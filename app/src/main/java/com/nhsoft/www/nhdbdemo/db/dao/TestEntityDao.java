package com.nhsoft.www.nhdbdemo.db.dao;

import com.nhsoft.www.nhdbdemo.db.entity.TestEntity;

import java.util.List;

/**
 * Created by nhsoft.fanshidong on 2017/4/6.
 */

public interface TestEntityDao {
    public void insert(TestEntity testEntity);
    public List<TestEntity> findAll();
    public int update(TestEntity testEntity);
    public TestEntity find(TestEntity testEntity);
    public int delete(TestEntity testEntity);
}
