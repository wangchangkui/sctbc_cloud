package sctbc.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sctbc.service.impl.StudentFeignImpl;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月09日 16:18:00
 */
@Component
@FeignClient(name = "nacos-student", fallback = StudentFeignImpl.class)

public interface StudentFeign {

    /**
     * 远程调用 获得redis的值
     *
     * @param value value
     * @return String
     */
    @GetMapping("/student/index/redis/getValue/{value}")
    String getRedisValue(@PathVariable String value);
}
