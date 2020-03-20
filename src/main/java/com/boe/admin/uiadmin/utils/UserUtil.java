package com.boe.admin.uiadmin.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.boe.admin.uiadmin.security.User;

public class UserUtil {
	
	public static Long getCurrentUserId() {
		SecurityContext context = SecurityContextHolder.getContext();
    	Authentication authentication = context.getAuthentication();
    	User user = (User) authentication.getPrincipal();
    	Long currentUserId = user.getId();
    	return currentUserId;
	}

}
