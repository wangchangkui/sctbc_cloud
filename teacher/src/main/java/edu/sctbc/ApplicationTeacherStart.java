package edu.sctbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月12日 14:31:00
 */
@EnableDiscoveryClient
@ComponentScan({"edu.sctbc.*"})
@SpringBootApplication
public class ApplicationTeacherStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTeacherStart.class, args);
    }
}
