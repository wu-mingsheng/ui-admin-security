package com.boe.admin.uiadmin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
	 */
	private static HashMap<String, Collection<ConfigAttribute>> map = null;

	/**
	 * 返回请求的资源需要的角色
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
		if (null == map) {
			loadResourceDefine();
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
	public void loadResourceDefine() {
		map = new HashMap<>(16);
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
	}

}
