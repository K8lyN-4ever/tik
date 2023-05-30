package edu.td.zy.tik_user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.td.zy.tik_user.mapper.UserMapper;
import edu.td.zy.tik_common.pojo.User;
import edu.td.zy.tik_user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/5 16:40
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
