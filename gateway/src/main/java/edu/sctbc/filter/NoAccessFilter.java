package edu.sctbc.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月12日 16:41:00
 */

@Slf4j
@Component
@EnableConfigurationProperties()
@ConfigurationProperties(prefix = "auth")
public class NoAccessFilter implements GlobalFilter {
    /**
     * 拒绝请求的路径
     */
    public List<String> noAccess;

    public List<String> getNoAccess() {
        return noAccess;
    }

    public void setNoAccess(List<String> noAccess) {
        this.noAccess = noAccess;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (CollectionUtils.isEmpty(noAccess)) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        int count = (int) noAccess.stream().filter(path::contains).count();
        if (count > 0) {
            throw new RuntimeException("该地址不允许访问：" + path);
        }
        return chain.filter(exchange);
    }
}
