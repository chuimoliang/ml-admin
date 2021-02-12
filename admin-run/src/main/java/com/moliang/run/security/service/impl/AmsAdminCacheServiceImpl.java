package com.moliang.run.security.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.moliang.run.mapper.AmsAdminRoleRelationMapper;
import com.moliang.run.model.AmsAdmin;
import com.moliang.run.model.AmsAdminRoleRelation;
import com.moliang.run.model.AmsAdminRoleRelationExample;
import com.moliang.run.model.AmsResource;
import com.moliang.run.security.dao.AmsAdminRoleRelationDao;
import com.moliang.run.security.service.AmsAdminCacheService;
import com.moliang.run.security.service.AmsAdminService;
import com.moliang.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Use 缓存服务实现类
 * @Author Chui moliang
 * @Date 2020/12/19 17:31
 * @Version 1.0
 */
@Service
@Slf4j
public class AmsAdminCacheServiceImpl implements AmsAdminCacheService {

    @Autowired
    private AmsAdminService adminService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private AmsAdminRoleRelationDao adminRoleRelationDao;

    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;

    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;

    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public void delAdmin(Long adminId) {
        AmsAdmin admin = adminService.getItem(adminId);
        if (admin != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.del(key);
    }

    @Override
    public void delResourceListByRole(Long roleId) {
        AmsAdminRoleRelationExample example = new AmsAdminRoleRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<AmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation ->
                    keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        AmsAdminRoleRelationExample example = new AmsAdminRoleRelationExample();
        example.createCriteria().andRoleIdIn(roleIds);
        List<AmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation ->
                    keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByResource(Long resourceId) {

        List<Long> adminIdList = adminRoleRelationDao.getAdminIdList(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId ->
                    keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }

    }

    @Override
    public AmsAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (AmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(AmsAdmin admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin, REDIS_EXPIRE);
    }

    @Override
    public List<AmsResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<AmsResource>) redisService.get(key);
    }

    @Override
    public void setResourceList(Long adminId, List<AmsResource> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }
}
