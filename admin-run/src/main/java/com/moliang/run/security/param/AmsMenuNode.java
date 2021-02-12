package com.moliang.run.security.param;

import com.moliang.run.model.AmsMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/1/5 17:52
 * @Version 1.0
 */
@Getter
@Setter
public class AmsMenuNode extends AmsMenu {
    @ApiModelProperty(value = "子菜单")
    private List<AmsMenuNode> children;
}