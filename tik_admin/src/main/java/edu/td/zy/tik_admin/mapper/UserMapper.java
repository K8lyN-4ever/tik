package edu.td.zy.tik_admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.td.zy.tik_admin.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author K8lyN
 * @date 2023年5月10日 11:48:31
 * @version 1.0
 * */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
