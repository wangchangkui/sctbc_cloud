package edu.sctbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 10:37:00
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationNacosStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationNacosStart.class,args);
    }
}
