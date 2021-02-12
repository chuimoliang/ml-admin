package com.moliang.run.mapper;

import com.moliang.run.model.AmsAdminRoleRelation;
import com.moliang.run.model.AmsAdminRoleRelationExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsAdminRoleRelationMapper {
    long countByExample(AmsAdminRoleRelationExample example);

    int deleteByExample(AmsAdminRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsAdminRoleRelation record);

    int insertSelective(AmsAdminRoleRelation record);

    List<AmsAdminRoleRelation> selectByExample(AmsAdminRoleRelationExample example);

    AmsAdminRoleRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsAdminRoleRelation record, @Param("example") AmsAdminRoleRelationExample example);

    int updateByExample(@Param("record") AmsAdminRoleRelation record, @Param("example") AmsAdminRoleRelationExample example);

    int updateByPrimaryKeySelective(AmsAdminRoleRelation record);

    int updateByPrimaryKey(AmsAdminRoleRelation record);
}