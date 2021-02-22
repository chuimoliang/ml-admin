package com.moliang.run.mnt.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/22 1:14
 * @Version 1.0
 */
@Data
public class SmsDeployParam {

    @NotNull
    @ApiModelProperty("应用编号")
    private String appId;

    @NotNull
    @ApiModelProperty("服务器编号")
    private String serverId;
}
