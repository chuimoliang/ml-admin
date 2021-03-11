package com.moliang.run.quartz.service;

import com.moliang.run.quartz.model.QuartzJob;
import com.moliang.run.quartz.model.QuartzJobParam;
import com.moliang.run.quartz.model.QuartzJobQueryParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 17:23
 * @Version 1.0
 */
public interface QuartzJobService {

    /**
     * 查询
     * @param param \
     * @return \
     */
    List<QuartzJob> listAll(QuartzJobQueryParam param);

    /**
     * 分页查询
     * @param param 查询参数
     * @param PageSize 页大小
     * @param pageNum 页码
     * @return \
     */
    List<QuartzJob> list(QuartzJobQueryParam param, Integer PageSize, Integer pageNum);

    /**
     * 创建定时任务
     * @param param \
     * @param principal \
     * @return \
     */
    QuartzJob create(QuartzJobParam param, Principal principal);

    /**
     * 更新定时任务
     * @param param \
     * @param principal \
     * @param id 更新编号
     * @return 更新数量
     */
    int update(QuartzJobParam param, Long id, Principal principal);

    /**
     * 更新任务状态
     * @param job \
     * @return \
     */
    int updateStatus(QuartzJob job);

    /**
     * 更新任务状态
     * @param id \
     * @return \
     */
    int updateStatus(Long id);

    /**
     * 删除任务
     * @param ids 任务编号集合
     * @return 删除数量
     */
    int delete(Set<Long> ids);

    /**
     * 执行任务
     * @param id 任务编号
     * @return 执行结果
     */
    int execute(Long id);

    /**
     * 执行子任务
     * @param tasks \
     * @return \
     */
    int executeSubTasks(String[] tasks) throws InterruptedException;

    /**
     * 导出任务记录
     * @param res \
     * @param response \
     */
    void download(List<QuartzJob> res, HttpServletResponse response) throws IOException;

}
