package com.moliang.run.mnt.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/22 17:26
 * @Version 1.0
 */
@Data
public class SmsDeployQueryParam {

    @ApiModelProperty("起始编号")
    private Long startId;
    @ApiModelProperty("最大编号")
    private Long toId;

    @ApiModelProperty("项目名称")
    private String itemName;

    @ApiModelProperty("服务器名称")
    private String serverIp;

    @ApiModelProperty("部署状态")
    private String status;

    @ApiModelProperty("起始创建时间")
    private Date startCreateTime;
    @ApiModelProperty("最晚创建时间")
    private Date toCreateTime;

    @ApiModelProperty("创建人")
    private String createBy;
}
