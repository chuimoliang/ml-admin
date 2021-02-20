package com.moliang.run.mnt.service;

import com.moliang.run.mnt.model.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @Use 服务器管理
 * @Author Chui moliang
 * @Date 2021/2/21 0:49
 * @Version 1.0
 */
public interface SmsServerService {

    /**
     * 查询全部
     * @return 结果集合
     */
    List<SmsServer> getServerList();

    /**
     * 查询全部
     * @param param 查询条件
     * @return 结果集合
     */
    List<SmsServer> getServerList(SmsServerQueryParam param);

    /**
     * 服务器通用查询
     * @param param 查询条件
     * @param pageSize 分页大小
     * @param pageNum 分页页码
     * @return 项目集合
     */
    List<SmsServer> getServerList(SmsServerQueryParam param, Integer pageSize, Integer pageNum);

    SmsServer getById(Long id);

    SmsServer getByIp(String ip);

    /**
     * 导出服务器
     * @param res 服务器集合
     * @param response http响应
     * @throws IOException
     */
    void download(List<SmsServer> res, HttpServletResponse response) throws IOException;

    /**
     * 测试服务器连接
     * @param server 服务器
     * @return 连接结果
     */
    Boolean testConnect(SmsServer server);

    SmsServer create(SmsServerParam param, Principal principal);

    int delete(Set<Long> ids);

    SmsServer update(SmsServerParam param, Long id, Principal principal);
}
