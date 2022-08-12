package edu.sctbc.pojo.teacher;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月12日 10:55:00
 */
@Data
@TableName("teacher")
public class Teacher {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "teacher_id")
    private String teacherId;

    @TableField(value = "teacher_name")
    private String teacherName;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "email")
    private String email;

    @TableField(value = "collegeId")
    private Long collegeId;

}
