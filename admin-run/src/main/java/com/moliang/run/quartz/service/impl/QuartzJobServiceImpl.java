package com.moliang.run.quartz.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.moliang.exception.ApiException;
import com.moliang.run.mnt.model.SmsServer;
import com.moliang.run.quartz.config.JobCache;
import com.moliang.run.quartz.config.JobResult;
import com.moliang.run.quartz.config.JobStatus;
import com.moliang.run.quartz.config.QuartzConfig;
import com.moliang.run.quartz.mapper.QuartzJobDao;
import com.moliang.run.quartz.mapper.QuartzJobMapper;
import com.moliang.run.quartz.model.QuartzJob;
import com.moliang.run.quartz.model.QuartzJobExample;
import com.moliang.run.quartz.model.QuartzJobParam;
import com.moliang.run.quartz.model.QuartzJobQueryParam;
import com.moliang.run.quartz.service.QuartzJobService;
import com.moliang.run.quartz.utils.QuartzManage;
import com.moliang.service.RedisService;
import com.moliang.utils.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    private QuartzManage quartzManage;
    @Autowired
    private RedisService redisService;

    @Override
    public int updateStatus(QuartzJob job) {
        if (job == null) {
            throw new ApiException("任务不存在");
        }
        if (job.getStatus()) {
            quartzManage.pauseJob(job);
            job.setStatus(false);
        } else {
            quartzManage.resumeJob(job);
            job.setStatus(true);
        }
        return jobMapper.updateByPrimaryKeySelective(job);
    }

    @Override
    public int updateStatus(Long id) {
        return updateStatus(jobMapper.selectByPrimaryKey(id));
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int executeSubTasks(String[] tasks) throws InterruptedException {
        int count = 0;
        for (String id : tasks) {
            QuartzJob job = jobMapper.selectByPrimaryKey(Long.valueOf(id));
            String uuid = IdUtil.simpleUUID();
            JobCache.put(job, uuid);
            execute(job);
            Boolean res = (Boolean) redisService.get(uuid);
            while (res == null) {
                // 休眠5秒，再次获取子任务执行情况
                Thread.sleep(5000);
                res = (Boolean) redisService.get(uuid);
            }
            if(!res){
                redisService.del(uuid);
                break;
            }
            count++;
        }
        return count;
    }

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

    public int execute(QuartzJob job) {
        if (job == null) return -1;
        quartzManage.runJobNow(job);
        return 1;
    }

    @Override
    public int execute(Long id) {
        return execute(jobMapper.selectByPrimaryKey(id));
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
