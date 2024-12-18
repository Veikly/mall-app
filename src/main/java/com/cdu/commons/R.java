package com.cdu.commons;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ProjectName: cdu-app
 * @Titile: R
 * @Author: Administrator
 * @Description: 返回数据
 */
@Data
@Accessors(chain = true)//支持连缀书写
public class R<T> {
    //业务码
    private int code;
    //提示信息
    private String message;
    //数据
    private T data;

    /**
     * 操作成功，不携带数据
     */
    public static <Void> R<Void> ok(String message) {
        return new R<Void>()
                .setCode(ServiceCode.OK.getCode())
                .setMessage(message);
    }

    /**
     * 操作成功，携带数据
     */
    public static <T> R<T> ok(String message, T data) {
        return new R<T>().setCode(ServiceCode.OK.getCode())
                .setMessage(message)
                .setData(data);
    }

    /**
     * 操作失败，因为异常
     */
    public static <Void> R<Void> fail(ServiceException e) {
        return new R<Void>()
                .setCode(e.getServiceCode().getCode())
                .setMessage(e.getMessage());
    }
}