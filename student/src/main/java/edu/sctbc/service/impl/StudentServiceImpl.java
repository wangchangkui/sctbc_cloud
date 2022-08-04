package edu.sctbc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.sctbc.config.RsaKey;
import edu.sctbc.dao.StudentMapper;
import edu.sctbc.pojo.Student;
import edu.sctbc.pojo.dto.StudentDto;
import edu.sctbc.service.StudentService;
import edu.sctbc.util.redis.RedisCommonKey;
import edu.sctbc.util.redis.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sctbc.util.Encoder;
import edu.sctbc.util.redis.RedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.time.LocalDateTime;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:35:00
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Autowired
    private RsaKey rsaKey;

    @Autowired
    private RedisPool redisPool;

    @Override
    public StudentDto login(Student student) {
        // 密码加密
        String password = student.getPassword();
        String encoderPassword = Encoder.encoder(password, rsaKey.getKey(), rsaKey.getIv());
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentId,student.getStudentId()).eq(Student::getPassword,encoderPassword);
        Student res = getOne(wrapper);
        // 生成token
        if(ObjectUtil.isNull(res)){
            throw new RuntimeException("没有该学生");
        }
        try(Jedis jedis=redisPool.getConnection()){
            StudentDto studentDto =  new StudentDto();
            // 判断数据库该用户是否登录 直接获取token
            String temp = jedis.get(RedisCommonKey.USER_ + res.getName());
            if(StringUtils.isNotBlank(temp)){
                jedis.del(RedisCommonKey.USER_+res.getName());
                jedis.del(RedisCommonKey.TOKEN_+temp);
            }
            // 存入新token
            String tokenPre = JSON.toJSONString(res);
            String token = TokenUtil.getToken(tokenPre);
            SetParams setParams = new SetParams();
            setParams.ex(RedisCommonKey.EXPIRE_ONE_DAY);
            jedis.set(RedisCommonKey.TOKEN_+token,tokenPre,setParams);
            // 多设置一个 可以快速删除
            jedis.set(RedisCommonKey.USER_+res.getName(),token,setParams);
            studentDto.setToken(token);
            studentDto.setExpire(LocalDateTime.now().plusDays(1));
            return studentDto;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
