package edu.td.zy.tik_gateway.controller;

import edu.td.zy.tik_common.utils.response.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/14 9:36
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/gateway")
public class GatewayController {

    @RequestMapping("/fallback")
    public String fallback() {
        return StandardResponse.getSimpleFail("该服务暂时不可用");
    }
}
