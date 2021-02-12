package com.moliang.run.mapper;

import com.moliang.run.model.Web_log;
import com.moliang.run.model.Web_logExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Web_logMapper {
    long countByExample(Web_logExample example);

    int deleteByExample(Web_logExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Web_log record);

    int insertSelective(Web_log record);

    List<Web_log> selectByExample(Web_logExample example);

    Web_log selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Web_log record, @Param("example") Web_logExample example);

    int updateByExample(@Param("record") Web_log record, @Param("example") Web_logExample example);

    int updateByPrimaryKeySelective(Web_log record);

    int updateByPrimaryKey(Web_log record);
}