package com.moliang.run.mnt.service;

import com.moliang.run.mnt.model.SmsItem;
import com.moliang.run.mnt.model.SmsItemParam;
import com.moliang.run.mnt.model.SmsItemQueryParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @Use 项目管理
 * @Author Chui moliang
 * @Date 2021/2/20 1:20
 * @Version 1.0
 */
public interface SmsItemService {

    /**
     * 查询全部
     * @return 结果集合
     */
    List<SmsItem> getItemList();

    /**
     * 查询全部
     * @param param 查询条件
     * @return 结果集合
     */
    List<SmsItem> getItemList(SmsItemQueryParam param);

    /**
     * 项目通用查询
     * @param param 查询条件
     * @param pageSize 分页大小
     * @param pageNum 分页页码
     * @return 项目集合
     */
    List<SmsItem> getItemList(SmsItemQueryParam param, Integer pageSize, Integer pageNum);

    /**
     * 导出项目
     * @param res 项目集合
     * @param response http响应
     * @throws IOException
     */
    void download(List<SmsItem> res, HttpServletResponse response) throws IOException;

    SmsItem create(SmsItemParam param, Principal principal);

    int delete(Set<Long> ids);

    int update(SmsItemParam param, Long id, Principal principal);

}
