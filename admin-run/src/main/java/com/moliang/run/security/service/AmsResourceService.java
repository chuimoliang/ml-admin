package com.moliang.run.security.service;

import com.moliang.run.model.AmsResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Use 资源服务类
 * @Author Chui moliang
 * @Date 2020/12/25 21:33
 * @Version 1.0
 */
public interface AmsResourceService {
    /**
     * 添加资源
     */
    int create(AmsResource amsResource);

    /**
     * 修改资源
     */
    int update(Long id, AmsResource amsResource);

    /**
     * 获取资源详情
     */
    AmsResource getItem(Long id);

    /**
     * 删除资源
     */
    int delete(Long id);

    /**
     * 分页查询资源
     */
    List<AmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

    /**
     * 查询全部资源
     */
    List<AmsResource> listAll();
}
