package edu.sctbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.sctbc.pojo.Student;
import edu.sctbc.pojo.dto.StudentDto;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:34:00
 */
public interface StudentService extends IService<Student> {


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
}
