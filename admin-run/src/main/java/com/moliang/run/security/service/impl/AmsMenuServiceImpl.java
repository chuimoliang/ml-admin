package com.moliang.run.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.run.mapper.AmsMenuMapper;
import com.moliang.run.model.AmsMenu;
import com.moliang.run.model.AmsMenuExample;
import com.moliang.run.security.param.AmsMenuNode;
import com.moliang.run.security.service.AmsMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Use 后台系统菜单服务实现类
 * @Author Chui moliang
 * @Date 2021/1/5 17:54
 * @Version 1.0
 */
@Service
public class AmsMenuServiceImpl implements AmsMenuService {

    @Autowired
    private AmsMenuMapper menuMapper;

    @Override
    public int create(AmsMenu amsMenu) {
        amsMenu.setCreateTime(new Date());
        updateLevel(amsMenu);
        return menuMapper.insert(amsMenu);
    }

    @Override
    public int update(Long id, AmsMenu amsMenu) {
        amsMenu.setId(id);
        updateLevel(amsMenu);
        return menuMapper.updateByPrimaryKeySelective(amsMenu);
    }

    @Override
    public AmsMenu getItem(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        AmsMenuExample menuExample = new AmsMenuExample();
        menuExample.setOrderByClause("sort desc");
        menuExample.createCriteria().andParentIdEqualTo(parentId);
        return menuMapper.selectByExample(menuExample);
    }

    @Override
    public List<AmsMenuNode> treeList() {
        List<AmsMenu> menuList = menuMapper.selectByExample(new AmsMenuExample());
        List<AmsMenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        AmsMenu umsMenu = new AmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return menuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private AmsMenuNode covertMenuNode(AmsMenu menu, List<AmsMenu> menuList) {
        AmsMenuNode node = new AmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<AmsMenuNode> children = menuList.stream()
                .filter(child -> child.getParentId().equals(menu.getId()))
                .map(child -> covertMenuNode(child, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(AmsMenu menu) {
        if (menu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            menu.setLevel(0);
        } else {
            //有父菜单时设置为父菜单level+1
            AmsMenu parentMenu = menuMapper.selectByPrimaryKey(menu.getParentId());
            if (parentMenu != null) {
                menu.setLevel(parentMenu.getLevel() + 1);
            } else {
                menu.setLevel(0);
            }
        }
    }

}
