package com.moliang.run.security.controller;

import com.moliang.model.NorResponse;
import com.moliang.run.model.AmsResourceCategory;
import com.moliang.run.security.service.AmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/1/5 20:23
 * @Version 1.0
 */
@Controller
@Api(tags = "AmsResourceCategory 资源分类管理")
@ResponseBody
@RequestMapping("/resourceCategory")
public class AmsResourceCategoryController {

    @Autowired
    private AmsResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有后台资源分类")
    @GetMapping(value = "/listAll")
    public NorResponse<List<AmsResourceCategory>> listAll() {
        List<AmsResourceCategory> resourceList = resourceCategoryService.listAll();
        return NorResponse.success(resourceList);
    }

    @ApiOperation("添加后台资源分类")
    @PostMapping(value = "/create")
    public NorResponse create(@RequestBody AmsResourceCategory resourceCategory) {
        int count = resourceCategoryService.create(resourceCategory);
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation("修改后台资源分类")
    @PostMapping(value = "/update/{id}")
    public NorResponse update(@PathVariable Long id,
                               @RequestBody AmsResourceCategory resourceCategory) {
        int count = resourceCategoryService.update(id, resourceCategory);
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }

    @ApiOperation("根据ID删除后台资源")
    @PostMapping(value = "/delete/{id}")
    public NorResponse delete(@PathVariable Long id) {
        int count = resourceCategoryService.delete(id);
        if (count > 0) {
            return NorResponse.success(count);
        } else {
            return NorResponse.failed();
        }
    }
}
