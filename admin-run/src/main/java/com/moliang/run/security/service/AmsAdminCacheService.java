package com.moliang.run.security.service;

import com.moliang.run.model.AmsAdmin;
import com.moliang.run.model.AmsResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Use 后台管理账号缓存类
 * @Author Chui moliang
 * @Date 2020/12/19 16:30
 * @Version 1.0
 */
public interface AmsAdminCacheService {

    /**
     * 删除后台用户缓存
     */
    void delAdmin(Long adminId);

    /**
     * 删除后台用户资源列表缓存
     */
    void delResourceList(Long adminId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 获取缓存后台用户信息
     */
    AmsAdmin getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(AmsAdmin admin);

    /**
     * 获取缓存后台用户资源列表
     */
    List<AmsResource> getResourceList(Long adminId);

    /**
     * 设置后台后台用户资源列表
     */
    void setResourceList(Long adminId, List<AmsResource> resourceList);

}
