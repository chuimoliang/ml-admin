package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsServerDeployRelation;
import com.moliang.run.mnt.model.SmsServerDeployRelationExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsServerDeployRelationMapper {
    long countByExample(SmsServerDeployRelationExample example);

    int deleteByExample(SmsServerDeployRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsServerDeployRelation record);

    int insertSelective(SmsServerDeployRelation record);

    List<SmsServerDeployRelation> selectByExample(SmsServerDeployRelationExample example);

    SmsServerDeployRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsServerDeployRelation record, @Param("example") SmsServerDeployRelationExample example);

    int updateByExample(@Param("record") SmsServerDeployRelation record, @Param("example") SmsServerDeployRelationExample example);

    int updateByPrimaryKeySelective(SmsServerDeployRelation record);

    int updateByPrimaryKey(SmsServerDeployRelation record);
}