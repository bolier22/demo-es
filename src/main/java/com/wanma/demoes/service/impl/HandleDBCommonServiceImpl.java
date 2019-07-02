package com.wanma.demoes.service.impl;

import com.wanma.demoes.mapper.HandleDBCommonMapper;
import com.wanma.demoes.service.HandleDBCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandleDBCommonServiceImpl implements HandleDBCommonService {
    @Autowired
    private HandleDBCommonMapper handleDBCommonMapper;

    @Override
    public Integer existsDB(String dbName) {
        return handleDBCommonMapper.existsDB(dbName);
    }

    @Override
    public void createDB(String dbName) {
        handleDBCommonMapper.createDB(dbName);
    }

    @Override
    public Integer existsTable(String tableName,String dbName) {
        return handleDBCommonMapper.existsTable(tableName,dbName);
    }

    @Override
    public void createChargingorderExtract(String dbName,String tableName) {
        handleDBCommonMapper.createChargingorderExtract(dbName,tableName);
    }

    @Override
    public void createChargingOrderRecord(String dbName, String tableName) {
        handleDBCommonMapper.createChargingOrderRecord(dbName,tableName);
    }

    @Override
    public List<String> selectStatDbNameList() {
        return handleDBCommonMapper.selectStatDbNameList();
    }

    @Override
    public void createBspUserElectricpileMapping(String dbName, String tableName) {
        handleDBCommonMapper.createBspUserElectricpileMapping(dbName,tableName);
    }

    @Override
    public void createTblChargingorderExtractBspUserYear(String dbName, String tableName) {
        handleDBCommonMapper.createTblChargingorderExtractBspUserYear(dbName,tableName);
    }

    @Override
    public List<String> selectChargingorderExtractTableNameList(String dbName) {
        return handleDBCommonMapper.selectChargingorderExtractTableNameList(dbName);
    }

    /**
     * 新建公司订单提取表
     * @param dbName 数据库名
     * @param tableName 表名
     */
    @Override
    public void createCpyOrderExtract(String dbName, String tableName) {
        handleDBCommonMapper.createCpyOrderExtract(dbName,tableName);
    }
}
