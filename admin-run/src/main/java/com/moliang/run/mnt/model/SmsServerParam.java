package com.moliang.run.mnt.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @Use 服务器实体类
 * @Author Chui moliang
 * @Date 2021/2/20 18:39
 * @Version 1.0
 */
@Data
public class SmsServerParam {

    @ApiModelProperty("服务器名称")
    private String name;

    @ApiModelProperty("服务器ip")
    private String ip;

    @ApiModelProperty("服务器端口")
    private Integer port;

    @ApiModelProperty("服务器账号")
    private String account;

    @ApiModelProperty("服务器密码")
    private String password;

}
