package com.moliang.run.log.service;

import com.moliang.run.log.model.LogQueryParam;
import com.moliang.run.log.model.WebLog;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/13 1:33
 * @Version 1.0
 */
public interface WebLogService {

    /**
     * 分页查询日志
     * @param pageSize 页大小
     * @param pageNum 页码
     * @return 日志集合
     */
    List<WebLog> list(Integer pageSize, Integer pageNum);

    /**
     * 获取指定用户日志
     * @param username 用户名
     * @return 用户日志记录
     */
    List<WebLog>  list(String username, Integer pageSize, Integer pageNum);

    /**
     * 获取所有日志
     * @return 日志集合
     */
    List<WebLog> listAll();

    /**
     * 获取所有符合查询条件的日志
     * @return 日志集合
     */
    List<WebLog> listAll(LogQueryParam param);

    /**
     * 根据条件分页查询日志
     * @param param 日志查询条件
     * @param pageSize 数据页大小
     * @param pageNum 数据页编号
     * @return 日志列表
     */
    List<WebLog> list(LogQueryParam param, Integer pageSize, Integer pageNum);

    /**
     * 异步存入日志
     * @param webLog 日志
     */
    @Async
    void save(WebLog webLog);

    /**
     * 导出日志记录
     * @param logs 日志记录
     * @param response http响应
     * @throws IOException io异常
     */
    void download(List<WebLog> logs, HttpServletResponse response) throws IOException;

    /**
     * 删除某条记录
     * @param id 日志主键
     */
    void delete(Long id);
}
