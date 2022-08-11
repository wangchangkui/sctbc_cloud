package sctbc.filter;

import edu.sctbc.util.redis.RedisCommonKey;
import edu.sctbc.util.redis.RedisPool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月08日 21:36:00
 */
@Data
@EnableConfigurationProperties
@ConfigurationProperties("filter")
@Component
@Slf4j
public class TokenFilter implements GlobalFilter {


    private List<String> noAuthor;

    @Autowired
    private RedisPool redisPool;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求头
        HttpHeaders headers = request.getHeaders();
        String url = request.getURI().getPath();
        long count = noAuthor.stream().filter(url::contains).count();
        if (count > 0) {
            return chain.filter(exchange);
        }
        List<String> header = headers.get("Token");
        if (CollectionUtils.isEmpty(header)) {
            throw new RuntimeException("请传入认证头!");
        }
        String token = header.get(0);
        // 验证token是否有效
        try (Jedis jedis = redisPool.getConnection()) {
            String s = jedis.get(RedisCommonKey.TOKEN_ + token);
            if (StringUtils.isNotBlank(s)) {
                return chain.filter(exchange);
            }
            throw new RuntimeException("无效Token");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
