package sctbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 16:15:00
 */

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "auth")
@Configuration
public class RouteConfig {

    public void setNoAuthor(List<String> noAuthor) {
        this.noAuthor = noAuthor;
    }

    public List<String> getNoAuthor() {
        return noAuthor;
    }

    private final String token="X-Token";

    /**
     * 不需要验证的
     */
    public List<String> noAuthor;

    @Bean
    public RouteLocator customRoute(RouteLocatorBuilder builder){
        // 路由配置
        return builder.routes().route("nacos-student", s -> s.path("/student")
                        .uri("lb://nacos-student")
                        .predicate(m->{
                            HttpHeaders headers = m.getRequest().getHeaders();
                            // 过滤掉不需要拦截的url路径
                            int size = (int) noAuthor.stream().filter(h -> m.getRequest().getURI().getPath().contains(h)).count();
                            if(size > 0){
                                return true;
                            }
                            return headers.containsKey(token);
                        })
                )
                .build();
    }
}
