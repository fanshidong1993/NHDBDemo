package com.nhsoft.www.nhdbdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nhsoft.www.nhdbdemo.db.NHDBHelper;
import com.nhsoft.www.nhdbdemo.db.dao.TestEntityDao;
import com.nhsoft.www.nhdbdemo.db.dao.TestEntityDaoImpl;
import com.nhsoft.www.nhdbdemo.db.dao.TestTableDao;
import com.nhsoft.www.nhdbdemo.db.dao.TestTableDaoImpl;
import com.nhsoft.www.nhdbdemo.db.entity.TestEntity;
import com.nhsoft.www.nhdbdemo.db.entity.TestTable;
import com.nhsoft.www.nhdbdemo.db.util.TableUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestEntityDao testEntityDao = new TestEntityDaoImpl();
        TestEntity testEntity = new TestEntity();
        testEntity.setColumnName1((long) 5133333);
        testEntity.setColumnName2(513.33);
        testEntity.setColumnName3(513);
        testEntity.setColumnName4(513.11);
        testEntity.setColumnName5(513);
        testEntity.setColumnName6("513");
        testEntity.setColumn(1.0);
        testEntity.setPrimaryKey1("rp");
        testEntity.setPrimaryKey2(52);

        //testEntity = testEntityDao.find(testEntity);
        testEntityDao.insert(testEntity);
        //testEntityDao.update(testEntity);
        testEntityDao.delete(testEntity);
        List<TestEntity> list = testEntityDao.findAll();
        Log.d("onCreate","size:"+list.size());
        //Log.d("onCreate", testEntity.toString());

        TestTableDao dao = new TestTableDaoImpl();
        List<TestTable> listt;

        TestTable testTable = new TestTable();
        testTable.setKey("11");
        testTable.setColumn(1.0);
        dao.insert(testTable);

        listt = dao.findAll();
        testTable.setKey("22");
        testTable.setColumn(2.2);
        dao.insert(testTable);
        listt = dao.findAll();
        testTable.setColumn(11.2);
        dao.update(testTable);
        listt = dao.findAll();
        testTable.setKey("11");
        testTable = dao.find(testTable);

        dao.delete(testTable);
        listt = dao.findAll();

        Log.d("size:",""+list.size());

    }
}
