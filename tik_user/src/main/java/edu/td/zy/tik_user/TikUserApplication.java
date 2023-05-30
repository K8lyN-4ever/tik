package edu.td.zy.tik_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author K8lyN
 * @date 2023年3月6日 10:36:52
 * @version 1.0
 */

@SpringBootApplication
@EnableFeignClients
public class TikUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(TikUserApplication.class, args);

    }
}
