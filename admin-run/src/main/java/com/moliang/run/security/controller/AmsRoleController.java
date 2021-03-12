package com.moliang.run.security.controller;

import com.moliang.model.DataPage;
import com.moliang.model.NorResponse;
import com.moliang.run.model.AmsMenu;
import com.moliang.run.model.AmsResource;
import com.moliang.run.model.AmsRole;
import com.moliang.run.security.service.AmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Use 后台管理角色控制类
 * @Author Chui moliang
 * @Date 2020/12/26 17:40
 * @Version 1.0
 */
@Controller
@Api(tags = "AmsRole 后台系统角色管理")
@RequestMapping("/api/role")
@ResponseBody
public class AmsRoleController {

    @Autowired
    private AmsRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping(value = "/create")
    public NorResponse create(@RequestBody AmsRole role) {
        int count = roleService.create(role);
        if (count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("修改角色")
    @PostMapping(value = "/update/{id}")
    public NorResponse update(@PathVariable Long id, @RequestBody AmsRole role) {
        int count = roleService.update(id, role);
        if (count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("批量删除角色")
    @PostMapping(value = "/delete")
    public NorResponse delete(@RequestParam("ids") List<Long> ids) {
        int count = roleService.delete(ids);
        if (count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    public NorResponse<List<AmsRole>> listAll() {
        List<AmsRole> roleList = roleService.list();
        return NorResponse.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    public NorResponse<DataPage<AmsRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AmsRole> roleList = roleService.list(keyword, pageSize, pageNum);
        return NorResponse.success(DataPage.restPage(roleList));
    }

    @ApiOperation("修改角色状态")
    @PostMapping(value = "/updateStatus/{id}")
    public NorResponse updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        AmsRole role = new AmsRole();
        role.setStatus(status);
        int count = roleService.update(id, role);
        if (count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping(value = "/listMenu/{roleId}")
    public NorResponse<List<AmsMenu>> listMenu(@PathVariable Long roleId) {
        List<AmsMenu> roleList = roleService.listMenu(roleId);
        return NorResponse.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping(value = "/listResource/{roleId}")
    public NorResponse<List<AmsResource>> listResource(@PathVariable Long roleId) {
        List<AmsResource> roleList = roleService.listResource(roleId);
        return NorResponse.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenu")
    public NorResponse allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return NorResponse.success(count);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping(value = "/allocResource")
    public NorResponse allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return NorResponse.success(count);
    }

}
