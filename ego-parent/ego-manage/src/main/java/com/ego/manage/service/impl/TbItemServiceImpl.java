package com.ego.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ego.TbItem;
import com.ego.TbItemDesc;
import com.ego.TbItemParamItem;
import com.ego.commons.pojo.EasyUIDataGrid;

import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;

import com.ego.dubboservice.service.TbItemDescDubboService;
import com.ego.dubboservice.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@PropertySource("redis.properties")
public class TbItemServiceImpl implements TbItemService{
    @Reference(version = "1.0.0")
    private TbItemDubboService tbleItemDubboServiceImpl;

    @Value("${search.url}")
    private String url ;

    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.item.key}")
    private String itemKey;

    @Override
    public EasyUIDataGrid show(int page, int rows) {
        return tbleItemDubboServiceImpl.show(page,rows);
    }

    @Override
    public int updItemStatus(String ids, byte status) {
        int index = 0;
        TbItem tbItem = new TbItem();
        String[] idsStr = ids.split(",");
        for (String id : idsStr) {
            tbItem.setId(Long.parseLong(id));
            tbItem.setStatus(status);
            index+= tbleItemDubboServiceImpl.updItemStatus(tbItem);

            if(status == 2 || status ==3){
                jedisDaoImpl.del(itemKey+id);
            }
        }

        if(index == idsStr.length){
            return 1;
        }
        return 0;
    }

    @Override
    public int save(TbItem item, String desc,String itemParams) throws Exception{
        long id = IDUtils.genItemId();
        item.setId(id);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setStatus((byte)1);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(id);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);

        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        tbItemParamItem.setId(id);
        tbItemParamItem.setParamData(itemParams);

        int index = 0;
            index = tbleItemDubboServiceImpl.insTbItemDesc(item,tbItemDesc,tbItemParamItem);

        final TbItem itemFinal = item;
        final String descFinal = desc;

        new Thread(){
            public void run() {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("item", itemFinal);
                map.put("desc", descFinal);

                HttpClientUtil.doPostJson(url, JSON.toJSONString(map));
                //使用java代码调用其他项目的控制器
            };
        }.start();
        return index;
    }
}
