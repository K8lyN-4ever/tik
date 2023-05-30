package edu.td.zy.tik_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author K8lyN
 * @date 2023年3月4日 16:45:01
 * @version 1.0
 */
@SpringBootApplication
@EnableConfigServer
public class TikConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(TikConfigApplication.class, args);
    }

}
