package com.wanma.demoes.controller;//package com.wanma.demoes.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wanma.demoes.config.ResultCodeConstants;
import com.wanma.demoes.entity.OrderQueryTotalInfo;
import com.wanma.demoes.entity.PagingParam;
import com.wanma.demoes.entity.TblChargingorderRecord;
import com.wanma.demoes.es001.Clazz;
import com.wanma.demoes.es001.Student;
import com.wanma.demoes.repository.ChargingorderRecordRepository;
import com.wanma.demoes.service.TblChargingorderRecordService;
import com.wanma.demoes.utils.JsonResult;
import com.wanma.demoes.utils.Pager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.el.parser.ParseException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * 登录
 */
@RestController
@RequestMapping("/orderRecord")
public class OrderRecordController{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HttpServletRequest request;

    @Autowired
    TblChargingorderRecordService tblChargingorderRecordService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Autowired
    private ChargingorderRecordRepository chargingorderRecordRepository;

    /**
     * 订单列表信息查询
     * @param requestCode
     * @param orderRecord 必填项： beginChargeTimeStart beginChargeTimeEnd userId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getOrderRecordList/{requestCode}")
    public JsonResult getOrderRecordList(@PathVariable(name = "requestCode") String requestCode, @RequestBody TblChargingorderRecord orderRecord){// 使用 @RequestBody注解 postman 采用body raw -json 传递参数 eg: {"beginChargeTimeStart": "2019-01-15","beginChargeTimeEnd": "2019-02-05","userId": "94124"}
//    public JsonResult getOrderList(@PathVariable(name = "requestCode") String requestCode, OrderData orderData) throws Exception { //不使用 @RequestBody  注解 postman采用 body form-data 参数传递
        JsonResult result = new JsonResult();


        try {
            Pager pager = orderRecord.getPager();

            List<PagingParam> getPagingParamList = tblChargingorderRecordService.selectOrderDataCountList(orderRecord);
            long total = getPagingParamList.stream().map(PagingParam::getCount).reduce(Long::sum).get();
            if (total <= pager.getOffset()) {
                pager.setPageNo(1L);
            }
            pager.setPageNo(orderRecord.getPageNo());
            pager.setTotal(total);

            orderRecord.setPager(pager);
            List<TblChargingorderRecord> orderDataList = tblChargingorderRecordService.selectOrderDataList(getPagingParamList,orderRecord);

            result.setDataInfo(orderDataList);
            result.setPager(pager);
            result.initError(ResultCodeConstants.CODE_SUCCESS,ResultCodeConstants.ERROR_MSG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultCodeConstants.CODE_EXCEPTION,ResultCodeConstants.ERROR_MSG_QUERY);
            return result;
        }
        return result;
    }


    /**
     * 单字段对某字符串模糊查询
     * http://localhost:9123/es/post/singleMatch?content=落日熔金&size=20
     * http://localhost:9123/demo_es/orderRecord/post/singleMatch?userId=1&size=20
     * http://localhost:9123/demo_es/orderRecord/getOrderRecordList/100003
     * http://localhost:9123/demo_es/orderRecord/post/singleMatch?userId=900996&size=20
     */
    @RequestMapping("/post/singleMatch")
    public Object singleMatch(String content, Long userId, @PageableDefault Pageable pageable) {
        try{
            String ds = elasticsearchTemplate.delete(TblChargingorderRecord.class,"809837");
            //  SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("content", content)).withPageable(pageable).build();
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("pkChargingorder", userId)).withPageable(pageable).build();
            return elasticsearchTemplate.queryForList(searchQuery, TblChargingorderRecord.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "查询失败了";
    }




    /**
     {
     "chorCode": "",
     "electricPileCode": "",
     "userIdList": [1741],
     "ucIdList": [],
     "cpyNumberList": [],
     "cvVinCode": "",
     "beginChargeTimeStart": "2019-04-01",
     "beginChargeTimeEnd": "2019-04-17",
     "queryDateType": 0,
     "provinceCode": "",
     "cityCode": "",
     "areaCode": "",
     "normPlateNum": "",
     "chorParterUserLogo": "",
     "chorParterExtradata": "",
     "epCodeList": [],
     "orderFilter": 1,
     "pageNo": 1,
     "pager": {
     "status": "",
     "keywords": "",
     "pageNo": 1,
     "pageSize": 20,
     "pageTotal": 0,
     "total": 0,
     "offset": 0,
     "offsetEx": 0
     }
     }

     */
    @RequestMapping("/initOrderRecordList/{requestCode}")
    public JsonResult initOrderRecordList(@PathVariable(name = "requestCode") String requestCode, @RequestBody TblChargingorderRecord orderRecord){// 使用 @RequestBody注解 postman 采用body raw -json 传递参数 eg: {"beginChargeTimeStart": "2019-01-15","beginChargeTimeEnd": "2019-02-05","userId": "94124"}
        JsonResult result = new JsonResult();
        try {
            Pager pager = orderRecord.getPager();

            List<PagingParam> getPagingParamList = tblChargingorderRecordService.selectOrderDataCountList(orderRecord);
            long total = getPagingParamList.stream().map(PagingParam::getCount).reduce(Long::sum).get();
            if (total <= pager.getOffset()) {
                pager.setPageNo(1L);
            }
            pager.setPageNo(orderRecord.getPageNo());
            pager.setTotal(total);

            orderRecord.setPager(pager);
            for(int pageNo=1;pageNo<=pager.getPageTotal();pageNo++){
                pager.setPageNo((long) pageNo);
                List<TblChargingorderRecord> orderDataList = tblChargingorderRecordService.selectOrderDataList(getPagingParamList,orderRecord);
                orderDataListBulkIndex(orderDataList);
            }
            result.initError(ResultCodeConstants.CODE_SUCCESS,ResultCodeConstants.ERROR_MSG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultCodeConstants.CODE_EXCEPTION,ResultCodeConstants.ERROR_MSG_QUERY);
            return result;
        }
        return result;
    }

    protected Document getDocument(Object t) {
        Document annotation = t.getClass().getAnnotation(Document.class);
        return annotation;
    }



    @RequestMapping("/saveChargingorderRecord/{requestCode}")
    public void saveChargingorderRecord(){
        try{
            List<TblChargingorderRecord> orderDataList = tblChargingorderRecordService.selectOrderDataListNew();
            Gson gson = new Gson();
            orderDataList.forEach(orderRecord->{
                orderRecord = chargingorderRecordRepository.save(orderRecord);
                System.out.println(orderRecord.getPkChargingorder()+"-->");
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping("/queryChargingorderRecordByPkId/{pkChargingorder}")
    public JsonResult queryChargingorderRecordByPkId(@PathVariable(name = "pkChargingorder") String pkChargingorder){
        JsonResult result = new JsonResult();
        result.setDataInfo(chargingorderRecordRepository.findBypkChargingorder(pkChargingorder));
        return result;

    }

    /*****************************************华丽丽的分割线********以下部分使用的是******elasticsearchTemplate调用方法****************************************/

    /**
     * 使用  elasticsearchTemplate 进行批量修改
     * @param requestCode
     * @return
     */
    @RequestMapping("/bulkIndexUpdateOrderRecord/{requestCode}")
    public JsonResult bulkIndexUpdateOrderRecord(@PathVariable(name = "requestCode") String requestCode){// 使用 @RequestBody注解 postman 采用body raw -json 传递参数 eg: {"beginChargeTimeStart": "2019-01-15","beginChargeTimeEnd": "2019-02-05","userId": "94124"}
        JsonResult result = new JsonResult();
        try {
            List<TblChargingorderRecord> orderDataList = tblChargingorderRecordService.selectOrderDataListNew();
            List<UpdateQuery> bulkIndex = new ArrayList<>();
            Gson gson = new Gson();
            for (TblChargingorderRecord orderRecord : orderDataList) {
                Document document = getDocument(orderRecord);
                UpdateQuery updateQuery = new UpdateQuery();
                updateQuery.setIndexName(document.indexName());
                updateQuery.setType(document.type());
                updateQuery.setId(orderRecord.getPkChargingorder().toString());
//                updateQuery.setUpdateRequest(new UpdateRequest(updateQuery.getIndexName(), updateQuery.getType(), updateQuery.getId()).doc(JSONObject.toJSONString(t, SerializerFeature.WriteMapNullValue)));
                Map<String,Object> map = gson.fromJson(gson.toJson(orderRecord), new TypeToken<HashMap<String,Object>>(){}.getType());
//                updateQuery.setUpdateRequest(new UpdateRequest(updateQuery.getIndexName(), updateQuery.getType(), updateQuery.getId()).doc(map));
                IndexRequest indexQuery = new IndexRequest();
                indexQuery.source(map);
                updateQuery.setUpdateRequest(new UpdateRequest(updateQuery.getIndexName(), updateQuery.getType(), updateQuery.getId()).doc(indexQuery));
                updateQuery.setDoUpsert(true);
                updateQuery.setClazz(orderRecord.getClass());
                bulkIndex.add(updateQuery);
            }

            elasticsearchTemplate.bulkUpdate(bulkIndex);
            result.initError(ResultCodeConstants.CODE_SUCCESS,ResultCodeConstants.ERROR_MSG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultCodeConstants.CODE_EXCEPTION,ResultCodeConstants.ERROR_MSG_QUERY);
            return result;
        }
        return result;
    }


//    {
//        "query": {
//        "bool": {
//            "must": [{
//                "term": {
//                    "state": 1
//                }
//            }, {
//                "term": {
//                    "appId": "99999999-9999-9999-9999-999999999999"
//                }
//            }, {
//                "bool": {
//                    "should": [{
//                        "match": {
//                            "payPlatform": {
//                                "query": "TL",
//                                        "type": "boolean"
//                            }
//                        }
//                    }, {
//                        "match": {
//                            "payPlatform": {
//                                "query": "TL_WX_APP",
//                                        "type": "boolean"
//                            }
//                        }
//                    }, {
//                        "match": {
//                            "payPlatform": {
//                                "query": "TL_ALI",
//                                        "type": "boolean"
//                            }
//                        }
//                    }, {
//                        "match": {
//                            "payPlatform": {
//                                "query": "TL_WX_JS",
//                                        "type": "boolean"
//                            }
//                        }
//                    }, {
//                        "match": {
//                            "payPlatform": {
//                                "query": "TL_APP",
//                                        "type": "boolean"
//                            }
//                        }
//                    }, {
//                        "match": {
//                            "payPlatform": {
//                                "query": "TL_WX_H5",
//                                        "type": "boolean"
//                            }
//                        }
//                    }, {
//                        "match": {
//                            "payPlatform": {
//                                "query": "WX_GWORLD",
//                                        "type": "boolean"
//                            }
//                        }
//                    }, {
//                        "match": {
//                            "payPlatform": {
//                                "query": "ALI_GWORLD",
//                                        "type": "boolean"
//                            }
//                        }
//                    }]
//                }
//            }]
//        }
//    }
//    }
//对应使用Java查询
//    public Sum getEsPaySummaryInfo(String appId) throws Exception {
//        SumBuilder sb = AggregationBuilders.sum("tpPrice").field("payPrice");
//        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
//        bqb.must(QueryBuilders.termQuery("state",SuperAppConstant.PAY_STATUS_SUCCESS));
//        bqb.must(QueryBuilders.termQuery("appId",appId));
//        bqb.must(QueryBuilders.boolQuery()
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_TL))
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_TL_WX_APP))
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_TL_ALI))
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_TL_WX_JS))
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_TL_APP))
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_TL_WX_H5))
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_WX_GWORLD))
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_ALI_GWORLD))
//        );
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(bqb).withIndices(SuperAppConstant.ES_INDEX_NAME).withTypes(SuperAppConstant.ES_PAY_TYPE)
//                .withSearchType(SearchType.DEFAULT)
//                .addAggregation(sb).build();
//        Aggregations aggregations = esTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
//            @Override
//            public Aggregations extract(SearchResponse response) {
//                return response.getAggregations();
//            }
//        });
//        Sum _sum = aggregations.get("tpPrice");
//        if(_sum != null){
//            logger.info("sum="+_sum.getValue());
//        }
//        return _sum;
//    }
    //聚合查询，分组求和（对应sql的group by）
//    public Map getEsCashSummaryInfo(String appId) throws Exception {
//        Map map = new HashMap<>();
//        TermsBuilder tb = AggregationBuilders.terms("cash").field("appId");//appId 是分组字段名，cash是查询结果的别名
//        SumBuilder sb = AggregationBuilders.sum("amount").field("paid");//paid是求和字段名称，amount是结果别名
//        tb.subAggregation(sb);
//        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
//        bqb.mustNot(QueryBuilders.termQuery("settled",SuperAppConstant.CASH_STATUS_CANCLED));
//        bqb.must(QueryBuilders.termQuery("appId",appId));
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(bqb).withIndices(SuperAppConstant.ES_INDEX_NAME).withTypes(SuperAppConstant.ES_CASH_TYPE)
//                .withSearchType(SearchType.DEFAULT)
//                .addAggregation(tb)
//                .build();
//        Aggregations aggregations = esTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
//            @Override
//            public Aggregations extract(SearchResponse response) {
//                return response.getAggregations();
//            }
//        });
//        Terms term = aggregations.get("cash");//获取结果后进行解析
//        if(term.getBuckets().size()>0){
//            for (Bucket bk : term.getBuckets()) {
//                long count = bk.getDocCount();
//                //得到所有子聚合
//                Map subaggmap = bk.getAggregations().asMap();
//                //sum值获取方法
//                double amount = ((InternalSum) subaggmap.get("amount")).getValue();
//                map.put("count", count);
//                map.put("amount", amount);
//            }
//            return map;
//        }else{
//            return null;
//        }
//    }



//    //对应使用Java查询
//    public void getEsPaySummaryInfo(String appId) throws Exception {
//        SumBuilder sb = AggregationBuilders.sum("tpPrice").field("payPrice");
//        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
//        bqb.must(QueryBuilders.termQuery("state",SuperAppConstant.PAY_STATUS_SUCCESS));
//        bqb.must(QueryBuilders.termQuery("appId",appId));
//        bqb.must(QueryBuilders.boolQuery()
//                .should(QueryBuilders.matchQuery("payPlatform", SuperAppConstant.PAY_PLATFORM_TL))
//        );
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(bqb).withIndices(SuperAppConstant.ES_INDEX_NAME).withTypes(SuperAppConstant.ES_PAY_TYPE)
//                .withSearchType(SearchType.DEFAULT)
//                .addAggregation(sb).build();
//        Aggregations aggregations = esTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
//            @Override
//            public Aggregations extract(SearchResponse response) {
//                return response.getAggregations();
//            }
//        });
//        Sum _sum = aggregations.get("tpPrice");
//        if(_sum != null){
//            logger.info("sum="+_sum.getValue());
//        }
//        return _sum;
//    }



    /**
     * 使用  elasticsearchTemplate 进行批量新增 http://www.cnblogs.com/double-yuan/p/9798103.html 父子关联例子
     * @param requestCode
     * @return
     */
    @RequestMapping("/bulkIndexModifyOrderRecord/{requestCode}")
    public JsonResult bulkIndexModifyOrderRecord(@PathVariable(name = "requestCode") String requestCode){// 使用 @RequestBody注解 postman 采用body raw -json 传递参数 eg: {"beginChargeTimeStart": "2019-01-15","beginChargeTimeEnd": "2019-02-05","userId": "94124"}
        JsonResult result = new JsonResult();
        try {

//            SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                    .withQuery(matchAllQuery())
//                    .withIndices("test-index")
//                    .withTypes("test-type")
//                    .withPageable(new PageRequest(0,1))
//                    .build();

            List<TblChargingorderRecord> orderDataList = tblChargingorderRecordService.selectOrderDataListNew();
            for (TblChargingorderRecord orderRecord : orderDataList) {
                String s = addEsCashInfo(orderRecord);
                System.out.println("---->"+s);
            }
            result.initError(ResultCodeConstants.CODE_SUCCESS,ResultCodeConstants.ERROR_MSG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultCodeConstants.CODE_EXCEPTION,ResultCodeConstants.ERROR_MSG_QUERY);
            return result;
        }
        return result;
    }

    //增加或者更新
    public String addEsCashInfo(TblChargingorderRecord orderRecord){
        try{
            Document document = getDocument(orderRecord);
            String indexName = document.indexName();
            String type = document.type();
            IndexQuery indexQuery = new IndexQueryBuilder().withIndexName(indexName)
                    .withType(type).withId(orderRecord.getPkChargingorder()).withObject(orderRecord).build();
            return elasticsearchTemplate.index(indexQuery);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "增加或者更新失败";
    }


    /**
     * 使用  elasticsearchTemplate 进行批量新增(存在则修改，不存在则插入)
     * @param requestCode
     * @return
     */
    @RequestMapping("/bulkIndexAddOrderRecord/{requestCode}")
    public JsonResult bulkIndexAddOrderRecord(@PathVariable(name = "requestCode") String requestCode){// 使用 @RequestBody注解 postman 采用body raw -json 传递参数 eg: {"beginChargeTimeStart": "2019-01-15","beginChargeTimeEnd": "2019-02-05","userId": "94124"}
        JsonResult result = new JsonResult();
        try {
            List<TblChargingorderRecord> orderDataList = tblChargingorderRecordService.selectOrderDataListNew();
            orderDataListBulkIndex(orderDataList);
            result.initError(ResultCodeConstants.CODE_SUCCESS,ResultCodeConstants.ERROR_MSG_SUCCESS);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultCodeConstants.CODE_EXCEPTION,ResultCodeConstants.ERROR_MSG_QUERY);
            return result;
        }
        return result;
    }

    public void orderDataListBulkIndex(List<TblChargingorderRecord> orderDataList) {
        int counter = 0;
        try {
            Document document = getDocument(new TblChargingorderRecord());
            String indexName = document.indexName();
            if (!elasticsearchTemplate.indexExists(indexName)) {//ORDER_INDEX_NAME
                elasticsearchTemplate.createIndex(indexName);//ORDER_INDEX_TYPE
            }
            List<IndexQuery> queries = new ArrayList<>();
            for (TblChargingorderRecord orderRecord : orderDataList) {
//                IndexQuery indexQuery = new IndexQuery();
//                indexQuery.setId(orderRecord.getPkChargingorder() + "");
//                indexQuery.setObject(orderRecord);
//                indexQuery.setIndexName(ORDER_INDEX_NAME);
//                indexQuery.setType(ORDER_INDEX_TYPE);
                //上面的那几步也可以使用IndexQueryBuilder来构建
                IndexQuery indexQuery = new IndexQueryBuilder().withId(orderRecord.getPkChargingorder() + "").withObject(orderRecord).build();
                queries.add(indexQuery);
                counter++;
                if (counter % 30 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                }
            }
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
    }


    public void getTblChargingorderRecordList(OrderQueryTotalInfo orderQueryTotalInfo, Pageable pageable){

        NativeSearchQueryBuilder searchQuery =new NativeSearchQueryBuilder().withPageable(pageable);
//        searchQuery.withQuery(matchQuery("pkChargingorder", orderQueryTotalInfo.getPkChargingorder()));

        //or
//        BoolQueryBuilder pinyinQuery = QueryBuilders.boolQuery();
//        pinyinQuery.should(QueryBuilders.matchQuery("pkChargingorder", pinyin));

        //查询条件 between
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        RangeQueryBuilder rangeDateQuery = QueryBuilders.rangeQuery("beginChargeDay").gte(orderQueryTotalInfo.getBeginChargeTimeStart())
                .lte(orderQueryTotalInfo.getBeginChargeTimeEnd());
        queryBuilder.must(rangeDateQuery);
        searchQuery.withQuery(queryBuilder);

        AggregatedPage<TblChargingorderRecord> bookPage = elasticsearchTemplate.queryForPage(searchQuery.build(), TblChargingorderRecord.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<TblChargingorderRecord> orderRecordVoList = new ArrayList<>();
                //命中记录
                SearchHits hits = response.getHits();
                Gson gson = new Gson();
                for (SearchHit hit : hits){
                    if (hits.totalHits <= 0){
                        return null;
                    }

                    String json  = hit.getSourceAsString();
                    orderRecordVoList.add(gson.fromJson(json, new TypeToken<TblChargingorderRecord>() {}.getType()));

//                    TblChargingorderRecord orderRecord = new TblChargingorderRecord();
//                    Map<String, Object> getSourceAsMap = hit.getSourceAsMap();
//                    orderRecord.setPkChargingorder(hit.getId());
//                    orderRecord.setChorOrgno(Integer.valueOf(getSourceAsMap.get("chorOrgno").toString())); //     `chOr_OrgNo` int(11) NOT NULL DEFAULT '1000' COMMENT '充电伙伴标识，1000:爱充自己，其它',  cpyNumber
//                    orderRecord.setCvVinCode(String.valueOf(getSourceAsMap.get("cvVinCode"))); // `cv_vin_code` varchar(20) NOT NULL DEFAULT '' COMMENT 'VIN', vinCode
//                    orderRecord.setElectricPileCode(String.valueOf(getSourceAsMap.get("electricPileCode"))); // 桩体编号 //            "electricPileCode": "222222222",
//                    orderRecord.setChorCode(String.valueOf(getSourceAsMap.get("chorCode"))); // `chOr_Code` varchar(50) NOT NULL DEFAULT '' COMMENT '充电订单编号', orderCode
//                    orderRecord.setChorParterExtradata(String.valueOf(getSourceAsMap.get("chorParterExtradata"))); // `chor_parter_extradata` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方流水号或者会话ID',
//                    orderRecord.setChorParterUserLogo(String.valueOf(getSourceAsMap.get("chorParterUserLogo")));//  `chor_parter_user_logo` varchar(30) NOT NULL DEFAULT '' COMMENT '第三方合作方用户标识', parterUserLogo
//                    orderRecord.setChorCodeNew(String.valueOf(getSourceAsMap.get("chorCodeNew")));//  `chor_parter_user_logo` varchar(30) NOT NULL DEFAULT '' COMMENT '第三方合作方用户标识', parterUserLogo
//                    //设置高亮（若对应字段有高亮的话）
//                    setHighLight(hit, "chorCodeNew", orderRecord);
//                    orderRecordVoList.add(orderRecord);
                }
                return new AggregatedPageImpl<>((List<T>)orderRecordVoList);
            }
        });

        Gson gson = new Gson();
        List<TblChargingorderRecord> list2 = bookPage.getContent();

        System.out.println(bookPage.getTotalPages()+"list2---->"+gson.toJson(list2));
    }


    /**
     * 使用  elasticsearchTemplate 根据主键查询单条信息
     * @param pkChargingorder
     * @param pageable
     * @return
     */
    @RequestMapping("/queryByPkId/{pkChargingorder}")
    public Object queryByPkId(@PathVariable(name = "pkChargingorder") String pkChargingorder, @PageableDefault Pageable pageable) {
        try{
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("pkChargingorder", pkChargingorder)).withPageable(pageable).build();
            List<TblChargingorderRecord> list = elasticsearchTemplate.queryForList((SearchQuery) searchQuery, TblChargingorderRecord.class);

            OrderQueryTotalInfo orderQueryTotalInfo = new OrderQueryTotalInfo();
            orderQueryTotalInfo.setBeginChargeTimeStart("2019-02-19");
            orderQueryTotalInfo.setBeginChargeTimeEnd("2019-03-19");
            getTblChargingorderRecordList(orderQueryTotalInfo, pageable);
            return list;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "查询失败了";
    }


    @RequestMapping("/queryOrderRecordList/{pkChargingorder}")
    public Object queryOrderRecordList(@PathVariable(name = "pkChargingorder") String pkChargingorder,@RequestBody Map<String,Object> data, @PageableDefault Pageable pageable) {
        try{
            int pageNo = (int) data.get("pageNo");
            int pageSize = (int) data.get("pageSize");

            //创建查询构建器
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            //添加源过滤
//            queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
            //分页
            queryBuilder.withPageable(PageRequest.of(pageNo,pageSize));
            //过滤
//            queryBuilder.withQuery(QueryBuilders.matchQuery("pkChargingorder",pkChargingorder));
            List<TblChargingorderRecord> list = elasticsearchTemplate.queryForList(queryBuilder.build(), TblChargingorderRecord.class);
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("pkChargingorder", pkChargingorder)).withPageable(pageable).build();
            List<TblChargingorderRecord> list2 = elasticsearchTemplate.queryForList(searchQuery, TblChargingorderRecord.class);





            AggregatedPage<TblChargingorderRecord> bookPage = elasticsearchTemplate.queryForPage(searchQuery, TblChargingorderRecord.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                    List<TblChargingorderRecord> orderRecordVoList = new ArrayList<>();
                //命中记录
                    SearchHits hits = response.getHits();
                    for (SearchHit hit : hits){
                        if (hits.totalHits <= 0){
                            return null;
                        }
                        TblChargingorderRecord orderRecord = new TblChargingorderRecord();

                        orderRecord.setPkChargingorder(hit.getId());
                        orderRecord.setChorOrgno(Integer.valueOf(hit.getSourceAsMap().get("chorOrgno").toString())); //     `chOr_OrgNo` int(11) NOT NULL DEFAULT '1000' COMMENT '充电伙伴标识，1000:爱充自己，其它',  cpyNumber
                        orderRecord.setCvVinCode(String.valueOf(hit.getSourceAsMap().get("cvVinCode"))); // `cv_vin_code` varchar(20) NOT NULL DEFAULT '' COMMENT 'VIN', vinCode
                        orderRecord.setElectricPileCode(String.valueOf(hit.getSourceAsMap().get("electricPileCode"))); // 桩体编号 //            "electricPileCode": "222222222",
                        orderRecord.setChorCode(String.valueOf(hit.getSourceAsMap().get("chorCode"))); // `chOr_Code` varchar(50) NOT NULL DEFAULT '' COMMENT '充电订单编号', orderCode
                        orderRecord.setChorParterExtradata(String.valueOf(hit.getSourceAsMap().get("chorParterExtradata"))); // `chor_parter_extradata` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方流水号或者会话ID',
                        orderRecord.setChorParterUserLogo(String.valueOf(hit.getSourceAsMap().get("chorParterUserLogo")));//  `chor_parter_user_logo` varchar(30) NOT NULL DEFAULT '' COMMENT '第三方合作方用户标识', parterUserLogo

                        //设置高亮（若对应字段有高亮的话）
                        setHighLight(hit, "chorCode", orderRecord);
                        orderRecordVoList.add(orderRecord);
                    }
                        return new AggregatedPageImpl<>((List<T>)orderRecordVoList);
                }
            });


            return list;
        }catch (Exception e){
            e.getMessage();
        }
        return "查询失败了";
    }
    /**
     * 设置高亮
     * @param hit 命中记录
     * @param filed 字段
     * @param object 待赋值对象
     */
    private void setHighLight(SearchHit hit, String filed, Object object) {
        //获取对应的高亮域
        Map < String, HighlightField> highlightFields = hit.getHighlightFields();
        HighlightField highlightField = highlightFields.get(filed);
        if (highlightField != null) {
            //取得定义的高亮标签
            String highLightMessage = highlightField.fragments()[0].toString();
            // 反射调用set方法将高亮内容设置进去
            try {
                String setMethodName = parSetMethodName(filed);
                Class < ?>Clazz = object.getClass();
                Method setMethod = Clazz.getMethod(setMethodName, String.class);
                setMethod.invoke(object, highLightMessage);
            } catch(Exception e) {
                logger.info("反射报错");
            }
        }
    }
    /**
     * 根据字段名，获取Set方法名
     * @param fieldName 字段名
     * @return  Set方法名
     */
    private static String parSetMethodName(String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase() + fieldName.substring(startIndex + 1);
    }


    /**
     * 将 yyyy-MM-dd'T'HH:mm:ss.SSS Z 转换成 Date
     */
    private static Date formateDate(String dateStr) {
        SimpleDateFormat format = null;
        try {
            if (dateStr.endsWith("Z")) {
                dateStr = dateStr.replace("Z", " UTC");
                format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                return format.parse(dateStr);
            } else {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return format.parse(dateStr);
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用  elasticsearchTemplate 根据主键删除单条信息
     * @param pkChargingorder
     * @return
     */
    @RequestMapping("/deleteByPkId/{pkChargingorder}")
    public Object deleteByPkId(@PathVariable(name = "pkChargingorder") String pkChargingorder) {
        try{
            Document document = getDocument(TblChargingorderRecord.class);
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("pkChargingorder", pkChargingorder)).build();
            List<TblChargingorderRecord> list = elasticsearchTemplate.queryForList(searchQuery, TblChargingorderRecord.class);

            boolean ds = elasticsearchTemplate.deleteIndex(TblChargingorderRecord.class);
            return ds;
        }catch (Exception e){
            e.getMessage();
        }
        return "删除失败了";
    }


    /************************************ clazz student begin ********************************/
    /**
     * init clazz
     */
    @RequestMapping("/clazz/initClazz")
    public Object queryClazz(){
        int counter = 0;
        try {
            Document document = getDocument(new Clazz());
            String indexName = document.indexName();
            if (!elasticsearchTemplate.indexExists(indexName)) {//ORDER_INDEX_NAME
                elasticsearchTemplate.createIndex(indexName);//ORDER_INDEX_TYPE
            }
            List<IndexQuery> queries = new ArrayList<>();
//            for (TblChargingorderRecord orderRecord : Object) {
//                IndexQuery indexQuery = new IndexQuery();
//                indexQuery.setId(orderRecord.getPkChargingorder() + "");
//                indexQuery.setObject(orderRecord);
//                indexQuery.setIndexName(ORDER_INDEX_NAME);
//                indexQuery.setType(ORDER_INDEX_TYPE);
                //上面的那几步也可以使用IndexQueryBuilder来构建
//                IndexQuery indexQuery = new IndexQueryBuilder().withId(orderRecord.getPkChargingorder() + "").withObject(orderRecord).build();
//                queries.add(indexQuery);
                counter++;
                if (counter % 30 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                }
//            }
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }

        return null;
    }

    private List<Clazz> initClazz(){
        List<Clazz> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            int rd = (int)(Math.random()*5+1);

            Clazz clazz = new Clazz();
            clazz.setId(i);
            clazz.setName("");
            clazz.setAddr("中华路 "+rd+"号");
            clazz.setSutdentCount(rd);

            List<Student> listStudent = new ArrayList<>();

            for(int k=1;k<rd;k++){
                Student student = new Student();

                listStudent.add(student);
            }
            clazz.setStudentList(listStudent);
            list.add(clazz);
        }


        return list;
    }

    /**
     * query clazz
     */


    /************************************ clazz student end ********************************/
}
