package edu.sctbc.service.login.abstracts.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.sctbc.dao.StudentMapper;
import edu.sctbc.pojo.student.Student;
import edu.sctbc.service.login.abstracts.AbstractLogin;
import edu.sctbc.util.OkHttpUtils;
import edu.sctbc.util.redis.RedisPool;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月07日 20:20:00
 */
@Slf4j
public class WxLogin extends AbstractLogin {


    /**
     * 连接池
     */
    private RedisPool redisPool;


    /**
     * 连接数据库的操作
     */
    private StudentMapper studentMapper;

    private String wxId;


    public WxLogin(RedisPool redisPool, StudentMapper studentMapper,String wxId) {
        this.redisPool = redisPool;
        this.studentMapper = studentMapper;
        this.wxId =wxId;
    }

    @Override
    public Student preLoginUser() {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getWxId,wxId);
        Student res = studentMapper.selectOne(wrapper);
        if(ObjectUtil.isNull(res)){
            throw new RuntimeException("该微信号未绑定任何用户");
        }
        return res;
    }

    @Override
    public RedisPool getRedisPool() {
        return this.redisPool;
    }

    /**
     * 根据微信的开发规范 需要通过一个code拿到wxId
     * @param code code码
     * @return WxId
     */
    public static String getWxIdFormRemote(String code,String appId,String secret,String grantType){
        OkHttpUtils builder = OkHttpUtils.builder();
        // 这些是死的 没必要单独抽离出来 一个想
        OkHttpUtils okHttpUtils = builder.url("https://api.weixin.qq.com/sns/jscode2session")
                .addParam("appid", appId)
                .addParam("secret", secret)
                .addParam("js_code", code)
                .addParam("grant_type", grantType).get();
        String sync = okHttpUtils.sync();
        log.info("请求完成:{}",sync);
        return JSONObject.parseObject(sync).get("openid").toString();
    }
}
