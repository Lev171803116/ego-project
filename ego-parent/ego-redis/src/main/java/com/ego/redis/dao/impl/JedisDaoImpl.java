package com.ego.redis.dao.impl;

import com.ego.redis.dao.JedisDao;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;


@Repository
public class JedisDaoImpl implements JedisDao {

    private Jedis jedis = new Jedis("192.168.109.128",6379);
    public Boolean exists(String key) {
        return jedis.exists(key);
    }

    public Long del(String key) {
        return jedis.del(key);
    }

    public String set(String key, String value) {
        return jedis.set(key,value);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedis.expire(key,seconds);
    }
}