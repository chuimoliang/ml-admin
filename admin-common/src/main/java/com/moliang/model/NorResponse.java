package com.moliang.model;

import com.moliang.enums.ResponseCode;

/**
 * @Use 通用响应
 * @Author Chui moliang
 * @Date 2021/2/9 19:10
 * @Version 1.0
 */
public class NorResponse<T> {
    /**
     * 状态码
     */
    private long code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据封装
     */
    private T data;

    protected NorResponse() {
    }

    protected NorResponse(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> NorResponse<T> success(T data) {
        return new NorResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> NorResponse<T> success(T data, String message) {
        return new NorResponse<>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> NorResponse<T> failed(IErrorCode errorCode) {
        return new NorResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> NorResponse<T> failed(IErrorCode errorCode, String message) {
        return new NorResponse<>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> NorResponse<T> failed(String message) {
        return new NorResponse<>(ResponseCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> NorResponse<T> failed() {
        return failed(ResponseCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> NorResponse<T> validateFailed() {
        return failed(ResponseCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> NorResponse<T> validateFailed(String message) {
        return new NorResponse<>(ResponseCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> NorResponse<T> unauthorized(T data) {
        return new NorResponse<>(ResponseCode.UNAUTHORIZED.getCode(), ResponseCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> NorResponse<T> forbidden(T data) {
        return new NorResponse<>(ResponseCode.FORBIDDEN.getCode(), ResponseCode.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
