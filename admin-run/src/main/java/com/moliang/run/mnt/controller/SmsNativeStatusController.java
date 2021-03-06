package com.moliang.run.mnt.controller;

import com.moliang.model.NorResponse;
import com.moliang.run.mnt.service.NativeServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/7 2:32
 * @Version 1.0
 */
@RestController
@Api(tags = "SmsNative 系统状态监控")
@RequestMapping("/api/status")
public class SmsNativeStatusController {

    @Autowired
    private NativeServerService serverService;

    @ApiOperation("获取服务器运行状态")
    @GetMapping
    public NorResponse<Object> status() {
        return NorResponse.success(serverService.getStatus());
    }

}
