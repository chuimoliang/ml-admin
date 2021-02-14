package com.moliang.run.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Use 线程池配置属性
 * @Author Chui moliang
 * @Date 2021/2/15 1:54
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "async.pool")
public class AsyncProperties {

    /** 核心线程池大小 **/
    private int corePoolSize;

    /** 最大线程数 **/
    private int maxPoolSize;

    /** 活跃时间 **/
    private int keepAliveSeconds;

    /** 队列容量 **/
    private int queueCapacity;
}
