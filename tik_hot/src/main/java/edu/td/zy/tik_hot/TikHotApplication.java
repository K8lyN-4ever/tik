package edu.td.zy.tik_hot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author K8lyN
 * @date 2023年4月9日 19:49:10
 * @version 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class TikHotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TikHotApplication.class, args);
    }

}
