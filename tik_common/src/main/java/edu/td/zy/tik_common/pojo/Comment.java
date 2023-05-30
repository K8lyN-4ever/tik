package edu.td.zy.tik_common.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/26 10:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Comment extends Media{

    private String content;
    @TableField("parent_uid")
    private String parentUid;
    @TableField("to_uid")
    private String toUid;

    @TableField(exist = false)
    private transient String title;
    @TableField(exist = false)
    private transient String description;
    @TableField(exist = false)
    private List<Comment> comment;

    @Override
    public String toString() {
        String str;
        if(getComment() != null) {
            str = getComment().toString();
        }else {
            str = "null";
        }
        return "{\"uid\":\"" + getUid() + "\",\"content\":\"" + getContent() + "\",\"parentUid\":\"" + getParentUid() + "\",\"comments\":\"" + str + "\",\"creator\":\"" + getCreator() + "\",\"toUid\":\"" + getToUid() + "\",\"timestamp\":" + getTimestamp() + ",\"likes\":" + getLikes() + ",\"comments\":" + getComments() + "}";
    }

}
