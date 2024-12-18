package com.cdu.utils;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
/**
 * @ProjectName: cdu-app
 * @Titile: MD5Utils
 * @Author: Administrator
 * @Description: 密码加密工具类
 */
public class MD5Utils {
    /**
     * 密码加密
     * @param password 明文
     * @param salt 盐值
     * @param times 散列次数
     * @return 密文
     */
    public static String enctype(String password,String salt, int times){
        password = password + salt;
//散列
        for (int i = 0; i < times; i++) {
            password =
                    DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        }
        return password;
    }
    public static void main(String[] args) {
        String password = "123456";
        String salt = UUID.randomUUID().toString().replace("-","");
        System.out.println("盐值："+salt);
        String newPwd = enctype(password, salt, 5);
        System.out.println("密文: "+newPwd);
    }
}