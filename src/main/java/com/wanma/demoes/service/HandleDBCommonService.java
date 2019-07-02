package com.wanma.demoes.service;

import java.util.List;

public interface HandleDBCommonService {
    Integer existsDB(String dbName);

    void createDB(String dbName);

    Integer existsTable(String dbName, String tableName);

    void createChargingorderExtract(String dbName, String tableName);

    void createChargingOrderRecord(String dbName, String tableName);

    List<String> selectStatDbNameList();

    void createBspUserElectricpileMapping(String dbName, String tableName);

    void createTblChargingorderExtractBspUserYear(String dbName, String tableName);

    List<String> selectChargingorderExtractTableNameList(String dbName);

    void createCpyOrderExtract(String dbName, String tableName);
}
