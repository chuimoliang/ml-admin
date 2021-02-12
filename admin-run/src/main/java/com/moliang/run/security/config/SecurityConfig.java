package com.moliang.run.security.config;

import com.moliang.run.security.component.*;
import com.moliang.run.security.service.AmsAdminService;
import com.moliang.run.security.service.LoadResourceMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/9 22:53
 * @Version 1.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoadResourceMap loadResourceMap;
    @Autowired
    private AmsAdminService adminService;
    @Autowired
    private SecurityFilter securityFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> adminService.loadUserByUsername(username);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        //配置白名单
        for (String url : ignoreUrls().getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        //允许跨域请求的options请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        //配置身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                //所有请求开启身份认证
                .authenticated()
                .and()
                //关闭跨站保护,关闭session
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //自定义拒绝处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(securityFilter, FilterSecurityInterceptor.class);
    }

    @Bean
    public IgnoreUrls ignoreUrls() {
        return new IgnoreUrls();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    @ConditionalOnBean(name = "loadResourceMap")
    public MyAccessDecisionManager myAccessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    @Bean
    @ConditionalOnBean(name = "loadResourceMap")
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    @Bean
    @ConditionalOnBean(name = "loadResourceMap")
    public MySecurityMetaDataSource mySecurityMetadataSource() {
        return new MySecurityMetaDataSource();
    }

}
