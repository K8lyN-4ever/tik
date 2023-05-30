package edu.td.zy.tik_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.td.zy.tik_common.pojo.Video;

/**
 * @author K8lyN
 * @date 2023年4月2日 10:39:16
 * @version 1.0
 * */
public interface VideoService extends IService<Video> {

    /**
     * 点赞
     * @param uid 被点赞的视频
     * @return 点赞后的点赞数
     * */
    double like(String uid);

    /**
     *
     * 取消点赞
     * @param uid 被取消的视频
     * @return 取消后的喜欢数
     * */
    double cancelLike(String uid);

    /**
     * 查询点赞量
     * @param uid 要查询的视频
     * @return 点赞量
     * */
    double getLikes(String uid);

    /**
     * 查询评论量
     * @param uid 要查询的视频
     * @return 评论量
     * */
    double getComments(String uid);
}
