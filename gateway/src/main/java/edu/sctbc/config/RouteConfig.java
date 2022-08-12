package edu.sctbc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 16:15:00
 */

@Configuration
@EnableConfigurationProperties()
@ConfigurationProperties(prefix = "auth")
@Slf4j
public class RouteConfig {
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

    @Bean
    public RouteLocator customRoute(RouteLocatorBuilder builder) {
        // 路由配置
        return builder.routes().route("nacos-student", s -> s.path("/student")
                        .uri("lb://nacos-student")
                        .predicate(m -> {
                            // 如果没有需要处理的地址 直接丢给下面的拦截器去处理
                            if (CollectionUtils.isEmpty(noAccess)) {
                                return true;
                            }
                            // 如果地址是不放行的 直接返回404
                            int size = (int) noAccess.stream().filter(h -> m.getRequest().getURI().getPath().contains(h)).count();
                            return size == 0;
                        })
                )
                .build();
    }
}
