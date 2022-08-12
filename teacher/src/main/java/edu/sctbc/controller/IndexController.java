package edu.sctbc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月12日 14:38:00
 */
@RequestMapping("/index")
@RestController
public class IndexController {

    @GetMapping("/index")
    public String indexAction() {
        return "index";
    }
}
