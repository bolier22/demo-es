package com.wanma.demoes.controller;

import com.wanma.demoes.ds.DynamicDataSource;
import com.wanma.demoes.service.HandleDBCommonService;
import com.wanma.demoes.service.TblChargingorderRecordService;
import com.wanma.demoes.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MoveData {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TblChargingorderRecordService tblChargingorderRecordService;

//    @Autowired
//    private RedisConfig redisMaster;

    @Autowired
    HandleDBCommonService handleDBCommonService;

    /**
     * 获取总页码
     * @param total
     * @param pageSize
     * @return
     */
    static int getPageTotal(int total,int pageSize){
        int pageTotal = 0;
        if(total > 0) {
            pageTotal = (total + pageSize - 1) / pageSize;
        }
        return pageTotal;
    }

    /**
     * @auth libg 2019.2.12
     * @Desc 检查db或表是否存在 不存在则创建
     * @param dbName
     * @param tableName
     */
    private void checkDbOrTabNotIsCreate(String dbName, String tableName){

        String keyName = "bsp:dbName:"+dbName;
        int checkDbOrTab;
//        if(!redisMaster.hasKey(keyName)){
            checkDbOrTab = handleDBCommonService.existsDB(dbName);
            if(checkDbOrTab == 0){
                handleDBCommonService.createDB(dbName);
            }
//            redisMaster.strSet(keyName,keyName);
//            redisMaster.setTime(keyName,30L, TimeUnit.DAYS);
//        }

        String tableNameKey = "bsp:dbName:"+dbName+"."+tableName;
//        if(!redisMaster.hasKey(tableNameKey)){
            checkDbOrTab = handleDBCommonService.existsTable(tableName,dbName);
            if(checkDbOrTab == 0){
                if(tableName.contains("tbl_charging_order_record_")){
                    handleDBCommonService.createChargingOrderRecord(dbName,tableName);
                }else if(tableName.contains("tbl_chargingorder_extract_")){
                    handleDBCommonService.createChargingorderExtract(dbName,tableName);
                }else if (tableName.contains("cpy_order_extract_data_")){
                    handleDBCommonService.createCpyOrderExtract(dbName,tableName);
                }
            }

//            redisMaster.strSet(tableNameKey,tableNameKey);
//            redisMaster.setTime(tableNameKey,30L, TimeUnit.DAYS);
//        }
    }



    /**
     * 根据开始时间生成dbname和extract dbtable 并放入到map中
     * @param paramMap
     * @param beginChargeTime
     */
    public void getDbNameAndOrderExtractNameMap(HashMap paramMap,String beginChargeTime){
        DynamicDataSource.setDataSource("statDB");//切换至扩展库
        String yearStr = DateUtil.getYearStr(beginChargeTime);
        String monthStr = DateUtil.getMonthStr(beginChargeTime);
        String dbName = "eichong_stat_"+yearStr;//
        String tableName = "tbl_chargingorder_extract_"+monthStr;
        checkDbOrTabNotIsCreate(dbName,tableName);

        paramMap.put("dbName",dbName);
        paramMap.put("tableName",tableName);
    }

    /**
     * 根据开始时间生成dbname和orderRecord dbtable 并放入到map中
     * @param paramMap
     * @param beginChargeTime
     */
    public void getDbNameAndOrderRecordNameMap(HashMap paramMap,String beginChargeTime){
        DynamicDataSource.setDataSource("statDB");//切换至扩展库
        String yearStr = DateUtil.getYearStr(beginChargeTime);
        String monthStr = DateUtil.getMonthStr(beginChargeTime);
        String dbName = "eichong_stat_"+yearStr;//
        String tableName = "tbl_charging_order_record_"+monthStr;
        checkDbOrTabNotIsCreate(dbName,tableName);

        paramMap.put("dbName",dbName);
        paramMap.put("tableName",tableName);
    }
}
