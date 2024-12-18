package com.cdu.commons;

import lombok.Getter;

/**
 * 业务码
 *
 * @Author: whj
 * @Date: 2024/12/17 9:09
 * @Version 1.0
 */
@Getter// 公开外部访问的getter
public enum ServiceCode {
    /**
     * 请求错误
     */
    OK(2000),//操作成功
    /**
     * 登录失败
     */
    LOGIN_ERROR(4001),
    /**
     * 注册失败
     */
    REGISTER_ERROR(4002),
    /**
     * 数据不存在
     */
    DATA_NOT_FOUND(4003),
    /**
     * 修改失败
     */
    UPDATE_ERROR(4004),
    /**
     * 删除失败
     */
    DELETE_ERROR(4005),
    /**
     * 新增失败
     */
    INSERT_ERROR(4006),
    /**
     * 请求错误
     */
    REQUEST_ERROR(4007),
    /**
     * 数据校验错误
     */
    DATA_CHECK_ERROR(4008),

    /**
     * 路径未找到
     */
    PATH_NOT_FOUND(4009),

    /**
     * 未知错误
     */
    UN_KNOW_ERROR(4010),

    ERROR_TOKEN(4011),//token错误

    AVATAR_IS_NULL(40012),//头像为空
    FILE_UPLOAD_ERROR(40013);//上传失败


    private int code;

    private ServiceCode(int code) {
    }
}
