package com.moliang.run.quartz.controller;

import com.moliang.model.DataPage;
import com.moliang.model.NorResponse;
import com.moliang.run.quartz.model.QuartzJobParam;
import com.moliang.run.quartz.model.QuartzJobQueryParam;
import com.moliang.run.quartz.service.QuartzJobService;
import com.moliang.run.quartz.service.QuartzLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @Date 2021/3/8 17:14
 * @Version 1.0
 */
@RestController
@Api(tags = "Quartz 定时任务管理")
@RequestMapping("api/quartz")
@Slf4j
public class QuartzController {

    @Autowired
    private QuartzJobService jobService;
    @Autowired
    private QuartzLogService logService;

    @ApiOperation("查询定时任务")
    @GetMapping
    public NorResponse<Object> query(QuartzJobQueryParam param,
                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        return NorResponse.success(DataPage.restPage(jobService.list(param, pageSize, pageNum)));
    }

    @ApiOperation("查询定时任务日志")
    @GetMapping("/logs")
    public NorResponse<Object> queryLog(QuartzJobQueryParam param,
                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        return NorResponse.success(DataPage.restPage(logService.list(param, pageSize, pageNum)));
    }

    @ApiOperation("导出任务数据")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, QuartzJobQueryParam param) throws IOException {
        jobService.download(jobService.listAll(param), response);
    }

    @ApiOperation("导出日志数据")
    @GetMapping(value = "/logs/download")
    public void downloadLog(HttpServletResponse response, QuartzJobQueryParam param) throws IOException {
        logService.download(logService.listAll(param), response);
    }

    @ApiOperation("新增定时任务")
    @PostMapping
    public NorResponse<Object> create(@Validated @RequestBody QuartzJobParam param, Principal principal){
        return NorResponse.success(jobService.create(param, principal));
    }

    @ApiOperation("修改定时任务")
    @PutMapping("/{id}")
    public NorResponse<Object> update(@Validated @RequestBody QuartzJobParam param, @PathVariable Long id, Principal principal){
        int count = jobService.update(param, id, principal);
        if (count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/status/{id}")
    public NorResponse<Object> update(@PathVariable Long id){
        return NorResponse.success(jobService.updateStatus(id));
    }

    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    public NorResponse<Object> execution(@PathVariable Long id){
        int ans = jobService.execute(id);
        if (ans < 0) return NorResponse.failed();
        return NorResponse.success(ans);
    }

    @ApiOperation("删除定时任务")
    @DeleteMapping
    public NorResponse<Object> delete(@RequestBody Set<Long> ids){
        int count = jobService.delete(ids);
        if(count == ids.size()) {
            return NorResponse.success(count);
        }
        if(count == 0) {
            return NorResponse.failed("删除操作执行失败");
        }
        return NorResponse.failed();
    }

}
