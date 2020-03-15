package com.boe.admin.uiadmin.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boe.admin.uiadmin.po.PermissionPo;
import com.boe.admin.uiadmin.service.UserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CacheUtil {
	
	
	private static UserService userService;
	
	
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}




	public static final LoadingCache<Long, Set<String>> USERID_ALLPERMISSION = CacheBuilder
            .newBuilder()//CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
            .concurrencyLevel(1)//设置并发级别为1，并发级别是指可以同时写缓存的线程数
            .expireAfterWrite(1, TimeUnit.DAYS)//设置写缓存后过期
            .initialCapacity(10)//设置缓存容器的初始容量为10
            .maximumSize(1000)//设置缓存最大容量为1000，超过1000之后就会按照LRU最近虽少使用算法来移除缓存项
            .recordStats()//设置要统计缓存的命中率
            .removalListener(new RemovalListener<Object, Object>() {//设置缓存的移除通知
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    log.info(notification.getKey() + " was removed, cause is " + notification.getCause());
                }
            })
            .build(//build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存

                new CacheLoader<Long, Set<String>>() {
                    @Override
                    public Set<String> load(Long key) throws Exception {
                    	Set<String> permissions = new HashSet<>();
                        List<PermissionPo> permissionPoList = userService.selectPermissionsByUserId(key);
                        for (PermissionPo per :permissionPoList) {
                            permissions.add(per.getUrl());
                        }
                        return permissions;
                    }

            });

}
