package com.moliang.run.security.component;

import com.moliang.run.model.AmsAdmin;
import com.moliang.run.model.AmsResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Use SpringSecurity需要的用户详情
 * @Author Chui moliang
 * @Date 2021/2/9 20:05
 * @Version 1.0
 */
public class AdminDetails implements UserDetails {

    private AmsAdmin amsAdmin;

    private List<AmsResource> resourceList;

    public AdminDetails(AmsAdmin amsAdmin,List<AmsResource> resourceList) {
        this.amsAdmin = amsAdmin;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        if(!isEnabled()) return null;
        return resourceList.stream()
                .map(role ->new SimpleGrantedAuthority(role.getId()+":"+role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return amsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return amsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        System.out.println(amsAdmin.getStatus());
        return amsAdmin.getStatus().equals(1);
    }
}
