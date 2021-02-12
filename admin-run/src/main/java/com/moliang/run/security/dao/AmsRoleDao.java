package com.moliang.run.security.dao;

import com.moliang.run.model.AmsMenu;
import com.moliang.run.model.AmsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/10 23:23
 * @Version 1.0
 */
@Mapper
public interface AmsRoleDao {
    /**
     * 返回对应角色的菜单列表
     */
    List<AmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    /**
     * 返回对应角色的资源列表
     */
    List<AmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}
