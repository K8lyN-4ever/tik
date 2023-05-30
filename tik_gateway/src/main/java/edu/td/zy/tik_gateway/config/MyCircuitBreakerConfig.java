package edu.td.zy.tik_gateway.config;

import edu.td.zy.tik_gateway.filter.FallbackGatewayFilterFactory;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/11 16:11
 */
@Configuration
public class MyCircuitBreakerConfig {

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED) // 滑动窗口的类型为时间窗口
                .slidingWindowSize(10) // 时间窗口的大小为60秒
                .minimumNumberOfCalls(5) // 在单位时间窗口内最少需要5次调用才能开始进行统计计算
                .failureRateThreshold(50) // 在单位时间窗口内调用失败率达到50%后会启动断路器
                .enableAutomaticTransitionFromOpenToHalfOpen() // 允许断路器自动由打开状态转换为半开状态
                .permittedNumberOfCallsInHalfOpenState(5) // 在半开状态下允许进行正常调用的次数
                .waitDurationInOpenState(Duration.ofSeconds(5)) // 断路器打开状态转换为半开状态需要等待60秒
                .recordExceptions(Exception.class) // 所有异常都当作失败来处理
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(1000)).build())
                .build());
    }

    @Bean
    public FallbackGatewayFilterFactory fallbackGatewayFilterFactory() {
        return new FallbackGatewayFilterFactory();
    }


}
