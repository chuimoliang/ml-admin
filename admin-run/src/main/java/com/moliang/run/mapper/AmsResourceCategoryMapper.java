package com.moliang.run.mapper;

import com.moliang.run.model.AmsResourceCategory;
import com.moliang.run.model.AmsResourceCategoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface AmsResourceCategoryMapper {
    long countByExample(AmsResourceCategoryExample example);

    int deleteByExample(AmsResourceCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsResourceCategory record);

    int insertSelective(AmsResourceCategory record);

    List<AmsResourceCategory> selectByExample(AmsResourceCategoryExample example);

    AmsResourceCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsResourceCategory record, @Param("example") AmsResourceCategoryExample example);

    int updateByExample(@Param("record") AmsResourceCategory record, @Param("example") AmsResourceCategoryExample example);

    int updateByPrimaryKeySelective(AmsResourceCategory record);

    int updateByPrimaryKey(AmsResourceCategory record);
}