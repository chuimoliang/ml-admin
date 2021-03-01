package com.moliang.run.mnt.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.run.mnt.mapper.SmsServerDao;
import com.moliang.run.mnt.mapper.SmsServerMapper;
import com.moliang.run.mnt.model.*;
import com.moliang.run.mnt.service.SmsServerService;
import com.moliang.utils.ExecuteShellUtil;
import com.moliang.utils.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

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
    @Autowired
    private SmsServerDao serverDao;

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
        return serverMapper.selectByPrimaryKey(id);
    }

    @Override
    public SmsServer getByIp(String ip) {
        return serverDao.getByIp(ip);
    }

    @Override
    public void download(List<SmsServer> res, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SmsServer server : res) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("服务器名称", server.getName());
            map.put("IP", server.getIp());
            map.put("端口", server.getPort());
            map.put("服务器账号", server.getAccount());
            map.put("密码", server.getPassword());
            map.put("创建日期", server.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    @Override
    public long testConnect(Long id) {
        ExecuteShellUtil shellUtil = null;
        try {
            SmsServer server = serverMapper.selectByPrimaryKey(id);
            shellUtil = new ExecuteShellUtil(server.getIp(), server.getAccount(), server.getPassword(), server.getPort());
            long startTime = System.currentTimeMillis();
            int res = shellUtil.execute("ls");
            long endTime = System.currentTimeMillis();
            if(res == 0) return (endTime - startTime) / 2;
        } catch (Exception e) {
            return -1L;
        } finally {
            if(shellUtil != null) {
                shellUtil.close();
            }
        }
        return -1L;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsServer create(SmsServerParam param, Principal principal) {
        SmsServer server = new SmsServer();
        BeanUtils.copyProperties(param, server);
        server.setCreateBy(principal.getName());
        server.setCreateTime(new Date());
        int count = serverDao.insertGetId(server);
        if(count > 0) return server;
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
    public int update(SmsServerParam param, Long id, Principal principal) {
        SmsServer server = new SmsServer();
        BeanUtils.copyProperties(param, server);
        server.setUpdateBy(principal.getName());
        server.setId(id);
        return serverMapper.updateByPrimaryKeySelective(server);
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
            criteria.andNameLike("%" + param.getName() + "%");
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
