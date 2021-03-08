package com.moliang.run.quartz.mapper;

import com.moliang.run.quartz.model.QuartzJob;
import com.moliang.run.quartz.model.QuartzJobExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuartzJobMapper {
    long countByExample(QuartzJobExample example);

    int deleteByExample(QuartzJobExample example);

    int deleteByPrimaryKey(Long id);

    int insert(QuartzJob record);

    int insertSelective(QuartzJob record);

    List<QuartzJob> selectByExample(QuartzJobExample example);

    QuartzJob selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QuartzJob record, @Param("example") QuartzJobExample example);

    int updateByExample(@Param("record") QuartzJob record, @Param("example") QuartzJobExample example);

    int updateByPrimaryKeySelective(QuartzJob record);

    int updateByPrimaryKey(QuartzJob record);
}