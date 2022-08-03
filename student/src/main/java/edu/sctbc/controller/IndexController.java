package edu.sctbc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 14:25:00
 */

@RestController
@Scope
public class IndexController {

    @Value("${config.id}")
    private String id;


    @GetMapping("/index")
    public String indexName(){
        return id;
    }

}
