package com.wanma.demoes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingParam {
        private String dateStr;//查询的表的日期
        private Long count;//查询的条数
        private int index;//用户排序

        private String startQueryTime;
        private String endQueryTime;

    @Override
    public String toString() {
        return "PagingParam{" +
                "dateStr='" + dateStr + '\'' +
                ", count=" + count +
                ", index=" + index +
                ", startQueryTime='" + startQueryTime + '\'' +
                ", endQueryTime='" + endQueryTime + '\'' +
                '}';
    }
}