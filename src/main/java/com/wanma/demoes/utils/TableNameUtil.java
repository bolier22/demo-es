package com.wanma.demoes.utils;

public class TableNameUtil {
    /**
     * 主任务表名
     * @param mercSeq
     * @return
     */
    public static String getTaskInfo(int mercSeq){
        //主任务 tbl_call_task_info_商户编号  每个商户一张表
        String taskInfoName = "tbl_call_task_info_"+mercSeq;
        return taskInfoName;
    }

    /**
     * detail表名
     * @param mercSeq
     * @param taskSeq
     * @return
     */
    public static String getDetail(int mercSeq,int taskSeq){
        //detail 分二十张表  tbl_detail_info_商户编号_(0-19)  ，主任务主键 %20 路由到表
        int routeDetail = taskSeq % 20;
        String detailName = "tbl_detail_info_"+mercSeq+"_"+routeDetail;
        return detailName;
    }

    /**
     * 私海表名
     * @return
     */
    public static String getPrivateCustomer(int mercSeq){
        //私海一张表 tbl_customer_private_商户编号
        return "tbl_customer_private_"+mercSeq;
    }

    /**
     * 话术表名
     * @return
     */
    public static String getStatement(int mercSeq){
        //7.4、 tbl_speech_statement_商户编号 (100000001)
        return "tbl_merchant_speech_statement_"+mercSeq;
    }
    /**
     * 关系表名
     * @return
     */
    public static String getRelation(int mercSeq){
        //7.2、 tbl_speech_relation_商户编号 (100000001)
        return "tbl_merchant_speech_relation_"+mercSeq;
    }
    /**
     * 纠错表名
     * @return
     */
    public static String getError(int mercSeq){
        //7.2、 tbl_error_template_商户编号 (100000001)
        return "tbl_error_template_"+mercSeq;
    }
    /**
     * 意向模板表名
     * @return
     */
    public static String getGrade(int mercSeq){
        // tbl_speech_grade_商户编号 (100000001)
        return "tbl_merchant_speech_grade_"+mercSeq;
    }

    /**
     * 允许拨打时间段表
     * @return
     */
    public static String getAllowTime(int mercSeq){
        // tbl_allow_conversation_time_10000001
        return "tbl_allow_conversation_time_"+mercSeq;
    }

}
