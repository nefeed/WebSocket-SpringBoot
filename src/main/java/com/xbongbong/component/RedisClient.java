package com.xbongbong.component;

import com.xbongbong.LocalConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-07
 * Time: 19:39
 */
@Component
public class RedisClient {

    private static final Logger LOG = LogManager
            .getLogger(RedisClient.class);

    @Autowired
    private LocalConstant localConstant;
    @Autowired
    private JedisPool jedisPool;

    public void set(String key, String value) throws Exception {
        Jedis jedis = null;
        String subUserHashKey = localConstant.getProjectName() + ":" + key;
        try {
            jedis = jedisPool.getResource();
            jedis.set(subUserHashKey, value);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public String get(String key) throws Exception {
        Jedis jedis = null;
        String subUserHashKey = localConstant.getProjectName() + ":" + key;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(subUserHashKey);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public long lpush(String key, String value) throws Exception {
        Jedis jedis = null;
        String subUserHashKey = localConstant.getProjectName() + ":" + key;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(subUserHashKey, value);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     *  将任务 List<String> 左边插入 key 的 jedis 队列
     * @param key
     * @param valueList
     * @return
     * @throws Exception
     */
    public void lpushList(String key, List<String> valueList) throws Exception {
        Jedis jedis = null;
        String subUserHashKey = localConstant.getProjectName() + ":" + key;
        try {
            jedis = jedisPool.getResource();
            for (String value : valueList) {
                jedis.lpush(subUserHashKey, value);
            }
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public String rpop(String key) throws Exception  {
        Jedis jedis = null;
        String subUserHashKey = localConstant.getProjectName() + ":" + key;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpop(subUserHashKey);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public String rpoplpush(String popKey, String pushKey) throws Exception {
        Jedis jedis = null;
        String subUserHashPopKey = localConstant.getProjectName() + ":" + popKey;
        String subUserHashPushKey = localConstant.getProjectName() + ":" + pushKey;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpoplpush(subUserHashPopKey, subUserHashPushKey);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public Long showListLength(String key) throws Exception  {
        Jedis jedis = null;
        String subUserHashKey = localConstant.getProjectName() + ":" + key;
        try {
            jedis = jedisPool.getResource();
            return jedis.llen(subUserHashKey);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

}