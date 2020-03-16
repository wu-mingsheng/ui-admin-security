package com.boe.admin.uiadmin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.DigestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * @EnableWebSecurity注解以及WebSecurityConfigurerAdapter一起配合提供基于web的security。 自定义类
 * 继承了WebSecurityConfigurerAdapter来重写了一些方法来指定一些特定的Web安全设置。
 *
 */
@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// 校验用户
		auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
			// 对密码进行加密
			@Override
			public String encode(CharSequence charSequence) {
				log.info("对密码进行加密: {}", charSequence.toString());
				return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
			}

			// 对密码进行判断匹配
			@Override
			public boolean matches(CharSequence charSequence, String s) {
				String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
				boolean res = s.equals(encode);
				return res;
			}
		});

	}
	
	
	/**跨域问题*/
	/**
	 * 跨域过滤器最先执行
	 * 这里定义的过滤器第二执行
	 * security过滤器最后执行
	 *
	 */
	@Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


	@Override
	protected void configure(HttpSecurity http) throws Exception {

			http.cors().and().csrf().disable()
				// 因为使用JWT，所以不需要HttpSession
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				// OPTIONS请求全部放行
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// 登录接口放行
				.antMatchers("/user/login").permitAll()
				// 其他接口全部验证
				.anyRequest().authenticated();

		// 使用自定义的 Token过滤器 验证请求的Token是否合法
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();

	}

	@Bean
	public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }
    


}
