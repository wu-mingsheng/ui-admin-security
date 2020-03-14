package com.boe.admin.uiadmin.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * Role 类实现了 GrantedAuthority 接口，并重写 getAuthority() 方法。权限点可以为任何字符串，不一定是非要用角色名。
 * 所有的Authentication实现类都保存了一个GrantedAuthority列表，其表示用户所具有的权限。
 * GrantedAuthority是通过AuthenticationManager设置到Authentication对象中的，
 * 然后AccessDecisionManager将从Authentication中获取用户所具有的GrantedAuthority来鉴定用户是否具有访问对应资源的权限。
 *
 */

public class Role implements GrantedAuthority {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

}
