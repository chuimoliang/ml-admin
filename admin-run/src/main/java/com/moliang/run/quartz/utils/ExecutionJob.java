package com.moliang.run.quartz.utils;

import com.moliang.run.config.ThreadPoolExecutorUtil;
import com.moliang.run.quartz.config.JobCache;
import com.moliang.run.quartz.config.JobResult;
import com.moliang.run.quartz.config.JobStatus;
import com.moliang.run.quartz.config.QuartzConfig;
import com.moliang.run.quartz.mapper.QuartzLogMapper;
import com.moliang.run.quartz.model.QuartzJob;
import com.moliang.run.quartz.model.QuartzLog;
import com.moliang.run.quartz.service.QuartzJobService;
import com.moliang.service.RedisService;
import com.moliang.utils.SpringContextHolder;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/9 15:34
 * @Version 1.0
 */
@Async
public class ExecutionJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionJob.class);

    /** 该处仅供参考 */
    private final static ThreadPoolExecutor EXECUTOR = ThreadPoolExecutorUtil.getPool();

    @Override
    public void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get("JOB_KEY");
        // 获取spring bean
        QuartzLogMapper quartzLogRepository = SpringContextHolder.getBean(QuartzLogMapper.class);
        QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);
        RedisService redisUtils = SpringContextHolder.getBean(RedisService.class);

        String uuid = JobCache.getUuid(quartzJob);

        QuartzLog log = new QuartzLog();
        log.setJobName(quartzJob.getJobName());
        log.setBeanName(quartzJob.getBeanName());
        log.setMethodName(quartzJob.getMethodName());
        log.setParams(quartzJob.getParams());
        long startTime = System.currentTimeMillis();
        log.setCronExpression(quartzJob.getCronExpression());
        try {
            // 执行任务
            logger.info("--------------------------------------------------------------");
            logger.info("任务开始执行，任务名称：" + quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(),
                    quartzJob.getParams());
            Future<?> future = EXECUTOR.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            log.setTime(times);
            if(StringUtils.isNotBlank(uuid)) {
                redisUtils.set(uuid, true);
            }
            // 任务状态
            log.setResult(true);
            logger.info("任务执行完毕，任务名称：" + quartzJob.getJobName() + ", 执行时间：" + times + "毫秒");
            logger.info("--------------------------------------------------------------");
            // 判断是否存在子任务
            if(quartzJob.getSubTask() != null){
                String[] tasks = quartzJob.getSubTask().split("[,，]");
                // 执行子任务
                quartzJobService.executeSubTasks(tasks);
            }
        } catch (Exception e) {
            if(StringUtils.isNotBlank(uuid)) {
                redisUtils.set(uuid, false);
            }
            logger.error("--------------------------------------------------------------");
            logger.error("任务执行失败，任务名称：" + quartzJob.getJobName());
            e.printStackTrace();
            logger.error("--------------------------------------------------------------");
            long times = System.currentTimeMillis() - startTime;
            log.setTime(times);
            // 任务状态 0：成功 1：失败
            log.setResult(JobResult.SUCCESS.getCode());
            log.setExceptionDetail(Arrays.toString(e.getStackTrace()));
            // 任务如果失败了则暂停
            if(quartzJob.getPauseAfterFailure() != null && quartzJob.getPauseAfterFailure()){
                quartzJob.setStatus(JobStatus.CANCEL.getCode());
                //更新状态
                quartzJobService.updateStatus(quartzJob);
            }
            /**
            if(quartzJob.getEmail() != null){
                EmailService emailService = SpringContextHolder.getBean(EmailService.class);
                // 邮箱报警
                if(StringUtils.isNotBlank(quartzJob.getEmail())){
                    EmailVo emailVo = taskAlarm(quartzJob, ThrowableUtil.getStackTrace(e));
                    emailService.send(emailVo, emailService.find());
                }
            }
             **/
        } finally {
            quartzLogRepository.insert(log);
        }
    }

    /**
    private EmailVo taskAlarm(QuartzJob quartzJob, String msg) {
        EmailVo emailVo = new EmailVo();
        emailVo.setSubject("定时任务【"+ quartzJob.getJobName() +"】执行失败，请尽快处理！");
        Map<String, Object> data = new HashMap<>(16);
        data.put("task", quartzJob);
        data.put("msg", msg);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email/taskAlarm.ftl");
        emailVo.setContent(template.render(data));
        List<String> emails = Arrays.asList(quartzJob.getEmail().split("[,，]"));
        emailVo.setTos(emails);
        return emailVo;
    }
     **/
}
