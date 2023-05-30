package edu.td.zy.tik_admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.td.zy.tik_admin.pojo.User;

import java.util.List;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/5/10 11:49
 */
public interface UserService extends IService<User> {

    /**
     * 返回所有库中的用户
     * @return 包含所有用户的集合
     * */
    List<User> users();
}
