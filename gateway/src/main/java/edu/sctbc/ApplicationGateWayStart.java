package edu.sctbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月12日 16:17:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan({"edu.sctbc.*", "edu.sctbc.*"})
public class ApplicationGateWayStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationGateWayStart.class, args);
    }

}
