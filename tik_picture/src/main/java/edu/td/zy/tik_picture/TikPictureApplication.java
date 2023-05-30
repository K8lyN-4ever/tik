package edu.td.zy.tik_picture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author K8lyN
 * @date 2023年3月10日 16:41:02
 * @version 1.0
 * */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class TikPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(TikPictureApplication.class, args);
    }

}
