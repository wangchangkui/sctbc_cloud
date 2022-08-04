package edu.sctbc.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月04日 16:39:00
 */
@ControllerAdvice
public class ResponseError {

    /**
     * 全局参数验证返回值
     *
     * @param ex 异常
     * @return 返回值
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ResponseResult<?> validationException(Exception ex) {
        return ResponseResult.error(ResponseType.EXCEPTION,ex.getMessage());
    }

}
