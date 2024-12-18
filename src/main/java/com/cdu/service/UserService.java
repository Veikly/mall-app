package com.cdu.service;

import com.cdu.pojo.dto.UserLoginDTO;
import com.cdu.pojo.dto.UserRegDTO;
import com.cdu.pojo.entity.User;
import com.cdu.pojo.vo.LoginUserVO;
import com.cdu.pojo.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * 用户注册
     * @param userRegDTO 用户注册信息
     */
    void reg(UserRegDTO userRegDTO);


    /**
     * 登录
     * @param loginDTO 登录信息
     * @return 登陆成功后的用户数据
     */
    LoginUserVO login(UserLoginDTO loginDTO);

    /**
     * 查询个人资料
     * @param user 登录成功的用户
     * @return 个人资料
     */
    UserInfoVO  getUserInfo(User user);

    /**
     * 上传头像
     * @param avatar 头像
     * @param user 登录的用户
     */
    String upload(MultipartFile avatar, User user);

}