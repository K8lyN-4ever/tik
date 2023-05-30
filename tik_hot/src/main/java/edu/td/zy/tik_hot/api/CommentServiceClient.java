package edu.td.zy.tik_hot.api;

import edu.td.zy.tik_common.pojo.Comment;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author K8lyN
 * @date 2023年4月10日 12:25:01
 * @version 1.0
 */
@FeignClient(value = "tik-comment", path = "/api/v1/comment")
public interface CommentServiceClient {

    /**
     * 查询评论
     * @param timestamp 评论创建时间
     * @param uid 被评论媒体的uid
     * @param current 当前页数
     * @return 评论数据
     * */
    @GetMapping("/{timestamp}/uid/{uid}")
    StandardResponse<List<Comment>> queryComment(@PathVariable String uid, @PathVariable long timestamp, @RequestParam int current);
}
