package com.moliang.enums;

import com.moliang.model.IErrorCode;

/**
 * @Use 响应码
 * @Author Chui moliang
 * @Date 2021/2/9 19:12
 * @Version 1.0
 */
public enum ResponseCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private final long code;
    private final String message;

    ResponseCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(long code) {
        switch ((int) code) {
            case 200 : {
                return ResponseCode.SUCCESS.getMessage();
            }
            case 500 : {
                return ResponseCode.FAILED.getMessage();
            }
            case 404 : {
                return ResponseCode.VALIDATE_FAILED.getMessage();
            }
            case 401 : {
                return ResponseCode.UNAUTHORIZED.getMessage();
            }
            case 403 : {
                return ResponseCode.FORBIDDEN.getMessage();
            }
            default : {
                return  "unknown";
            }
        }
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
