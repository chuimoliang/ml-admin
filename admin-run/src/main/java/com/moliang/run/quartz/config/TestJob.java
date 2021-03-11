package com.moliang.run.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/12 3:36
 * @Version 1.0
 */
@Slf4j
@Component
public class TestJob {
    private static int jobExecuteCounts = 0;
    private void testJob() {
        log.info("任务执行成功" + " : " + jobExecuteCounts);
    }
}
