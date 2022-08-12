package edu.sctbc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.sctbc.pojo.student.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:34:00
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
