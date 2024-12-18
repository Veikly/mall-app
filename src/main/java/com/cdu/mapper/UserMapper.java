package com.cdu.mapper;

import com.cdu.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    /**
     * 新增用户
     *
     * @param user 用户
     * @return
     */
    int insert(User user);

    /**
     * 根据用户名查询用户（包括已禁用的用户）
     *
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(String username);

    /**
     * 根据用户名查询未禁用的用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User findActiveUserByUsername(String username);

    /**
     * 根据用户名查询用户详细信息
     *
     * @param username 用户名
     * @return 用户详细信息
     */
    User findDetailsByUsername(String username);


    /**
     * 设置头像
     *
     * @param user 用户包含了头像
     * @return
     */
    int setAvatar(User user);
}
