package com.moliang.run.mnt.service;

import com.moliang.run.mnt.model.SmsDeploy;
import com.moliang.run.mnt.model.SmsDeployParam;
import com.moliang.run.mnt.model.SmsDeployQueryParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @Use 部署管理类
 * @Author Chui moliang
 * @Date 2021/2/22 1:28
 * @Version 1.0
 */
public interface SmsDeployService {

    /**
     * 上传部署应用文件
     * @param file 文件
     * @param id 部署编号
     * @return count
     * @throws IOException \
     */
    int upload(MultipartFile file, Long id) throws IOException;

    /**
     * 部署分页查询
     * @param param 查询参数
     * @param pageSize 分页大小
     * @param pageNum 分页码数
     * @return \
     */
    List<SmsDeploy> list(SmsDeployQueryParam param, Integer pageSize, Integer pageNum);

    /**
     * 部署查询 全部
     * @param param 查询参数
     * @return \
     */
    List<SmsDeploy> listAll(SmsDeployQueryParam param);

    /**
     * 根据编号获得部署
     * @param id \
     * @return \
     */
    SmsDeploy getById(Long id);

    /**
     *  新建部署
     * @param param 部署参数
     * @param principal \
     * @return \
     */
    SmsDeploy create(SmsDeployParam param, Principal principal);

    /**
     * 更新部署
     * @param param \
     * @param principal \
     * @return
     */
    SmsDeploy update(SmsDeployParam param, Long id, Principal principal);

    /**
     * 删除部署
     * @param ids 删除编号
     * @return \
     */
    int delete(Set<Long> ids);

    /**
     * 查询部署状态
     * @param id 编号
     * @return \
     */
    int serverStatus(Long id);

    /**
     * 服务还原
     * @param historyId 部署历史
     */
    void serverReduction(Long historyId);

    /**
     * 启动服务
     * @param id \
     * @return 成功启动的服务器数量
     */
    int start(Long id);

    /**
     * 停止服务
     * @param id \
     * @return 停止服务数量
     */
    int stop(Long id);

    /**
     * 导出数据
     * @param res \
     * @param response \
     * @throws IOException \
     */
    void download(List<SmsDeploy> res, HttpServletResponse response) throws IOException;

}
