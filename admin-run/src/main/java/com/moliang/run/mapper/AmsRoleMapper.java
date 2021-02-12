package com.moliang.run.mapper;

import com.moliang.run.model.AmsRole;
import com.moliang.run.model.AmsRoleExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsRoleMapper {
    long countByExample(AmsRoleExample example);

    int deleteByExample(AmsRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsRole record);

    int insertSelective(AmsRole record);

    List<AmsRole> selectByExample(AmsRoleExample example);

    AmsRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsRole record, @Param("example") AmsRoleExample example);

    int updateByExample(@Param("record") AmsRole record, @Param("example") AmsRoleExample example);

    int updateByPrimaryKeySelective(AmsRole record);

    int updateByPrimaryKey(AmsRole record);
}