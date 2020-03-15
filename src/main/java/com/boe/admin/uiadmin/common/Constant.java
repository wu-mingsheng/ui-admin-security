package com.boe.admin.uiadmin.common;

public class Constant {
	
	 // 过期时间5分钟
    public static final long TOKEN_EXPIRE_TIME = 5*60*1000;
    

    //TOKEN HEADER
    public static final String TOKEN_HEADER = "Authorization";
    // LOGIN URL
    public static final String LOGIN_URI = "/user/login";
    // LOGOUT URL
    public static final String LOGOUT_URI = "/user/logout";
    
    public static final String TOKEN_SECRET = "secret";

}
