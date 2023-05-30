package edu.td.zy.tik_video.controller;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.td.zy.tik_common.pojo.Video;
import edu.td.zy.tik_common.redis.RedisService;
import edu.td.zy.tik_common.table_handler.DateTableNameHandler;
import edu.td.zy.tik_common.utils.UUID;
import edu.td.zy.tik_common.utils.media.Clip;
import edu.td.zy.tik_common.utils.response.ResponseStatus;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import edu.td.zy.tik_video.service.VideoService;
import edu.td.zy.tik_video.util.OSS;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/2 10:41
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/video")
public class VideoController {

    private final VideoService videoService;

    @Value("${video.path}")
    private String videoPath;
    @Value("${video.format}")
    private String videoFormat;


    @Value("${ali.oss.endpoint}")
    private String endpoint;
    @Value("${ali.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${ali.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${ali.oss.roleArn}")
    private String roleArn;
    @Value("${ali.oss.roleSessionName}")
    private String roleSessionName;
    @Value("${ali.oss.durationSeconds}")
    private String durationSeconds;
    @Value("${ali.oss.bucketName}")
    private String bucketName;

    @GetMapping(value = "/getToken")
    public String getToken() {
        try {
            return new StandardResponse<>(ResponseStatus.OK, "获取成功", OSS.getToken(endpoint, accessKeyId, accessKeySecret,
                                                                                            roleArn, roleSessionName, "", durationSeconds)).toString(true);
        }catch(ClientException e) {
            log.error(e.getErrCode() + e.getMessage());
            return StandardResponse.getSimpleFail("获取失败：" + e.getMessage());
        }
    }

    @GetMapping(value = "/getSign")
    public String getSign() {
        return new StandardResponse<>(ResponseStatus.OK, "获取成功", OSS.getSign(accessKeyId, accessKeySecret, endpoint, bucketName)).toString(true);
    }

    @PostMapping("/phone/{phone}/post")
    public String postVideo(@PathVariable String phone, String path, String title, String desc) {
        Video video = new Video();
        video.setPath(path);
        video.setDescription(desc);
        video.setTitle(title);
        video.setCreator(phone);
        video.setTimestamp(System.currentTimeMillis() / 1000);
        DateTableNameHandler.setData(video.getTimestamp());
        videoService.save(video);
        DateTableNameHandler.removeData();
        return StandardResponse.getSimpleSuccess("上传成功");
    }

    @PutMapping(value = "/phone/{phone}/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String postVideo(@RequestPart("file") MultipartFile file, @PathVariable String phone, String title, String desc) {
        if(file == null) {
            return StandardResponse.getSimpleFail("不能上传空文件");
        }
        try {
            String fileName = Clip.upload(file, UUID.getUUID().concat(videoFormat), videoPath);
            Video video = new Video();
            video.setCreator(phone);
            video.setPath(fileName);
            video.setTitle(title);
            video.setDescription(desc);
            video.setTimestamp(System.currentTimeMillis() / 1000);
            DateTableNameHandler.setData(video.getTimestamp());
            videoService.save(video);
            DateTableNameHandler.removeData();
            JsonObject res = new JsonObject();
            res.addProperty("uid", video.getUid());
            return new StandardResponse<>(ResponseStatus.CREATED, "发布成功", res).toString();
        }catch(IOException e) {
            log.error(e.getMessage());
            return StandardResponse.getSimpleFail("视频发布失败，请稍后再试");
        }
    }

    @GetMapping(value = "/uid/{uid}/get")
    public ResponseEntity<byte[]> getVideo(@PathVariable String uid, long timestamp, HttpServletRequest request) {
        DateTableNameHandler.setData(timestamp);
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Video::getUid, uid);
        byte[] res;
        HttpHeaders headers = new HttpHeaders();
        Video video = videoService.getOne(wrapper);
        DateTableNameHandler.removeData();
        if(video != null) {
            try {
                String fileName = video.getPath();
                res = Clip.download(fileName, videoPath);
                headers.setContentType(MediaType.parseMediaTypes("video/mp4").get(0));
                headers.setContentLength(res.length);
                String rangeHeader = request.getHeader("Range");
                System.out.println(rangeHeader);
                if(rangeHeader != null) {
                    long[] range = Clip.parseRange(rangeHeader, res.length);
                    headers.set("Content-Range", "bytes" + range[0] + "-" + range[1] + "/" + res.length);
                    headers.set("Content-Length", Long.toString(range[1] - range[0] + 1));
                    headers.set("Accept-Ranges", "bytes");
                    res = Arrays.copyOfRange(res, (int)range[0], (int)range[1] + 1);
                    return new ResponseEntity<>(res, headers, HttpStatus.PARTIAL_CONTENT);
                }
            }catch(FileNotFoundException e) {
                res = StandardResponse.getSimpleFail("文件不存在，请重试").getBytes();
                log.warn("尝试获取uid为 {} 的视频失败了，视频不存在", video.getUid());
            }catch(IOException e) {
                res = StandardResponse.getSimpleFail("网络错误，请稍后重试").getBytes();
                log.error("尝试获取uid为 {} 的视频失败了，未知错误", video.getUid());
            }
        }else {
            res = StandardResponse.getSimpleFail("请输入正确的uid").getBytes();
        }
        return new ResponseEntity<>(res, headers, HttpStatus.OK);
    }

    @GetMapping("/getVideos/{timestamp}/size/{size}")
    public StandardResponse<List<Video>> getRandVideos(@PathVariable long timestamp, @PathVariable int size) {
        DateTableNameHandler.setData(timestamp);
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("RAND()").last("LIMIT " + size);
        List<Video> lists = videoService.list(wrapper);
        DateTableNameHandler.removeData();
        return new StandardResponse<>(ResponseStatus.OK, "查询成功", lists);
    }

    @GetMapping("/get/uid/{uid}")
    public String get(@PathVariable String uid) {
        double likes = videoService.getLikes(uid);
        double comments = videoService.getComments(uid);
        Map<String, Double> map = new HashMap<>(2);
        map.put("likes", likes);
        map.put("comments", comments);
        return new StandardResponse<>(ResponseStatus.OK, "获取成功", new Gson().toJson(map)).toString(true);
    }

    @PutMapping("/like/uid/{uid}")
    public String like(@PathVariable String uid) {
        videoService.like(uid);
        return StandardResponse.getSimpleSuccess("点赞成功");
    }

    @PutMapping("/cancelLike/uid/{uid}")
    public String cancelLike(@PathVariable String uid) {
        videoService.cancelLike(uid);
        return StandardResponse.getSimpleSuccess("取消点赞成功");
    }
}
