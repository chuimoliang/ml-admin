package com.moliang.run.mnt.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.exception.ApiException;
import com.moliang.run.mnt.mapper.SmsItemDao;
import com.moliang.run.mnt.mapper.SmsItemMapper;
import com.moliang.run.mnt.model.SmsItem;
import com.moliang.run.mnt.model.SmsItemExample;
import com.moliang.run.mnt.model.SmsItemParam;
import com.moliang.run.mnt.model.SmsItemQueryParam;
import com.moliang.run.mnt.service.SmsItemService;
import com.moliang.run.security.component.AdminDetails;
import com.moliang.utils.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * @Use 项目管理实现类
 * @Author Chui moliang
 * @Date 2021/2/20 1:50
 * @Version 1.0
 */
@Service
public class SmsItemServiceImpl implements SmsItemService {

    @Autowired
    private SmsItemMapper itemMapper;
    @Autowired
    private SmsItemDao itemDao;

    @Override
    public List<SmsItem> getItemList() {
        return itemMapper.selectByExample(new SmsItemExample());
    }

    @Override
    public List<SmsItem> getItemList(SmsItemQueryParam param) {
        return itemMapper.selectByExample(getExample(param));
    }

    @Override
    public List<SmsItem> getItemList(SmsItemQueryParam param, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return itemMapper.selectByExample(getExample(param));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsItem create(SmsItemParam param, Principal principal) {
        verification(param);
        SmsItem item = new SmsItem();
        BeanUtils.copyProperties(param, item);
        item.setCreateTime(new Date());
        item.setCreateBy(principal.getName());
        itemDao.insertGetId(item);
        return item;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Set<Long> ids) {
        int count = 0;
        for(Long id : ids) {
            count += itemMapper.deleteByPrimaryKey(id);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SmsItemParam param, Long id, Principal principal) {
        verification(param);
        SmsItem item = new SmsItem();
        BeanUtils.copyProperties(param, item);
        item.setUpdateBy(principal.getName());
        item.setId(id);
        return itemMapper.updateByPrimaryKeySelective(item);
    }

    private void verification(SmsItemParam item) {
        String home = "/home";
        if(!item.getBackupPath().startsWith(home)) {
            throw new ApiException("只能上传到home目录");
        }
        if(!item.getUploadPath().startsWith(home)) {
            throw new ApiException("只能上传到home目录");
        }
        if(!item.getDeployPath().startsWith(home)) {
            throw new ApiException("只能上传到home目录");
        }
    }

    private SmsItemExample getExample(SmsItemQueryParam param) {
        SmsItemExample example = new SmsItemExample();
        SmsItemExample.Criteria criteria = example.createCriteria();
        if (param.getStartId() != null) {
            criteria.andIdGreaterThanOrEqualTo(param.getStartId());
        }
        if (param.getToId() != null) {
            criteria.andIdLessThanOrEqualTo(param.getToId());
        }
        if (param.getStartCreateTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(param.getStartCreateTime());
        }
        if (param.getToCreateTime() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(param.getToCreateTime());
        }
        if (param.getStartUpdateTime() != null) {
            criteria.andUpdateTimeGreaterThanOrEqualTo(param.getStartUpdateTime());
        }
        if (param.getToUpdateTime() != null) {
            criteria.andUpdateTimeLessThanOrEqualTo(param.getToUpdateTime());
        }
        if (param.getStartPort() != null) {
            criteria.andPortGreaterThanOrEqualTo(param.getStartPort());
        }
        if (param.getToPort() != null) {
            criteria.andPortLessThanOrEqualTo(param.getToPort());
        }
        if (param.getName() != null && !"".equals(param.getName())) {
            criteria.andNameEqualTo(param.getName());
        }
        if (param.getUploadPath() != null && !"".equals(param.getUploadPath())) {
            criteria.andUploadPathEqualTo(param.getUploadPath());
        }
        if (param.getBackupPath()!= null && !"".equals(param.getBackupPath())) {
            criteria.andBackupPathEqualTo(param.getBackupPath());
        }
        if (param.getDeployPath()!= null && !"".equals(param.getDeployPath())) {
            criteria.andDeployPathEqualTo(param.getDeployPath());
        }
        if (param.getCreateBy() != null && !"".equals(param.getCreateBy())) {
            criteria.andCreateByEqualTo(param.getCreateBy());
        }
        if (param.getUpdateBy() != null && !"".equals(param.getUpdateBy())) {
            criteria.andUpdateByEqualTo(param.getUpdateBy());
        }
        return example;
    }

    @Override
    public void download(List<SmsItem> res, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SmsItem item : res) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("应用名称", item.getName());
            map.put("端口", item.getPort());
            map.put("上传目录", item.getUploadPath());
            map.put("部署目录", item.getDeployPath());
            map.put("备份目录", item.getBackupPath());
            map.put("启动脚本", item.getStartScript());
            map.put("部署脚本", item.getDeployScript());
            map.put("创建日期", item.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
