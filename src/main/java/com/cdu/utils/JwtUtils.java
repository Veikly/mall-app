package com.cdu.utils;

import com.cdu.commons.ServiceCode;
import com.cdu.commons.ServiceException;
import com.cdu.pojo.entity.User;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @ProjectName: cdu-app
 * @Titile: JwtUtils
 * @Author: Administrator
 * @Description: jwt工具类
 */
public class JwtUtils {
    //设置token的有效期：一周
    private static final long TOKEN_EXPIRE = 7*24*60*60*1000;
    //设置一个密钥\私钥,为了安全需要定期更换
    private static final String TOKEN_SECRET = "lj114445533@#";


    /**
     * 生成token
     * @param user 登录成功的用户对象
     * @return 身份
     */
    public static String generateToken(User user){
        //主体：终端，人/设备
        String subject = user.getUsername();
        //当前时间
        Date now = new Date();
        //租约到期时间
        long expireTime = now.getTime() + TOKEN_EXPIRE;
        //创建过期时间
        Date exp = new Date(expireTime);
        //生成token
        String token = Jwts.builder().setSubject(subject) //设置主体
                .setIssuedAt(now) //设置当前时间
                .setExpiration(exp) //设置过期时间
                .claim("id", user.getId()) // id
                .claim("username",user.getUsername()) //用户名称
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET) //算法+签名
                .compact();
        return token;
    }
    /**
     * 檢查token的合法性
     * @param token
     */
    public static Claims validateToken(String token) {
        //容器
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(TOKEN_SECRET)//设置签名
                    .parseClaimsJws(token) //设置解析的token
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ServiceException("解析token异常:token已经失效", ServiceCode.ERROR_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new ServiceException("解析token异常:不支持的token解析",ServiceCode.ERROR_TOKEN);
        } catch (MalformedJwtException e) {
            throw new ServiceException("解析token异常:非法token格式",ServiceCode.ERROR_TOKEN);
        } catch (SignatureException e) {
            throw new ServiceException("解析token异常：token的签名不合法",ServiceCode.ERROR_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new ServiceException("解析token异常：非法token参数",ServiceCode.ERROR_TOKEN);
        }
        return claims;
    }
    /**
     * 从token中获取用户信息
     * @param
     * @return
     */
    public static User parseToken(String token){
        //校验token
        Claims claims = validateToken(token);
        //获取id
        Integer id = Integer.valueOf(claims.get("id").toString());
        //获取员工名称
        String username = claims.get("username").toString();
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }
    public static void main(String[] args) {
        User user = new User();
        user.setId(1001);
        user.setUsername("rose");
        String token = generateToken(user);
        System.out.println(token);
    }
}