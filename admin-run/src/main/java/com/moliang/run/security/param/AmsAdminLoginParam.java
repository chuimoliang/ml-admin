package com.moliang.run.security.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @Use 登录接口参数类
 * @Author Chui moliang
 * @Date 2020/12/23 19:57
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AmsAdminLoginParam {

    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "密码",required = true)
    private String password;

}
