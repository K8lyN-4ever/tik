package edu.td.zy.tik_comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.td.zy.tik_comment.mapper.CommentMapper;
import edu.td.zy.tik_comment.service.CommentService;
import edu.td.zy.tik_common.pojo.Comment;
import edu.td.zy.tik_common.redis.RedisService;
import org.springframework.stereotype.Service;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/31 10:26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final RedisService redis = RedisService.getInstance();

    private final String LIKES_NAMESPACE = "likes";
    private final String LIKES_KEY = "comment";

    private final String COMMENTS_NAMESPACE = "comments";
    private final String COMMENTS_KEY = "comment";

    private double zaddIncrNx(double score, String value) {
        return redis.zaddIncrNx(LIKES_NAMESPACE, LIKES_KEY, score, value);
    }

    @Override
    public double like(String uid) {
        return zaddIncrNx( 1, uid);
    }

    @Override
    public double cancelLike(String uid) {
        return zaddIncrNx( -1, uid);
    }

    @Override
    public double getLikes(String uid) {
        return redis.zscoreNx(LIKES_NAMESPACE, LIKES_KEY, uid);
    }

    @Override
    public double getComments(String uid) {
        return redis.zscoreNx(COMMENTS_NAMESPACE, COMMENTS_KEY, uid);
    }
}
