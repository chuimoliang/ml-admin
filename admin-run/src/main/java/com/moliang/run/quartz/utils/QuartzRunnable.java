package com.moliang.run.quartz.utils;

import com.moliang.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @Use 执行任务线程
 * @Author Chui moliang
 * @Date 2021/3/9 11:52
 * @Version 1.0
 */
@Slf4j
public class QuartzRunnable implements Callable<Object> {
    private final Object target;
    private final Method method;
    private final String params;

    /**
     * 获取执行方法
     * @param beanName Bean名称
     * @param methodName 方法名称
     * @param params 参数
     */
    QuartzRunnable(String beanName, String methodName, String params)
            throws NoSuchMethodException, SecurityException {
        this.target = SpringContextHolder.getBean(beanName);
        this.params = params;
        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    /**
     * 执行方法
     */
    @Override
    public Object call() throws Exception {
        ReflectionUtils.makeAccessible(method);
        if (StringUtils.isNotBlank(params)) {
            method.invoke(target, params);
        } else {
            method.invoke(target);
        }
        return null;
    }
}
