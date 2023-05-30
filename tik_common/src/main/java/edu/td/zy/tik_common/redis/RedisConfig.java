package edu.td.zy.tik_common.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/10 9:53
 */
@AllArgsConstructor
@NoArgsConstructor
public class RedisConfig {

    String host = "127.0.0.1";
    int port = 6379;
    String password = "123456";
    int maxTotal = 10;
    int maxIdle = 5;
    int minIdle = 1;
    int timeout = 5000;

    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setJmxEnabled(false);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        return new JedisPool(poolConfig, host, port, timeout, password);
    }
}
