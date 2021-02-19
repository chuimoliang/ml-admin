package com.moliang.run.mnt.mapper;

import com.moliang.run.mnt.model.SmsItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/20 2:58
 * @Version 1.0
 */
@Mapper
public interface SmsItemDao {

    void insertGetId(SmsItem item);

}
