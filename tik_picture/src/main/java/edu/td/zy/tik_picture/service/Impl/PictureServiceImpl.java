package edu.td.zy.tik_picture.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.td.zy.tik_common.table_handler.DateTableNameHandler;
import edu.td.zy.tik_picture.mapper.PictureMapper;
import edu.td.zy.tik_picture.pojo.Picture;
import edu.td.zy.tik_picture.pojo.PictureDetail;
import edu.td.zy.tik_picture.service.PictureDetailService;
import edu.td.zy.tik_picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/27 9:49
 */
@Service
@RequiredArgsConstructor
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    private final PictureDetailService pdService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePost(String uid) {
        Picture picture = getById(uid);
        DateTableNameHandler.setData(picture.getTimestamp());
        QueryWrapper<PictureDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("picture_uid", uid);
        pdService.remove(wrapper);
        removeById(picture);
        return true;
    }
}
