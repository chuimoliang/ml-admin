package com.moliang.run.mnt.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/3 23:49
 * @Version 1.0
 */
@Data
public class SmsDeployHistoryQueryParam {

    @ApiModelProperty("部署ip")
    private String ip;

    @ApiModelProperty("项目名称关键字")
    private String keyword;

    @ApiModelProperty("起始日期")
    private Date startDate;

    @ApiModelProperty("最晚日期")
    private Date toDate;

}
