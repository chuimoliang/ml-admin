package com.moliang.run.mnt.controller;

import com.moliang.enums.ResponseCode;
import com.moliang.model.DataPage;
import com.moliang.model.NorResponse;
import com.moliang.run.mnt.model.SmsDeploy;
import com.moliang.run.mnt.model.SmsDeployParam;
import com.moliang.run.mnt.model.SmsDeployQueryParam;
import com.moliang.run.mnt.service.SmsDeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    @ApiOperation("上传应用")
    @PutMapping(value = "/upload/{id}")
    public NorResponse<Object> upload(@RequestPart("file") MultipartFile file, @PathVariable Long id) throws IOException {
        return NorResponse.success(deployService.upload(file, id));
    }

    @ApiOperation("分页查询部署")
    @GetMapping
    public NorResponse<Object> list(SmsDeployQueryParam param,
                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return NorResponse.success(DataPage.restPage(deployService.list(param, pageSize, pageNum)));
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

    @ApiOperation("启动部署")
    @PostMapping("/start/{id}")
    public NorResponse<Object> start(@PathVariable Long id) {
        int count = deployService.start(id);
        if(count == -1) {
            return NorResponse.failed(ResponseCode.VALIDATE_FAILED, "部署过期或不存在");
        }
        if(count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed("启动数量为0, 请检查服务器设置");
    }

    @ApiOperation("停止部署")
    @PostMapping("/stop/{id}")
    public NorResponse<Object> stop(@PathVariable Long id) {
        int count = deployService.stop(id);
        if(count == -1) {
            return NorResponse.failed(ResponseCode.VALIDATE_FAILED, "部署过期或不存在");
        }
        if(count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed("停止服务数量为0, 请检查服务器设置");
    }

    @ApiOperation("项目还原")
    @PutMapping("/reduction/{id}")
    public NorResponse<Object> reduction(@PathVariable Long historyId) {
        deployService.serverReduction(historyId);
        return NorResponse.success(1);
    }

    @ApiOperation("导出部署数据")
    @GetMapping("/download")
    public void download(HttpServletResponse response, SmsDeployQueryParam param) throws IOException {
        deployService.download(deployService.listAll(param), response);
    }

}
