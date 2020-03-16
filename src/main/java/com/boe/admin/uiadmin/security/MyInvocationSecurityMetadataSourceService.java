package com.boe.admin.uiadmin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.security.access.SecurityConfig;
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
	//private static HashMap<String, Collection<ConfigAttribute>> map = null;
	//URL_ROLES_CACHE.invalidateAll();
	public static final Cache<String, Map<String, Collection<ConfigAttribute>>> URL_ROLES_CACHE = CacheBuilder.newBuilder().build();

	/**
	 * 返回请求的资源需要的角色
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
		
		Map<String, Collection<ConfigAttribute>> map = null;

		try {
			map = URL_ROLES_CACHE.get("user_roles_cache", this::loadResourceDefine);
		} catch (ExecutionException e) {
			log.error("查询全局url-roles对照表错误: {}", ExceptionUtils.getMessage(e));	
		}

		// object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String url = it.next();
			if (new AntPathRequestMatcher(url).matches(request)) {
				return map.get(url);
			}
		}

		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		loadResourceDefine();
        return null;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return true;
	}

	/**
	 * 初始化 所有资源 对应的角色
	 */
	public Map<String, Collection<ConfigAttribute>> loadResourceDefine() {
		Map<String, Collection<ConfigAttribute>> map = new HashMap<>(16);
		// 权限资源 和 角色对应的表 也就是 角色权限 中间表

		List<PermissionPo> permissionList = permissionMapper.selectList(null);
		// 某个资源 可以被哪些角色访问
		for (PermissionPo permissionPo : permissionList) {// 所有的权限
			List<ConfigAttribute> roles = new ArrayList<>();

			Long permissionId = permissionPo.getId();
			LambdaQueryWrapper<RolePermissionPo> rolePermissionQuery = Wrappers.lambdaQuery();
			rolePermissionQuery.eq(RolePermissionPo::getPermissionId, permissionId);
			List<RolePermissionPo> rolePermissionList = rolePermissionMapper.selectList(rolePermissionQuery);
			for (RolePermissionPo rolePermissionPo : rolePermissionList) {
				Long roleId = rolePermissionPo.getRoleId();
				RolePo rolePo = roleMapper.selectById(roleId);
				String roleName = rolePo.getName();
				ConfigAttribute role = new SecurityConfig(roleName);
				roles.add(role);
			}

			String url = permissionPo.getUrl();
			map.put(url, roles);

		}
		
		return map;
	}

}
