package edu.sctbc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.util.ResponseResult;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 14:54:00
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/test")
    public ResponseResult<String> test(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseResult.success("success");
    }
}
