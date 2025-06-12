package org.lw.vms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.lw.vms.entity.User;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */


/**
 * 用户数据访问接口 (Mybatis Mapper)。
 * 负责与数据库进行用户数据的增删改查操作。
 */
@Mapper
public interface UserMapper {


        /**
         * 根据用户名查询用户。
         * 用于登录和注册时检查用户名是否存在。
         *
         * @param username 用户名
         * @return 对应的用户对象，如果不存在则返回 null
         */
        User findByUsername(String username);

        /**
         * 插入新的用户记录。
         *
         * @param user 用户对象，包含要插入的用户信息。
         * 在插入成功后，Mybatis 会将生成的 `user_id` 填充回 `user` 对象的 `userId` 属性中。
         * @return 影响的行数 (通常为 1 表示插入成功)
         */
        int insertUser(User user);
}
