package com.moliang.run.security.controller;

import cn.hutool.core.collection.CollUtil;
import com.moliang.model.DataPage;
import com.moliang.model.NorResponse;
import com.moliang.run.log.annotation.Persistence;
import com.moliang.run.model.AmsAdmin;
import com.moliang.run.model.AmsRole;
import com.moliang.run.security.param.AmsAdminLoginParam;
import com.moliang.run.security.param.AmsAdminParam;
import com.moliang.run.security.param.AmsUpdateAdminPasswordParam;
import com.moliang.run.security.service.AmsAdminService;
import com.moliang.run.security.service.AmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/10 23:27
 * @Version 1.0
 */
@Controller
@Api(tags = "AmsAdmin 后台系统用户管理")
@RequestMapping("/admin")
@ResponseBody
@Slf4j
public class AmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AmsAdminService adminService;
    @Autowired
    private AmsRoleService roleService;

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public NorResponse<AmsAdmin> register(@Validated @RequestBody AmsAdminParam amsAdminParam) {
        AmsAdmin admin = adminService.register(amsAdminParam);
        if (admin == null) {
            return NorResponse.failed();
        }
        return NorResponse.success(admin);
    }

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    public NorResponse login(@Validated @RequestBody AmsAdminLoginParam amsAdminLoginParam) {
        String token = adminService.login(amsAdminLoginParam.getUsername(), amsAdminLoginParam.getPassword());
        if (token == null) {
            return NorResponse.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return NorResponse.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    public NorResponse refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return NorResponse.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return NorResponse.success(tokenMap);
    }

    @Persistence
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/info")
    public NorResponse getAdminInfo(Principal principal) {
        if(principal==null){
            return NorResponse.unauthorized(null);
        }
        String username = principal.getName();
        AmsAdmin amsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", amsAdmin.getUsername());
        data.put("menus", adminService.getMenuList(amsAdmin.getId()));
        data.put("icon", amsAdmin.getIcon());
        List<AmsRole> roleList = adminService.getRoleList(amsAdmin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(AmsRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return NorResponse.success(data);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    public NorResponse<DataPage<AmsAdmin>> list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AmsAdmin> adminList = adminService.list(keyword, pageSize, pageNum);
        return NorResponse.success(DataPage.restPage(adminList));
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/{id}")
    public NorResponse<AmsAdmin> getItem(@PathVariable Long id) {
        AmsAdmin admin = adminService.getItem(id);
        return NorResponse.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping(value = "/update/{id}")
    public NorResponse update(@PathVariable Long id, @RequestBody AmsAdmin admin) {
        int count = adminService.update(id, admin);
        if (count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("修改指定用户密码")
    @PostMapping(value = "/updatePassword")
    public NorResponse updatePassword(@Validated @RequestBody AmsUpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return NorResponse.success(status);
        } else if (status == -1) {
            return NorResponse.failed("提交参数不合法");
        } else if (status == -2) {
            return NorResponse.failed("找不到该用户");
        } else if (status == -3) {
            return NorResponse.failed("旧密码错误");
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @PostMapping(value = "/delete/{id}")
    public NorResponse delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        if (count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("修改帐号状态")
    @PostMapping(value = "/updateStatus/{id}")
    public NorResponse updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
        AmsAdmin umsAdmin = new AmsAdmin();
        umsAdmin.setStatus(status);
        int count = adminService.update(id,umsAdmin);
        if (count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/role/update")
    public NorResponse updateRole(@RequestParam("adminId") Long adminId,
                                  @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping(value = "/role/{adminId}")
    public NorResponse<List<AmsRole>> getRoleList(@PathVariable Long adminId) {
        List<AmsRole> roleList = adminService.getRoleList(adminId);
        return NorResponse.success(roleList);
    }

}
