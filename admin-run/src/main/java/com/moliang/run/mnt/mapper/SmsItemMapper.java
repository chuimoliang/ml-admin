package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsItem;
import com.moliang.run.mnt.model.SmsItemExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsItemMapper {
    long countByExample(SmsItemExample example);

    int deleteByExample(SmsItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsItem record);

    int insertSelective(SmsItem record);

    List<SmsItem> selectByExample(SmsItemExample example);

    SmsItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsItem record, @Param("example") SmsItemExample example);

    int updateByExample(@Param("record") SmsItem record, @Param("example") SmsItemExample example);

    int updateByPrimaryKeySelective(SmsItem record);

    int updateByPrimaryKey(SmsItem record);
}