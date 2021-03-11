package com.moliang.run.config;

import com.moliang.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/9 1:01
 * @Version 1.0
 */
public class ThreadPoolExecutorUtil {

    private ThreadPoolExecutorUtil() {}

    public static ThreadPoolExecutor getPool() {
        AsyncProperties properties = SpringContextHolder.getBean(AsyncProperties.class);
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(properties.getQueueCapacity()),
                new ThreadFactoryName()
        );
    }

}
