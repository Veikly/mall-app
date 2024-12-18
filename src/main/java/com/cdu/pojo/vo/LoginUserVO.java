package com.cdu.pojo.vo;

import lombok.Data;

/**
 * @ProjectName: cdu-app
 * @Titile: LoginUserVO
 * @Author: Administrator
 * @Description: 登录成功后的用户VO
 */
@Data
public class LoginUserVO {
    private Integer id;
    private String username;
    private String avatar;
    // 前后端分离，后端不会保存用户的登录状态，需要返回用户的身份
    private String token;
}