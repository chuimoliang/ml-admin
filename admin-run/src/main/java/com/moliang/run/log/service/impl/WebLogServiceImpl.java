package com.moliang.run.log.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.moliang.run.log.model.LogQueryParam;
import com.moliang.run.log.model.WebLog;
import com.moliang.run.log.model.WebLogExample;
import com.moliang.run.log.service.WebLogService;
import com.moliang.run.mapper.WebLogMapper;
import com.moliang.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 16:45
 * @Version 1.0
 */
@Service
@Slf4j
public class WebLogServiceImpl implements WebLogService {

    @Autowired
    private WebLogMapper webLogMapper;

    @Override
    public List<WebLog> list(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return webLogMapper.selectByExample(new WebLogExample());
    }

    @Override
    public List<WebLog> list(String username, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        WebLogExample example = new WebLogExample();
        WebLogExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        return webLogMapper.selectByExample(example);
    }

    @Override
    public List<WebLog> listAll() {
        return webLogMapper.selectByExample(new WebLogExample());
    }

    @Override
    public List<WebLog> list(LogQueryParam param, Integer pageSize, Integer pageNum) {
        WebLogExample example = new WebLogExample();
        WebLogExample.Criteria criteria = example.createCriteria();
        if(param.getUsername() != null && !param.getUsername().equals("")) {
            criteria.andUsernameEqualTo(param.getUsername());
        }
        if(param.getUri() != null && !param.getUri().equals("")) {
            criteria.andUriEqualTo(param.getUri());
        }
        if(param.getAddress() != null && !param.getAddress().equals("")) {
            criteria.andAddressEqualTo(param.getAddress());
        }
        if(param.getDescription() != null && !param.getDescription().equals("")) {
            criteria.andDescriptionEqualTo(param.getDescription());
        }
        if(param.getBrowser() != null && !param.getBrowser().equals("")) {
            criteria.andBrowserEqualTo(param.getBrowser());
        }
        if(param.getStartTime() != null && param.getToTime() != null) {
            criteria.andCreateTimeBetween(param.getStartTime(), param.getToTime());
        } else if(param.getStartTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(param.getStartTime());
        } else if(param.getToTime() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(param.getToTime());
        }
        PageHelper.startPage(pageNum, pageSize);
        return webLogMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(WebLog webLog) {
        webLogMapper.insert(webLog);
    }

    @Override
    public void download(List<WebLog> logs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WebLog log : logs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", log.getUsername());
            map.put("方法", log.getMethod());
            map.put("uri", log.getUri());
            map.put("IP", log.getIp());
            map.put("IP来源", log.getAddress());
            map.put("描述", log.getDescription());
            map.put("浏览器", log.getBrowser());
            map.put("请求耗时/毫秒", log.getSpendTime());
            map.put("创建日期", log.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void delete(Long id) {

    }
}
