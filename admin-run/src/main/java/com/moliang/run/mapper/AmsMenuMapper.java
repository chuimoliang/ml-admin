package com.moliang.run.mapper;

import com.moliang.run.model.AmsMenu;
import com.moliang.run.model.AmsMenuExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsMenuMapper {
    long countByExample(AmsMenuExample example);

    int deleteByExample(AmsMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsMenu record);

    int insertSelective(AmsMenu record);

    List<AmsMenu> selectByExample(AmsMenuExample example);

    AmsMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsMenu record, @Param("example") AmsMenuExample example);

    int updateByExample(@Param("record") AmsMenu record, @Param("example") AmsMenuExample example);

    int updateByPrimaryKeySelective(AmsMenu record);

    int updateByPrimaryKey(AmsMenu record);
}