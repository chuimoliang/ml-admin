package com.moliang.run.mnt.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.github.pagehelper.PageHelper;
import com.moliang.enums.ResponseCode;
import com.moliang.exception.ApiException;
import com.moliang.run.mnt.mapper.*;
import com.moliang.run.mnt.model.*;
import com.moliang.run.mnt.service.SmsDeployService;
import com.moliang.utils.ExecuteShellUtil;
import com.moliang.utils.FileUtils;
import com.moliang.utils.ScpClientUtil;
import com.moliang.websocket.MsgType;
import com.moliang.websocket.SocketMsg;
import com.moliang.websocket.WebSocketMsgServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * @Use 部署管理实现类
 * @Author Chui moliang
 * @Date 2021/2/23 1:10
 * @Version 1.0
 */
@Service
@Slf4j
public class SmsDeployServiceImpl implements SmsDeployService {

    @Autowired
    private SmsDeployMapper deployMapper;
    @Autowired
    private SmsServerMapper serverMapper;
    @Autowired
    private SmsItemMapper itemMapper;
    @Autowired
    private SmsServerDeployRelationMapper serverDeployRelationMapper;
    @Autowired
    private SmsServerDeployRelationDao serverDeployRelationDao;

    private void stopItem(int port, ExecuteShellUtil shellUtil) {
        shellUtil.execute(String.format("lsof -i :%d|grep -v \"PID\"|awk '{print \"kill -9\",$2}'|sh", port));
    }


    @Override
    public int serverStatus(Long id) {
        SmsDeploy deploy = deployMapper.selectByPrimaryKey(id);
        if(deploy == null) return -1;
        List<SmsServer> servers = serverDeployRelationDao.getServersByDeployId(id);
        SmsItem item = itemMapper.selectByPrimaryKey(deploy.getItemId());
        if(servers.size() == 0 || item == null) return -1;
        int count = 0;
        for(SmsServer server : servers) {
            ExecuteShellUtil shell =
                    new ExecuteShellUtil(server.getIp(), server.getAccount(), server.getPassword(), server.getPort());
            if(isRunning(item.getPort(), shell)) count++;
        }
        return count;
    }

    private boolean isRunning(int port, ExecuteShellUtil shell) {
        String res = shell.executeForResult(String.format("fuser -n tcp %d", port));
        return res.indexOf("/tcp:") > 0;
    }

    public int start(SmsDeploy deploy) {
        List<SmsServer> servers = serverDeployRelationDao.getServersByDeployId(deploy.getId());
        SmsItem item = itemMapper.selectByPrimaryKey(deploy.getItemId());
        if(servers.size() == 0 || item == null) return -1;
        int count = 0;
        for(SmsServer server : servers) {
            StringBuilder sb = new StringBuilder();
            ExecuteShellUtil shell =
                    new ExecuteShellUtil(server.getIp(), server.getAccount(), server.getPassword(), server.getPort());
            stopItem(item.getPort(), shell);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(item.getName());
            if(!checkFile(shell, item)) {
                sendMsg(sb.append("<br>启动失败 : 文件不存在").toString(), MsgType.ERROR);
            }
            sendMsg("下发启动命令", MsgType.INFO);
            shell.execute(item.getStartScript());
            sleep(3);
            sendMsg("应用启动中, 请耐心等待结果, 或者稍后手动查看运行状态", MsgType.INFO);
            int i = 0;
            boolean res = false;
            while(i++ < 30) {
                res = isRunning(item.getPort(), shell);
                if(res) {
                    break;
                }
                sleep(6);
            }
            if(res) {
                sb.append("<br>启动成功!");
                sendMsg(sb.toString(), MsgType.INFO);
                count++;
            } else {
                sb.append("启动失败");
                sendMsg(sb.toString(), MsgType.ERROR);
            }
            log.info(sb.toString());
            shell.close();
        }
        return count;
    }

    @Override
    public int start(Long id) {
        SmsDeploy deploy = deployMapper.selectByPrimaryKey(id);
        if(deploy == null) return -1;
        return start(deploy);
    }

    private void sleep(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMsg(String msg, MsgType type) {
        try {
            WebSocketMsgServer.sendInfo(new SocketMsg(msg, type), "deploy");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public int stop(Long id) {
        SmsDeploy deploy = deployMapper.selectByPrimaryKey(id);
        if(deploy == null) return -1;
        List<SmsServer> servers = serverDeployRelationDao.getServersByDeployId(id);
        SmsItem item = itemMapper.selectByPrimaryKey(deploy.getItemId());
        if(servers.size() == 0 || item == null) return -1;
        int count = 0;
        for(SmsServer server : servers) {
            StringBuilder sb = new StringBuilder();
            ExecuteShellUtil shell = getShell(server);
            stopItem(item.getPort(), shell);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(item.getName());
            sendMsg("下发停止命令", MsgType.INFO);
            sleep(1);
            boolean res = isRunning(item.getPort(), shell);
            if(!res) {
                sb.append("<br>关闭成功!");
                sendMsg(sb.toString(), MsgType.INFO);
                count++;
            } else {
                sb.append("<br>关闭失败");
                sendMsg(sb.toString(), MsgType.ERROR);
            }
            log.info(sb.toString());
            shell.close();
        }
        return count;
    }

    @Override
    public int upload(MultipartFile file, Long id) throws IOException {
        int count = 0;
        if(file == null) {
            throw new ApiException(ResponseCode.VALIDATE_FAILED);
        }
        SmsDeploy deploy = deployMapper.selectByPrimaryKey(id);
        if(deploy == null) {
            throw new ApiException(ResponseCode.VALIDATE_FAILED);
        }
        SmsItem item = itemMapper.selectByPrimaryKey(deploy.getItemId());
        if(item == null) {
            throw new ApiException(ResponseCode.VALIDATE_FAILED);
        }
        File itemFile = new File("/home/mlAdmin/jar"+ "/" + item.getName());
        FileUtil.del(itemFile);
        file.transferTo(itemFile);
        String msg;
        List<SmsServer> servers = serverDeployRelationDao.getServersByDeployId(id);
        for(SmsServer server : servers) {
            ExecuteShellUtil shell = getShell(server);
            boolean exist = checkFile(shell, item);
            shell.execute("mkdir -p " + item.getUploadPath());
            shell.execute("mkdir -p " + item.getBackupPath());
            shell.execute("mkdir -p " + item.getDeployPath());
            ScpClientUtil scp = getScpClientUtil(server);
            msg = String.format("上传文件到服务器:%s<br>目录:%s下，请稍等...", server.getName(), item.getUploadPath());
            sendMsg(msg, MsgType.INFO);
            scp.putFile(itemFile.getPath(), item.getUploadPath());
            if(exist) {
                sendMsg("停止原来应用", MsgType.INFO);
                stopItem(item.getPort(), shell);
                sendMsg("备份原来应用", MsgType.INFO);
                backUp(shell, item);
            }
            sendMsg("部署应用", MsgType.INFO);
            StringBuilder sb = new StringBuilder();
            sb.append("mv ").append(item.getUploadPath()).append("/")
                    .append(item.getName()).append(" ").append(item.getDeployPath());
            log.info(sb.toString());
            shell.execute(sb.toString());
            sleep(3);
            shell.execute(item.getStartScript());
            int i = 0;
            while(i++ < 30) {
                if(isRunning(item.getPort(), shell)) {
                    count++;
                    break;
                }
                sleep(6);
            }
            sb = new StringBuilder();
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(item.getName());
            sendMsg(sb.toString(), MsgType.INFO);
        }
        return count;
    }

    private void backUp(ExecuteShellUtil shell, SmsItem item) {
        String backupPath = item.getBackupPath() + "/" + item.getName() + "/" +
                DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + "\n";
        StringBuilder sb = new StringBuilder();
        sb.append("mkdir -p ").append(backupPath);
        sb.append("mv -f ").append(item.getDeployPath()).append("/").append(item.getName())
                .append(" ").append(backupPath);
        log.info("备份应用 : " + sb.toString());
        shell.execute(sb.toString());
    }

    private boolean checkFile(ExecuteShellUtil shell, SmsItem item) {
        String result = shell.executeForResult(
                "find " + item.getDeployPath() + " -name " + item.getName());
        return result.indexOf(item.getName()) > 0;
    }

    private ExecuteShellUtil getShell(SmsServer server) {
        return new ExecuteShellUtil(server.getIp(), server.getAccount(), server.getPassword(), server.getPort());
    }

    private ScpClientUtil getScpClientUtil(SmsServer server) {
        return new ScpClientUtil(server.getIp(),server.getPort(), server.getAccount(), server.getPassword());
    }

    @Override
    public List<SmsDeploy> list(SmsDeployQueryParam param, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return deployMapper.selectByExample(getExample(param));
    }

    @Override
    public List<SmsDeploy> listAll(SmsDeployQueryParam param) {
        return deployMapper.selectByExample(getExample(param));
    }

    @Override
    public SmsDeploy getById(Long id) {
        return deployMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsDeploy create(SmsDeployParam param, Principal principal) {
        SmsServer server = serverMapper.selectByPrimaryKey(param.getServerId());
        SmsItem item = itemMapper.selectByPrimaryKey(param.getItemId());
        if (server == null || item == null) {
            return null;
        }
        SmsDeploy deploy = new SmsDeploy();
        deploy.setDescription(param.getDescription());
        deploy.setItemId(item.getId());
        deploy.setCreateBy(principal.getName());
        deploy.setCreateTime(new Date());
        deploy.setItemName(item.getName());
        deploy.setServerIp(server.getIp());
        deploy.setStatus("新建");
        deployMapper.insertSelective(deploy);
        return deploy;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsDeploy update(SmsDeployParam param, Long id, Principal principal) {
        SmsServer server = serverMapper.selectByPrimaryKey(param.getServerId());
        SmsItem item = itemMapper.selectByPrimaryKey(param.getItemId());
        if (server == null || item == null) {
            return null;
        }
        SmsDeploy deploy = new SmsDeploy();
        deploy.setDescription(param.getDescription());
        deploy.setItemId(item.getId());
        deploy.setItemName(item.getName());
        deploy.setServerIp(server.getIp());
        deploy.setUpdateBy(principal.getName());
        deploy.setId(id);
        deployMapper.updateByPrimaryKeySelective(deploy);
        return deploy;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Set<Long> ids) {
        int count = 0;
        for(Long id : ids) {
            count += deployMapper.deleteByPrimaryKey(id);
        }
        return count;
    }

    @Override
    public void download(List<SmsDeploy> res, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SmsDeploy deployDto : res) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("应用名称", deployDto.getItemName());
            map.put("服务器", deployDto.getServerIp());
            map.put("部署日期", deployDto.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    private SmsDeployExample getExample(SmsDeployQueryParam param) {
        SmsDeployExample example = new SmsDeployExample();
        SmsDeployExample.Criteria criteria = example.createCriteria();
        if (param.getStartId() != null) {
            criteria.andIdGreaterThanOrEqualTo(param.getStartId());
        }
        if (param.getToId() != null) {
            criteria.andIdLessThanOrEqualTo(param.getToId());
        }
        if (param.getStartItemId() != null) {
            criteria.andItemIdGreaterThanOrEqualTo(param.getStartItemId());
        }
        if (param.getToItemId() != null) {
            criteria.andItemIdLessThanOrEqualTo(param.getToItemId());
        }
        if (param.getStartCreateTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(param.getStartCreateTime());
        }
        if (param.getToCreateTime() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(param.getToCreateTime());
        }
        if (param.getCreateBy() != null && !"".equals(param.getCreateBy())) {
            criteria.andCreateByEqualTo(param.getCreateBy());
        }
        if(param.getItemName() != null && !"".equals(param.getItemName())) {
            criteria.andItemNameLike("%" + param.getItemName() + "%");
        }
        if(param.getStatus() != null && !"".equals(param.getStatus())) {
            criteria.andStatusEqualTo(param.getStatus());
        }
        return example;
    }
}
