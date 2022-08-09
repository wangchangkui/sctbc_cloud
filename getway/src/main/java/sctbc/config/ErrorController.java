package sctbc.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sctbc.util.CommonResultError;

import java.util.HashMap;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月08日 21:50:00
 */
@Order(-1)
@Configuration
public class ErrorController implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // 将返回格式设为JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }
        // 改变请求响应返回行为
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            HashMap<String, Object> stringObjectHashMap = new HashMap<>(3);
            stringObjectHashMap.put("code", 400000);
            stringObjectHashMap.put("message", CommonResultError.transFromMessage(ex.getMessage()));
            try {
                // 返回ErrorResult
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(stringObjectHashMap));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
