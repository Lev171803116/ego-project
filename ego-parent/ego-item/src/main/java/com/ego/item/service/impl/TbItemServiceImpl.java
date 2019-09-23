package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ego.TbItem;
import com.ego.commons.pojo.TbItemChild;
import com.ego.dubboservice.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@PropertySource("redis.properties")
public class TbItemServiceImpl implements TbItemService {

    @Reference(version = "1.0.0")
    private TbItemDubboService tbItemDubboServicImpl;

    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${redis.item.key}")
    private String itemKey;

    @Override
    public TbItemChild show(long id) {

        String key = itemKey+id;
        if(jedisDaoImpl.exists(key)){
            String json=jedisDaoImpl.get(key);
            if(json != null && !json.equals("")){
                return JSONObject.parseObject(json,TbItemChild.class);
            }
        }
        TbItem item = tbItemDubboServicImpl.selById(id);
        TbItemChild tbItemChild = new TbItemChild();
        tbItemChild.setId(item.getId());
        tbItemChild.setTitle(item.getTitle());
        tbItemChild.setPrice(item.getPrice());
        tbItemChild.setSellPoint(item.getSellPoint());
        tbItemChild.setImages(item.getImage()!=null && !item.equals("")?item.getImage().split(","):new String[1]);
        return tbItemChild;
    }
}
