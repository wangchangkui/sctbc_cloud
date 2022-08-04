package edu.sctbc.controller;

import edu.sctbc.pojo.Student;
import edu.sctbc.pojo.dto.StudentDto;
import edu.sctbc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.sctbc.util.ResponseResult;

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
    public ResponseResult<StudentDto> login(@RequestBody Student student){
        return ResponseResult.success(studentService.login(student));
    }


}
