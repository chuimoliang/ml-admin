package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsServer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/21 14:40
 * @Version 1.0
 */
@Mapper
public interface SmsServerDao {

    SmsServer getByIp(String ip);

    int insertGetId(SmsServer server);

}
