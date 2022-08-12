package edu.sctbc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 16:15:00
 */

@Configuration
@Slf4j
public class RouteConfig {

    @Bean
    public RouteLocator customRoute(RouteLocatorBuilder builder) {
        // 路由配置
        return builder.routes()
                .route("nacos-student", s -> s.path("/student").uri("lb://nacos-student").predicate(h -> {
                    ServerHttpRequest request = h.getRequest();
                    // 只要是满足student的地址 才会被转发到 服务
                    return request.getURI().getPath().contains("/student");
                }))
                .route("nacos-teacher", m -> m.path("/teacher").uri("lb://nacos-teacher").predicate(f -> {
                    ServerHttpRequest request = f.getRequest();
                    // 只要是满足student的地址 才会被转发到 服务
                    return request.getURI().getPath().contains("/teacher");
                }))
                .build();
    }
}
