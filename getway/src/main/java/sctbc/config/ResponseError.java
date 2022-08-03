package sctbc.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 15:02:00
 */
@ControllerAdvice
public class ResponseError {

    @ResponseBody
    @ExceptionHandler
    public Map<String,Object> responseErrorMap(Exception e){
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("code",500);
        map.put("error",e.getMessage());
        return map;
    }
}
