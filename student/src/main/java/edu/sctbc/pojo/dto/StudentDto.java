package edu.sctbc.pojo.dto;

import edu.sctbc.pojo.Student;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:41:00
 */

public class StudentDto extends Student {

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private LocalDateTime expire;
}
