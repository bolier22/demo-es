package com.wanma.demoes.es001;

import com.google.gson.Gson;

import com.wanma.demoes.entity.TblUserAdmin;
import com.wanma.demoes.service.TblUserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RestController
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    private EmployeeRepository er;

    @Autowired
    PostRepository postRepository;

    @Autowired
    Init init;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    TblUserAdminService tblUserAdminService;

    @RequestMapping("/query/list/{id}")
    public List<TblUserAdmin> queryList(@PathVariable("id")Integer id){
        tblUserAdminService.totle(1,1);

//        if(id == 1){
//            return tblUserAdminService.getList(1,1);
//        }else if(id == 2){
//            return tblUserAdminService.getList2(1,1);
//        }else{
//            return tblUserAdminService.getList3(1,1);
//        }
        return tblUserAdminService.getList3(1,1);

    }



    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     * http://localhost:9123/es/post/singlePhraseMatch?content=落日熔金&size=20
     */
    @RequestMapping("/singlePhraseMatch")
    public Object singlePhraseMatch(String content, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery("content", content)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }


    /**
     * 单字段对某字符串模糊查询
     * http://localhost:9123/es/post/singleMatch?content=落日熔金&size=20
     * http://localhost:9123/es/post/singleMatch?userId=1&size=20
     */
    @RequestMapping("/post/singleMatch")
    public Object singleMatch(String content, Integer userId, @PageableDefault Pageable pageable) {
      //  SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("content", content)).withPageable(pageable).build();
      SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("userId", userId)).withPageable(pageable).build();
      return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    /**
     * 单字符串模糊查询，默认排序。将从所有字段中查找包含传来的word分词后字符串的数据集
     * http://localhost:9123/es/post/singleWord?word=浣溪沙&size=20
     */
    @RequestMapping("/post/singleWord")
//    public Object singleTitle(String word, @PageableDefault Pageable pageable) { //未排序
    public Object singlePost(String word, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable) {//排序
        //使用queryStringQuery完成单字符串查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    //@PostConstruct
    @RequestMapping("/post/init")
    public void init() {
        //只初始化一次
        Iterable<Post> posts = postRepository.findAll();
        if (posts.iterator().hasNext()) {
            return;
        }
        for (int i = 0; i < 40; i++) {
            Post post = new Post();
            post.setTitle(init.getTitle().get(i));
            post.setContent(init.getContent().get(i));
            post.setWeight(i);
            post.setUserId(i % 10);
            postRepository.save(post);
        }

//        long b = System.currentTimeMillis();
//        List postList = assembleTestData();
//        System.out.println("=======assembleTestData() ======="+(System.currentTimeMillis()-b));
//        bulkIndex(postList);
//        System.out.println("=======init() ======="+(System.currentTimeMillis()-b));

    }


    private List assembleTestData() {
        List postList = new ArrayList();
        for (int i = 0; i < 40000; i++) {
            Post post = new Post();
            post.setTitle(init.getTitle().get(i%40));
            post.setContent(init.getContent().get(i%40));
            post.setWeight(i);
            post.setUserId(i % 10);
            postList.add(post);
        }
        return postList;
    }


    public void bulkIndex(List<Post> personList) {
        String POST_INDEX_NAME = "post_index";
        String POST_INDEX_TYPE = "post_type";
        int counter = 0;
        try {
            if (!elasticsearchTemplate.indexExists(POST_INDEX_NAME)) {
                elasticsearchTemplate.createIndex(POST_INDEX_TYPE);
            }
            List<IndexQuery> queries = new ArrayList<>();
            for (Post person : personList) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(person.getId() + "");
                indexQuery.setObject(person);//indexQuery.setSource(gson.toJson(car));
                indexQuery.setIndexName(POST_INDEX_NAME);
                indexQuery.setType(POST_INDEX_TYPE);

                //上面的那几步也可以使用IndexQueryBuilder来构建
                //IndexQuery index = new IndexQueryBuilder().withId(person.getId() + "").withObject(person).build();

                queries.add(indexQuery);
                if (counter % 500 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                    System.out.println("bulkIndex counter : " + counter);
                }
                counter++;
            }
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
            elasticsearchTemplate.refresh(POST_INDEX_NAME);

            System.out.println("bulkIndex completed.");
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
    }

    //增加
    @RequestMapping("/add/{id}")
    public String add(@PathVariable("id")String id){

        Employee employee=new Employee();
        employee.setId(id);
        employee.setFirstName("2345");
        employee.setLastName("李先刚");
        employee.setAge(26);
        employee.setAbout("");
        employee.setCreateTime(new Date());
        er.save(employee);

        System.err.println("add a obj");

        return "success";
    }

    //删除
    @RequestMapping("/delete")
    public String delete(){
        Employee employee=new Employee();
        employee.setId("1");
        er.delete(employee);

        return "success";
    }

    //局部更新
    @RequestMapping("/update")
    public String update(){

        Employee employee=er.queryEmployeeById("1");
//        employee.setFirstName("哈哈");
        er.save(employee);

        System.err.println("update a obj");

        return "success";
    }

    //查询
    @RequestMapping("/query/{id}")
    public Employee query(@PathVariable("id")String id){

        Employee accountInfo=er.queryEmployeeById(id);
        System.err.println(new Gson().toJson(accountInfo));

        return accountInfo;
    }

}
