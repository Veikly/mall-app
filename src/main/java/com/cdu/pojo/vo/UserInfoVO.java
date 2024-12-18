package com.cdu.pojo.vo;

import lombok.Data;

/**
 * @ProjectName: cdu-app
 * @Titile: UserInfoVO
 * @Author: Administrator
 * @Description: 个人资料VO
 */
@Data
public class UserInfoVO {
    private String username;
    private String phone;
    private String email;
    private Integer gender;
}