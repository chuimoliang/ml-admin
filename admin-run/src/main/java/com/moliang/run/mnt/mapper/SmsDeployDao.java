package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsDeploy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/3/3 0:27
 * @Version 1.0
 */
@Mapper
public interface SmsDeployDao {

    void insertGetId(SmsDeploy deploy);

}
