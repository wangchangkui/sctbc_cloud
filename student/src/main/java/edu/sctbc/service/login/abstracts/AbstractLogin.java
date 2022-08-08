package edu.sctbc.service.login.abstracts;

import com.alibaba.fastjson.JSON;
import edu.sctbc.pojo.Student;
import edu.sctbc.pojo.dto.StudentDto;
import edu.sctbc.service.login.LoginInterFace;
import edu.sctbc.util.redis.RedisCommonKey;
import edu.sctbc.util.redis.RedisPool;
import edu.sctbc.util.redis.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月07日 20:05:00
 */
public abstract class AbstractLogin implements LoginInterFace {
    /**
     * 子类实现通用的登录结果
     * @return 登录
     */
    public abstract Student preLoginUser();
    /**
     * 统一的redis池
     * @return  RedisPool
     */
    public abstract RedisPool getRedisPool();
    @Override
    public StudentDto login() {
        return afterLogin(preLoginUser(),getRedisPool());
    }


    /**
     * 该方法保证 微信和账号登录 都只能生成1个token
     * @param res 数据库查询对象
     * @param redisPool redis
     * @return 返回登录结果
     */
    public StudentDto afterLogin(Student res, RedisPool redisPool){
        try(Jedis jedis=redisPool.getConnection()){
            StudentDto studentDto =  new StudentDto(res);
            // 判断数据库该用户是否登录 直接获取token
            String temp = jedis.get(RedisCommonKey.USER_ + res.getName());
            if(StringUtils.isNotBlank(temp)){
                jedis.del(RedisCommonKey.USER_+res.getName());
                jedis.del(RedisCommonKey.TOKEN_+temp);
            }
            // 存入新token
            String tokenPre = JSON.toJSONString(res);
            String token = TokenUtil.getToken(tokenPre);
            jedis.set(RedisCommonKey.TOKEN_+token,tokenPre,RedisCommonKey.getAnyParam(RedisCommonKey.EXPIRE_ONE_DAY,false));
            // 多设置一个 可以快速删除
            jedis.set(RedisCommonKey.USER_+res.getName(),token,RedisCommonKey.getAnyParam(RedisCommonKey.EXPIRE_ONE_DAY,false));
            studentDto.setToken(token);
            studentDto.setExpire(LocalDateTime.now().plusDays(1));
            return studentDto;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
