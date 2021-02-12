package com.moliang.run.mapper;

import com.moliang.run.model.AmsAdmin;
import com.moliang.run.model.AmsAdminExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsAdminMapper {
    long countByExample(AmsAdminExample example);

    int deleteByExample(AmsAdminExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsAdmin record);

    int insertSelective(AmsAdmin record);

    List<AmsAdmin> selectByExample(AmsAdminExample example);

    AmsAdmin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsAdmin record, @Param("example") AmsAdminExample example);

    int updateByExample(@Param("record") AmsAdmin record, @Param("example") AmsAdminExample example);

    int updateByPrimaryKeySelective(AmsAdmin record);

    int updateByPrimaryKey(AmsAdmin record);
}