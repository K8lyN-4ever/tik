package edu.td.zy.tik_hot.service.impl;

import com.google.gson.Gson;
import edu.td.zy.tik_common.pojo.Comment;
import edu.td.zy.tik_common.pojo.Media;
import edu.td.zy.tik_common.pojo.User;
import edu.td.zy.tik_common.pojo.Video;
import edu.td.zy.tik_common.redis.RedisService;
import edu.td.zy.tik_common.utils.DateUtil;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import edu.td.zy.tik_hot.api.CommentServiceClient;
import edu.td.zy.tik_hot.api.UserServiceClient;
import edu.td.zy.tik_hot.api.VideoServiceClient;
import edu.td.zy.tik_hot.service.HotService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/10 15:27
 */
@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "hot")
public class HotServiceImpl implements HotService {

    private final String USER_NAMESPACE = "user";
    private final String COMMENT_NAMESPACE = "comment";
    private final String HOT_NAMESPACE = "hot";

    private final RedisService redis;
    private final UserServiceClient userClient;
    private final CommentServiceClient commentClient;
    private final VideoServiceClient videoClient;

    private int size;
    private List<String> dates;

    public void setSize(int size) {
        this.size = size;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    @Override
    public void generateHot() {
        for(String date: dates) {
            long timestamp = DateUtil.getTimestampFromDateString(date);
            StandardResponse<List<Video>> res = videoClient.getRandVideos(timestamp, size);
            if(res.ok()) {
                List<Video> videos = res.getData();
                setVideo(videos);
                for(Video video: videos) {
                    setLikesAndComments(video);
                    StandardResponse<User> userRes = userClient.getUser(video.getCreator());
                    if(userRes.ok()) {
                        setUser(userRes.getData().getPhone(), userRes.getData());
                    }

                    if(video.getComments() != 0) {
                        StandardResponse<List<Comment>> commentRes = commentClient.queryComment(video.getUid(), video.getTimestamp(), 1);
                        if(commentRes.ok()) {
                            List<Comment> comments = commentRes.getData();
                            setComment(video.getUid(), comments);
                            for(Comment comment: comments) {
                                setLikesAndComments(comment);
                                userRes = userClient.getUser(comment.getCreator());
                                if(userRes.ok()) {
                                    setUser(userRes.getData().getPhone(), userRes.getData());
                                }
                                if(comment.getComments() != 0) {
                                    List<Comment> subComments = comment.getComment();
                                    setComment(comment.getUid(), subComments);
                                    for(Comment subComment: subComments) {
                                        setLikesAndComments(subComment);
                                        if(subComment.getToUid() != null) {
                                            userRes = userClient.getUser(subComment.getToUid());
                                            if(userRes.ok()) {
                                                setUser(userRes.getData().getPhone(), userRes.getData());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0/15 * * * ?")
    public void generateHotWithParallelProcessing() {
        dates.parallelStream().forEach(date -> {
            long timestamp = DateUtil.getTimestampFromDateString(date);
            StandardResponse<List<Video>> res = videoClient.getRandVideos(timestamp, size);
            if(res.ok()) {
                List<Video> videos = res.getData();
                setVideo(videos);
                videos.parallelStream().forEach(video -> {
                    CompletableFuture.runAsync(() -> {
                        setUserAndComments(video);
                        setLikesAndComments(video);
                    });
                });
            }
        });
    }

    private void setUserAndComments(Video video) {
        StandardResponse<User> userRes = userClient.getUser(video.getCreator());
        if(userRes.ok()) {
            setUser(userRes.getData().getPhone(), userRes.getData());
        }
        if(video.getComments() != 0) {
            StandardResponse<List<Comment>> commentRes = commentClient.queryComment(video.getUid(), video.getTimestamp(), 1);
            if(commentRes.ok()) {
                List<Comment> comments = commentRes.getData();
                setComment(video.getUid(), comments);
                comments.parallelStream().forEach(comment -> {
                    CompletableFuture.runAsync(() -> {
                        setUserAndSubComments(comment);
                        setLikesAndComments(comment);
                    });
                });
            }
        }
    }

    private void setUserAndSubComments(Comment comment) {
        StandardResponse<User> userRes = userClient.getUser(comment.getCreator());
        if(userRes.ok()) {
            setUser(userRes.getData().getPhone(), userRes.getData());
        }
        if(comment.getComments() != 0) {
            List<Comment> subComments = comment.getComment();
            setComment(comment.getUid(), subComments);
            subComments.parallelStream().forEach(subComment -> {
                CompletableFuture.runAsync(() -> {
                    setLikesAndComments(subComment);
                    if(subComment.getToUid() != null) {
                        StandardResponse<User> toUserRes = userClient.getUser(subComment.getToUid());
                        if(toUserRes.ok()) {
                            setUser(toUserRes.getData().getPhone(), toUserRes.getData());
                        }
                    }
                });
            });
        }
    }

    private void setLikesAndComments(Media media) {
        redis.zaddNx("comments", media.getClass().getSimpleName().toLowerCase(), media.getComments(), media.getUid());
        redis.zaddNx("likes", media.getClass().getSimpleName().toLowerCase(), media.getLikes(), media.getUid());
    }

    private void setLikesAndComments(Comment comment) {
        redis.zaddNx("comments", "comment", comment.getComments(), comment.getUid());
        redis.zaddNx("likes", "comment", comment.getLikes(), comment.getUid());
    }



    private void setUser(String phone, User user) {
        redis.setValueNx(USER_NAMESPACE, phone, new Gson().toJson(user));
    }

    private void setComment(String parentUid, List<Comment> comments) {
        redis.setValue(COMMENT_NAMESPACE, parentUid, new Gson().toJson(comments));
    }

    private void setVideo(List<Video> videos) {
        redis.setValue(HOT_NAMESPACE, "hot", new Gson().toJson(videos));
    }

    private void setPicture() {

    }
}
