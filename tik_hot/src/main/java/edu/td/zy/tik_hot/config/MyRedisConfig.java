package edu.td.zy.tik_hot.config;

import edu.td.zy.tik_common.redis.RedisConfig;
import edu.td.zy.tik_common.redis.RedisService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/10 10:46
 */
@Data
@Configuration
public class MyRedisConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    String host;

    @Value("${spring.redis.port:#{6379}}")
    int port;

    @Value("${spring.redis.password:123456}")
    String password;

    @Value("${spring.redis.max-total:#{10}}")
    int maxTotal;

    @Value("${spring.redis.max-idle:#{5}}")
    int maxIdle;

    @Value("${spring.redis.min-idle:#{1}}")
    int minIdle;

    @Value("${spring.redis.timeout:#{5000}}")
    int timeout;

    public RedisConfig redisConfig() {
        return new RedisConfig(host, port, password, maxTotal, maxIdle, minIdle, timeout);
    }

    @Bean
    public RedisService redisService() {
        return RedisService.getInstance(redisConfig());
    }
}
