package com.moliang.run.mnt.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.run.mnt.mapper.SmsDeployMapper;
import com.moliang.run.mnt.model.SmsDeploy;
import com.moliang.run.mnt.model.SmsDeployExample;
import com.moliang.run.mnt.model.SmsDeployParam;
import com.moliang.run.mnt.model.SmsDeployQueryParam;
import com.moliang.run.mnt.service.SmsDeployService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/23 1:10
 * @Version 1.0
 */
@Service
@Slf4j
public class SmsDeployServiceImpl implements SmsDeployService {

    @Autowired
    private SmsDeployMapper deployMapper;

    @Override
    public List<SmsDeploy> list(SmsDeployQueryParam param, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return deployMapper.selectByExample(getExample(param));
    }

    @Override
    public List<SmsDeploy> listAll(SmsDeployQueryParam param) {
        return deployMapper.selectByExample(getExample(param));
    }

    @Override
    public SmsDeploy getById(Long id) {
        return deployMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsDeploy create(SmsDeployParam param, Principal principal) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsDeploy update(SmsDeployParam param, Principal principal) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Set<Long> ids) {
        int count = 0;
        for(Long id : ids) {
            count += deployMapper.deleteByPrimaryKey(id);
        }
        return count;
    }

    @Override
    public int serverStatus() {
        return 0;
    }

    @Override
    public int start(Long id) {
        return 0;
    }

    @Override
    public int stop(Long id) {
        return 0;
    }

    @Override
    public void download(List<SmsDeploy> res, HttpServletResponse response) throws IOException {

    }

    private SmsDeployExample getExample(SmsDeployQueryParam param) {
        SmsDeployExample example = new SmsDeployExample();
        SmsDeployExample.Criteria criteria = example.createCriteria();
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
        if (param.getCreateBy() != null && !"".equals(param.getCreateBy())) {
            criteria.andCreateByEqualTo(param.getCreateBy());
        }
        if(param.getItemName() != null && !"".equals(param.getItemName())) {
            criteria.andItemNameEqualTo(param.getItemName());
        }
        if(param.getStatus() != null && !"".equals(param.getStatus())) {
            criteria.andStatusEqualTo(param.getStatus());
        }
        if(param.getServerIp() != null && !"".equals(param.getServerIp())) {
            criteria.andServerIpEqualTo(param.getServerIp());
        }
        return example;
    }
}
