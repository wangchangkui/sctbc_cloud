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
     * 登录接口
     * @param student 实体
     * @return studentDto
     */
    StudentDto login(Student student);
}
