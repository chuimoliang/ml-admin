package com.moliang.run.security.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.moliang.exception.ApiException;
import com.moliang.run.mapper.AmsAdminLoginLogMapper;
import com.moliang.run.mapper.AmsAdminMapper;
import com.moliang.run.mapper.AmsAdminRoleRelationMapper;
import com.moliang.run.mapper.AmsRoleMapper;
import com.moliang.run.model.*;
import com.moliang.run.security.component.AdminDetails;
import com.moliang.run.security.dao.AmsAdminDao;
import com.moliang.run.security.dao.AmsAdminRoleRelationDao;
import com.moliang.run.security.param.AmsAdminParam;
import com.moliang.run.security.param.AmsUpdateAdminPasswordParam;
import com.moliang.run.security.service.AmsAdminCacheService;
import com.moliang.run.security.service.AmsAdminService;
import com.moliang.utils.JwtTokenUtil;
import com.moliang.utils.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Use
 * @Author Chui moliang
 * @Date 2021/2/9 23:33
 * @Version 1.0
 */
@Service
@Slf4j
public class AmsAdminServiceImpl implements AmsAdminService {

    @Autowired
    private AmsAdminCacheService adminCacheService;
    @Autowired
    private AmsAdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private AmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private AmsRoleMapper roleMapper;
    @Autowired
    private AmsAdminMapper adminMapper;
    @Autowired
    private AmsAdminDao adminDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AmsAdminLoginLogMapper loginLogMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        //获取用户信息
        AmsAdmin admin = getAdminByUsername(userName);
        if (admin != null) {
            List<AmsResource> resourceList = getResourceList(admin.getId());
            return new AdminDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    public List<AmsResource> getResourceList(Long adminId) {
        List<AmsResource> resourceList = adminCacheService.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            log.info("成功从缓存中读取到资源列表");
            return  resourceList;
        }
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            adminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public List<AmsMenu> getMenuList(Long adminId) {
        return adminDao.getMenuList(adminId);
    }

    @Override
    public int updatePassword(AmsUpdateAdminPasswordParam param) {
        log.info("password updating......");
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        AmsAdminExample example = new AmsAdminExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<AmsAdmin> adminList = adminMapper.selectByExample(example);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        AmsAdmin umsAdmin = adminList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),umsAdmin.getPassword())){
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        adminMapper.updateByPrimaryKey(umsAdmin);
        adminCacheService.delAdmin(umsAdmin.getId());
        return 1;
    }

    public AmsAdmin getAdminByUsername(String username) {
        AmsAdmin admin = adminCacheService.getAdmin(username);
        if(admin != null) {
            log.info("成功从缓存中加载出管理员");
            return admin;
        }
        AmsAdminExample example = new AmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<AmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            adminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public AmsAdmin register(AmsAdminParam adminParam) {
        AmsAdmin admin = new AmsAdmin();
        BeanUtils.copyProperties(adminParam, admin);
        admin.setCreateTime(new Date());
        admin.setStatus(1);
        //查询是否有相同用户名的用户
        AmsAdminExample example = new AmsAdminExample();
        example.createCriteria().andUsernameEqualTo(admin.getUsername());
        List<AmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodePassword);
        adminMapper.insert(admin);
        return admin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            log.info("password : " + password);
            log.info("userDetails.getPassword() :" + userDetails.getPassword());
            PasswordEncoder p = new BCryptPasswordEncoder();
            log.info(String.valueOf(p.matches(password,"$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG")));
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                log.info(passwordEncoder.getClass().toString());
                throw new ApiException("密码不正确");
            }
            if(!userDetails.isEnabled()){
                throw new ApiException("账号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
            log.info("登陆成功");
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        AmsAdmin admin = getAdminByUsername(username);
        if(admin==null) return;
        AmsAdminLoginLog loginLog = new AmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(RequestHandler.getRequestIp(request));
        loginLogMapper.insert(loginLog);
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public AmsAdmin getItem(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        AmsAdminExample example = new AmsAdminExample();
        AmsAdminExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
            example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
        }
        return adminDao.selectByExample(example);
    }

    @Override
    public int update(Long id, AmsAdmin admin) {
        admin.setId(id);
        AmsAdmin oldAdmin = adminMapper.selectByPrimaryKey(id);
        if(StrUtil.isEmpty(admin.getPassword())){
            admin.setPassword(null);
        }else{
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        int count = adminMapper.updateByPrimaryKeySelective(admin);
        adminCacheService.delAdmin(id);
        return count;
    }

    @Override
    public int delete(Long id) {
        adminCacheService.delAdmin(id);
        int count = adminMapper.deleteByPrimaryKey(id);
        adminCacheService.delResourceList(id);
        return count;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        AmsAdminRoleRelationExample adminRoleRelationExample = new AmsAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<AmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                AmsAdminRoleRelation roleRelation = new AmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            //adminRoleRelationMapper.insertList(list);
        }
        adminCacheService.delResourceList(adminId);
        return count;
    }

    @Override
    public List<AmsRole> getRoleList(Long adminId) {
        return adminDao.getRoleList(adminId);
    }
}
