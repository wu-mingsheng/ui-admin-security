package com.boe.admin.uiadmin.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.boe.admin.uiadmin.po.PermissionPo;
import com.boe.admin.uiadmin.service.UserService;


/**
 * 自定义权限注解验证
 * @PreAuthorize("hasPermission('/admin/userList','sys:user:info')")
 */
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private UserService userService;
    
    
    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * @Param  authentication  用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @Param  targetUrl  请求路径
     * @Param  permission 请求路径权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        // 获取用户信息
    	User user =(User) authentication.getPrincipal();
        // 查询用户权限(这里可以将权限放入缓存中提升效率)
    	// 如果用户的角色改变了,或者角色的权限改变了,要刷新缓冲 为了支持权限的注解
    	// 如果用户的角色变了,刷新用户的权限缓冲,用户的key -- userId
    	// 如果角色的权限变了,刷新所有的缓冲,所有用户的权限都变了, 和某一个key -- userId无关了
        Set<String> permissions = new HashSet<>();
        List<PermissionPo> permissionPoList = userService.selectPermissionsByUserId(user.getId());
        for (PermissionPo per :permissionPoList) {
            permissions.add(per.getUrl());
        }
        // 权限对比
        if (permissions.contains(permission.toString())){
            return true;
        }
        return false;
    }
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}