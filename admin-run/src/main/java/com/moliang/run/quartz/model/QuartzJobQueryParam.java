package com.moliang.run.quartz.model;

import cn.hutool.core.date.DateTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 18:43
 * @Version 1.0
 */
@Data
public class QuartzJobQueryParam {

    @ApiModelProperty("任务名称")
    private String keyword;

    @ApiModelProperty("最早时间")
    private DateTime startTime;

    @ApiModelProperty("最晚时间")
    private DateTime toTime;

}
