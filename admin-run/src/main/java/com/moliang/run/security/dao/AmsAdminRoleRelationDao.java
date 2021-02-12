package com.moliang.run.security.dao;

import com.moliang.run.model.AmsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/10 16:51
 * @Version 1.0
 */
@Mapper
public interface AmsAdminRoleRelationDao {
    /**
     * 获取用户所有可访问资源
     */
    List<AmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
