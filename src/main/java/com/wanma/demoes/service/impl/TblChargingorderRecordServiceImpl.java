package com.wanma.demoes.service.impl;

import com.wanma.demoes.controller.MoveData;
import com.wanma.demoes.entity.PagingParam;
import com.wanma.demoes.entity.TblChargingorderRecord;
import com.wanma.demoes.mapper.TblChargingorderRecordMapper;
import com.wanma.demoes.service.TblChargingorderRecordService;
import com.wanma.demoes.utils.DateUtil;
import com.wanma.demoes.utils.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TblChargingorderRecordServiceImpl implements TblChargingorderRecordService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TblChargingorderRecordMapper tblChargingorderRecordMapper;

    @Autowired
    private MoveData moveData;

    @Override
    public ArrayList<TblChargingorderRecord> selectTblChargingorderRecordList(HashMap paramMap) {
        try{
            return tblChargingorderRecordMapper.selectTblChargingorderRecordList(paramMap);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Long> selectTblChargingorderRecordIdsList(HashMap paramMap) {
        return tblChargingorderRecordMapper.selectTblChargingorderRecordIdsList(paramMap);
    }


    @Override
    public int selectCountTblChargingorderRecord(HashMap paramMap) {
        try{
            return tblChargingorderRecordMapper.selectCountTblChargingorderRecord(paramMap);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return 0;

    }

    @Override
    public void deleteTblChargingorderRecord(HashMap paramMap) {
        try{
            tblChargingorderRecordMapper.deleteTblChargingorderRecord(paramMap);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }




    /**
     * 获取查询条件以及总的查询条数 orderRecord表
     * @param orderRecord
     * @return
     */
    @Override
    public List<PagingParam> selectOrderDataCountList(TblChargingorderRecord orderRecord){

        String beginChargeTime = orderRecord.getBeginChargeTimeStart();
        String endChargeTime = orderRecord.getBeginChargeTimeEnd();
        if(orderRecord.getUserId() != null){
            orderRecord.setTableNameOfMapping("tbl_bsp_user_electricpile_mapping_"+orderRecord.getUserId()%9);
        }

        List<PagingParam> getPagingParamList = DateUtil.getPagingParamList(beginChargeTime,endChargeTime);
        for(PagingParam pagingParam:getPagingParamList){
            orderRecord.setBeginChargeTimeStart(pagingParam.getStartQueryTime());
            orderRecord.setBeginChargeTimeEnd(pagingParam.getEndQueryTime());
            HashMap paramMap = new HashMap();

            moveData.getDbNameAndOrderRecordNameMap(paramMap,pagingParam.getDateStr());

            String dbName = paramMap.get("dbName").toString();
            String tableName = paramMap.get("tableName").toString();
            orderRecord.setDbName(dbName);
            orderRecord.setTableName(tableName);
            pagingParam.setCount(tblChargingorderRecordMapper.selectOrderDataCount(orderRecord));
            System.out.println(pagingParam.toString());
        }
//        long total1 = getPagingParamList.stream().map(PagingParam::getCount).reduce(Long::sum).get();
        return getPagingParamList;
    }



    @Override
    public List<TblChargingorderRecord>  selectOrderDataList(List<PagingParam> pagingParamList,TblChargingorderRecord orderRecord){
        pagingParamList = pagingParamList.stream().sorted(Comparator.comparing(PagingParam::getIndex).reversed()).collect(Collectors.toList());
        Pager pager = orderRecord.getPager();
        long pageNo = pager.getPageNo();
        long pageSize = pager.getPageSize();
        long limitStart = pager.getOffset();
        long limitEnd = pageNo*pageSize;
        int currentSum = 0;
        int preSum = 0;
        boolean flag = false;

        List<TblChargingorderRecord> selectOrderDataList = new ArrayList<TblChargingorderRecord>();
        for(PagingParam pagingParam:pagingParamList){
            orderRecord.setBeginChargeTimeStart(pagingParam.getStartQueryTime());
            orderRecord.setBeginChargeTimeEnd(pagingParam.getEndQueryTime());
            HashMap paramMap = new HashMap();
            moveData.getDbNameAndOrderRecordNameMap(paramMap,pagingParam.getDateStr());
            String dbName = paramMap.get("dbName").toString();
            String tableName = paramMap.get("tableName").toString();
            orderRecord.setDbName(dbName);
            orderRecord.setTableName(tableName);

            if(flag){
                if(pagingParam.getCount() >= pageSize){
                    limitStart = 0;
                    limitEnd = pageSize;
                    pager.setOffsetEx(limitStart);
                    pager.setPageSize(pageSize);
                    selectOrderDataList.addAll(tblChargingorderRecordMapper.selectOrderDataList(orderRecord));
                    pager.setPageSize(20l);
                    break;
                } else{
                    pageSize = pageSize - pagingParam.getCount();//剩余条数 下一部分 0 到 n;
                    limitStart = 0;
                    limitEnd = pagingParam.getCount();
                    pager.setOffsetEx(limitStart);
                    orderRecord.setPager(pager);
                    selectOrderDataList.addAll(tblChargingorderRecordMapper.selectOrderDataList(orderRecord));
                }
            }else {
                currentSum += pagingParam.getCount();
                if(limitStart<currentSum&&limitEnd<=currentSum){//说明第一张表的数据大于当前分页所需的数据 直接取
                    limitStart = limitStart-preSum;
                    limitEnd = limitEnd-preSum;
                    pager.setOffsetEx(limitStart);
                    orderRecord.setPager(pager);
                    selectOrderDataList.addAll(tblChargingorderRecordMapper.selectOrderDataList(orderRecord));
                    break;//取到所需的数据 结束
                }

                if(limitStart<currentSum&&limitEnd>currentSum){// 说明 第一张表的数据 不足当前分页所需数据
                    limitStart = limitStart-preSum;
                    pager.setOffsetEx(limitStart);
                    selectOrderDataList.addAll(tblChargingorderRecordMapper.selectOrderDataList(orderRecord));
                    pageSize = limitEnd - preSum - pagingParam.getCount();//剩余条数 下一部分 0 到 n;
                    flag = true;
                }
            }
            preSum = currentSum;
        }
        return selectOrderDataList;
    }

    @Override
    public List<TblChargingorderRecord>  selectOrderDataListNew(){
        List<TblChargingorderRecord> selectOrderDataList = new ArrayList<TblChargingorderRecord>();
        selectOrderDataList = tblChargingorderRecordMapper.selectOrderDataListNew();
        return selectOrderDataList;
    }
}

