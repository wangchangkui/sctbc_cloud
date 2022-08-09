package sctbc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sctbc.service.StudentFeign;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月09日 16:21:00
 */

@Service
@Slf4j
public class StudentFeignImpl implements StudentFeign {
    @Override
    public String getRedisValue(String value) {
        log.error("传入参数：" + value);
        log.error("服务不可用,nacos-student");
        log.error("请求地址：{}", "/redis/getValue/{value}");
        return "没有可用的服务";
    }
}
