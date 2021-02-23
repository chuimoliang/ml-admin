package com.moliang.run.mnt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Use 注册websocket
 * @Author Chui moliang
 * @Date 2021/2/24 1:55
 * @Version 1.0
 */
@Slf4j
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        log.info("spring容器注入ServerEndpointExporter对象...");
        return new ServerEndpointExporter();
    }

}
