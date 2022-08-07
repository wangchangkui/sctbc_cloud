package edu.sctbc.service.login;

import edu.sctbc.pojo.dto.StudentDto;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月07日 20:04:00
 */
public interface LoginInterFace {

    /**
     * 通用的登录返回结果
     * @return StudentDto
     */
    StudentDto login();
}
