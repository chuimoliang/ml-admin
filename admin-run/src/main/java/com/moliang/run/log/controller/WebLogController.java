package com.moliang.run.log.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 16:27
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("api/logs")
@Api(tags = "日志管理")
public class WebLogController {
    /**

    private final LogService logService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check()")
    public void download(HttpServletResponse response, LogQueryCriteria criteria) throws IOException {
        criteria.setLogType("INFO");
        logService.download(logService.queryAll(criteria), response);
    }

    @Log("导出错误数据")
    @ApiOperation("导出错误数据")
    @GetMapping(value = "/error/download")
    @PreAuthorize("@el.check()")
    public void downloadErrorLog(HttpServletResponse response, LogQueryCriteria criteria) throws IOException {
        criteria.setLogType("ERROR");
        logService.download(logService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("日志查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> query(LogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("INFO");
        return new ResponseEntity<>(logService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public ResponseEntity<Object> queryUserLog(LogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("INFO");
        criteria.setBlurry(SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(logService.queryAllByUser(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    @ApiOperation("错误日志查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLog(LogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("ERROR");
        return new ResponseEntity<>(logService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("日志异常详情查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLogs(@PathVariable Long id){
        return new ResponseEntity<>(logService.findByErrDetail(id), HttpStatus.OK);
    }
    @DeleteMapping(value = "/del/error")
    @Log("删除所有ERROR日志")
    @ApiOperation("删除所有ERROR日志")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllErrorLog(){
        logService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("删除所有INFO日志")
    @ApiOperation("删除所有INFO日志")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllInfoLog(){
        logService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    **/
}