package com.moliang.run.quartz.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 17:26
 * @Version 1.0
 */
@Data
public class QuartzJobParam {

    @ApiModelProperty("任务名称")
    @NotBlank
    private String jobName;

    @ApiModelProperty("Bean名称")
    @NotBlank
    private String beanName;

    @ApiModelProperty("cron表达式")
    @NotBlank
    private String cronExpression;

    @ApiModelProperty("状态 : 暂停或启用")
    @NotNull
    private Boolean status;

    @ApiModelProperty("执行方法名称")
    @NotBlank
    private String methodName;

    @ApiModelProperty("参数内容")
    private String params;

    @ApiModelProperty("任务描述")
    private String description;

    @ApiModelProperty("告警邮箱")
    private String email;

    @ApiModelProperty("子任务ID")
    private String subTask;

    @ApiModelProperty("失败后暂停")
    @NotNull
    private Boolean pauseAfterFailure;

}
