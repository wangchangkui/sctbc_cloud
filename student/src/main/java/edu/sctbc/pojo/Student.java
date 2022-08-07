package edu.sctbc.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 16:59:00
 */
@Data
@TableName("student")
public class Student {


    public Student() {
    }

    public Student(Long id, String name, String studentId, Integer sex, String phone, LocalDateTime birthday, String graduationSchool, String home, String parentNumber, String parentName, String email, String wxId,String wxName, String qq) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.sex = sex;
        this.phone = phone;
        this.birthday = birthday;
        this.graduationSchool = graduationSchool;
        this.home = home;
        this.parentNumber = parentNumber;
        this.parentName = parentName;
        this.email = email;
        this.wxId = wxId;
        this.qq = qq;
        this.wxName = wxName;
    }

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("student_id")
    private String studentId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField("password")
    private String password;

    @TableField("sex")
    private Integer sex;

    @TableField("phone")
    private String phone;

    @TableField("birthday")
    private LocalDateTime birthday;

    @TableField("graduation_school")
    private String graduationSchool;

    @TableField("home")
    private String home;

    @TableField("parent_number")
    private String parentNumber;

    @TableField("parent_name")
    private String parentName;

    @TableField("email")
    private String email;

    @TableField("wx_id")
    private String wxId;

    @TableField("wx_code")
    private String wxName;

    @TableField("qq")
    private String qq;





}
