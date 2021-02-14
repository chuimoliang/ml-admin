package com.moliang.run.log.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Use 查询日志参数
 * @Author Chui moliang
 * @Date 2021/2/14 0:46
 * @Version 1.0
 */
@Data
public class LogQueryParam {

    @ApiModelProperty("接口说明")
    private String description;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("所在地址")
    private String address;

    @ApiModelProperty("接口uri")
    private String uri;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("查询的最早时间")
    private Date startTime;

    @ApiModelProperty("查询的最晚时间")
    private Date toTime;

    @ApiModelProperty("浏览器")
    private String browser;
}
