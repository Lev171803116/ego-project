package com.ego.dubboservice.service;



import com.ego.TbOrder;
import com.ego.TbOrderItem;
import com.ego.TbOrderShipping;

import java.util.List;

public interface TbOrderDubboService {

    //创建订单
    int isrOrder(TbOrder order, List<TbOrderItem> list, TbOrderShipping shipping) throws Exception;
}
