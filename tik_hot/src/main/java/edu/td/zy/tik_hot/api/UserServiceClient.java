package edu.td.zy.tik_hot.api;


import edu.td.zy.tik_common.pojo.User;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author K8lyN
 * @date 2023年4月10日 12:24:49
 * @version 1.0
 */
@FeignClient(value = "tik-user", path = "/api/v1/user")
public interface UserServiceClient {

    /**
     * 查询用户信息
     * @param phone 要查询用户的手机号
     * @return 用户信息
     * */
    @GetMapping("/{phone}")
    StandardResponse<User> getUser(@PathVariable String phone);
}
