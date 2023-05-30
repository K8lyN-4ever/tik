package edu.td.zy.tik_comment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import edu.td.zy.tik_comment.service.CommentService;
import edu.td.zy.tik_common.pojo.Comment;
import edu.td.zy.tik_common.table_handler.DateTableNameHandler;
import edu.td.zy.tik_common.utils.response.ResponseStatus;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/1 10:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PutMapping("/uid/{uid}")
    public String comment(@PathVariable String uid, String phone, String content, String toUid, Long timestamp) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setParentUid(uid);
        comment.setCreator(phone);
        comment.setToUid(toUid);
        comment.setTimestamp(System.currentTimeMillis() / 1000);
        DateTableNameHandler.setData(timestamp);
        boolean res = commentService.save(comment);
        DateTableNameHandler.removeData();
        if(res) {
            return StandardResponse.getSimpleSuccess(ResponseStatus.CREATED, "发表成功");
        }else {
            return StandardResponse.getSimpleFail("发表失败");
        }
    }

    @GetMapping("/{timestamp}/uid/{uid}")
    public StandardResponse<List<Comment>> queryComment(@PathVariable String uid, @PathVariable long timestamp, @RequestParam int current) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Comment::getParentUid, uid);
        wrapper.lambda().isNull(Comment::getToUid);
        wrapper.lambda().orderByDesc(Comment::getComments);
        DateTableNameHandler.setData(timestamp);
        IPage<Comment> page = new Page<>(current, 10);
        commentService.page(page, wrapper);
        List<Comment> comments = page.getRecords();
        for (Comment tmp : comments) {
            if (tmp.getComments() != 0) {
                page = new Page<>(1, 3);
                wrapper.clear();
                wrapper.lambda().eq(Comment::getParentUid, tmp.getUid());
                wrapper.lambda().orderByDesc(Comment::getComments);
                tmp.setComment(commentService.page(page, wrapper).getRecords());
            }
        }
        DateTableNameHandler.removeData();
        return new StandardResponse<>(ResponseStatus.OK, "查询成功", comments);
    }

    @PutMapping("/like/uid/{uid}")
    public String like(@PathVariable String uid) {
        commentService.like(uid);
        return StandardResponse.getSimpleSuccess("点赞成功");
    }

    @PutMapping("/cancelLike/uid/{uid}")
    public String cancelLike(@PathVariable String uid) {
        commentService.cancelLike(uid);
        return StandardResponse.getSimpleSuccess("取消点赞成功");
    }

    @GetMapping("/get/uid/{uid}")
    public String getLikesAndComments(@PathVariable String uid) {
        double likes = commentService.getLikes(uid);
        double comments = commentService.getComments(uid);
        Map<String, Double> map = new HashMap<>(2);
        map.put("likes", likes);
        map.put("comments", comments);
        return new StandardResponse<>(ResponseStatus.OK, "获取成功", new Gson().toJson(map)).toString(true);
    }
}
