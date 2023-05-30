package edu.td.zy.tik_gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/13 9:28
 */
public class FallbackGatewayFilterFactory extends AbstractGatewayFilterFactory<FallbackGatewayFilterFactory.Config> {

    @Data
    public static class Config {
        private String fallbackUri;
    }

    public FallbackGatewayFilterFactory() {
        super(FallbackGatewayFilterFactory.Config.class);
    }

    @Override
    public ShortcutType shortcutType() {
        // 使用yaml配置文件时的数据映射方式
        return ShortcutType.GATHER_LIST;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 使用GATHER_LIST映射方式时，指定对应List的变量名（即Config对象中的List变量）
        return Collections.singletonList("fallbackUri");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new FallbackFilter(config);
    }

}
