package com.moliang.run.quartz.mapper;

import com.moliang.run.quartz.model.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/8 17:10
 * @Version 1.0
 */
@Mapper
public interface QuartzJobDao {

    /**
     * 插入并获取编号
     * @param job 插入任务
     * @return \
     */
    int insert(QuartzJob job);

    /**
     * 根据状态获取任务
     * @param bool \
     * @return \
     */
    List<QuartzJob> selectByStatus(Boolean bool);

}
