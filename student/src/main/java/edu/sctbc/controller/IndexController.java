package edu.sctbc.controller;

import edu.sctbc.util.redis.RedisCommonKey;
import edu.sctbc.util.redis.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:04:00
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private RedisPool redisPool;


    @GetMapping("/redis/getValue/{value}")
    public String getRedisValue(@PathVariable String value) {
        return RedisCommonKey.getRedisValue(RedisCommonKey.TOKEN_ + value, redisPool.getConnection());
    }

}
