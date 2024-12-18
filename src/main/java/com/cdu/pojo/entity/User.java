package com.cdu.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ProjectName: cdu-app
 * @Titile: User
 * @Author: Administrator
 * @Description: 用户实体类
 */
@Data
public class User {
    //用户id
    private Integer id;
    //用户名
    private String username;
    //密码
    private String password;
    //盐值
    private String salt;
    //是否删除
    private Integer isDelete;
    //电话号码
    private String phone;
    //邮箱
    private String email;
    //性别
    private Integer gender;
    //头像
    private String avatar;
    //创建用户
    private String createdUser;
    //创建时间
    private Date createdTime;
    //最后修改用户
    private String modifiedUser;
    //最后修改时间
    private Date modifiedTime;
    //登录失败次数
    private Integer loginAttempts;
    //最后登录失败时间
    private Date lastFailedLogin;
    //最后登录时间
    private Date lastLoginTime;
    //最后登录IP
    private String lastLoginIp;

}