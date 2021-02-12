package com.moliang.run.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.run.mapper.AmsRoleMapper;
import com.moliang.run.mapper.AmsRoleMenuRelationMapper;
import com.moliang.run.mapper.AmsRoleResourceRelationMapper;
import com.moliang.run.model.*;
import com.moliang.run.security.dao.AmsRoleDao;
import com.moliang.run.security.service.AmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Use 后台账号角色管理服务实现类
 * @Author Chui moliang
 * @Date 2020/12/24 11:42
 * @Version 1.0
 */
@Service
public class AmsRoleServiceImpl implements AmsRoleService {

    @Autowired
    AmsAdminCacheServiceImpl adminCacheService;
    @Autowired
    private AmsRoleMapper roleMapper;
    @Autowired
    private AmsRoleDao roleDao;
    @Autowired
    private AmsRoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private AmsRoleResourceRelationMapper roleResourceRelationMapper;

    @Override
    public int create(AmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public int update(Long id, AmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int delete(List<Long> ids) {
        AmsRoleExample example = new AmsRoleExample();
        example.createCriteria().andIdIn(ids);
        int count = roleMapper.deleteByExample(example);
        adminCacheService.delResourceListByRoleIds(ids);
        return count;
    }

    @Override
    public List<AmsRole> list() {
        return roleMapper.selectByExample(new AmsRoleExample());
    }

    @Override
    public List<AmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        AmsRoleExample example = new AmsRoleExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        return roleMapper.selectByExample(example);
    }

    @Override
    public List<AmsMenu> listMenu(Long roleId) {
        return roleDao.getMenuListByRoleId(roleId);
    }

    @Override
    public List<AmsResource> listResource(Long roleId) {
        return roleDao.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        AmsRoleMenuRelationExample example=new AmsRoleMenuRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleMenuRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long menuId : menuIds) {
            AmsRoleMenuRelation relation = new AmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            roleMenuRelationMapper.insert(relation);
        }
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        AmsRoleResourceRelationExample example = new AmsRoleResourceRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            AmsRoleResourceRelation relation = new AmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(relation);
        }
        adminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }
}
