package com.moliang.run.mnt.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/21 0:51
 * @Version 1.0
 */
@Data
public class SmsServerQueryParam {

    @ApiModelProperty("id范围最小值")
    private Long startId;

    @ApiModelProperty("id范围最大值")
    private Long toId;

    @ApiModelProperty("服务器名称")
    private String name;

    @ApiModelProperty("服务器ip")
    private String ip;

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
