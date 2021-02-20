package com.moliang.run.mnt.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Use 新建及修改item参数
 * @Author Chui moliang
 * @Date 2021/2/20 15:59
 * @Version 1.0
 */
@Data
public class SmsItemParam {

    @NotNull
    @ApiModelProperty(value = "应用名称", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(value = "部署端口", required = true)
    private Integer port;

    @ApiModelProperty("上传路径")
    private String uploadPath;

    @ApiModelProperty("部署路径")
    private String deployPath;

    @ApiModelProperty("备份路径")
    private String backupPath;

    @ApiModelProperty("启动脚本")
    private String startScript;

    @ApiModelProperty("部署脚本")
    private String deployScript;
}
