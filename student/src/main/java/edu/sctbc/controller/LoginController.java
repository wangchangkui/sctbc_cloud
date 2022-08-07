package edu.sctbc.controller;

import edu.sctbc.pojo.dto.StudentDto;
import edu.sctbc.service.StudentService;
import edu.sctbc.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 14:54:00
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private StudentService studentService;


    @PostMapping("/login")
    public ResponseResult<StudentDto> login(@RequestBody StudentDto student) throws IllegalAccessException {
        return ResponseResult.success(studentService.login(student));
    }

    @GetMapping("/verify")
    public ResponseResult<String>verifyCode(){
        return ResponseResult.success(studentService.verify());
    }


}
