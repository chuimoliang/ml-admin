package com.moliang.run.mapper;

import com.moliang.run.model.AmsRoleMenuRelation;
import com.moliang.run.model.AmsRoleMenuRelationExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsRoleMenuRelationMapper {
    long countByExample(AmsRoleMenuRelationExample example);

    int deleteByExample(AmsRoleMenuRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsRoleMenuRelation record);

    int insertSelective(AmsRoleMenuRelation record);

    List<AmsRoleMenuRelation> selectByExample(AmsRoleMenuRelationExample example);

    AmsRoleMenuRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsRoleMenuRelation record, @Param("example") AmsRoleMenuRelationExample example);

    int updateByExample(@Param("record") AmsRoleMenuRelation record, @Param("example") AmsRoleMenuRelationExample example);

    int updateByPrimaryKeySelective(AmsRoleMenuRelation record);

    int updateByPrimaryKey(AmsRoleMenuRelation record);
}