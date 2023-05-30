package edu.td.zy.tik_admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/5/10 11:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    private String phone;
    private String password;
    private String nickname;
    private String avatar;
    private String gender;
    private int birthday;
    @Version
    private Integer version;

    @Override
    public String toString() {
        if("F".equals(gender)) {
            return "{" +
                    "\"phone\":\"" + phone + "\"," +
                    "\"nickname\":\"" + nickname + "\"," +
                    "\"avatar\":\"" + avatar + "\"," +
                    "\"gender\":\"女\"" +
                    "}";
        }else {
            return "{" +
                    "\"phone\":\"" + phone + "\"," +
                    "\"nickname\":\"" + nickname + "\"," +
                    "\"avatar\":\"" + avatar + "\"," +
                    "\"gender\":\"男\"" +
                    "}";
        }

    }

}
