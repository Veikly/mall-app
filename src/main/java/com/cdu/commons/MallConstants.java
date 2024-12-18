package com.cdu.commons;

public interface MallConstants {

    /**
     * 密码加密的散列次数
     */
    Integer HASH_TIME = 1024;  // 或其他合适的散列次数

    /**
     * 用户删除状态：0-未删除
     */
    Integer IS_NOT_DELETE = 0;

    /**
     * 登录失败最大尝试次数
     */
    Integer MAX_LOGIN_ATTEMPTS = 5;

    /**
     * 登录失败锁定时间（分钟）
     */
    Integer LOGIN_LOCK_MINUTES = 30;

    /**
     * Token在请求头中的标识
     */
    public static final String TOKEN_SIGN = "Authorization";

    /**
     * JWT密钥
     */
    String JWT_SECRET_KEY = "your-secret-key-here";

    /**
     * JWT过期时间（毫秒）- 24小时
     */
    long JWT_EXPIRATION = 86400000;

}