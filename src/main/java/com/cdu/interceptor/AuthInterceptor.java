package com.cdu.interceptor;

import com.cdu.commons.MallConstants;
import com.cdu.commons.ServiceCode;
import com.cdu.commons.ServiceException;
import com.cdu.utils.JwtUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: cdu-app
 * @Titile: AuthInterceptor
 * @Author: Administrator
 * @Description: token拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {
    //前置拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取用户身份
        String token = request.getHeader(MallConstants.TOKEN_SIGN);
        if (!StringUtils.hasLength(token)) {
            throw new ServiceException("token不能为空", ServiceCode.ERROR_TOKEN);
        }
        //校验
        JwtUtils.validateToken(token);
        return true;//放行
    }
}