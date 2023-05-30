package edu.td.zy.tik_hot.api;

import edu.td.zy.tik_common.pojo.Video;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author K8lyN
 * @date 2023年4月10日 12:25:24
 * @version 1.0
 */
@FeignClient(value = "tik-video", path = "/api/v1/video")
public interface VideoServiceClient {

    /**
     * 随机查询video数据
     * @param timestamp 查询表时间戳
     * @param size 查询数量
     * @return 查询结果
     * */
    @GetMapping("/getVideos/{timestamp}/size/{size}")
    StandardResponse<List<Video>> getRandVideos(@PathVariable long timestamp, @PathVariable int size);
}
