package edu.sctbc.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.sctbc.config.RsaKey;
import edu.sctbc.config.WxProperties;
import edu.sctbc.dao.StudentMapper;
import edu.sctbc.pojo.student.Student;
import edu.sctbc.pojo.student.dto.StudentDto;
import edu.sctbc.pojo.student.reqentity.QrCode;
import edu.sctbc.pojo.student.reqentity.WxLoginEntity;
import edu.sctbc.service.StudentService;
import edu.sctbc.service.login.abstracts.impl.TextLogin;
import edu.sctbc.service.login.abstracts.impl.WxLogin;
import edu.sctbc.util.DateUtil;
import edu.sctbc.util.QrUtil;
import edu.sctbc.util.redis.RedisCommonKey;
import edu.sctbc.util.redis.RedisPool;
import edu.sctbc.util.redis.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static edu.sctbc.util.redis.RedisCommonKey.THREE_MINUTES;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:35:00
 */

@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Autowired
    private RsaKey rsaKey;

    @Autowired
    private RedisPool redisPool;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private WxProperties wxProperties;

    @Autowired
    private QrUtil qrUtil;

    @Override
    public String verify() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30, 4, 3);
        lineCaptcha.setBackground(new Color(245, 247, 250));
        try {
            Jedis jedis = redisPool.getConnection();
            RedisCommonKey.setValues(RedisCommonKey.CAPTCHA + lineCaptcha.getCode(), lineCaptcha.getCode(), THREE_MINUTES, false, jedis);
            return "data:image/png;base64," + lineCaptcha.getImageBase64();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public StudentDto wxLogin(WxLoginEntity entity) {
        // 远程调用微信服务器 获取openId
        String wxIdFormRemote = WxLogin.getWxIdFormRemote(entity.getWxId(), wxProperties.getAppId(), wxProperties.getSecret(), wxProperties.getGrantType());
        if (!StringUtils.isNotBlank(wxIdFormRemote)) {
            throw new RuntimeException("过期的临时token，请求微信服务器失败");
        }
        WxLogin wxLogin = new WxLogin(redisPool, studentMapper, wxIdFormRemote);
        StudentDto login = wxLogin.login();
        login.setWxId(wxIdFormRemote);
        login.setWxName(entity.getWxName());
        login.setWxAvatar(entity.getWxAvatar());
        // 更新微信头像
        try {
            log.info("更新用户头像已经微信信息");
            studentMapper.update(login, new LambdaQueryWrapper<Student>().eq(Student::getId, login.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return login;
    }


    @Override
    public QrCode<String> createQr() {
        LocalDateTime now = LocalDateTime.now();
        // 生成一个临时的token 然后放入redis 并生成验证码
        String times = now.format(DateTimeFormatter.ofPattern(DateUtil.yyyy_MM_dd_HH_mm_ss));
        String token = TokenUtil.getToken(times);
        return new QrCode<>(qrUtil.createQr(token), token, LocalDateTime.now().plusSeconds((long) THREE_MINUTES * 2));
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
