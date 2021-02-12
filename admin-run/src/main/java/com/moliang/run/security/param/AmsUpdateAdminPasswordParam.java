package com.moliang.run.security.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @Use 更新密码参数类
 * @Author Chui moliang
 * @Date 2020/12/23 19:57
 * @Version 1.0
 */
@Getter
@Setter
public class AmsUpdateAdminPasswordParam {

    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotEmpty
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
