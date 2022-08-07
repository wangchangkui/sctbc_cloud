package edu.sctbc.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sctbc.pojo.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:41:00
 */
@AllArgsConstructor
@NoArgsConstructor

public class StudentDto extends Student {

    /**
     * token
     */
    @Getter
    @Setter
    private String token;

    /**
     * token过期时间
     */
    @Getter
    @Setter
    private LocalDateTime expire;

    /**
     * 携带验证码
     */
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String code;


    public StudentDto(Student student){
        super(student.getId(),student.getName(),student.getStudentId(),student.getSex(),student.getPhone(),student.getBirthday(),student.getGraduationSchool(),student.getHome(),student.getParentNumber(),student.getParentName(),student.getEmail(),student.getWxId(),student.getWxName(),student.getQq());
    }
}
