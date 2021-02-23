package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsDeploy;
import com.moliang.run.mnt.model.SmsDeployExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsDeployMapper {
    long countByExample(SmsDeployExample example);

    int deleteByExample(SmsDeployExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsDeploy record);

    int insertSelective(SmsDeploy record);

    List<SmsDeploy> selectByExample(SmsDeployExample example);

    SmsDeploy selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsDeploy record, @Param("example") SmsDeployExample example);

    int updateByExample(@Param("record") SmsDeploy record, @Param("example") SmsDeployExample example);

    int updateByPrimaryKeySelective(SmsDeploy record);

    int updateByPrimaryKey(SmsDeploy record);
}