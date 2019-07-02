package com.wanma.demoes.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Document(indexName = "poem",type = "poemarticle")
public class Poem {
    @Id
    private Integer id;
    private String title;
    private String content;

}
