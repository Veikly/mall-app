package com.cdu.service.impl;

import com.cdu.commons.MallConstants;
import com.cdu.commons.ServiceCode;
import com.cdu.commons.ServiceException;
import com.cdu.mapper.UserMapper;
import com.cdu.pojo.dto.UserLoginDTO;
import com.cdu.pojo.dto.UserRegDTO;
import com.cdu.pojo.entity.User;
import com.cdu.pojo.vo.LoginUserVO;
import com.cdu.pojo.vo.UserInfoVO;
import com.cdu.service.UserService;
import com.cdu.utils.JwtUtils;
import com.cdu.utils.MD5Utils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


import java.util.UUID;

/**
 * @ProjectName: cdu-app
 * @Titile: UserServiceImpl
 * @Author: Administrator
 * @Description: 用户业务类
 */
@Service //语义化，代表的是业务层
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void reg(UserRegDTO userRegDTO) {
        log.debug("开始用户注册��程，用户名：{}", userRegDTO.getUsername());

        // 检查用户名是否已存在（包括已禁用的用户）
        User existUser = userMapper.findByUsername(userRegDTO.getUsername());
        log.debug("检查用户名是否存在：{}", existUser != null ? "已存在" : "不存在");

        if (existUser != null) {
            throw new ServiceException("用户名已存在", ServiceCode.REGISTER_ERROR);
        }

        // 判断密码是否一致
        if (!userRegDTO.getPassword().equals(userRegDTO.getRePassword())) {
            throw new ServiceException("两次输入的密码不一致", ServiceCode.REGISTER_ERROR);
        }

        try {
            // 密码加密
            String salt = UUID.randomUUID().toString().replace("-", "");
            String password = MD5Utils.enctype(userRegDTO.getPassword(), salt,
                    MallConstants.HASH_TIME);

            // 封装用户信息
            User user = new User();
            user.setUsername(userRegDTO.getUsername());
            user.setPassword(password);
            user.setSalt(salt);
            user.setIsDelete(MallConstants.IS_NOT_DELETE);
            // 设置默认值
            user.setPhone("");  // 默认空字符串
            user.setEmail("");  // 默认空字符串
            user.setGender(0);  // 默认性别
            user.setAvatar("");  // 默认头像
            user.setCreatedUser(userRegDTO.getUsername());
            user.setModifiedUser(userRegDTO.getUsername());

            // 执行注册
            log.debug("开始插入用户数据");
            int result = userMapper.insert(user);
            log.debug("用户数据插入结果：{}", result);

            if (result != 1) {
                throw new ServiceException("注册失败，请稍后再试", ServiceCode.REGISTER_ERROR);
            }
        } catch (ServiceException e) {
            throw e;  // 直接抛出业务异常
        } catch (Exception e) {
            log.error("注册失败", e);
            throw new ServiceException("注册失败，服务器异常", ServiceCode.REGISTER_ERROR);
        }

        log.debug("用户注册成功");
    }

    @Override
    public LoginUserVO login(UserLoginDTO loginDTO) {
        //根据用户名查询用户
        User user = userMapper.findByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new ServiceException("登录失败:用户名不存在", ServiceCode.DATA_NOT_FOUND);
        }
        //用户存在
        //将登录密码进行加密
        String loginPwd = MD5Utils.enctype(loginDTO.getPassword(), user.getSalt(), MallConstants.HASH_TIME);
        //判断密码
        if (!loginPwd.equals(user.getPassword())) {
            throw new ServiceException("登录失败:密码错误", ServiceCode.LOGIN_ERROR);
        }
        //密码ok的
        //封装登录成功后的用户VO
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setUsername(user.getUsername());
        loginUserVO.setId(user.getId());
        loginUserVO.setAvatar(user.getAvatar());//头像的路径
        //生成用户的身份
        String token = JwtUtils.generateToken(user);
        //身份
        loginUserVO.setToken(token);
        return loginUserVO;
    }

    @Override
    public UserInfoVO getUserInfo(User user) {
        //根据用户名查询信息
        User loginUser = userMapper.findByUsername(user.getUsername());
        //封装个人资料
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(loginUser.getUsername());
        userInfoVO.setPhone(loginUser.getPhone());
        userInfoVO.setEmail(loginUser.getEmail());
        userInfoVO.setGender(loginUser.getGender());
        return userInfoVO;
    }

    //注入保存头像的路径
    @Setter
    @Value("${avatar.save.path}")
    private String saveDir;

    //todo 为了性能，考虑使用多线程，进行异步操作，但是要考虑事务：使用原子化
    @Override
    public String upload(MultipartFile avatar, User user) {
        if (avatar == null || avatar.isEmpty()) {
            throw new ServiceException("头像不能为空", ServiceCode.AVATAR_IS_NULL);
        }
        //获取文件名
        String filename = avatar.getOriginalFilename();
        log.debug("上传的文件名：{}", filename);
        //UUID+截取后缀
        filename = UUID.randomUUID().toString().replace("-", "")
                + filename.substring(filename.lastIndexOf("."));
        log.debug("上传的文件新名：{}", filename);
        //创建保存的父目录
        File parentDir = new File(saveDir);
        if (!parentDir.exists()) {
            //逐级创建
            parentDir.mkdirs();
        }
        //保存文件
        File file = new File(saveDir, filename);
        try {
            avatar.transferTo(file);
        } catch (IOException e) {
            log.debug("上传文件失败：{}", e.getMessage());
            throw new ServiceException("上传文件失败", ServiceCode.FILE_UPLOAD_ERROR);
        }
        //封装用户数据
        //设置头像名称
        user.setAvatar(filename);
        //更新人
        user.setModifiedUser(user.getUsername());
        //更新时间，直接在数据库设置即可
        //调用持久层进行头像的更新
        int result = 0;
        try {
            result = userMapper.setAvatar(user);
        } catch (Exception e) {
            //更新失败
            //删除已经上传的头像
            if (file.exists()) {
                file.delete();
            }
            throw new ServiceException("上传头像失败:数据库执行SQL语句失败", ServiceCode.FILE_UPLOAD_ERROR);
        }
        //SQL语句执行成功，但是结果不对
        if (result != 1) {
            //更新失败
            //删除已经上传的头像
            if (file.exists()) {
                file.delete();
            }
            throw new ServiceException("上传头像失败:数据库更新失败", ServiceCode.FILE_UPLOAD_ERROR);
        }
        return filename;

    }
}