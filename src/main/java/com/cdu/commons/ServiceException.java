package com.cdu.commons;
import lombok.Getter;
/**
 * @ProjectName: cdu-app
 * @Titile: ServiceException
 * @Author: Administrator
 * @Description: 自定义业务异常
 */
@Getter
public class ServiceException extends RuntimeException {
    private ServiceCode serviceCode;
    public ServiceException(String message, ServiceCode serviceCode) {
        super(message);
        this.serviceCode = serviceCode;
    }
}