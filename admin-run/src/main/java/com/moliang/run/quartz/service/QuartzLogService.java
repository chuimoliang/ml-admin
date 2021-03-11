package com.moliang.run.quartz.service;

import com.moliang.run.quartz.model.QuartzJobQueryParam;
import com.moliang.run.quartz.model.QuartzLog;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 17:23
 * @Version 1.0
 */
public interface QuartzLogService {

    /**
     * 查询
     * @param param \
     * @return \
     */
    List<QuartzLog> listAll(QuartzJobQueryParam param);

    /**
     * 分页查询
     * @param param 查询参数
     * @param PageSize 页大小
     * @param pageNum 页码
     * @return \
     */
    List<QuartzLog> list(QuartzJobQueryParam param, Integer PageSize, Integer pageNum);

    /**
     * 导出任务日志记录
     * @param res \
     * @param response \
     */
    void download(List<QuartzLog> res, HttpServletResponse response) throws IOException;
}
