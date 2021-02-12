package com.moliang.run.log.model;

import lombok.Data;

import java.util.Date;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 2:04
 * @Version 1.0
 */
@Data
public class WebLogDto {
    /** 数据库主键 **/
    private Long id;

    /** 操作描述 **/
    private String description;

    /** 操作用户 **/
    private String username;

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

    /** 地址 */
    private String address;

    /** 浏览器  */
    private String browser;

    /** 请求参数 **/
    private Object parameter;

    /** 返回结果 **/
    private Object result;

    /** 插入日期 **/
    private Date createTime;
}
