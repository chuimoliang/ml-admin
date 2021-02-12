package com.moliang.run.mapper;

import com.moliang.run.model.AmsAdminLoginLog;
import com.moliang.run.model.AmsAdminLoginLogExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsAdminLoginLogMapper {
    long countByExample(AmsAdminLoginLogExample example);

    int deleteByExample(AmsAdminLoginLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsAdminLoginLog record);

    int insertSelective(AmsAdminLoginLog record);

    List<AmsAdminLoginLog> selectByExample(AmsAdminLoginLogExample example);

    AmsAdminLoginLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsAdminLoginLog record, @Param("example") AmsAdminLoginLogExample example);

    int updateByExample(@Param("record") AmsAdminLoginLog record, @Param("example") AmsAdminLoginLogExample example);

    int updateByPrimaryKeySelective(AmsAdminLoginLog record);

    int updateByPrimaryKey(AmsAdminLoginLog record);
}