package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsItemDeployRelation;
import com.moliang.run.mnt.model.SmsItemDeployRelationExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsItemDeployRelationMapper {
    long countByExample(SmsItemDeployRelationExample example);

    int deleteByExample(SmsItemDeployRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsItemDeployRelation record);

    int insertSelective(SmsItemDeployRelation record);

    List<SmsItemDeployRelation> selectByExample(SmsItemDeployRelationExample example);

    SmsItemDeployRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsItemDeployRelation record, @Param("example") SmsItemDeployRelationExample example);

    int updateByExample(@Param("record") SmsItemDeployRelation record, @Param("example") SmsItemDeployRelationExample example);

    int updateByPrimaryKeySelective(SmsItemDeployRelation record);

    int updateByPrimaryKey(SmsItemDeployRelation record);
}