package edu.sctbc.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 16:59:00
 */
@Data
@TableName("student")
public class Student {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("student_id")
    private String studentId;

    @TableField("password")
    private String password;

    @TableField("sex")
    private Integer sex;

    @TableField("phone")
    private String phone;

    @TableField("birthday")
    private String birthday;

    @TableField("graduationSchool")
    private String graduationSchool;

    @TableField("home")
    private String home;

    @TableField("parent_number")
    private String parentNumber;

    @TableField("parent_name")
    private String parentName;

    @TableField("email")
    private String email;

    @TableField("wx")
    private String wx;

    @TableField("qq")
    private String qq;





}
