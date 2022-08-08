package edu.sctbc.controller;

import edu.sctbc.pojo.dto.StudentDto;
import edu.sctbc.pojo.reqentity.QrCode;
import edu.sctbc.pojo.reqentity.WxLoginEntity;
import edu.sctbc.service.StudentService;
import edu.sctbc.util.QrUtil;
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
    private QrUtil qrUtil;
    @Autowired
    private StudentService studentService;


    /**
     * 轮询获取验证码的内容
     * @param qr 二维码临时token
     * @return 内容
     */
    @GetMapping("/getQrContent/{qr}")
    public ResponseResult<String> getQrContent(@PathVariable String qr){
        return ResponseResult.success(qrUtil.getQrData(qr));
    }


    @GetMapping("/getQr")
    public ResponseResult<QrCode> createQr(){
        return ResponseResult.success(studentService.createQr());
    }

    @PostMapping("/wxLogin")
    public ResponseResult<StudentDto> getWxId(@RequestBody WxLoginEntity wxLogin){
        return ResponseResult.success(studentService.wxLogin(wxLogin));
    }



    @PostMapping("/login")
    public ResponseResult<StudentDto> login(@RequestBody StudentDto student) throws IllegalAccessException {
        return ResponseResult.success(studentService.login(student));
    }

    @GetMapping("/verify")
    public ResponseResult<String>verifyCode(){
        return ResponseResult.success(studentService.verify());
    }


}
