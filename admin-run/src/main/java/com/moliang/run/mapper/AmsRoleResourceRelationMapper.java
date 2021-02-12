package com.moliang.run.mapper;

import com.moliang.run.model.AmsRoleResourceRelation;
import com.moliang.run.model.AmsRoleResourceRelationExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsRoleResourceRelationMapper {
    long countByExample(AmsRoleResourceRelationExample example);

    int deleteByExample(AmsRoleResourceRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsRoleResourceRelation record);

    int insertSelective(AmsRoleResourceRelation record);

    List<AmsRoleResourceRelation> selectByExample(AmsRoleResourceRelationExample example);

    AmsRoleResourceRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsRoleResourceRelation record, @Param("example") AmsRoleResourceRelationExample example);

    int updateByExample(@Param("record") AmsRoleResourceRelation record, @Param("example") AmsRoleResourceRelationExample example);

    int updateByPrimaryKeySelective(AmsRoleResourceRelation record);

    int updateByPrimaryKey(AmsRoleResourceRelation record);
}