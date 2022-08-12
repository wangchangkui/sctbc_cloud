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
 * @createTime 2022年08月12日 10:41:00
 */
@Data
@TableName("college")
public class College {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "college_name")
    private String collegeName;

    @TableField("teacher_id")
    private String teacherId;

    @TableField(exist = false)
    private Teacher teacher;
}
