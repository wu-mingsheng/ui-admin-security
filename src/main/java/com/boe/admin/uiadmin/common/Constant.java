package com.boe.admin.uiadmin.common;

public class Constant {
	
	 // 过期时间1h
    public static final long TOKEN_EXPIRE_TIMEMILLS = 1 * 60 * 60 * 1000;
    

    //TOKEN HEADER
    public static final String TOKEN_HEADER = "Authorization";
    // LOGIN URL
    public static final String LOGIN_URI = "/user/login";
    // LOGOUT URL
    public static final String LOGOUT_URI = "/user/logout";
    
    public static final String TOKEN_SECRET = "secret";

}
