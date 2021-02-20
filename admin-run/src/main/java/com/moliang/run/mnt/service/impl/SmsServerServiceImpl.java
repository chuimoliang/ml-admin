package com.moliang.run.mnt.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.run.mnt.mapper.SmsServerMapper;
import com.moliang.run.mnt.model.SmsServer;
import com.moliang.run.mnt.model.SmsServerExample;
import com.moliang.run.mnt.model.SmsServerParam;
import com.moliang.run.mnt.model.SmsServerQueryParam;
import com.moliang.run.mnt.service.SmsServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @Use 服务器管理实现类
 * @Author Chui moliang
 * @Date 2021/2/21 1:47
 * @Version 1.0
 */
@Service
public class SmsServerServiceImpl implements SmsServerService {

    @Autowired
    private SmsServerMapper serverMapper;

    @Override
    public List<SmsServer> getServerList() {
        return serverMapper.selectByExample(new SmsServerExample());
    }

    @Override
    public List<SmsServer> getServerList(SmsServerQueryParam param) {
        return serverMapper.selectByExample(getExample(param));
    }

    @Override
    public List<SmsServer> getServerList(SmsServerQueryParam param, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return serverMapper.selectByExample(getExample(param));
    }

    @Override
    public SmsServer getById(Long id) {
        return null;
    }

    @Override
    public SmsServer getByIp(String ip) {
        return null;
    }

    @Override
    public void download(List<SmsServer> res, HttpServletResponse response) throws IOException {

    }

    @Override
    public Boolean testConnect(SmsServer server) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsServer create(SmsServerParam param, Principal principal) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Set<Long> ids) {
        int count = 0;
        for(Long id : ids) {
            count += serverMapper.deleteByPrimaryKey(id);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsServer update(SmsServerParam param, Long id, Principal principal) {
        return null;
    }

    private SmsServerExample getExample(SmsServerQueryParam param) {
        SmsServerExample example = new SmsServerExample();
        SmsServerExample.Criteria criteria = example.createCriteria();
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
        if (param.getName() != null && !"".equals(param.getName())) {
            criteria.andNameEqualTo(param.getName());
        }
        if (param.getCreateBy() != null && !"".equals(param.getCreateBy())) {
            criteria.andCreateByEqualTo(param.getCreateBy());
        }
        if (param.getUpdateBy() != null && !"".equals(param.getUpdateBy())) {
            criteria.andUpdateByEqualTo(param.getUpdateBy());
        }
        if(param.getIp() != null && !"".equals(param.getIp())) {
            criteria.andIpEqualTo(param.getIp());
        }
        return example;
    }
}
