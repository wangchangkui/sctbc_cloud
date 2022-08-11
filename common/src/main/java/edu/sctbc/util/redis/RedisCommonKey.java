package edu.sctbc.util.redis;

import com.alibaba.druid.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月04日 16:22:00
 */
public class RedisCommonKey {

    /**
     * 1天过期
     */
    public final static Integer EXPIRE_ONE_DAY = 60 * 60 * 24;

    /**
     * 180 秒 3分
     */
    public final static Integer THREE_MINUTES = 60 * 3;
    public final static String USER_ = "user:";

    public final static String TOKEN_ = "token:";

    public final static String CAPTCHA = "captcha:";

    public final static String QR = "qr:";


    public static SetParams getAnyParam(int expire,boolean nx){
        SetParams setParams = new SetParams();
        if(expire != 0){
            setParams.ex(expire);
        }
        if(nx){
            setParams.nx();
        }
        return setParams;
    }

    public static void setValues(String key, String value, int expire, boolean nx, Jedis jedis) {
        try {
            SetParams anyParam = getAnyParam(expire, nx);
            jedis.set(key, value, anyParam);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }


    public static String getRedisValue(String key, Jedis jedis) {
        String s;
        // 加锁 并发请求超时
        synchronized (RedisCommonKey.class) {
            s = jedis.get(key);
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isEmpty(s)) {
            throw new RuntimeException("不存在的KEY");
        }
        return s;
    }

    public static String removeRedisValue(String key, Jedis jedis) {
        synchronized (RedisCommonKey.class) {
            jedis.del(key);
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return key;
    }


}
