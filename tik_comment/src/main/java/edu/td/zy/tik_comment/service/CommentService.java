package edu.td.zy.tik_comment.service;


import com.baomidou.mybatisplus.extension.service.IService;
import edu.td.zy.tik_common.pojo.Comment;

/**
 * @author K8lyN
 * @date 2023年3月31日 10:25:55
 * @version 1.0
 * */
public interface CommentService extends IService<Comment> {

    /**
     * 点赞
     * @param uid 被点赞的评论
     * @return 点赞后的点赞数
     * */
    double like(String uid);

    /**
     *
     * 取消点赞
     * @param uid 被取消的评论
     * @return 取消后的喜欢数
     * */
    double cancelLike(String uid);

    /**
     * 查询点赞量
     * @param uid 要查询的评论
     * @return 点赞量
     * */
    double getLikes(String uid);

    /**
     * 查询评论量
     * @param uid 要查询的评论
     * @return 评论量
     * */
    double getComments(String uid);


}
