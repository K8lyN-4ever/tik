package edu.td.zy.tik_user.api;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author K8lyN
 * @date 2023年3月10日 17:10:44
 * @version 1.0
 * */

@FeignClient(value = "tik-picture", path = "/picture")
public interface PictureServiceClient {

    /**
     * 上传头像
     * @param file 要上传的头像
     * @param fileName 上传的图片文件名
     * @return 头像名
     * */
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Headers("Content-Type: multipart/form-data")
    String upload(@RequestPart("file") MultipartFile file, @RequestParam("fileName") String fileName);

}
