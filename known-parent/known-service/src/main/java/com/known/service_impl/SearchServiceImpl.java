package com.known.service_impl;

import com.known.common.model.AskEsModel;
import com.known.common.model.KnowledgeEsModel;
import com.known.common.model.TopicEsModel;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.mapper.MyResultMapper;
import com.known.service.SearchService;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

@Service
public class SearchServiceImpl implements SearchService {


    // 自定义反射类 用于重新设置高亮显示
    @Resource
    private MyResultMapper myResultMapper;

    // springboot 整合es工具类
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;    //es工具


    @Override
    public PageResult<TopicEsModel> findTopicEsByPage(String keyword, Integer pageNum, Integer countTotal) {

        // 设置每页显示条数
        if (countTotal <= 0) {
            countTotal = 10;
        }

        // 设置查询条件
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(keyword);

        // 分页设置 索引从第0页开始
        Pageable pageable = PageRequest.of(pageNum<1 ?  0 : --pageNum, countTotal);

        // 设置查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder)
                .withHighlightFields(new HighlightBuilder.
                        Field("*").preTags("<em style='color:red'>").postTags("</em>")
                ).build();

        // 根据查询条件返回结果 反射设置高亮显示
        Page<TopicEsModel> pageAll = elasticsearchTemplate.queryForPage(searchQuery, TopicEsModel.class, myResultMapper);



        if(pageAll != null || !CollectionUtils.isEmpty(pageAll.getContent())) {
            new BussinessException("未查到符合条件的内容");
        }

        PageResult<TopicEsModel> pageResult = new PageResult<>();
        pageResult.setList(pageAll.getContent());


        com.known.common.vo.Page page = new com.known.common.vo.Page(pageNum, (int)pageAll.getTotalElements(), countTotal);
        pageResult.setPage(page);

        return pageResult;
    }


    @Override
    public PageResult<KnowledgeEsModel> findKnowledgeEsByPage(String keyword, Integer pageNum, Integer countTotal) {

        // 设置每页显示条数
        if (countTotal <= 0) {
            countTotal = 10;
        }

        // 设置查询条件
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(keyword);

        // 分页设置 索引从第0页开始
        Pageable pageable = PageRequest.of(pageNum<1 ?  0 : --pageNum, countTotal);

        // 设置查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder)
                .withHighlightFields(new HighlightBuilder.
                        Field("*").preTags("<em style='color:red'>").postTags("</em>")
                ).build();

        // 根据查询条件返回结果 反射设置高亮显示
        Page<KnowledgeEsModel> pageAll = elasticsearchTemplate.queryForPage(searchQuery, KnowledgeEsModel.class, myResultMapper);



        if(pageAll != null || !CollectionUtils.isEmpty(pageAll.getContent())) {
            new BussinessException("未查到符合条件的内容");
        }

        PageResult<KnowledgeEsModel> pageResult = new PageResult<>();
        pageResult.setList(pageAll.getContent());


        com.known.common.vo.Page page = new com.known.common.vo.Page(pageNum, (int)pageAll.getTotalElements(), countTotal);
        pageResult.setPage(page);

        return pageResult;
    }


    @Override
    public PageResult<AskEsModel> findAskEsByPage(String keyword, Integer pageNum, Integer countTotal) throws BussinessException {


        // 设置每页显示条数
        if (countTotal <= 0) {
            countTotal = 10;
        }
        // 设置查询条件
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(keyword);

        // 分页设置 索引从第0页开始
        Pageable pageable = PageRequest.of(pageNum<1 ?  0 : --pageNum, countTotal);

        // 设置查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder)
                .withHighlightFields(new HighlightBuilder.
                        Field("*").preTags("<em style='color:red'>").postTags("</em>")
                ).build();

        // 根据查询条件返回结果 反射设置高亮显示
        Page<AskEsModel> pageAll = elasticsearchTemplate.queryForPage(searchQuery, AskEsModel.class, myResultMapper);

        if(pageAll != null || !CollectionUtils.isEmpty(pageAll.getContent())) {
            new BussinessException("未查到符合条件的内容");
        }

        PageResult<AskEsModel> pageResult = new PageResult<>();
        pageResult.setList(pageAll.getContent());

        pageAll.getContent().parallelStream().forEach(r->{
            System.out.println(r);
        });

        com.known.common.vo.Page page = new com.known.common.vo.Page(pageNum, (int)pageAll.getTotalElements(), countTotal);
        pageResult.setPage(page);

        return pageResult;
    }

}
