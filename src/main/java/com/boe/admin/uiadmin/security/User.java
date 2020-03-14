package com.boe.admin.uiadmin.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * 上面的 User 类实现了 UserDetails 接口，该接口是实现Spring Security 认证信息的核心接口。
 * 其中 getUsername 方法为 UserDetails 接口 的方法，这个方法返回 username，也可以是其他的用户信息，例如手机号、邮箱等。
 * getAuthorities() 方法返回的是该用户设置的权限信息，在本实例中，从数据库取出用户的所有角色信息，权限信息也可以是用户的其他信息，不一定是角色信息。
 * 另外需要读取密码，最后几个方法一般情况下都返回 true，也可以根据自己的需求进行业务判断。 
 *
 */


public class User implements UserDetails , Serializable {

    private Long id;
    private String username;
    private String password;

    private List<Role> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    /**
     * 用户账号是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户账号是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}