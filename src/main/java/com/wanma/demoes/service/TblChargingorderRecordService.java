package com.wanma.demoes.service;

import com.wanma.demoes.entity.PagingParam;
import com.wanma.demoes.entity.TblChargingorderRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TblChargingorderRecordService {
    /**
     * ArrayList<TblChargingorderRecord> list, String dbName, String tableName
     */
    //void insertBatchTblChargingorderRecordList(HashMap paramMap, DataInsertRecord record);

    ArrayList<TblChargingorderRecord> selectTblChargingorderRecordList(HashMap paramMap);

    List<Long> selectTblChargingorderRecordIdsList(HashMap paramMap);

    int selectCountTblChargingorderRecord(HashMap paramMap);

    void deleteTblChargingorderRecord(HashMap paramMap);



    List<PagingParam> selectOrderDataCountList(TblChargingorderRecord orderRecord);

    List<TblChargingorderRecord>  selectOrderDataList(List<PagingParam> pagingParamList, TblChargingorderRecord orderRecord);

    List<TblChargingorderRecord>  selectOrderDataListNew();
}

