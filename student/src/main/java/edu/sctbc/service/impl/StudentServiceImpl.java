package edu.sctbc.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.sctbc.config.RsaKey;
import edu.sctbc.dao.StudentMapper;
import edu.sctbc.pojo.Student;
import edu.sctbc.pojo.dto.StudentDto;
import edu.sctbc.service.StudentService;
import edu.sctbc.service.login.abstracts.impl.TextLogin;
import edu.sctbc.service.login.abstracts.impl.WxLogin;
import edu.sctbc.util.redis.RedisCommonKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sctbc.util.redis.RedisPool;
import redis.clients.jedis.Jedis;


import java.awt.*;

import static edu.sctbc.util.redis.RedisCommonKey.THREE_MINUTES;

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

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public String verify() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30,4,3);
        lineCaptcha.setBackground(new Color(245,247,250));
        Jedis jedis=redisPool.getConnection();
        RedisCommonKey.setValues(RedisCommonKey.CAPTCHA+lineCaptcha.getCode(),lineCaptcha.getCode(),THREE_MINUTES,false,jedis);
        return "data:image/png;base64,"+lineCaptcha.getImageBase64();
    }

    @Override
    public StudentDto wxLogin(String wxId) {
        WxLogin wxLogin = new WxLogin(redisPool, studentMapper, wxId);
        return wxLogin.login();
    }


    @Override
    public boolean checkCode(String code) {
        try( Jedis jedis=redisPool.getConnection()){
            if(StringUtils.isNotBlank(jedis.get(RedisCommonKey.CAPTCHA + code))){
                jedis.del(RedisCommonKey.CAPTCHA+code);
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public StudentDto login(StudentDto student) throws IllegalAccessException {
        if(!verifyUser(student)){
            throw new IllegalAccessException("请检查参数的完整性");
        }
        if(checkCode(student.getCode())){
            throw new RuntimeException("验证码不存在");
        }
        TextLogin textLogin = new TextLogin(rsaKey, redisPool, student, studentMapper);
        return textLogin.login();
    }


    public boolean verifyUser(Student student){
        return !StringUtils.isBlank(student.getPassword()) && !StringUtils.isBlank(student.getStudentId());
    }



}
