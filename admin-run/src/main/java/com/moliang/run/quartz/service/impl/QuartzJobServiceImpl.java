package com.moliang.run.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.moliang.run.mnt.model.SmsServer;
import com.moliang.run.quartz.mapper.QuartzJobDao;
import com.moliang.run.quartz.mapper.QuartzJobMapper;
import com.moliang.run.quartz.model.QuartzJob;
import com.moliang.run.quartz.model.QuartzJobExample;
import com.moliang.run.quartz.model.QuartzJobParam;
import com.moliang.run.quartz.model.QuartzJobQueryParam;
import com.moliang.run.quartz.service.QuartzJobService;
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
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 23:53
 * @Version 1.0
 */
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    @Autowired
    private QuartzJobMapper jobMapper;
    @Autowired
    private QuartzJobDao jobDao;

    @Override
    public List<QuartzJob> listAll(QuartzJobQueryParam param) {
        return jobMapper.selectByExample(getExample(param));
    }

    @Override
    public List<QuartzJob> list(QuartzJobQueryParam param, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return jobMapper.selectByExample(getExample(param));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob create(QuartzJobParam param, Principal principal) {
        QuartzJob job = new QuartzJob();
        BeanUtils.copyProperties(param, job);
        job.setCreateBy(principal.getName());
        job.setCreateTime(new Date());
        int count = jobDao.insert(job);
        if (count > 0) return job;
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(QuartzJobParam param, Long id, Principal principal) {
        QuartzJob job = new QuartzJob();
        BeanUtils.copyProperties(param, job);
        job.setUpdateBy(principal.getName());
        job.setId(id);
        return jobMapper.updateByPrimaryKeySelective(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Set<Long> ids) {
        int count = 0;
        for (Long id : ids) {
            count += jobMapper.deleteByPrimaryKey(id);
        }
        return count;
    }

    @Override
    public int execute(Long id) {
        return 0;
    }

    @Override
    public void download(List<QuartzJob> res, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzJob job : res) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务名称", job.getJobName());
            map.put("Bean名称", job.getBeanName());
            map.put("cron表达式", job.getCronExpression());
            map.put("状态", job.getStatus());
            map.put("执行方法", job.getMethodName());
            map.put("参数", job.getParams());
            map.put("描述", job.getDescription());
            map.put("创建日期", job.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    private QuartzJobExample getExample(QuartzJobQueryParam param) {
        QuartzJobExample example = new QuartzJobExample();
        QuartzJobExample.Criteria criteria = example.createCriteria();
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
