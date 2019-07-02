package com.wanma.demoes.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderQueryTotalInfo {
//    private ArrayList itemlist = new ArrayList();
//    private String tableNameOfMapping;
    private long pkChargingorder;
    private long userId;//用户编号
    private Integer chorChargingstatus;//订单状态
    private long powerStationId;//电站id
    private Integer queryDateType;//0:按开始时间查询  1：按结束时间查询            "queryDateType": 0,  queryDateType == 1 --->

    private String beginChargeTimeStart;//订单开始时间  "2019-02-01");
    private String beginChargeTimeEnd;//订单结束时间  2019-02-05");

    private Integer chorOrgno; //充电伙伴标识，1000:爱充自己，其它'   and chOr_OrgNo = #{chorOrgno}
    private String electricPileCode;// 桩体编号  and chOr_PileNumber = #{electricPileCode}
    private String cvVinCode;//'VIN', vinCode  and cv_vin_code = #{cvVinCode}
    private String chorCode;//'充电订单编号' and INSTR (chOr_Code ,#{chorCode}) > 0
    private String chorCodeNew;//新的充电订单编号
    private String chorParterExtradata;//第三方流水号或者会话ID and INSTR (chor_parter_extradata ,#{chorParterExtradata}) > 0

    private String chorParterUserLogo;// and INSTR (chor_parter_user_logo ,#{chorParterUserLogo}) > 0
    private Integer orderFilter;//1.不过滤 2.过滤(a.充电量小于1度的订单 b.卖桩项目产生的订单；c.内部人员充电产生的订单；d.被标记为异常状态的订单)   <if test="orderFilter == 2"> and chOr_QuantityElectricity > 1 and chOr_ChargingStatus in (1,2,3)
    private List<Integer> cpyNumberList;//公司标示 and chOr_OrgNo in
    private List<Long> ucIdList;//电卡主键list and pk_UserCard in

    private List<Long> userIdList;//用户主键list and chOr_UserId in
    private List<String> epCodeList;//电桩编号list and chOr_PileNumber in
    private List<Long> ownUserIdList;//内部人员id and chOr_UserId not in


}


/***
 select count(1) from ${dbName}.${tableName}

 <where>

 <if test="chorChargingstatus != null and chorChargingstatus == 0">
 and chOr_ChargingStatus in (2,3) and chOr_QuantityElectricity > 0
 </if>

 <if test="chorChargingstatus != null and chorChargingstatus != 0">
 and chOr_ChargingStatus = #{chorChargingstatus}
 </if>

 <if test="userId !=null and userId !=''">
 and chOr_PileNumber in (SELECT electricPile_code FROM ${dbName}.${tableNameOfMapping} WHERE user_id = #{userId}
 <if test="powerStationId !=null and powerStationId !=''">
 and powerstation_id = #{powerStationId}
 </if>
 )
 </if>


 <if test="queryDateType == 0">
 <if test="beginChargeTimeStart !=null and beginChargeTimeStart !=''">
 and begin_charge_day &gt;= #{beginChargeTimeStart}
 </if>
 <if test="beginChargeTimeEnd !=null and beginChargeTimeEnd !=''">
 and begin_charge_day &lt;= #{beginChargeTimeEnd}
 </if>
 </if>

 <if test="queryDateType == 1">
 <if test="beginChargeTimeStart !=null and beginChargeTimeStart !=''">
 and date_format(end_charge_time,'%Y-%m-%d') &gt;= #{beginChargeTimeStart}
 </if>
 <if test="beginChargeTimeEnd !=null and beginChargeTimeEnd !=''">
 and date_format(end_charge_time,'%Y-%m-%d') &lt;= #{beginChargeTimeEnd}
 </if>
 </if>

 <if test="electricPileCode !=null and electricPileCode !=''">
 and chOr_PileNumber = #{electricPileCode}
 </if>

 <if test="chorOrgno != null and chorOrgno !=0">
 and chOr_OrgNo = #{chorOrgno}
 </if>

 <if test="cvVinCode != null and cvVinCode !=''">
 and cv_vin_code = #{cvVinCode}
 </if>

 <if test="chorCode != null and chorCode !=''">
 and INSTR (chOr_Code ,#{chorCode}) > 0
 </if>




 <if test="chorParterExtradata !=null and chorParterExtradata !=''">
 and INSTR (chor_parter_extradata ,#{chorParterExtradata}) > 0
 </if>


 <if test="chorParterUserLogo !=null and chorParterUserLogo !=''">
 and INSTR (chor_parter_user_logo ,#{chorParterUserLogo}) > 0
 </if>

 <if test="orderFilter == 2">
 and chOr_QuantityElectricity > 1 and chOr_ChargingStatus in (1,2,3)
 </if>

 <if test="cpyNumberList != null and cpyNumberList.size() > 0">
 and chOr_OrgNo in
 <foreach collection="cpyNumberList" item="id" open="(" close=")" separator=",">
 #{id}
 </foreach>
 </if>

 <if test="ucIdList != null and ucIdList.size() > 0">
 and pk_UserCard in
 <foreach collection="ucIdList" item="id" open="(" close=")" separator=",">
 #{id}
 </foreach>
 </if>
 <if test="userIdList != null and userIdList.size() > 0">
 and chOr_UserId in
 <foreach collection="userIdList" item="id" open="(" close=")" separator=",">
 #{id}
 </foreach>
 </if>
 <if test="epCodeList != null and epCodeList.size() > 0">
 and chOr_PileNumber in
 <foreach collection="epCodeList" item="id" open="(" close=")" separator=",">
 #{id}
 </foreach>
 </if>


 <if test="ownUserIdList !=null and ownUserIdList.size()>0">
 and chOr_UserId not in
 <foreach collection="ownUserIdList" item="id" open="(" close=")" separator=",">
 #{id}
 </foreach>
 </if>
 </where>

 ***/