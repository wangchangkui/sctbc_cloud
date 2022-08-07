package edu.sctbc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月07日 21:38:00
 */
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "wx")
public class WxProperties {

    private String appId;

    private String secret;

    private String grantType;
}
