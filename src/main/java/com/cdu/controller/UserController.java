package com.cdu.controller;

import com.cdu.commons.R;
import com.cdu.commons.ServiceCode;
import com.cdu.commons.ServiceException;
import com.cdu.pojo.dto.UserLoginDTO;
import com.cdu.pojo.dto.UserRegDTO;
import com.cdu.pojo.entity.User;
import com.cdu.pojo.vo.LoginUserVO;
import com.cdu.pojo.vo.UserInfoVO;
import com.cdu.service.UserService;
import com.cdu.commons.MallConstants;
import com.cdu.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: cdu-app
 * @Titile: UserController
 * @Author: Administrator
 * @Description: 用户控制器
 */
@RestController//返回的都是数据，不再是视图名
@RequestMapping("/api/user")//一级映射目录
@Slf4j//日志注解
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("reg")//注册
    public R<Void> register(@Validated UserRegDTO userRegDTO) {
        log.debug("用户注册信息:{}", userRegDTO);
        userService.reg(userRegDTO);
        return R.ok("注册成功");
    }

    @PostMapping("login")
    public R<LoginUserVO> login(@Validated UserLoginDTO loginDTO) {
        log.debug("登录的信息:{}", loginDTO);
        //调用业务层方法
        LoginUserVO loginUserVO =  userService.login(loginDTO);
        return R.ok("登录成功", loginUserVO);
    }

    /**
     * 查询个人资料
     * @param request web请求
     * @return 个人资料
     */
    @GetMapping("userInfo")
    public R<UserInfoVO> getUserInfo(HttpServletRequest request){
        //从请求中获取身份
        String token = request.getHeader(MallConstants.TOKEN_SIGN);
        //解析token
        User user = JwtUtils.parseToken(token);
        UserInfoVO userInfo = userService.getUserInfo(user);
        return R.ok("查询个人资料成功",userInfo);
    }

    /**
     * 查询个人资料
     * @param request web请求
     * @return 个人资料
     */
    @PutMapping("avatar")
    public R<String> upload(MultipartFile avatar,HttpServletRequest request){
        //从请求中获取身份
        String token = request.getHeader(MallConstants.TOKEN_SIGN);
        //解析token
        User user = JwtUtils.parseToken(token);
        String avatarPath = userService.upload(avatar,user);
        return R.ok("上传成功",avatarPath);
    }


}