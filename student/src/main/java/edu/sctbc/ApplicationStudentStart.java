package edu.sctbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 14:17:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"edu.sctbc.*"})
public class ApplicationStudentStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStudentStart.class,args);
    }
}
