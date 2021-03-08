package com.moliang.run.quartz.config;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 16:52
 * @Version 1.0
 */
public enum JobResult {

    SUCCESS(true, "成功"),
    FAIL(false, "失败");

    private boolean code;

    private String message;

    JobResult(boolean code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
