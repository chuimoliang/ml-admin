package com.moliang.run.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.moliang.run.mapper.AmsResourceMapper;
import com.moliang.run.model.AmsResource;
import com.moliang.run.model.AmsResourceExample;
import com.moliang.run.security.service.AmsAdminCacheService;
import com.moliang.run.security.service.AmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Use 资源服务实现类
 * @Author Chui moliang
 * @Date 2020/12/25 21:36
 * @Version 1.0
 */
@Service
public class AmsResourceServiceImpl implements AmsResourceService {

    @Autowired
    private AmsResourceMapper resourceMapper;

    @Autowired
    private AmsAdminCacheService adminCacheService;

    @Override
    public int create(AmsResource amsResource) {
        amsResource.setCreateTime(new Date());
        return resourceMapper.insert(amsResource);
    }

    @Override
    public int update(Long id, AmsResource amsResource) {
        amsResource.setId(id);
        int count = resourceMapper.updateByPrimaryKeySelective(amsResource);
        adminCacheService.delResourceListByResource(id);
        return count;
    }

    @Override
    public AmsResource getItem(Long id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        int count = resourceMapper.deleteByPrimaryKey(id);
        adminCacheService.delResourceListByResource(id);
        return count;
    }

    @Override
    public List<AmsResource> list(Long categoryId, String nameKeyword, String urlKeyword,
                                  Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        AmsResourceExample example = new AmsResourceExample();
        AmsResourceExample.Criteria criteria = example.createCriteria();
        if(categoryId != null){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(StrUtil.isNotEmpty(nameKeyword)){
            criteria.andNameLike('%'+nameKeyword+'%');
        }
        if(StrUtil.isNotEmpty(urlKeyword)){
            criteria.andUrlLike('%'+urlKeyword+'%');
        }
        return resourceMapper.selectByExample(example);
    }

    @Override
    public List<AmsResource> listAll() {
        return resourceMapper.selectByExample(new AmsResourceExample());
    }
}
