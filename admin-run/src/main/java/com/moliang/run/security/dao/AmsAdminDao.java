package com.moliang.run.security.dao;

import com.moliang.run.model.AmsAdmin;
import com.moliang.run.model.AmsAdminExample;
import com.moliang.run.model.AmsMenu;
import com.moliang.run.model.AmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/10 23:15
 * @Version 1.0
 */
@Mapper
public interface AmsAdminDao {

    /**
     * 获取用户列表
     * 重写登录日志部分后改写select
     * @param example 查询逻辑
     * @return 用户列表
     */
    List<AmsAdmin> selectByExample(AmsAdminExample example);
    /**
     * 获取用户的菜单列表
     */
    List<AmsMenu> getMenuList(@Param("adminId") Long adminId);

    /**
     * 获取用户的权限列表
     */
    List<AmsRole> getRoleList(@Param("adminId") Long adminId);
}
