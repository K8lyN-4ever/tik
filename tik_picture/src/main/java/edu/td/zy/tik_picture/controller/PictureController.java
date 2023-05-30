package edu.td.zy.tik_picture.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.td.zy.tik_common.table_handler.DateTableNameHandler;
import edu.td.zy.tik_common.table_handler.PhoneTableNameHandler;
import edu.td.zy.tik_picture.pojo.Picture;
import edu.td.zy.tik_picture.pojo.PictureDetail;
import edu.td.zy.tik_picture.service.PictureDetailService;
import edu.td.zy.tik_picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/10 16:36
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/picture")
public class PictureController {

    private final PictureService pictureService;
    private final PictureDetailService pictureDetailService;

    @Value("${image.path}")
    private String imagePath;

    @GetMapping("/{phone}/hello")
    public String test(@PathVariable String phone, Picture picture) {
        PhoneTableNameHandler.setData(picture.getCreator());
        picture.setTimestamp(System.currentTimeMillis() / 1000);
        pictureService.save(picture);
        QueryWrapper<Picture> wrapper = new QueryWrapper<>();
        wrapper.eq("creator", picture.getCreator());
        Picture p = pictureService.getOne(wrapper);
        DateTableNameHandler.setData(picture.getTimestamp());
        PictureDetail pd = new PictureDetail();
        pd.setPictureUid(p.getUid());
        pd.setPath("/hello");
        pictureDetailService.save(pd);
        PhoneTableNameHandler.removeData();
        DateTableNameHandler.removeData();
        return "successful";
    }
}
