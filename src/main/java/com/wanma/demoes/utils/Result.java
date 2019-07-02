package com.wanma.demoes.utils;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {


    /**0 :success 1 :fail  3:
     * 结果信息编号，对应字典
     */
    private int status;

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


}
