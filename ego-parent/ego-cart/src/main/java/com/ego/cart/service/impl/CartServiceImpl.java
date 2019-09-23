package com.ego.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ego.TbItem;
import com.ego.TbUser;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.dubboservice.service.TbItemDubboService;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource(value = "classpath:commons.properties")
public class CartServiceImpl implements CartService {

    @Resource
    private JedisDao jedisDaoImpl;
    @Reference(version = "1.0.0")
    private TbItemDubboService tbItemDubboServiceImpl;

    @Value("${passport.url}")
    private String passportUrl;
    @Value("${cart.key}")
    private String cartKey;
    @Override
    public void addCart(long id, int num, HttpServletRequest request) {
        //集合存放有所购物车的商品
        List<TbItemChild> list =null;
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JSONObject.parseObject(jsonUser, EgoResult.class);
        TbUser tbUser = JSONObject.parseObject(JSON.toJSONString(er.getData()), TbUser.class);
        //redis中的key
        String key = cartKey + tbUser.getUsername();

        if(jedisDaoImpl.exists(key)){
            String json = jedisDaoImpl.get(key);
            if(json != null && json.equals("")){
                 list = JSONObject.parseArray(json, TbItemChild.class);
                for (TbItemChild tbItemChild : list) {
                    if((long)tbItemChild.getId()==id){
                        //购物车中存在该商品
                        tbItemChild.setNum(tbItemChild.getNum()+num);
                        jedisDaoImpl.set(key, JSON.toJSONString(list));
                        return;
                    }
                }

            }
        }
        //redis中key对应的value是null或""或不存在
        list = new ArrayList<TbItemChild>();
        TbItem item = tbItemDubboServiceImpl.selById(id);
        TbItemChild child = new TbItemChild();
        child.setId(item.getId());
        child.setTitle(item.getTitle());
        child.setImages(item.getImage() ==null || item.getImage().equals("")? new String[1]:item.getImage().split(","));
        child.setNum(item.getNum());
        child.setPrice(item.getPrice());
        list.add(child);
        jedisDaoImpl.set(key, JSON.toJSONString(list));


    }

    @Override
    public List<TbItemChild> showCart(HttpServletRequest request) {
        List<TbItemChild> list =null;
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JSONObject.parseObject(jsonUser, EgoResult.class);
        TbUser tbUser = JSONObject.parseObject(JSON.toJSONString(er.getData()), TbUser.class);
        //redis中的key
        String key = cartKey + tbUser.getUsername();

        String json = jedisDaoImpl.get(key);
        return JSONObject.parseArray(json, TbItemChild.class);
    }

    @Override
    public EgoResult update(long id, int num,HttpServletRequest request) {

        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JSONObject.parseObject(jsonUser, EgoResult.class);
        TbUser tbUser = JSONObject.parseObject(JSON.toJSONString(er.getData()), TbUser.class);
        String key = cartKey + tbUser.getUsername();
        String json = jedisDaoImpl.get(key);

        List<TbItemChild> list = JSONObject.parseArray(json, TbItemChild.class);
        for (TbItemChild child : list) {
            if((long)child.getId()==id){
                child.setNum(num);
            }
        }
        String ok = jedisDaoImpl.set(key, JSON.toJSONString(list));
        EgoResult egoResult = new EgoResult();
        if(ok.equals("OK")){
            egoResult.setStatus(200);
        }
        return egoResult;
    }

    @Override
    public EgoResult delete(long id, HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JSONObject.parseObject(jsonUser, EgoResult.class);
        TbUser tbUser = JSONObject.parseObject(JSON.toJSONString(er.getData()), TbUser.class);
        String key = cartKey + tbUser.getUsername();
        String json = jedisDaoImpl.get(key);

        List<TbItemChild> list = JSONObject.parseArray(json, TbItemChild.class);
        TbItemChild TbItemChild=null;
        for (TbItemChild child : list) {
            if((long)child.getId()==id){
                TbItemChild=child;

            }
        }
        list.remove(TbItemChild);
        String ok = jedisDaoImpl.set(key, JSON.toJSONString(list));
        EgoResult egoResult = new EgoResult();
        if(ok.equals("OK")){
            egoResult.setStatus(200);
        }
        return egoResult;
    }

}
