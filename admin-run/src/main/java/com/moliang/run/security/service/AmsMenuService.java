package com.moliang.run.security.service;

import com.moliang.run.model.AmsMenu;
import com.moliang.run.security.param.AmsMenuNode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Use 后台系统菜单服务接口
 * @Author Chui moliang
 * @Date 2021/1/5 17:46
 * @Version 1.0
 */
public interface AmsMenuService {

    /**
     * 创建后台菜单
     */
    int create(AmsMenu amsMenu);

    /**
     * 修改后台菜单
     */
    int update(Long id, AmsMenu amsMenu);

    /**
     * 根据ID获取菜单详情
     */
    AmsMenu getItem(Long id);

    /**
     * 根据ID删除菜单
     */
    int delete(Long id);

    /**
     * 分页查询后台菜单
     */
    List<AmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     */
    List<AmsMenuNode> treeList();

    /**
     * 修改菜单显示状态
     */
    int updateHidden(Long id, Integer hidden);
}