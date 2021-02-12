package com.moliang.run.security.service;

import com.moliang.run.model.AmsAdmin;
import com.moliang.run.model.AmsMenu;
import com.moliang.run.model.AmsResource;
import com.moliang.run.model.AmsRole;
import com.moliang.run.security.param.AmsAdminParam;
import com.moliang.run.security.param.AmsUpdateAdminPasswordParam;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/9 23:26
 * @Version 1.0
 */
public interface AmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    AmsAdmin getAdminByUsername(String userName);

    /**
     * 注册功能
     */
    AmsAdmin register(AmsAdminParam umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户id获取用户
     */
    AmsAdmin getItem(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<AmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    int update(Long id, AmsAdmin admin);

    /**
     * 删除指定用户
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     */
    List<AmsRole> getRoleList(Long adminId);

    /**
     * 获取指定用户的可访问资源
     */
    List<AmsResource> getResourceList(Long adminId);

    /**
     * 获取指定用户的菜单列表
     */
    List<AmsMenu> getMenuList(Long adminId);

    /**
     * 修改密码
     */
    int updatePassword(AmsUpdateAdminPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);
}
