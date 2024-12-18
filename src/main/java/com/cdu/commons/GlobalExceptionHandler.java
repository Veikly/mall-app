package com.cdu.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;

/**
 * @ProjectName: cdu-app
 * @Titile: GlobalExceptionHandler
 * @Author: Administrator
 * @Description: 统一的异常处理
 */
@RestControllerAdvice//异常增强注解
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理ServiceException的方法
     */
    @ExceptionHandler
    public R<Void> serviceExceptionHandle(ServiceException e) {
        log.debug("--->serviceExceptionHandler:{}", e.getMessage());
        return R.fail(e);
    }

    /**
     * 处理错误请求的异常方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public R<Void> BadRequestHandle(NestedRuntimeException e) {
        log.debug("--->NestedRuntimeException：{}", e.getMessage());
        return R.fail(new ServiceException("请求的方式或者数据错误，请检查", ServiceCode.REQUEST_ERROR));
    }

    /**
     * 处理校验出现异常的方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public R<Void> validateExceptionHandle(BindException e) {
        log.debug("--->validateExceptionHandle: {}", e.getFieldError().getDefaultMessage());
//将获取到的校验注解上的错误信息，进行封装，返回给前端
        return R.fail(new ServiceException(e.getFieldError().getDefaultMessage(), ServiceCode.DATA_CHECK_ERROR));
    }

    /**
     * 处理servlet出现的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public R<Void> servletExceptionHandle(ServletException e) {
        log.debug("--->servletExceptionHandle:{}", e.getMessage());
//异常类型属于没有找到handler的异常
        if (e instanceof NoHandlerFoundException) {
            return R.fail(new ServiceException("请求的路径有误，请检查", ServiceCode.PATH_NOT_FOUND));
        }
        return R.fail(new
                ServiceException(e.getMessage(), ServiceCode.REQUEST_ERROR));
    }

    /**
     * 处理未知的异常
     * 如果出现以下的这个异常处理信息，后端一定要及时处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public R<Void> notKnowExceptionHandle(Throwable e) {
//打印异常信息
        e.printStackTrace();//这个方式是堵塞式，不建议使用,项目上线后一定要注释掉或者删掉
        log.info("未知异常：{}", e.getMessage());
        return R.fail(new ServiceException("系统异常,请联系管理员", ServiceCode.UN_KNOW_ERROR));
    }
}