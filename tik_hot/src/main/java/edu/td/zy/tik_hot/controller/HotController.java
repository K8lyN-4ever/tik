package edu.td.zy.tik_hot.controller;

import edu.td.zy.tik_common.redis.RedisService;
import edu.td.zy.tik_common.utils.response.ResponseStatus;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import edu.td.zy.tik_hot.service.impl.HotServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/9 19:26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hot")
public class HotController {

    private final RedisService redis;
    private final HotServiceImpl hotService;

    @GetMapping("/generateHotWithParallelProcessing")
    public String t() {
        hotService.generateHotWithParallelProcessing();
        return StandardResponse.getSimpleSuccess("生成成功");
    }

    @GetMapping("/generateHot")
    public String tt() {
        hotService.generateHot();
        return StandardResponse.getSimpleSuccess("生成成功");
    }

    @GetMapping("/get")
    public String getHot() {
        return new StandardResponse<>(ResponseStatus.OK, "查询成功", redis.getValue("hot", "hot")).toString(true);
    }
}
