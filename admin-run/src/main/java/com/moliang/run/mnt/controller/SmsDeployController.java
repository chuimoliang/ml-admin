package com.moliang.run.mnt.controller;

import com.moliang.model.NorResponse;
import com.moliang.run.mnt.model.SmsDeploy;
import com.moliang.run.mnt.model.SmsDeployParam;
import com.moliang.run.mnt.model.SmsDeployQueryParam;
import com.moliang.run.mnt.service.SmsDeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/25 1:03
 * @Version 1.0
 */
@RestController
@Api(tags = "SmsDeploy 系统 : 部署管理")
@RequestMapping("api/deploy")
public class SmsDeployController {

    @Autowired
    private SmsDeployService deployService;

    @ApiOperation("分页查询部署")
    @GetMapping
    public NorResponse<List<SmsDeploy>> list(SmsDeployQueryParam param,
                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return NorResponse.success(deployService.list(param, pageSize, pageNum));
    }

    @ApiOperation("新增部署")
    @PostMapping
    public NorResponse<Object> add(@Validated @RequestBody SmsDeployParam param, Principal principal) {
        return NorResponse.success(deployService.create(param, principal));
    }

    @ApiOperation("更新部署")
    @PutMapping("/{id}")
    public NorResponse<Object> update(@Validated @RequestBody SmsDeployParam param,@PathVariable Long id, Principal principal) {
        return NorResponse.success(deployService.update(param, id, principal));
    }

    @ApiOperation("删除部署")
    @DeleteMapping
    public NorResponse<Object> delete(@RequestBody Set<Long> ids) {
        int count = deployService.delete(ids);
        if(count == 0) {
            return NorResponse.failed("删除失败");
        }
        if(count == ids.size()) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("导出部署数据")
    @GetMapping("/download")
    public void download(HttpServletResponse response, SmsDeployQueryParam param) throws IOException {
        deployService.download(deployService.listAll(param), response);
    }

}
