package com.moliang.run.security.service;

import com.moliang.run.model.AmsMenu;
import com.moliang.run.model.AmsResource;
import com.moliang.run.model.AmsRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Use 后台账号角色管理
 * @Author Chui moliang
 * @Date 2020/12/23 20:26
 * @Version 1.0
 */
public interface AmsRoleService {
    /**
     * 添加角色
     */
    int create(AmsRole role);

    /**
     * 修改角色信息
     */
    int update(Long id, AmsRole role);

    /**
     * 批量删除角色
     */
    int delete(List<Long> ids);

    /**
     * 获取所有角色列表
     */
    List<AmsRole> list();

    /**
     * 分页获取角色列表
     */
    List<AmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 获取角色相关菜单
     */
    List<AmsMenu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     */
    List<AmsResource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
