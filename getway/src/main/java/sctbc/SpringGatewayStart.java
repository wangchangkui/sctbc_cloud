package sctbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 11:58:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringGatewayStart {
    public static void main(String[] args) {
        SpringApplication.run(SpringGatewayStart.class,args);
    }
}
