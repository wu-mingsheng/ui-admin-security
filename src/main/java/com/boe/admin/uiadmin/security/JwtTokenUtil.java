package com.boe.admin.uiadmin.security;

import java.time.Instant;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.boe.admin.uiadmin.common.Constant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT 工具类
 */
@Component
public class JwtTokenUtil{

  


    /**
     * 签发JWT
     */
    public String generateToken(UserDetails userDetails) {
    	 // 登陆成功生成JWT
	       return  Jwts.builder()
	                // 主题
	                .setSubject(userDetails.getUsername())
	                // 签发时间
	                .setIssuedAt(new Date())
	                // 签发者
	                .setIssuer("wms")
	                // 自定义属性 放入用户拥有权限
	                .claim("authorities", JSON.toJSONString(userDetails.getAuthorities()))
	                // 失效时间
	                .setExpiration(new Date(Instant.now().toEpochMilli() + Constant.TOKEN_EXPIRE_TIMEMILLS))
	                // 签名算法和密钥
	                .signWith(SignatureAlgorithm.HS512, Constant.TOKEN_SECRET)
	                .compact();
	       
	       

    }

    /**
     * 验证JWT
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        String username = getUsernameFromToken( token );

        return (username.equals( user.getUsername() ) && !isTokenExpired( token ));
    }

    /**
     * 获取token是否过期
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken( token );
        return expiration.before( new Date() );
    }

    /**
     * 根据token获取username
     */
    public String getUsernameFromToken(String token) {
        String username = getClaimsFromToken( token ).getSubject();
        return username;
    }

    /**
     * 获取token的过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration = getClaimsFromToken( token ).getExpiration();
        return expiration;
    }

    /**
     * 解析JWT
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey( Constant.TOKEN_SECRET )
                .parseClaimsJws( token )
                .getBody();
        return claims;
    }



}
