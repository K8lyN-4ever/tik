package edu.td.zy.tik_picture.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author K8lyN
 * @date 2023年3月10日 17:00:01
 * @version 1.0
 * */
@FeignClient(value = "tik-user", path = "/user")
public interface UserServiceClient {


}
