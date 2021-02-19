package com.moliang.run.log.controller;

import com.moliang.enums.ResponseCode;
import com.moliang.exception.ApiException;
import com.moliang.model.NorResponse;
import com.moliang.run.log.annotation.Persistence;
import com.moliang.run.log.model.LogQueryParam;
import com.moliang.run.log.service.WebLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private WebLogService logService;

    @ApiOperation("导出数据")
    @Persistence
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, LogQueryParam queryParam) throws IOException {
        logService.download(logService.listAll(queryParam), response);
    }

    @GetMapping
    @ApiOperation("日志查询")
    public NorResponse query( LogQueryParam queryParam,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        return NorResponse.success(logService.list(queryParam, pageSize, pageNum));
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public NorResponse queryUserLog(LogQueryParam queryParam,
                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) throw new ApiException(ResponseCode.UNAUTHORIZED);
        UserDetails user = (UserDetails) auth.getPrincipal();
        queryParam.setUsername(user.getUsername());
        return NorResponse.success(logService.list(queryParam, pageSize, pageNum));
    }

    /**
    @DeleteMapping(value = "/del/logs")
    @ApiOperation("删除条件日志")
    public NorResponse<Object> delLog(){

    }
    **/
}
