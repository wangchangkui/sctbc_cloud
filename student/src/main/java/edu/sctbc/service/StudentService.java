package edu.sctbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.sctbc.pojo.student.Student;
import edu.sctbc.pojo.student.dto.StudentDto;
import edu.sctbc.pojo.student.reqentity.QrCode;
import edu.sctbc.pojo.student.reqentity.WxLoginEntity;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:34:00
 */
public interface StudentService extends IService<Student> {

    /**
     * 生成一个登录二维码
     *
     * @return 二维码图片
     */
    QrCode<String> createQr();

    /**
     * 检查验证码
     * @param code 验证码
     * @return boolean
     */
    boolean checkCode(String code);

    /**
     * 登录接口
     * @param student 实体
     * @return studentDto
     * @throws IllegalAccessException 参数异常
     */
    StudentDto login(StudentDto student) throws IllegalAccessException;

    /**
     * 生成验证码
     * @return url
     */
    String verify();

    /**
     * 根据微信的id来登录用户
     * @param entity 微信临时token
     * @return StudentDto
     */
    StudentDto wxLogin(WxLoginEntity entity);
}
