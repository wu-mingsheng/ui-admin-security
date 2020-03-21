package com.boe.admin.uiadmin.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.boe.admin.uiadmin.dao.PermissionMapper;
import com.boe.admin.uiadmin.dao.RoleMapper;
/**
 * 
 * MyInvocationSecurityMetadataSourceService 类实现了 FilterInvocationSecurityMetadataSource
 * FilterInvocationSecurityMetadataSource 的作用是用来储存请求与权限的对应关系。
 * FilterInvocationSecurityMetadataSource接口有3个方法：
 * boolean supports(Class<?> clazz)：指示该类是否能够为指定的方法调用或Web请求提供ConfigAttributes。
 * Collection getAllConfigAttributes()：Spring容器启动时自动调用, 一般把所有请求与权限的对应关系也要在这个方法里初始化, 保存在一个属性变量里。
 * Collection getAttributes(Object object)：当接收到一个http请求时, filterSecurityInterceptor会调用的方法. 参数object是一个包含url信息的HttpServletRequest实例. 这个方法要返回请求该url所需要的所有权限集合。
 *
 */
import com.boe.admin.uiadmin.dao.RolePermissionMapper;
import com.boe.admin.uiadmin.po.PermissionPo;
import com.boe.admin.uiadmin.po.RolePermissionPo;
import com.boe.admin.uiadmin.po.RolePo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
	 * 定义的缓冲 : 访问路径(url=>也就是权限),需要那些角色,
	 * 这个是脱离注解数据库单独存在的吗?
	 * 和注解没关系,为了支持数据库配置的
	 * 默认每一个url都需要那些role,如果当前用户没有这个role,即使没有注解说明要这个role,也是不允许访问的
	 * 如果设置了role-permission的关系,要刷新缓冲
	 * url-roles 如果添加了新的url-roles, 要添加新的key
	 * 如果修改了原来的url-roles关系,刷新这个key,也就是url
	 */
	public static final Cache<String, Collection<ConfigAttribute>> URI_ROLES_CACHE = CacheBuilder.newBuilder().build();

	/**
	 * 返回请求的资源需要的角色,每次请求的时候会调用
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
		String uri = request.getRequestURI();
		log.info("==== request uri : [{}]", uri);
		try {
			Collection<ConfigAttribute> collection = URI_ROLES_CACHE.get(uri, () -> {
				Set<ConfigAttribute> roles = Sets.newHashSet();
				LambdaQueryWrapper<PermissionPo> uriQuery = Wrappers.lambdaQuery();
				uriQuery.eq(PermissionPo::getUrl, uri);
				PermissionPo permissionPo = permissionMapper.selectOne(uriQuery);
				if(null == permissionPo) {
					return roles;
				}
				Long permissionId = permissionPo.getId();
				LambdaQueryWrapper<RolePermissionPo> permissionIdQuery= Wrappers.lambdaQuery();
				permissionIdQuery.eq(RolePermissionPo::getPermissionId, permissionId);
				List<RolePermissionPo> rolePermissionList = rolePermissionMapper.selectList(permissionIdQuery);
				
				for (RolePermissionPo rolePermissionPo : rolePermissionList) {
					Long roleId = rolePermissionPo.getRoleId();
					RolePo rolePo = roleMapper.selectById(roleId);
					String roleName = rolePo.getName();
					ConfigAttribute role = new SecurityConfig(roleName);
					roles.add(role);
				}
				
				return roles;
			});
			log.info(" === 访问当前资源url需要的角色{}", collection);
			return collection;
			
		} catch (Exception e) {
			log.error("获取用户请求uri对应的role失败, 默认启用请求放行: {}", ExceptionUtils.getMessage(e));
		}
		
		
		return null;

	}

	
	/**
	 * 方法如果返回了所有定义的权限资源，Spring Security会在启动时校验每个ConfigAttribute是否配置正确，不需要校验直接返回null
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return true;
	}



}
