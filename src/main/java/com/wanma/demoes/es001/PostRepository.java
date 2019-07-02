package com.wanma.demoes.es001;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface PostRepository extends ElasticsearchRepository<Post, String> {

}
