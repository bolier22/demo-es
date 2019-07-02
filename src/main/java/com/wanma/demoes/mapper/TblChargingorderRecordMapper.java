package com.wanma.demoes.mapper;

import com.wanma.demoes.entity.TblChargingorderRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface TblChargingorderRecordMapper {

    /**
     * @Param("listChargingorder") ArrayList<TblChargingorderRecord> list, @Param("dbName") String dbName, @Param("tableName") String tableName
     * @return
     */
    int insertBatchTblChargingorderRecordList(HashMap paramMap);

    /**
     * @param paramMap "beginChargeTime","2017-04-05"  "endChargeTime","2017-04-10"
     * @return
     */
    ArrayList<TblChargingorderRecord> selectTblChargingorderRecordList(HashMap paramMap);

    /**
     * @param paramMap "beginChargeTime","2017-04-05"
     * @return
     */
    List<Long> selectTblChargingorderRecordIdsList(HashMap paramMap);


    int selectCountTblChargingorderRecord(HashMap paramMap);

    /**
     * @Param("dbName") String dbName,@Param("tableName")String tableName,@Param("beginChargeTime")String beginChargeTime,@Param("endChargeTime")String endChargeTime ,@Param("pkChargingorderIds")String pkChargingorderIds
     * @param paramMap
     */
    void deleteTblChargingorderRecord(HashMap paramMap);


    Long selectOrderDataCount(TblChargingorderRecord searchModel);

    List<TblChargingorderRecord> selectOrderDataList(TblChargingorderRecord searchModel);

    int selectOrderDataListNewCount();

    List<TblChargingorderRecord> selectOrderDataListNew();
}