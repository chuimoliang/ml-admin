package com.moliang.run.mapper;

import com.moliang.run.log.model.WebLog;
import com.moliang.run.log.model.WebLogExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Async;

@Mapper
public interface WebLogMapper {
    long countByExample(WebLogExample example);

    int deleteByExample(WebLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WebLog record);

    int insertSelective(WebLog record);

    List<WebLog> selectByExample(WebLogExample example);

    WebLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WebLog record, @Param("example") WebLogExample example);

    int updateByExample(@Param("record") WebLog record, @Param("example") WebLogExample example);

    int updateByPrimaryKeySelective(WebLog record);

    int updateByPrimaryKey(WebLog record);
}