package com.moliang.run.quartz.controller;

import com.moliang.model.NorResponse;
import com.moliang.run.quartz.model.QuartzJobParam;
import com.moliang.run.quartz.service.QuartzJobService;
import com.moliang.run.quartz.service.QuartzLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 17:14
 * @Version 1.0
 */
@RestController
@RequestMapping("api/quartz")
public class QuartzJobController {

    @Autowired
    private QuartzJobService jobService;
    @Autowired
    private QuartzLogService logService;

    @ApiOperation("查询定时任务")
    @GetMapping
    public NorResponse<Object> query(){
        return null;
    }

    @ApiOperation("导出任务数据")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response) throws IOException {
    }

    @ApiOperation("导出日志数据")
    @GetMapping(value = "/logs/download")
    public void downloadLog(HttpServletResponse response) throws IOException {
    }

    @ApiOperation("查询任务执行日志")
    @GetMapping(value = "/logs")
    public NorResponse<Object> queryJobLog(){
    }

    @ApiOperation("新增定时任务")
    @PostMapping
    public NorResponse<Object> create(@Validated @RequestBody QuartzJobParam resources){
    }

    @ApiOperation("修改定时任务")
    @PutMapping
    public NorResponse<Object> update(@Validated @RequestBody QuartzJobParam resources){
    }

    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/{id}")
    public NorResponse<Object> update(@PathVariable Long id){
    }

    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    public NorResponse<Object> execution(@PathVariable Long id){
    }

    @ApiOperation("删除定时任务")
    @DeleteMapping
    public NorResponse<Object> delete(@RequestBody Set<Long> ids){
    }
}
