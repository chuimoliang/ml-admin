package com.moliang.run.quartz.config;

import com.moliang.run.quartz.mapper.QuartzJobDao;
import com.moliang.run.quartz.model.QuartzJob;
import com.moliang.run.quartz.utils.QuartzManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/10 23:27
 * @Version 1.0
 */
@Slf4j
@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private QuartzManage quartzManage;
    @Autowired
    private QuartzJobDao jobDao;

    /**
     * 启动项目时激活已启用的任务
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("--------------------注入定时任务---------------------");
        List<QuartzJob> quartzJobs = jobDao.selectByStatus(JobStatus.ENABLE.getCode());
        quartzJobs.forEach(quartzManage::addJob);
        log.info("--------------------定时任务注入完成---------------------");
    }
}
