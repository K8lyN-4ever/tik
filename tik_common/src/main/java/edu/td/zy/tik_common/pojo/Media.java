package edu.td.zy.tik_common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/26 10:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Media {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    private String title;
    private String description;
    private String creator;
    private long timestamp;
    private int likes;
    private int comments;
    @Version
    private Integer version;
}
