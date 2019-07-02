package com.wanma.demoes.es001;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Student {

    private int id;
    private String name;
    private int age;

    @JsonFormat
    private Date birthDay;
}
