package com.moliang.run.security.service;

import com.moliang.run.model.AmsResourceCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Use 资源分类管理接口
 * @Author Chui moliang
 * @Date 2021/1/5 20:30
 * @Version 1.0
 */
public interface AmsResourceCategoryService {

    /**
     * 获取所有资源分类
     */
    List<AmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    int create(AmsResourceCategory amsResourceCategory);

    /**
     * 修改资源分类
     */
    int update(Long id, AmsResourceCategory amsResourceCategory);

    /**
     * 删除资源分类
     */
    int delete(Long id);

}
