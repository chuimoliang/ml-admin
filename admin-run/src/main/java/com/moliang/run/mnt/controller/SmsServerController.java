package com.moliang.run.mnt.controller;

import com.moliang.enums.ResponseCode;
import com.moliang.exception.ApiException;
import com.moliang.model.DataPage;
import com.moliang.model.NorResponse;
import com.moliang.run.mnt.model.SmsServerParam;
import com.moliang.run.mnt.model.SmsServerQueryParam;
import com.moliang.run.mnt.service.SmsServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/21 16:06
 * @Version 1.0
 */
@RestController
@Api(tags = "系统管理 : 服务器管理")
@RequestMapping("api/server")
public class SmsServerController {

    @Autowired
    private SmsServerService serverService;

    @ApiOperation("新增服务器")
    @PostMapping
    public NorResponse<Object> addServer(@Validated @RequestBody SmsServerParam param, Principal principal) {
        return NorResponse.success(serverService.create(param, principal));
    }

    @ApiOperation("修改服务器")
    @PutMapping("/{id}")
    public NorResponse<Object> updateServer(@PathVariable Long id,
                                            @Validated @RequestBody SmsServerParam param, Principal principal) {
        int count = serverService.update(param, id, principal);
        if(count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("删除服务器")
    @DeleteMapping
    public NorResponse<Object> delServer(@RequestBody Set<Long> ids) {
        int count = serverService.delete(ids);
        if(count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("服务器延迟")
    @GetMapping("/test/{id}")
    public NorResponse<Object> testConnect(@PathVariable Long id) {
        long res = serverService.testConnect(id);
        if(res == -1) return NorResponse.failed("与服务器连接失败, 可能是账号密码错误, 或者服务器未开通ssh服务端口");
        return NorResponse.success(res);
    }

    @ApiOperation("查询服务器")
    @GetMapping
    public NorResponse<Object> serverList(SmsServerQueryParam param,
                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return NorResponse.success(DataPage.restPage(serverService.getServerList(param, pageSize, pageNum)));
    }

    @ApiOperation("下载服务器数据")
    @GetMapping("/download")
    public void download(SmsServerQueryParam param, HttpServletResponse response) throws IOException {
        serverService.download(serverService.getServerList(param), response);
    }

}
