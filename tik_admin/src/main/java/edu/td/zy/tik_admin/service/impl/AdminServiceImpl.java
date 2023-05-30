package edu.td.zy.tik_admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.td.zy.tik_admin.mapper.AdminMapper;
import edu.td.zy.tik_admin.pojo.Admin;
import edu.td.zy.tik_admin.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/5/11 10:18
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
