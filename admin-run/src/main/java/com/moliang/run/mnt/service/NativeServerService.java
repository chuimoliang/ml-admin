package com.moliang.run.mnt.service;

import java.util.Map;

/**
 * @Use 系统运行状态监控服务
 * @Author Chui moliang
 * @Date 2021/3/7 2:23
 * @Version 1.0
 */
public interface NativeServerService {

    /**
     * 返回服务器状态
     */
    Map<String, Object> getStatus();

}
