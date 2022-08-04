package edu.sctbc.util.redis;


import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author wck
 * @version 1.0.0
 * @createTime 2022年04月13日 18:23:00
 */
@Slf4j
public class TokenUtil {
    private static final String KEYS = "myxiaowang";

    /**
     * @param content 内容
     * @return  token
     */
    public static String getToken(String content) {
        Map<String, Object> map = new HashMap<String, Object>(16) {
            private static final long serialVersionUID = 1L;

            {
                put("content", content);
                // token 一天内有效
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
            }
        };
        return JWTUtil.createToken(map,KEYS.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取内容
     * @param token token
     * @return 内容
     */
    public static String getContent(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            return jwt.getPayload().getClaimsJson().get("content").toString();
        } catch (Exception e) {
            throw new RuntimeException("token验证失败");
        }
    }


    /**
     * 获取类容
     * @param token token
     * @param clazz 实例化的对象
     * @param <T> 泛型
     * @return 实例化的对象
     */
    public static <T> T getContent(String token,Class<T> clazz) {
        JWT jwt = JWTUtil.parseToken(token);
        String content = Optional.ofNullable(jwt.getPayload().getClaimsJson().get("content")).orElse("").toString();
        if (Strings.isBlank(content)) {
            return null;
        }
        return JSON.parseObject(content, clazz);
    }

    public static boolean verifyToken(String token) {
        try {
            return   JWTUtil.verify(token,KEYS.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }


    /**
     * 生成token
     *
     * @param content 内容
     * @return token
     */
    public static String createToken(String content) {
        return JWT.create().setPayload("key", content).setKey(KEYS.getBytes(StandardCharsets.UTF_8)).sign();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return 内容
     */
    public static String getPayload(String token) {
        JWT of = JWT.of(token);
        return of.getPayload("key").toString();
    }


    /**
     * 校验token
     *
     * @param token token 内容
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
           return JWT.of(token).setKey(KEYS.getBytes(StandardCharsets.UTF_8)).validate(0);
        }catch (Exception e){
            return false;
        }
    }


}
