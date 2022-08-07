package edu.sctbc.service.login.abstracts.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.sctbc.dao.StudentMapper;
import edu.sctbc.pojo.Student;
import edu.sctbc.service.login.abstracts.AbstractLogin;
import edu.sctbc.util.redis.RedisPool;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月07日 20:20:00
 */
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
        return studentMapper.selectOne(wrapper);
    }

    @Override
    public RedisPool getRedisPool() {
        return this.redisPool;
    }
}
