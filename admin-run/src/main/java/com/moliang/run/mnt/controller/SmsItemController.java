package com.moliang.run.mnt.controller;

import cn.hutool.core.io.FileUtil;
import com.moliang.model.DataPage;
import com.moliang.model.NorResponse;
import com.moliang.run.mnt.model.SmsItem;
import com.moliang.run.mnt.model.SmsItemParam;
import com.moliang.run.mnt.model.SmsItemQueryParam;
import com.moliang.run.mnt.service.SmsItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

/**
 * @Use 项目管理
 * @Author Chui moliang
 * @Date 2021/2/20 1:15
 * @Version 1.0
 */
@RestController
@RequestMapping("api/item")
@Api(tags = "系统管理 : 应用管理")
public class SmsItemController {

    @Autowired
    private SmsItemService itemService;

    @GetMapping
    @ApiOperation("查询应用数据")
    public NorResponse<Object> list(SmsItemQueryParam param,
                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return NorResponse.success(DataPage.restPage(itemService.getItemList(param, pageSize, pageNum)));
    }

    @PostMapping
    @ApiOperation("新增应用")
    public NorResponse<SmsItem> create(@Validated @RequestBody SmsItemParam item, Principal principal) {
        return NorResponse.success(itemService.create(item, principal));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新应用")
    public NorResponse<Object> update(@Validated @RequestBody SmsItemParam param, @PathVariable Long id, Principal principal) {
        int count = itemService.update(param, id, principal);
        if(count > 0) {
            return NorResponse.success(count);
        }
        return NorResponse.failed();
    }

    @DeleteMapping
    @ApiOperation("删除应用")
    public NorResponse<Object> delete(@RequestBody Set<Long> ids) {
        int count = itemService.delete(ids);
        if(count == ids.size()) {
            return NorResponse.success(count);
        }
        if(count == 0) {
            return NorResponse.failed("删除操作执行失败");
        }
        return NorResponse.failed();
    }

    @GetMapping("/download")
    @ApiOperation("导出应用数据")
    public void download(SmsItemQueryParam param, HttpServletResponse response) throws IOException {
        itemService.download(itemService.getItemList(param), response);
    }

}
