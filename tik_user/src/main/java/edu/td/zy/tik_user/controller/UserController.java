package edu.td.zy.tik_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.td.zy.tik_common.exception.CircuitBreakerException;
import edu.td.zy.tik_common.utils.response.ResponseStatus;
import edu.td.zy.tik_common.utils.media.Avatar;
import edu.td.zy.tik_common.utils.response.StandardResponse;
import edu.td.zy.tik_common.pojo.User;
import edu.td.zy.tik_user.service.UserService;
import edu.td.zy.tik_common.table_handler.PhoneTableNameHandler;
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


/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/5 16:44
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    @Value("${avatar.path}")
    private String avatarPath;

    @Value("${avatar.default}")
    private String avatarDefault;

    @Value("${avatar.format}")
    private String avatarFormat;

    private final UserService userService;

    @PutMapping(value = "/phone/{phone}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadAvatar(@RequestPart("file") MultipartFile file, @PathVariable String phone) {
        if(file == null) {
            return StandardResponse.getSimpleFail("不能上传空文件");
        }
        try {
            Avatar.upload(file, phone.concat(avatarFormat), avatarPath);
            PhoneTableNameHandler.setData(phone);
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.lambda().eq(User::getPhone, phone).set(User::getAvatar, phone.concat(avatarFormat));
            userService.update(wrapper);
            PhoneTableNameHandler.removeData();
            return StandardResponse.getSimpleSuccess(ResponseStatus.CREATED, "上传成功");
        }catch(IOException e) {
            log.error(e.getMessage());
            return StandardResponse.getSimpleFail("头像上传失败，请稍后重试");
        }
    }

    @GetMapping("/phone/{phone}/avatar")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable String phone) {
        PhoneTableNameHandler.setData(phone);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getPhone, phone);
        byte[] res;
        HttpHeaders headers = new HttpHeaders();
        User user = userService.getOne(wrapper);
        PhoneTableNameHandler.removeData();
        if(user != null) {
            try {
                String fileName = user.getAvatar();
                res = Avatar.download(fileName, avatarPath);
                headers.setContentType(MediaType.IMAGE_PNG);
            }catch(FileNotFoundException e) {
                res = StandardResponse.getSimpleFail("文件不存在，请重试").getBytes();
                log.warn("手机号为 {} 的用户尝试获取不存在的头像", phone);
            }catch(IOException e) {
                res = StandardResponse.getSimpleFail("网络错误，请稍后重试").getBytes();
                log.error("手机号为 {} 的用户尝试获取头像时发生了错误", phone);
            }
        }else {
            res = StandardResponse.getSimpleFail("请输入正确的手机号").getBytes();
        }
        // 该http请求的状态，非该操作的状态。所以一般为200
        return new ResponseEntity<>(res, headers, HttpStatus.OK);
    }

    @GetMapping("/test/{id}")
    public long test(@PathVariable int id) throws CircuitBreakerException {
        if(1 == id) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new CircuitBreakerException();
            }
        }
        return System.currentTimeMillis();
    }

    @GetMapping("/phone/{phone}")
    public String getUser(@PathVariable String phone) {
        PhoneTableNameHandler.setData(phone);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getPhone, phone);
        User user = userService.getOne(wrapper);
        PhoneTableNameHandler.removeData();
        return new StandardResponse<>(ResponseStatus.OK, "获取成功", user).toString(true);
    }

    @GetMapping("/{phone}")
    public StandardResponse<User> getUserWithEntity(@PathVariable String phone) {
        PhoneTableNameHandler.setData(phone);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getPhone, phone);
        User user = userService.getOne(wrapper);
        PhoneTableNameHandler.removeData();
        return new StandardResponse<>(ResponseStatus.OK, "获取成功", user);
    }

}
