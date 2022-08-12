package edu.sctbc.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 15:33:00
 */

@Configuration
public class LoadBalance {


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
