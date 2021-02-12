package com.moliang.run.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/11 18:03
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebLog {

    /** 数据库主键 **/
    private Long id;

    /** 操作描述 **/
    private String description;

    /** 操作用户 **/
    private String username;

    /** 操作时间 **/
    private Long startTime;

    /** 消耗时间 **/
    private Integer spendTime;

    /** 根路径 **/
    private String basePath;

    /** URI **/
    private String uri;

    /** URL **/
    private String url;

    /** 请求类型 **/
    private String method;

    /** IP地址 **/
    private String ip;

    /** 请求参数 **/
    private Object parameter;

    /** 返回结果 **/
    private Object result;

    /** 插入日期 **/
    private Date createTime;

}
