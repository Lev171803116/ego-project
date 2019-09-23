package com.ego.redis.dao;

import org.springframework.stereotype.Component;

@Component
public interface JedisDao {

    //判断key值是否存在
    Boolean exists(String key);

    //删除
    Long del(String key);

    //设置值
    String set(String key, String value);

    //取值
    String get(String key);

    //有key的有效时间
    Long expire(String key, int seconds);
}
