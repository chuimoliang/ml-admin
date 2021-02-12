package com.moliang.exception;

import com.moliang.model.IErrorCode;

/**
 * @Use 接口异常类
 * @Author Chui moliang
 * @Date 2020/12/24 11:17
 * @Version 1.0
 */
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    /**
     * 通过IErrorCode初始化异常类,因为是自己抛出,没有必要堆栈跟踪
     * @param e
     */
    public ApiException(IErrorCode e) {
        super(e.getMessage(), null, false, false);
        this.errorCode = e;
    }

    public ApiException(String message) {
        super(message, null, false, false);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
