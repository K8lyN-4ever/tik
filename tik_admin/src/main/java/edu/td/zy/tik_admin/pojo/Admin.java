package edu.td.zy.tik_admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2022/4/15 20:26
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @TableId(type = IdType.AUTO)
    private int id;
    private String nickname;
    private String account;
    private String password;

    @Override
    public String toString() {
        return "{\"account\":\"" + account + "\",\"nickname\":\"" + nickname + "\"}";
    }

}
