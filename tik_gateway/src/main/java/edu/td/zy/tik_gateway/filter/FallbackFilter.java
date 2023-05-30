package edu.td.zy.tik_gateway.filter;

import edu.td.zy.tik_common.utils.response.ResponseStatus;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/13 9:14
 */
@Slf4j
public class FallbackFilter implements GatewayFilter {

    private FallbackGatewayFilterFactory.Config config;

    public FallbackFilter() {}

    public FallbackFilter(FallbackGatewayFilterFactory.Config config) {
        this.config = config;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).onErrorResume(e -> {
            System.out.println("fallback filter");
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            MediaType contentType = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
            response.getHeaders().setContentType(contentType);
            return response.writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(
                    new StandardResponse<>(ResponseStatus.INTERNAL_SERVER_ERROR, "该服务暂时不可用!!", null).toString()
                            .getBytes())));
        });
    }

}
