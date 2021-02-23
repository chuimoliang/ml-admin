package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsServer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/24 0:10
 * @Version 1.0
 */
public interface SmsServerDeployRelationDao {

    List<SmsServer> getServersByDeployId(@Param("deployId") Long deployId);

}
