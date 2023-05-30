package edu.td.zy.tik_picture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.td.zy.tik_picture.pojo.Picture;

/**
 * @author K8lyN
 * @date 2023年3月27日 09:48:47
 * @version 1.0
 * */
public interface PictureService extends IService<Picture> {

    /**
     * @param uid 要删除的动态id
     * @return 是否成功删除
     * */
    boolean deletePost(String uid);
}
