package com.moliang.run.security.controller;

import com.moliang.model.DataPage;
import com.moliang.model.NorResponse;
import com.moliang.run.model.AmsMenu;
import com.moliang.run.security.param.AmsMenuNode;
import com.moliang.run.security.service.AmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/1/5 17:42
 * @Version 1.0
 */
@Controller
@Api(tags = "AmsMenu 后台系统菜单管理")
@RequestMapping("/menu")
@ResponseBody
public class AmsMenuController {

    @Autowired
    private AmsMenuService menuService;

    @ApiOperation(value = "添加后台菜单")
    @PostMapping(value = "/create")
    public NorResponse create(@RequestBody AmsMenu menu) {
        int count = menuService.create(menu);
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation(value = "修改后台菜单")
    @PostMapping(value = "/update/{id}")
    public NorResponse update(@PathVariable Long id, @RequestBody AmsMenu menu) {
        int count = menuService.update(id, menu);
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation(value = "根据ID获取菜单详情")
    @GetMapping(value = "/{id}")
    public NorResponse<AmsMenu> getItem(@PathVariable Long id) {
        AmsMenu menu = menuService.getItem(id);
        return NorResponse.success(menu);
    }

    @ApiOperation(value = "根据ID删除后台菜单")
    @PostMapping(value = "/delete/{id}")
    public NorResponse delete(@PathVariable Long id) {
        int count = menuService.delete(id);
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation(value = "分页查询后台菜单")
    @GetMapping(value = "/list/{parentId}")
    public NorResponse<DataPage<AmsMenu>> list(@PathVariable Long parentId,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AmsMenu> menuList = menuService.list(parentId, pageSize, pageNum);
        return NorResponse.success(DataPage.restPage(menuList));
    }

    @ApiOperation(value = "树形结构返回所有菜单列表")
    @GetMapping(value = "/treeList")
    public NorResponse<List<AmsMenuNode>> treeList() {
        List<AmsMenuNode> list = menuService.treeList();
        return NorResponse.success(list);
    }

    @ApiOperation(value = "修改菜单显示状态")
    @PostMapping(value = "/updateHidden/{id}")
    public NorResponse updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        int count = menuService.updateHidden(id, hidden);
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }
}
