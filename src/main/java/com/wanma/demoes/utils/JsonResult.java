package com.wanma.demoes.utils;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JsonResult implements Serializable {

    /**0 :success 1 :fail  3:
     * 结果信息编号，对应字典
     */
    private String status;

    /**
     * 返回的消息
     */
    private String errorMsg;
    /**
     * token 令牌
     */
    private String token;
    /**
     * 返回数据
     */
    private  Object dataInfo;

    private Pager pager;

    public void initError(String status,String errorMsg){
        this.status = status;
        this.errorMsg = errorMsg;
    }

}
