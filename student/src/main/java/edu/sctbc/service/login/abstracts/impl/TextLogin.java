package edu.sctbc.service.login.abstracts.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.sctbc.config.RsaKey;
import edu.sctbc.dao.StudentMapper;
import edu.sctbc.pojo.Student;
import edu.sctbc.service.login.abstracts.AbstractLogin;
import edu.sctbc.util.Encoder;
import edu.sctbc.util.redis.RedisPool;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月07日 20:10:00
 */

public class TextLogin extends AbstractLogin {
    /**
     * 加密Key
     */
    private RsaKey rsaKey;

    /**
     * 连接缓存池
     */
    private RedisPool redisPool;

    /**
     * 登录参数
     */
    private Student student;

    /**
     * 连接数据库的操作
     */
    private StudentMapper studentMapper;

    public TextLogin(RsaKey rsaKey, RedisPool redisPool, Student student,StudentMapper studentMapper) {
        this.rsaKey = rsaKey;
        this.redisPool = redisPool;
        this.student = student;
        this.studentMapper = studentMapper;
    }

    @Override
    public Student preLoginUser() {
        String password = student.getPassword();
        String encoderPassword = Encoder.encoder(password, rsaKey.getKey(), rsaKey.getIv());
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentId,student.getStudentId()).eq(Student::getPassword,encoderPassword);
        return studentMapper.selectOne(wrapper);
    }

    @Override
    public RedisPool getRedisPool() {
        return this.redisPool;
    }
}
