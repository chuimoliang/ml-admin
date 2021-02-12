package com.moliang.run.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Use 获取security不拦截的url名单
 * @Author Chui moliang
 * @Date 2021/2/9 19:55
 * @Version 1.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrls {

    private List<String> urls = new ArrayList<>();

}
