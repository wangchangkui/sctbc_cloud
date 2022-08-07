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
    private String code;
}
