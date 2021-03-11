package com.moliang.run.quartz.config;

import com.moliang.run.quartz.model.QuartzJob;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/11 11:32
 * @Version 1.0
 */
public class JobCache {

    private static Map<QuartzJob, String> map = new ConcurrentHashMap<>();

    public static String getUuid(QuartzJob job) {
        return map.getOrDefault(job, null);
    }

    public static void put(QuartzJob job, String Uuid) {
        map.put(job, Uuid);
    }

}
