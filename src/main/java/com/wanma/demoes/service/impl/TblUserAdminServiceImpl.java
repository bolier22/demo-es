package com.wanma.demoes.service.impl;

import com.wanma.demoes.ds.DataSourceAspectService;
import com.wanma.demoes.ds.DynamicDataSource;
import com.wanma.demoes.entity.TblUserAdmin;
import com.wanma.demoes.mapper.TblUserAdminMapper;
import com.wanma.demoes.service.TblUserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@DynamicDataSource.DataSource("slaveDB")
@Service
public class TblUserAdminServiceImpl implements TblUserAdminService {

    @Autowired
    private TblUserAdminMapper tblUserAdminMapper;

//    @DynamicDataSource.DataSource("masterDB")
    @DataSourceAspectService(dataSourceName = "masterDB")
    @Override
    public List<TblUserAdmin> getList(int start, int count) {
        List<TblUserAdmin> getList = tblUserAdminMapper.getList(start, count);
        return getList;
    }

//    @DynamicDataSource.DataSource("masterDB")
    @DataSourceAspectService(dataSourceName = "slaveDB")
    @Override
    public List<TblUserAdmin> getList2(int start, int count) {
        return tblUserAdminMapper.getList2(start, count);
    }

    @DataSourceAspectService(dataSourceName = "masterDB")
    @Override
    public List<TblUserAdmin> getList3(int start, int count) {
        return tblUserAdminMapper.getList3(start, count);
    }

//    @DynamicDataSource.DataSource("slaveDB")
    @DataSourceAspectService(dataSourceName = "masterDB")
    @Override
    public void update1() {
        tblUserAdminMapper.update1();
    }

//    @DynamicDataSource.DataSource("masterDB")
    @Override
    public void update2() {
        tblUserAdminMapper.update2();
    }

//    @DynamicDataSource.DataSource
    @DataSourceAspectService(dataSourceName = "masterDB")
    @Override
    public void update3() {
        tblUserAdminMapper.update3();
    }

//    @DynamicDataSource.DataSource
//    @DataSourceAspectService(dataSourceName = "slaveDB")
    @Override
    @Transactional
    public void totle(int start, int count) {

//        List<TblUserAdmin> getList = this.getList(start, count);
//
//        DynamicDataSource.setDataSource("slaveDB");
//        this.getList(1,1);
//
//        DynamicDataSource.setDataSource("masterDB");
//        this.getList2(1,1);
//
//        DynamicDataSource.setDataSource("masterDB");
//        this.getList3(1,1);
//
//        getList = this.getList2(start, count);
//        getList = this.getList3(start, count);

        DynamicDataSource.setDataSource("slaveDB");
        this.update1();
        DynamicDataSource.setDataSource("masterDB");
        this.update2();
        DynamicDataSource.setDataSource("");
        this.update3();

        int i = 0/2;
//        getList = this.getList(start, count);
//        getList = this.getList2(start, count);
//        getList = this.getList3(start, count);
    }
}
