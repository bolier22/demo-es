package com.wanma.demoes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanma.demoes.utils.Pager;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Document(indexName="stat_index",type="order_record_type",shards=5,replicas=1,refreshInterval="-1")
public class TblChargingorderRecord {

    //-----------------用于查询begin------
    @Ignore
    private String dbName;//数据库名
    @Mapping
    private String tableName;//表名
    @Ignore
    private String beginChargeTimeStart;//startGmtCreate
    @Ignore
    private String beginChargeTimeEnd;//endGmtCreate
    @Ignore
    private Integer hour;
    @JsonIgnore
    private String tableNameOfMapping;//对应的bspUser和电桩关系表名称
    @JsonIgnore
    private Long userId;//当前用户的userId
    @JsonIgnore
    private Long powerStationId;//电站id
    @JsonIgnore
    private Pager pager;
    @JsonIgnore
    protected Long pageNo;

    private Integer chorOrgno; //     `chOr_OrgNo` int(11) NOT NULL DEFAULT '1000' COMMENT '充电伙伴标识，1000:爱充自己，其它',  cpyNumber
    private String cvVinCode; // `cv_vin_code` varchar(20) NOT NULL DEFAULT '' COMMENT 'VIN', vinCode
    private String electricPileCode; // 桩体编号 //            "electricPileCode": "222222222",
    private String chorCode; // `chOr_Code` varchar(50) NOT NULL DEFAULT '' COMMENT '充电订单编号', orderCode
    private String chorCodeNew; //
    private String chorParterExtradata; // `chor_parter_extradata` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方流水号或者会话ID',
    private Integer chorChargingstatus;  //`chOr_ChargingStatus` int(11) NOT NULL DEFAULT '0' COMMENT '1：待支付 ,2：支付成功 ,3: 完成操作,4: 异常订单,5:临时结算', orderStatus
    private String chorParterUserLogo;//  `chor_parter_user_logo` varchar(30) NOT NULL DEFAULT '' COMMENT '第三方合作方用户标识', parterUserLogo

    @JsonInclude
    private List<Integer> cpyNumberList;//公司标示
    @JsonInclude
    private List<Long> ucIdList;//电卡主键list
    @JsonInclude
    private List<Long> userIdList;//用户主键list
    @JsonInclude
    private List<String> epCodeList;//电桩编号list
    @JsonInclude
    private List<Long> ownUserIdList;//内部人员id
    @JsonInclude
    private Integer orderFilter;//1.不过滤 2.过滤(a.充电量小于1度的订单 b.卖桩项目产生的订单；c.内部人员充电产生的订单；d.被标记为异常状态的订单)
    @JsonInclude
    private Integer queryDateType;//0:按开始时间查询  1：按结束时间查询            "queryDateType": 0,

    //------------------begin----
    @Id
    private String pkChargingorder;//`pk_ChargingOrder` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键', //        "orderCode": "11111111",

    private String chorPilenumber; // `chOr_PileNumber` varchar(50) NOT NULL COMMENT '桩体编号',

    private Integer chorUserid; // `chOr_UserId` int(11) NOT NULL COMMENT '用户ID(企业ID)',

    private Integer chorType; // `chOr_Type` int(11) NOT NULL COMMENT '1：普通用户 2：个体商家用户 3：纯商家用户',

    private BigDecimal chorMoeny; // `chOr_Moeny` decimal(10,4) DEFAULT NULL COMMENT '总(收益)金额',

    private BigDecimal chorTippower;  //`chOr_tipPower` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '尖时段用电度数',

    private BigDecimal chorPeakpower; // `chOr_peakPower` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '峰时段用电度数',

    private BigDecimal chorUsualpower; // `chOr_usualPower` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '平时段用电度数',

    private BigDecimal chorValleypower; // `chOr_valleyPower` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '谷时段用电度数',

    private BigDecimal chorQuantityelectricity;  //`chOr_QuantityElectricity` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '总电量',

    private String chorTimequantum;  //`chOr_TimeQuantum` varchar(50) NOT NULL COMMENT '12:00-13:00 时间段',

    private Short chorMuzzle; // `chOr_Muzzle` smallint(50) NOT NULL DEFAULT '0' COMMENT '枪口编号(1,2,3……)',


    private Integer chorTranstype;  //`chOr_TransType` int(11) NOT NULL COMMENT '0:正常;1:断网上传;2:离线充上传',
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat
    private Date chorCreatedate;  //`chOr_Createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date chorUpdatedate;  //`chOr_Updatedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    private String chorTransactionnumber; //   `chOr_TransactionNumber` varchar(50) NOT NULL COMMENT '交易流水号',

    private BigDecimal chorChargemoney; // `chOr_ChargeMoney` decimal(10,4) DEFAULT NULL COMMENT '充电金额',

    private BigDecimal chorServicemoney;//  `chOr_ServiceMoney` decimal(10,4) DEFAULT NULL COMMENT '充电服务费金额',

    private String beginChargeTime;//  `begin_charge_time` varchar(32) NOT NULL COMMENT '充电开始时间',

    private String endChargeTime; // `end_charge_time` varchar(32) NOT NULL COMMENT '充电结束时间',

    private Integer startSoc;//  `start_soc` int(11) NOT NULL DEFAULT '0' COMMENT '开始SOC(电池容量)',

    private Integer endSoc; // `end_soc` int(11) NOT NULL DEFAULT '0' COMMENT '结束SOC(电池容量)',

    private Short chorUserorigin;//  `chOr_UserOrigin` smallint(4) NOT NULL DEFAULT '0' COMMENT '用户来源 1:富士康; 2:吉利; 3:绿地; 4:浙誉; 5:车纷享; 以后根据情况再扩展或修改',


    private Integer pkUsercard; // `pk_UserCard` int(11) NOT NULL DEFAULT '0' COMMENT '卡主键',


    private BigDecimal chorCouponmoney; // `chOr_CouponMoney` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券抵扣金额',

    private Integer pkCoupon; // `pk_Coupon` int(11) NOT NULL DEFAULT '0' COMMENT '第三方优惠信息主键',

    private Short chorThirdType;//  `chOr_Third_Type` smallint(1) unsigned NOT NULL DEFAULT '0' COMMENT ' 默认 0 无优惠  优惠券 1 VIN码 2',

    private Long billAccountId;//  `bill_account_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '科目ID',


    private Integer reconciliationStatus; // `reconciliation_id` int(11) NOT NULL DEFAULT '0' COMMENT '第三方对账id',

    private Integer reconciliationId; // `reconciliation_status` int(4) NOT NULL DEFAULT '0' COMMENT '0:未对账 1：订单正常，2：订单异常',

    private String beginHh;//#{item.beginHh},  begin_hh,
    private String endHh;//#{item.endHh},end_hh,
    private String beginChargeDay;//#{item.beginChargeDay},begin_charge_day,

    //----------record-------------
    private Integer chreElectricpileid;//  `chRe_ElectricPileID` int(11) NOT NULL COMMENT '电桩ID(tbl_ElectricPile中获取)',

    private Integer chreChargingmethod;//   `chRe_ChargingMethod` int(11) NOT NULL COMMENT '1:自动充满 2:按金额充 3:按里程4:按度数',

    private Byte chreStatus;//  `chRe_Status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '充电状态,0:开始充电;1:结束充电;2:接受到充电客户的命令;3:充电失败,4:等待插枪;5:临时结算;6:结算;7:结算完成;',

    private BigDecimal chreJprice;//  `chRe_JPrice` decimal(8,4) DEFAULT NULL,

    private BigDecimal chreFprice;//  `chRe_FPrice` decimal(8,4) DEFAULT NULL,

    private BigDecimal chrePprice;//  `chRe_PPrice` decimal(8,4) DEFAULT NULL,

    private BigDecimal chreGprice;//  `chRe_GPrice` decimal(8,4) DEFAULT NULL,

    private BigDecimal chreFrozenamt;//    `chRe_FrozenAmt` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '预冻结金额',

    private BigDecimal chreRealamt;//  `chRe_RealAmt` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '实际金额(服务器重启后记录)',

    private Integer chrePaymode;//    `chRe_PayMode` int(11) NOT NULL DEFAULT '1' COMMENT '付费标识，1:先付费;2:后付费',

    private BigDecimal chreServicecharge;//      `chRe_ServiceCharge` decimal(8,4) DEFAULT NULL  COMMENT '服务费单价',

    private BigDecimal chreJmoney;//        `chRe_JMoney` decimal(8,4) DEFAULT NULL COMMENT '尖金额=单价x度数',

    private BigDecimal chreFmoney;//  `chRe_FMoney` decimal(8,4) DEFAULT NULL COMMENT '峰金额=单价x度数',

    private BigDecimal chrePmoney;//  `chRe_PMoney` decimal(8,4) DEFAULT NULL COMMENT '平金额=单价x度数',

    private BigDecimal chreGmoney;//  `chRe_GMoney` decimal(8,4) DEFAULT NULL COMMENT '谷金额=单价x度数',

    private Long accountId;//    `account_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '帐户ID',

    private BigDecimal chreTipprice;//      `chRe_TipPrice` decimal(8,4) DEFAULT NULL COMMENT '个性化尖电价',

    private BigDecimal chrePeakprice;//  `chRe_PeakPrice` decimal(8,4) DEFAULT NULL COMMENT '个性化峰电价',

    private BigDecimal chreFlatprice;//  `chRe_FlatPrice` decimal(8,4) DEFAULT NULL COMMENT '个性化平电价',

    private BigDecimal chreValleyprice;//  `chRe_valleyPrice` decimal(8,4) DEFAULT NULL COMMENT '个性化谷电价',

    private BigDecimal chreTipmoney;//  `chRe_TipMoney` decimal(8,4) DEFAULT NULL COMMENT '个性化尖服务费',

    private BigDecimal chrePeakmoney;//  `chRe_PeakMoney` decimal(8,4) DEFAULT NULL COMMENT '个性化峰服务费',

    private BigDecimal chreFlatmoney;//  `chRe_FlatMoney` decimal(8,4) DEFAULT NULL COMMENT '个性化平服务费',

    private BigDecimal chreValleymoney;//  `chRe_valleyMoney` decimal(8,4) DEFAULT NULL COMMENT '个性化谷服务费',

}