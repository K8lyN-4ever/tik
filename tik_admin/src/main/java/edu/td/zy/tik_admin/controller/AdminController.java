package edu.td.zy.tik_admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.td.zy.tik_admin.pojo.Admin;
import edu.td.zy.tik_admin.pojo.User;
import edu.td.zy.tik_admin.service.AdminService;
import edu.td.zy.tik_admin.service.UserService;
import edu.td.zy.tik_admin.utils.PhoneTableNameHandler;
import edu.td.zy.tik_admin.utils.response.ResponseStatus;
import edu.td.zy.tik_admin.utils.response.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/5/10 11:52
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("/get")
    public String getUser() {
        PhoneTableNameHandler.setData("15832306301");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getPhone, "15832306301");
        return userService.getOne(wrapper).toString();
    }

    @GetMapping("/login")
    public String login(String account, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getAccount, account);
        if(adminService.getOne(wrapper).getPassword().equals(password)) {
            return StandardResponse.getSimpleSuccess("登录成功");
        }else {
            return StandardResponse.getSimpleFail("登录失败");
        }
    }

    @GetMapping("/getAdmins")
    public String admins() {
        return new StandardResponse<>(ResponseStatus.OK, "获取成功", adminService.list()).toString(true);
    }

    @GetMapping("/addAdmin")
    public String addAdmin(Admin admin) {
        adminService.save(admin);
        return StandardResponse.getSimpleSuccess("添加成功");
    }

    @GetMapping("/delAdmin")
    public String delAdmin(String account) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getAccount, account);
        adminService.remove(wrapper);
        return StandardResponse.getSimpleSuccess("删除成功");
    }

    @GetMapping("/getUsers")
    public String users() {
        return new StandardResponse<>(ResponseStatus.OK, "获取成功", userService.users()).toString(true);
    }

    @GetMapping("/addUser")
    public String addUser(User user) {
        PhoneTableNameHandler.setData(user.getPhone());
        user.setAvatar("default.png");
        userService.save(user);
        PhoneTableNameHandler.removeData();
        return StandardResponse.getSimpleSuccess("添加成功");
    }

    @GetMapping("/delUser")
    public String delUser(String phone) {
        PhoneTableNameHandler.setData(phone);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getPhone, phone);
        userService.remove(wrapper);
        PhoneTableNameHandler.removeData();
        return StandardResponse.getSimpleSuccess("删除成功");
    }

    @GetMapping("/setAdmin")
    public String setAdmin(Admin admin) {
        UpdateWrapper<Admin> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(Admin::getAccount, admin.getAccount());
        if(!"".equals(admin.getNickname())) {
            wrapper.lambda().set(Admin::getNickname, admin.getNickname());
        }
        if(!"".equals(admin.getPassword())) {
            wrapper.lambda().set(Admin::getPassword, admin.getPassword());
        }
        adminService.update(wrapper);
        return StandardResponse.getSimpleSuccess("修改成功");
    }

    @GetMapping("/setUser")
    public String setUser(User user) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(User::getPhone, user.getPhone());
        if(!"".equals(user.getNickname())) {
            wrapper.lambda().set(User::getNickname, user.getNickname());
        }
        if(!"".equals(user.getPassword())) {
            wrapper.lambda().set(User::getPassword, user.getPassword());
        }
        PhoneTableNameHandler.setData(user.getPhone());
        userService.update(wrapper);
        PhoneTableNameHandler.removeData();
        return StandardResponse.getSimpleSuccess("修改成功");
    }
}
