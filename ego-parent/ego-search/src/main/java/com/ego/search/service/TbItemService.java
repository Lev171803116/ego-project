package com.ego.search.service;

import com.ego.TbItem;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Map;

public interface TbItemService {
    //初始化
    void init() throws IOException, SolrServerException;

    //分页查询
    Map<String,Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException;

    //新增商品
    int add(TbItem item) throws IOException, SolrServerException;
}
