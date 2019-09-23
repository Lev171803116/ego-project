package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.TbItem;
import com.ego.TbItemCat;
import com.ego.TbItemDesc;
import com.ego.commons.pojo.TbItemChild;
import com.ego.dubboservice.service.TbItemCatDubboService;
import com.ego.dubboservice.service.TbItemDescDubboService;
import com.ego.dubboservice.service.TbItemDubboService;
import com.ego.search.config.MySlorCloud;
import com.ego.search.service.TbItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbItemServiceImpl implements TbItemService {
    @Autowired
    private MySlorCloud solrClient;
    @Reference(version = "1.0.0")
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference(version = "1.0.0")
    private TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Reference(version = "1.0.0")
    private TbItemDescDubboService tbItemDescDubboServiceImpl;

    @Override
    public void init() throws IOException, SolrServerException {

        //获取CloudSolrClient对象
        CloudSolrClient cloudSolrClient = solrClient.ClientTest();
        cloudSolrClient.deleteByQuery("*:*");
        cloudSolrClient.commit();

        //查询所有正常的商品
        List<TbItem> listItem = tbItemDubboServiceImpl.selAllByStatus((byte) 1);
        for (TbItem tbItem : listItem) {
            //商品对应的类目信息
            TbItemCat cat = tbItemCatDubboServiceImpl.selectById(tbItem.getCid());
            //商品对应的描述信息
            TbItemDesc desc = tbItemDescDubboServiceImpl.selById(tbItem.getId());
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", tbItem.getId());
            doc.setField("item_title", tbItem.getTitle());
            doc.setField("item_sell_point", tbItem.getSellPoint());
            doc.setField("item_price", tbItem.getPrice());
            doc.setField("item_image", tbItem.getImage());
            doc.setField("item_category_name", cat.getName());
            doc.setField("item_desc", desc.getItemDesc());
            //doc.setField("item_update",tbItem.getUpdated());
            cloudSolrClient.add(doc);
        }
        cloudSolrClient.commit();
    }

    @Override
    public Map<String, Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException {
        //获取CloudSolrClient对象
        CloudSolrClient cloudSolrClient = solrClient.ClientTest();
        SolrQuery params = new SolrQuery();
        //设置分页条件
        params.setStart(rows * (page - 1));
        params.setRows(rows);
        //设置条件
        params.setQuery("item_keywords:" + query);
        //设置高亮
        params.setHighlight(true);
        params.addHighlightField("item_title");
        params.setHighlightSimplePre("<span style='color:red'>");
        params.setHighlightSimplePre("</span>");
        //添加排序
        //params.setSort("item_updated", SolrQuery.ORDER.desc);
        QueryResponse response = cloudSolrClient.query(params);

        List<TbItemChild> listChild = new ArrayList<>();

        //未高亮内容
        SolrDocumentList listSolr = response.getResults();

        //高亮内容
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for (SolrDocument doc : listSolr) {
            TbItemChild child = new TbItemChild();
            child.setId(Long.parseLong(doc.getFieldValue("id").toString()));

            List<String> list = highlighting.get(doc.getFieldValue("id")).get("item_title");
            if (list != null && list.size() > 0) {
                child.setTitle(list.get(0));
            }

            child.setPrice((Long) doc.getFieldValue("item_price"));
            Object image = doc.getFieldValue("item_image");
            child.setImages(image == null || image.equals("") ? new String[1] : image.toString().split(","));
            child.setTitle(doc.getFieldValue("item_title").toString());
            child.setSellPoint(doc.getFieldValue("item_sell_point").toString());
            listChild.add(child);
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("itemList", listChild);
        resultMap.put("totalPages", listSolr.getNumFound() % rows == 0 ? listSolr.getNumFound() / rows : listSolr.getNumFound() / rows + 1);
        return resultMap;
    }

    @Override
    public int add(TbItem item) throws IOException, SolrServerException {
        //获取CloudSolrClient对象
        CloudSolrClient cloudSolrClient = solrClient.ClientTest();
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", item.getId());
        doc.setField("item_title", item.getTitle());
        doc.setField("item_sell_point", item.getSellPoint());
        doc.setField("item_price", item.getPrice());
        doc.setField("item_image", item.getImage());
        doc.setField("item_category_name", tbItemCatDubboServiceImpl.selectById(item.getCid()).getName());
        doc.addField("item_desc", tbItemDescDubboServiceImpl.selById(item.getId()).getItemDesc());
        UpdateResponse response = cloudSolrClient.add(doc);
        cloudSolrClient.commit();
        if (response.getStatus() == 0) {
            return 1;
        }
        return 0;
    }
}
