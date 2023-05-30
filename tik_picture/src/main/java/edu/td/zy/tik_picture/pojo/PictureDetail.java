package edu.td.zy.tik_picture.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/27 9:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDetail {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    @TableField("picture_uid")
    private String pictureUid;
    private String path;
}
