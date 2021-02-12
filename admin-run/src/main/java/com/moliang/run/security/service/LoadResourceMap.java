package com.moliang.run.security.service;

import com.moliang.run.mapper.AmsResourceMapper;
import com.moliang.run.model.AmsResource;
import com.moliang.run.model.AmsResourceExample;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Use 加载资源ANT通配符和资源对应MAP
 * @Author Chui moliang
 * @Date 2021/2/9 21:15
 * @Version 1.0
 */
@Component
@RequiredArgsConstructor
public class LoadResourceMap {

    private final AmsResourceMapper resourceMapper;

    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<AmsResource> resourceList = resourceMapper.selectByExample(new AmsResourceExample());
        for (AmsResource resource : resourceList) {
            map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
        }
        return map;
    }
}
