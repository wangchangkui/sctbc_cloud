package edu.sctbc.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.sctbc.pojo.teacher.Teacher;
import lombok.Data;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月12日 10:48:00
 */
@Data
@TableName("teacher_class")
public class TeachClass {
    @TableId(value = "class_id", type = IdType.AUTO)
    private Long classId;
    @TableField(value = "class_name")
    private String className;
    @TableField("teacher_Id")
    private String teacherId;

    @TableField(exist = false)
    private Teacher teacher;
}
