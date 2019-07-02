package com.wanma.demoes.es001;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "stat_index",type = "clazz", shards = 1,replicas = 0, refreshInterval = "-1")
@Data
public class Clazz {
    private int id;
    private String name;
    private String addr;
    private int sutdentCount;
    private List<Student> studentList;
}
