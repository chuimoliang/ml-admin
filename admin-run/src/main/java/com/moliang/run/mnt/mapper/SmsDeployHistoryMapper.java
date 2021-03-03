package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsDeployHistory;
import com.moliang.run.mnt.model.SmsDeployHistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsDeployHistoryMapper {
    long countByExample(SmsDeployHistoryExample example);

    int deleteByExample(SmsDeployHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsDeployHistory record);

    int insertSelective(SmsDeployHistory record);

    List<SmsDeployHistory> selectByExample(SmsDeployHistoryExample example);

    SmsDeployHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsDeployHistory record, @Param("example") SmsDeployHistoryExample example);

    int updateByExample(@Param("record") SmsDeployHistory record, @Param("example") SmsDeployHistoryExample example);

    int updateByPrimaryKeySelective(SmsDeployHistory record);

    int updateByPrimaryKey(SmsDeployHistory record);
}