package com.moliang.run.mnt.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.enums.ResponseCode;
import com.moliang.exception.ApiException;
import com.moliang.run.mnt.mapper.*;
import com.moliang.run.mnt.model.*;
import com.moliang.run.mnt.service.SmsDeployService;
import com.moliang.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * @Use 部署管理实现类
 * @Author Chui moliang
 * @Date 2021/2/23 1:10
 * @Version 1.0
 */
@Service
@Slf4j
public class SmsDeployServiceImpl implements SmsDeployService {

    @Autowired
    private SmsDeployMapper deployMapper;
    @Autowired
    private SmsServerMapper serverMapper;
    @Autowired
    private SmsItemMapper itemMapper;
    @Autowired
    private SmsServerDeployRelationMapper serverDeployRelationMapper;
    @Autowired
    private SmsServerDeployRelationDao serverDeployRelationDao;

    @Override
    public int serverStatus(Long id) {
        return 0;
    }

    @Override
    public int start(Long id) {
        SmsDeploy deploy = deployMapper.selectByPrimaryKey(id);
        if(deploy == null) return -1;
        List<SmsServer> servers = serverDeployRelationDao.getServersByDeployId(id);
        SmsItem item = itemMapper.selectByPrimaryKey(deploy.getItemId());
        if(servers.size() == 0 || item == null) return -1;
        for(SmsServer server : servers) {

        }
        return 0;
    }

    @Override
    public int stop(Long id) {
        return 0;
    }

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
        SmsServer server = serverMapper.selectByPrimaryKey(param.getServerId());
        SmsItem item = itemMapper.selectByPrimaryKey(param.getItemId());
        if (server == null || item == null) {
            return null;
        }
        SmsDeploy deploy = new SmsDeploy();
        deploy.setDescription(param.getDescription());
        deploy.setItemId(item.getId());
        deploy.setCreateBy(principal.getName());
        deploy.setCreateTime(new Date());
        deploy.setItemName(item.getName());
        deploy.setServerIp(server.getIp());
        deploy.setStatus("新建");
        deployMapper.insertSelective(deploy);
        return deploy;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsDeploy update(SmsDeployParam param, Long id, Principal principal) {
        SmsServer server = serverMapper.selectByPrimaryKey(param.getServerId());
        SmsItem item = itemMapper.selectByPrimaryKey(param.getItemId());
        if (server == null || item == null) {
            return null;
        }
        SmsDeploy deploy = new SmsDeploy();
        deploy.setDescription(param.getDescription());
        deploy.setItemId(item.getId());
        deploy.setItemName(item.getName());
        deploy.setServerIp(server.getIp());
        deploy.setUpdateBy(principal.getName());
        deploy.setId(id);
        deployMapper.updateByPrimaryKeySelective(deploy);
        return deploy;
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
    public void download(List<SmsDeploy> res, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SmsDeploy deployDto : res) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("应用名称", deployDto.getItemName());
            map.put("服务器", deployDto.getServerIp());
            map.put("部署日期", deployDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
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
        if (param.getStartItemId() != null) {
            criteria.andItemIdGreaterThanOrEqualTo(param.getStartItemId());
        }
        if (param.getToItemId() != null) {
            criteria.andItemIdLessThanOrEqualTo(param.getToItemId());
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
        return example;
    }
}
