package com.moliang.run.quartz.mapper;

import com.moliang.run.quartz.model.QuartzLog;
import com.moliang.run.quartz.model.QuartzLogExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuartzLogMapper {
    long countByExample(QuartzLogExample example);

    int deleteByExample(QuartzLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(QuartzLog record);

    int insertSelective(QuartzLog record);

    List<QuartzLog> selectByExampleWithBLOBs(QuartzLogExample example);

    List<QuartzLog> selectByExample(QuartzLogExample example);

    QuartzLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QuartzLog record, @Param("example") QuartzLogExample example);

    int updateByExampleWithBLOBs(@Param("record") QuartzLog record, @Param("example") QuartzLogExample example);

    int updateByExample(@Param("record") QuartzLog record, @Param("example") QuartzLogExample example);

    int updateByPrimaryKeySelective(QuartzLog record);

    int updateByPrimaryKeyWithBLOBs(QuartzLog record);

    int updateByPrimaryKey(QuartzLog record);
}