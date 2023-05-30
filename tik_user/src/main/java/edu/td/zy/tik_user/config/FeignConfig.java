package edu.td.zy.tik_user.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/17 16:11
 */
@Configuration
public class FeignConfig {


    @Bean
    public Encoder formEncoder() {
        return new SpringFormEncoder();
    }

}
