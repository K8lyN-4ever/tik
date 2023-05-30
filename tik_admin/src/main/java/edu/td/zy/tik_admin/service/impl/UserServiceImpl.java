package edu.td.zy.tik_admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.td.zy.tik_admin.mapper.UserMapper;
import edu.td.zy.tik_admin.pojo.User;
import edu.td.zy.tik_admin.service.UserService;
import edu.td.zy.tik_admin.utils.PhoneTableNameHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/5/10 11:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final List<String> PHONES = Arrays.asList("13", "14", "15", "17", "18");

    @Override
    public List<User> users() {
        List<User> result = new LinkedList<>();
        PHONES.forEach(phone -> {
            PhoneTableNameHandler.setData(phone);
            result.addAll(list());
            PhoneTableNameHandler.removeData();
        });
        return result;
    }
}
