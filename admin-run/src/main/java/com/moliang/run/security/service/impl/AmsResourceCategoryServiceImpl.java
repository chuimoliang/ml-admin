package com.moliang.run.security.service.impl;

import com.moliang.run.mapper.AmsResourceCategoryMapper;
import com.moliang.run.model.AmsResourceCategory;
import com.moliang.run.model.AmsResourceCategoryExample;
import com.moliang.run.security.service.AmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Use 资源分类管理实现类
 * @Author Chui moliang
 * @Date 2021/1/5 20:34
 * @Version 1.0
 */
@Service
public class AmsResourceCategoryServiceImpl implements AmsResourceCategoryService {

    @Autowired
    private AmsResourceCategoryMapper resourceCategoryMapper;

    @Override
    public List<AmsResourceCategory> listAll() {
        AmsResourceCategoryExample example = new AmsResourceCategoryExample();
        example.setOrderByClause("sort desc");
        return resourceCategoryMapper.selectByExample(example);
    }

    @Override
    public int create(AmsResourceCategory amsResourceCategory) {
        amsResourceCategory.setCreateTime(new Date());
        return resourceCategoryMapper.insert(amsResourceCategory);
    }

    @Override
    public int update(Long id, AmsResourceCategory amsResourceCategory) {
        amsResourceCategory.setId(id);
        return resourceCategoryMapper.updateByPrimaryKeySelective(amsResourceCategory);
    }

    @Override
    public int delete(Long id) {
        return resourceCategoryMapper.deleteByPrimaryKey(id);
    }
}
