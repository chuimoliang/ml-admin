package com.moliang.run.mnt.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Use 查询项目参数
 * @Author Chui moliang
 * @Date 2021/2/20 1:38
 * @Version 1.0
 */
@Data
public class SmsItemQueryParam {

    @ApiModelProperty("id范围最小值")
    private Long startId;

    @ApiModelProperty("id范围最大值")
    private Long toId;

    @ApiModelProperty("项目名字")
    private String name;

    @ApiModelProperty("端口范围最小值")
    private Integer startPort;

    @ApiModelProperty("端口范围最大值")
    private Integer toPort;

    @ApiModelProperty("上传路径")
    private String uploadPath;

    @ApiModelProperty("部署路径")
    private String deployPath;

    @ApiModelProperty("备份路径")
    private String backupPath;

    @ApiModelProperty("创建者")
    private String createBy;

    @ApiModelProperty("更新者")
    private String updateBy;

    @ApiModelProperty("更新时间范围最小值")
    private Date startUpdateTime;

    @ApiModelProperty("更新时间范围最大值")
    private Date toUpdateTime;

    @ApiModelProperty("创建时间范围最小值")
    private Date startCreateTime;

    @ApiModelProperty("创建时间范围最大值")
    private Date toCreateTime;
}
