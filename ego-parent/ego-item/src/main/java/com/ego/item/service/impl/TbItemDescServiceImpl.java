package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubboservice.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@PropertySource("redis.properties")
public class TbItemDescServiceImpl implements TbItemDescService {

    @Reference(version = "1.0.0")
    private TbItemDescDubboService tbItemDescDubboServiceImpl;

    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${redis.desc.key}")
    private String descKey;

    @Override
    public String showDesc(long itemId) {
        String key = descKey+itemId;
        if(jedisDaoImpl.exists(key)){
            String json = jedisDaoImpl.get(key);
            if(json != null && !json.equals("")){
                return json;
            }
        }
        String result = tbItemDescDubboServiceImpl.selById(itemId).getItemDesc();
        jedisDaoImpl.set(key,result);
        return result;
    }
}
