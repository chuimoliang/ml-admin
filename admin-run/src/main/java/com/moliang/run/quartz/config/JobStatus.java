package com.moliang.run.quartz.config;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 16:42
 * @Version 1.0
 */
public enum JobStatus {

    ENABLE(true, "新建"),
    CANCEL(false, "已完成");

    private boolean code;

    private String message;

    JobStatus(boolean code, String message) {
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
