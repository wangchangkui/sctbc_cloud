package edu.sctbc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月04日 16:08:00
 */
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "res")
public class RsaKey {

    private String key;

    private String iv;

}
