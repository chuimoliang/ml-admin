package com.moliang.run.security.controller;

import com.moliang.model.DataPage;
import com.moliang.model.NorResponse;
import com.moliang.run.model.AmsResource;
import com.moliang.run.security.component.MySecurityMetaDataSource;
import com.moliang.run.security.service.AmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Use 资源控制管理类
 * @Author Chui moliang
 * @Date 2020/12/25 21:15
 * @Version 1.0
 */
@Controller
@Api(tags = "AmsResource 后台系统资源管理")
@RequestMapping("/api/resource")
@ResponseBody
public class AmsResourceController {

    @Autowired
    private AmsResourceService resourceService;
    @Autowired
    private MySecurityMetaDataSource securityMetadataSource;

    @ApiOperation("添加后台资源")
    @PostMapping(value = "/create")
    public NorResponse create(@RequestBody AmsResource amsResource) {
        int count = resourceService.create(amsResource);
        securityMetadataSource.clearDataSource();
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation("修改后台资源")
    @PostMapping(value = "/update/{id}")
    public NorResponse update(@PathVariable Long id,
                               @RequestBody AmsResource amsResource) {
        int count = resourceService.update(id, amsResource);
        securityMetadataSource.clearDataSource();
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation("根据ID获取资源详情")
    @GetMapping(value = "/{id}")
    public NorResponse<AmsResource> getItem(@PathVariable Long id) {
        AmsResource amsResource = resourceService.getItem(id);
        return NorResponse.success(amsResource);
    }

    @ApiOperation("根据ID删除后台资源")
    @PostMapping(value = "/delete/{id}")
    public NorResponse delete(@PathVariable Long id) {
        int count = resourceService.delete(id);
        securityMetadataSource.clearDataSource();
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping(value = "/list")
    public NorResponse<DataPage<AmsResource>> list(@RequestParam(required = false) Long categoryId,
                                                   @RequestParam(required = false) String nameKeyword,
                                                   @RequestParam(required = false) String urlKeyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AmsResource> resourceList =
                resourceService.list(categoryId,nameKeyword, urlKeyword, pageSize, pageNum);
        return NorResponse.success(DataPage.restPage(resourceList));
    }

    @ApiOperation("查询所有后台资源")
    @GetMapping(value = "/listAll")
    public NorResponse<List<AmsResource>> listAll() {
        List<AmsResource> resourceList = resourceService.listAll();
        return NorResponse.success(resourceList);
    }

}
