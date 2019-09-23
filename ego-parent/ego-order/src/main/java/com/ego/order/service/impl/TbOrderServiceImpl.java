package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ego.*;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.dubboservice.service.TbItemDubboService;
import com.ego.dubboservice.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParem;
import com.ego.order.service.TbOrderService;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@PropertySource(value = "classpath:commons.properties")
public class TbOrderServiceImpl implements TbOrderService {

    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${cart.key}")
    private String cartKey;

    @Value("${passport.url}")
    private String passPortUrl;

    @Reference(version = "1.0.0")
    private TbItemDubboService tbItemDubboServiceImpl;

    @Reference
    private TbOrderDubboService tbOrderDubboServiceImpl;
    @Override
    public List<TbItemChild> showOrderCart(List<Long> ids, HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String reslut = HttpClientUtil.doPost(passPortUrl + token);
        EgoResult er = JSONObject.parseObject(reslut, EgoResult.class);
        TbUser tbUser = JSONObject.parseObject(JSON.toJSONString(er.getData()), TbUser.class);
        String key = cartKey + tbUser.getUsername();

        String json = jedisDaoImpl.get(key);
        List<TbItemChild>   list = JSONObject.parseArray(json, TbItemChild.class);
        List<TbItemChild> listNew= new ArrayList<>();

        for (TbItemChild child : list) {
            for (Long id : ids) {
                if((long)child.getId() == (long)id){
                    //判断购买量是否大于等于库存
                    TbItem item = tbItemDubboServiceImpl.selById(id);
                    if(item.getNum() >= child.getNum()){
                        child.setEnough(true);
                    }else{
                        child.setEnough(false);
                    }
                    listNew.add(child);
                }
            }
        }
        return listNew;
    }

    @Override
    public EgoResult create(MyOrderParem param, HttpServletRequest request) {
        //订单表数据
        TbOrder order = new TbOrder();
        order.setPayment(param.getPayment());
        order.setPaymentType(param.getPaymentType());
        long id = IDUtils.genItemId();
        order.setOrderId(id+"");
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String reslut = HttpClientUtil.doPost(passPortUrl + token);
        EgoResult er = JSONObject.parseObject(reslut, EgoResult.class);
        TbUser tbUser = JSONObject.parseObject(JSON.toJSONString(er.getData()), TbUser.class);

        order.setUserId(tbUser.getId());
        order.setBuyerNick(tbUser.getUsername());
        order.setBuyerRate(0);

        //订单-商品表
        for (TbOrderItem item : param.getOrderItems()) {
            item.setId(IDUtils.genItemId()+"");
            item.setOrderId(id+"");
        }

        //收货人信息
        TbOrderShipping shipping = param.getOrderShipping();
        shipping.setOrderId(id+"");
        shipping.setCreated(date);
        shipping.setUpdated(date);

        EgoResult egoResult = new EgoResult();
        try {
            int index=tbOrderDubboServiceImpl.isrOrder(order,param.getOrderItems(),shipping);
            if(index>0){
                egoResult.setStatus(200);
                //删除购买的商品
                String json = jedisDaoImpl.get(cartKey + tbUser.getUsername());
                List<TbItemChild>  listCart = JSONObject.parseArray(json, TbItemChild.class);
                List<TbItemChild> listCartNew=new ArrayList<>();
                for (TbItemChild child : listCart) {
                    for (TbOrderItem item : param.getOrderItems()) {
                            if((long)child.getId() == (long)Long.parseLong(item.getItemId())){
                                listCartNew.add(child);
                            }
                    }
                }
                listCart.removeAll(listCartNew);
                jedisDaoImpl.set(cartKey + tbUser.getUsername(),JSON.toJSONString(listCart));
            }
        } catch (Exception e) {
            request.setAttribute("message","订单创建失败");
            e.printStackTrace();
        }
        return egoResult;
    }
}
