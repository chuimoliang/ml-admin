package com.moliang.run.mapper;

import com.moliang.run.model.AmsResource;
import com.moliang.run.model.AmsResourceExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsResourceMapper {
    long countByExample(AmsResourceExample example);

    int deleteByExample(AmsResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsResource record);

    int insertSelective(AmsResource record);

    List<AmsResource> selectByExample(AmsResourceExample example);

    AmsResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsResource record, @Param("example") AmsResourceExample example);

    int updateByExample(@Param("record") AmsResource record, @Param("example") AmsResourceExample example);

    int updateByPrimaryKeySelective(AmsResource record);

    int updateByPrimaryKey(AmsResource record);
}