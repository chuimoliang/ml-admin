package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsServer;
import com.moliang.run.mnt.model.SmsServerExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsServerMapper {
    long countByExample(SmsServerExample example);

    int deleteByExample(SmsServerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsServer record);

    int insertSelective(SmsServer record);

    List<SmsServer> selectByExample(SmsServerExample example);

    SmsServer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsServer record, @Param("example") SmsServerExample example);

    int updateByExample(@Param("record") SmsServer record, @Param("example") SmsServerExample example);

    int updateByPrimaryKeySelective(SmsServer record);

    int updateByPrimaryKey(SmsServer record);
}