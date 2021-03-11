package com.moliang.run.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.run.quartz.mapper.QuartzLogMapper;
import com.moliang.run.quartz.model.*;
import com.moliang.run.quartz.service.QuartzLogService;
import com.moliang.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/11 0:30
 * @Version 1.0
 */
@Service
public class QuartzLogServiceImpl implements QuartzLogService {

    @Autowired
    private QuartzLogMapper logMapper;

    @Override
    public List<QuartzLog> listAll(QuartzJobQueryParam param) {
        return logMapper.selectByExample(getExample(param));
    }

    @Override
    public List<QuartzLog> list(QuartzJobQueryParam param, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return logMapper.selectByExample(getExample(param));
    }

    @Override
    public void download(List<QuartzLog> res, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzLog log : res) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务名称", log.getJobName());
            map.put("Bean名称", log.getBeanName());
            map.put("cron表达式", log.getCronExpression());
            map.put("执行结果", log.getResult());
            map.put("执行方法", log.getMethodName());
            map.put("参数", log.getParams());
            map.put("消耗时间", log.getTime());
            map.put("异常堆栈信息", log.getExceptionDetail());
            map.put("创建日期", log.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    private QuartzLogExample getExample(QuartzJobQueryParam param) {
        QuartzLogExample example = new QuartzLogExample();
        QuartzLogExample.Criteria criteria = example.createCriteria();
        if(param.getKeyword() != null && !"".equals(param.getKeyword())) {
            criteria.andJobNameLike("%" + param.getKeyword() + "%");
        }
        if (param.getStartTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(param.getStartTime());
        }
        if (param.getToTime() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(param.getToTime());
        }
        return example;
    }
}
