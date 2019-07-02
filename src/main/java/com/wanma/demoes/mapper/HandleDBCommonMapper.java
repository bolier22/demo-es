package com.wanma.demoes.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface HandleDBCommonMapper {
    /**
     * @desc 检查数据库是否存在指定的数据库
     * @param dbName
     * @return
     */
    Integer existsDB(@Param("dbName") String dbName);

    /**
     * @desc 根据库名创建空数据库
     * @param dbName
     */
    void createDB(@Param("dbName") String dbName);

    /**
     * @desc 检查数据库中是否存在指定的表
     * @param tableName
     * @return
     */
    Integer existsTable(@Param("tableName") String tableName, @Param("dbName") String dbName);


    /**
     * @desc 根据表名创建表结构
     * @param tableName
     */
    void createChargingorderExtract(@Param("dbName") String dbName, @Param("tableName") String tableName);


    void createChargingOrderRecord(@Param("dbName") String dbName, @Param("tableName") String tableName);

    /**
     * @auth libg 2019.3.19
     * @desc 查询统计库的名称列表
     * @return
     */
    List<String> selectStatDbNameList();

    void createBspUserElectricpileMapping(@Param("dbName") String dbName, @Param("tableName") String tableName);

    void createTblChargingorderExtractBspUserYear(@Param("dbName") String dbName, @Param("tableName") String tableName);


    /**
     * @auth libg 2019.3.19
     * @desc 查询统计库的提取表名称列表
     * @return
     */
    List<String> selectChargingorderExtractTableNameList(@Param("dbName") String dbName);


    void createCpyOrderExtract(String dbName, String tableName);
}
