package edu.td.zy.tik_common.redis;

import com.google.gson.*;
import edu.td.zy.tik_common.pojo.Video;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/10 9:53
 */
public class RedisService {

    private final JedisPool jedisPool;

    private static RedisService INSTANCE = new RedisService();

    private final String HOT_NAMESPACE = "hot";
    private final String USER_NAMESPACE = "user";
    private final String COMMENT_NAMESPACE = "comment";

    private RedisService() {
        this.jedisPool = new RedisConfig().jedisPool();
    }

    private RedisService(RedisConfig config) {
        this.jedisPool = config.jedisPool();
    }

    public static RedisService getInstance() {
        return INSTANCE;
    }

    public static RedisService getInstance(RedisConfig config) {
        INSTANCE = new RedisService(config);
        return INSTANCE;
    }

    public void setValue(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    public String getValue(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    public void remove(String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    public void setValueNx(String key, String value) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.setnx(key, value);
        }
    }

    public void remove(String namespace, String key) {
        remove(namespace + ":" + key);
    }

    public void setValue(String namespace, String key, String value) {
        setValue(namespace + ":" + key, value);
    }

    public String getValue(String namespace, String key) {
        return getValue(namespace + ":" +  key);
    }

    public void setValueNx(String namespace, String key, String value) {
        setValueNx(namespace + ":" + key, value);
    }

    public double zadd(String key, double score, String value) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(key, score, value);
        }
    }

    public double zaddNx(String namespace, String key, double score, String value) {
        return zadd(namespace + ":" + key, score, value);
    }

    public double zaddIncr(String key, double score, String value) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.zincrby(key, score, value);
        }
    }

    public double zaddIncrNx(String namespace, String key, double score, String value) {
        return zaddIncr(namespace + ":" + key, score, value);
    }

    public double zscore(String key, String value) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.zscore(key, value);
        }
    }

    public double zscoreNx(String namespace, String key, String value) {
        return zscore(namespace + ":" + key, value);
    }

    public void lpush(String key, String... values) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(key, values);
        }
    }

    public void lpush(String namespace, String key, String... values) {
        lpush(namespace + ":" + key, values);
    }

    public List getHot() {
        String json = getValue(HOT_NAMESPACE, "hot");
        List lists = new Gson().fromJson(json, List.class);
        return lists;
    }

    public Video getVideo(String uid) {
        String json = getValue(HOT_NAMESPACE, "hot");
        JsonArray arr = JsonParser.parseString(json).getAsJsonArray();
        for(int i = 0;i < arr.size();i++) {
            JsonObject obj = arr.get(i).getAsJsonObject();
            if(uid.equals(obj.get("uid").getAsString())) {
                return new Gson().fromJson(obj, Video.class);
            }
        }
        return null;
    }
}
